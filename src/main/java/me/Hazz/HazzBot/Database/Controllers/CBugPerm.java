package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OBugPerm;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CBugPerm {

    private static String getTableName(String GuildID) {
        switch (GuildID) {
            case "345602641008525312":
                return "PixelmonBugPerm";
            case "631273365612789770":
                return "VanillaBugPerm";
            case "506172322332540938":
                return "TestBugPerm";
            default:
                return "Unknown";
        }
    }

    public static OBugPerm findByID(long MessageID, Guild guild) {
        OBugPerm bugperm = new OBugPerm();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE MessageID = ? ", MessageID)) {
            if (rs.next()) {
                bugperm = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bugperm;
    }

    private static OBugPerm fillRecord(ResultSet resultset) throws SQLException {
        OBugPerm bugperm = new OBugPerm();
        bugperm.id = resultset.getInt("id");
        bugperm.Content = resultset.getString("Content");
        bugperm.Author = resultset.getLong("Author");
        bugperm.Timestamp = resultset.getLong("TimeStamp");
        bugperm.MessageID = resultset.getLong("MessageID");
        bugperm.AuthorURL = resultset.getString("AuthorURL");
        return bugperm;
    }

    public static void addMessage(Message message, String content) {
        try {
            if (message.getChannelType() != ChannelType.TEXT) {
                return;
            }

            OBugPerm bugperm = findByID(message.getIdLong(), message.getGuild());
            bugperm.Content = content.replaceAll("[”“„»«‘’…•—§‰°₽¥€¢£₩¿]", "\'").replace("_", "\\_");
            bugperm.Author = message.getAuthor().getIdLong();
            bugperm.Timestamp = System.currentTimeMillis();
            bugperm.MessageID = message.getIdLong();
            if (message.getAuthor().getAvatarUrl() == null) {
                bugperm.AuthorURL = "http://i.hazzaaa.com/default.png";
            } else {
                bugperm.AuthorURL = message.getAuthor().getAvatarUrl();
            }

            insert(bugperm, message.getGuild());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void insert(OBugPerm bugperm, Guild guild) {
        try {
            String GuildID = guild.getId();
            bugperm.id = WebDb.get().insert("INSERT INTO " + getTableName(GuildID) + "(Content, Author, Timestamp, MessageID, AuthorURL) VALUES (?,?,?,?,?)",
                    bugperm.Content, bugperm.Author, bugperm.Timestamp, bugperm.MessageID, bugperm.AuthorURL);
        } catch (Exception e) {
            return;
        }
    }

    public static void update(OBugPerm record, Guild guild){
        if (record.id == 0){
            insert(record, guild);
            return;
        }
        try{
            String GuildID = guild.getId();
            WebDb.get().query("UPDATE " + getTableName(GuildID) + " SET Content = ?, Author = ?, Timestamp = ?, MessageID = ?, AuthorURL = ? WHERE id = ?", record.Content, record.Author, record.Timestamp, record.MessageID, record.AuthorURL, record.id);
        } catch (Exception e) {
            return;
        }
    }
}