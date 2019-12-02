package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OTodo;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CTodo {

    private static String getTableName(String GuildID) {
        switch (GuildID) {
            case "345602641008525312":
                return "PixelmonTodo";
            case "631273365612789770":
                return "VanillaTodo";
            case "506172322332540938":
                return "TestTodo";
            default:
                return "Unknown";
        }
    }

    public static OTodo findByID(long MessageID, Guild guild) {
        OTodo todo = new OTodo();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE MessageID = ? ", MessageID)) {
            if (rs.next()) {
                todo = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todo;
    }

    private static OTodo fillRecord(ResultSet resultset) throws SQLException {
        OTodo todo = new OTodo();
        todo.id = resultset.getInt("id");
        todo.Content = resultset.getString("Content");
        todo.Author = resultset.getLong("Author");
        todo.Timestamp = resultset.getLong("TimeStamp");
        todo.MessageID = resultset.getLong("MessageID");
        todo.AuthorURL = resultset.getString("AuthorURL");
        return todo;
    }

    public static void addMessage(Message message, String content) {
        try {
            if (message.getChannelType() != ChannelType.TEXT) {
                return;
            }

            OTodo todo = findByID(message.getIdLong(), message.getGuild());
            todo.Content = content.replaceAll("[”“„»«‘’…•—§‰°₽¥€¢£₩¿]", "\'").replace("_", "\\_");
            todo.Author = message.getAuthor().getIdLong();
            todo.Timestamp = System.currentTimeMillis();
            todo.MessageID = message.getIdLong();
            if (message.getAuthor().getAvatarUrl() == null) {
                todo.AuthorURL = "http://i.hazzaaa.com/default.png";
            } else {
                todo.AuthorURL = message.getAuthor().getAvatarUrl();
            }

            insert(todo, message.getGuild());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void insert(OTodo todo, Guild guild) {
        try {
            String GuildID = guild.getId();
            todo.id = WebDb.get().insert("INSERT INTO " + getTableName(GuildID) + "(Content, Author, Timestamp, MessageID, AuthorURL) VALUES (?,?,?,?,?)",
                    todo.Content, todo.Author, todo.Timestamp, todo.MessageID, todo.AuthorURL);
        } catch (Exception e) {
            return;
        }
    }

    public static void update(OTodo record, Guild guild){
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