package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OSuggestions;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CSuggestions {

    private static String getTableName(String GuildID) {
        switch (GuildID) {
            case "374350672981655571":
                return "PixelmonMainSuggest";
            case "345602641008525312":
                return "PixelmonStaffSuggest";
            case "631273255180959767":
                return "VanillaMainSuggest";
            case "631273365612789770":
                return "VanillaStaffSuggest";
            case "506172322332540938":
                return "TestSuggest";
            default:
                return "Unknown";
        }
    }

    public static OSuggestions findByID(long MessageID, Guild guild) {
        OSuggestions suggestion = new OSuggestions();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE MessageID = ? ", MessageID)) {
            if (rs.next()) {
                suggestion = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            return null;
        }
        return suggestion;
    }

    public static OSuggestions findByid(int id, Guild guild) {
        OSuggestions suggestion = new OSuggestions();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE id = ?", id)) {
            if (rs.next()) {
                suggestion = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            return null;
        }
        return suggestion;
    }

    private static OSuggestions fillRecord(ResultSet resultset) throws SQLException {
        OSuggestions suggestion = new OSuggestions();
        suggestion.id = resultset.getInt("id");
        suggestion.Content = resultset.getString("Content");
        suggestion.Author = resultset.getLong("Author");
        suggestion.Timestamp = resultset.getLong("TimeStamp");
        suggestion.MessageID = resultset.getLong("MessageID");
        suggestion.AuthorURL = resultset.getString("AuthorURL");
        return suggestion;
    }

    public static void addMessage(Message message, String content) {
        try {
            if (message.getChannelType() != ChannelType.TEXT) {
                return;
            }
            OSuggestions suggestions = findByID(message.getIdLong(), message.getGuild());
            suggestions.Content = content.replaceAll("[”“„»«‘’…•—§‰°₽¥€¢£₩¿]", "\'").replace("_", "\\_");
            suggestions.Author = message.getAuthor().getIdLong();
            suggestions.Timestamp = System.currentTimeMillis();
            suggestions.MessageID = message.getIdLong();
            if (message.getAuthor().getAvatarUrl() == null) {
                suggestions.AuthorURL = "http://i.hazzaaa.com/default.png";
            } else {
                suggestions.AuthorURL = message.getAuthor().getAvatarUrl();
            }

            insert(suggestions, message.getGuild());

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void insert(OSuggestions suggestion, Guild guild) {
        try {
            String GuildID = guild.getId();
            suggestion.id = WebDb.get().insert("INSERT INTO " + getTableName(GuildID) + "(Content, Author, Timestamp, MessageID, AuthorURL) VALUES (?,?,?,?,?)",
                    suggestion.Content, suggestion.Author, suggestion.Timestamp, suggestion.MessageID, suggestion.AuthorURL);
        } catch (Exception e) {
            return;
        }
    }

    public static void update(OSuggestions record, Guild guild) {
        if (record.id == 0) {
            insert(record, guild);
            return;
        }
        try {
            String GuildID = guild.getId();
            WebDb.get().query("UPDATE " + getTableName(GuildID) + " SET Content = ?, Author = ?, Timestamp = ?, MessageID = ?, AuthorURL = ? WHERE id = ?", record.Content, record.Author, record.Timestamp, record.MessageID, record.AuthorURL, record.id);
        } catch (Exception e) {
            return;
        }
    }
}