package me.Hazz.HazzBot.Database.Controllers;

import com.mashape.unirest.http.Unirest;
import me.Hazz.HazzBot.Database.Objects.OMessageLogger;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CMessageLogger {

    public static OMessageLogger findByID(long messageID) {
        OMessageLogger message = new OMessageLogger();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM MessageLogger WHERE MessageID = ? ", messageID)) {
            if (rs.next()) {
                message = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    private static OMessageLogger fillRecord(ResultSet resultset) throws SQLException {
        OMessageLogger message = new OMessageLogger();
        message.id = resultset.getInt("id");
        message.MessageID = resultset.getLong("MessageID");
        message.Guild = resultset.getLong("Guild");
        message.Author = resultset.getLong("Author");
        message.ImageName = resultset.getString("ImageName");
        message.Content = resultset.getString("Content");
        message.MessageDate = resultset.getLong("MessageDate");
        message.ChannelID = resultset.getLong("ChannelID");
        message.ChannelName = resultset.getString("ChannelName");
        return message;
    }

    public static void editMessage(Message m) {
        addMessage(m);
    }

    public static void addMessage(Message m) {
        Thread t = new Thread(() -> {
            try {
                if (m.getChannelType() != ChannelType.TEXT) {
                    return;
                }
                if (m.getGuild() == null || m.getGuild().getId() == null || m.getGuild().getId().isEmpty()) {
                    return;
                }
                if (m.getAuthor() == null || m.getAuthor().getId() == null || m.getAuthor().getId().isEmpty()) {
                    return;
                }

                OMessageLogger message = findByID(m.getIdLong());
                message.Author = m.getAuthor().getIdLong();
                message.MessageID = m.getIdLong();
                message.Guild = m.getGuild().getIdLong();

                List<Message.Attachment> attachments = m.getAttachments();
                if (attachments.isEmpty()) {
                    message.ImageName = "";
                } else {
                    String imagesNames = "";
                    int i = 0;
                    for (Message.Attachment a : m.getAttachments()) {
                        if (download(a.getUrl(), m.getId(), i)) {
                            try {
                                if (!imagesNames.isEmpty()) {
                                    imagesNames += ",";
                                }
                                String extension = a.getUrl().substring(a.getUrl().lastIndexOf("."));
                                imagesNames += m.getId() + "_" + i + extension;
                                i++;
                            } catch (Throwable t1) {
                                t1.printStackTrace();
                            }
                        }
                    }
                    message.ImageName = imagesNames;
                }

                message.ChannelID = m.getChannel().getIdLong();
                message.ChannelName = m.getChannel().getName();
                message.Content = m.getContentRaw().replaceAll("[”“„»«‘’…•—§‰°₽¥€¢£₩¿]", "\'").replace("_", "\\_");
                message.MessageDate = System.currentTimeMillis();
                updateOrInsert(message);
            } catch (Throwable t1) {
                t1.printStackTrace();
            }
        });
        t.setName("Message Logger");
        t.start();
    }


    private static boolean download(String url, String messageId, int file) {
        try {
            String extension = url.substring(url.lastIndexOf("."));
            InputStream in = Unirest.get(url).asBinary().getRawBody();

            File dir = new File("downloads/");
            if (dir.exists())
                dir.mkdir();
            File f = new File("downloads/" + messageId + "_" + file + extension);
            FileUtils.copyInputStreamToFile(in, f);
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }


    private static void insert(OMessageLogger message) {
        try {
            message.id = WebDb.get().insert("INSERT INTO MessageLogger(MessageID,Guild,Author,Content,MessageDate,ChannelID,ChannelName) VALUES (?,?,?,?,?,?,?)",
                    message.MessageID, message.Guild, message.Author, message.Content, message.MessageDate, message.ChannelID, message.ChannelName);
        } catch (Exception e) {
            return;
        }
    }

    public static void updateOrInsert(OMessageLogger record){
        if (record.id == 0){
            insert(record);
            return;
        }
        try{
            WebDb.get().query("UPDATE MessageLogger SET MessageID = ?, Guild = ?, Author = ?, Content = ?, MessageDate = ?, ChannelID = ?, ChannelName = ? WHERE id = ?", record.MessageID, record.Guild, record.Author, record.Content, record.MessageDate, record.ChannelID, record.ChannelName, record.id);
        } catch (Exception e) {
            return;
        }
    }
}