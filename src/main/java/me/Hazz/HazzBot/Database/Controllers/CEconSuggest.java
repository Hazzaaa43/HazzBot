package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OEconSuggest;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CEconSuggest {

    public static OEconSuggest findByID(long MessageID) {
        OEconSuggest suggestion = new OEconSuggest();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM EconSuggestions WHERE MessageID = ? ", MessageID)) {
            if (rs.next()) {
                suggestion = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suggestion;
    }

    public static OEconSuggest findByid(int id) {
        OEconSuggest suggestion = new OEconSuggest();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM EconSuggestions WHERE id = ?", id)) {
            if (rs.next()) {
                suggestion = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suggestion;
    }

    private static OEconSuggest fillRecord(ResultSet resultset) throws SQLException {
        OEconSuggest suggestion = new OEconSuggest();
        suggestion.id = resultset.getInt("id");
        suggestion.Content = resultset.getString("Content");
        suggestion.Author = resultset.getLong("Author");
        suggestion.Timestamp = resultset.getLong("TimeStamp");
        suggestion.MessageID = resultset.getLong("MessageID");
        suggestion.AuthorURL = resultset.getString("AuthorURL");
        suggestion.StaffContent = resultset.getString("StaffContent");
        suggestion.StaffAuthor = resultset.getLong("StaffAuthor");
        suggestion.StaffTimestamp = resultset.getLong("StaffTimeStamp");
        return suggestion;
    }

    public static void addMessage(Message message, String content) {
        try {
            if (message.getChannelType() != ChannelType.TEXT) {
                return;
            }

            OEconSuggest suggestions = findByID(message.getIdLong());
            suggestions.Content = content.replaceAll("[”“„»«‘’…•—§‰°₽¥€¢£₩¿]", "\'").replace("_", "\\_");
            suggestions.Author = message.getAuthor().getIdLong();
            suggestions.Timestamp = System.currentTimeMillis();
            suggestions.MessageID = message.getIdLong();
            if (message.getAuthor().getAvatarUrl() == null) {
                suggestions.AuthorURL = "http://i.hazzaaa.com/default.png";
            } else {
                suggestions.AuthorURL = message.getAuthor().getAvatarUrl();
            }

            insert(suggestions);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void insert(OEconSuggest suggestion) {
        try {
            suggestion.id = WebDb.get().insert("INSERT INTO EconSuggestions(Content, Author, Timestamp, MessageID, AuthorURL, StaffContent, StaffAuthor, StaffTimestamp) VALUES (?,?,?,?,?,?,?,?)",
                    suggestion.Content, suggestion.Author, suggestion.Timestamp, suggestion.MessageID, suggestion.AuthorURL, suggestion.StaffContent, suggestion.StaffAuthor, suggestion.StaffTimestamp);
        } catch (Exception e) {
            return;
        }
    }

    public static void update(OEconSuggest record){
        if (record.id == 0){
            insert(record);
            return;
        }
        try{
            WebDb.get().query("UPDATE EconSuggestions SET Content = ?, Author = ?, Timestamp = ?, MessageID = ?, AuthorURL = ?, StaffContent = ?, StaffAuthor = ?, StaffTimeStamp = ? WHERE id = ?", record.Content, record.Author, record.Timestamp, record.MessageID, record.AuthorURL, record.StaffContent, record.StaffAuthor, record.StaffTimestamp, record.id);
        } catch (Exception e) {
            return;
        }
    }
}