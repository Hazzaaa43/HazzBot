package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OUser;
import me.Hazz.HazzBot.Variables.WebDb;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CUser {

    public static OUser findBy(long userId) {
        OUser user = new OUser();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM Carrots WHERE DiscordID = ? ", userId)) {
            if (rs.next()) {
                user = fillRecord(rs);
            } else {
                user.DiscordID = userId;
                insert(user);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user.DiscordID == 0){
            user.DiscordID = userId;
        }
        return user;
    }

    private static OUser fillRecord(ResultSet resultset) throws SQLException {
        OUser user = new OUser();
        user.id = resultset.getInt("id");
        user.DiscordID = resultset.getLong("DiscordID");
        user.Carrots = resultset.getLong("Carrots");
        return user;
    }

    private static void insert(OUser user) {
        if (user.id > 0) {
            update(user);
            return;
        }
        try {
            user.id = WebDb.get().insert("INSERT INTO Carrots(DiscordID,Carrots) VALUES (?,?)", user.DiscordID, user.Carrots);
        } catch (Exception e) {
            return;
        }
    }

    public static void update(OUser user) {
        if(user.DiscordID == 0) return;
        if (user.id == 0) {
            insert(user);
            return;
        }
        try {
            WebDb.get().query("UPDATE  Carrots SET Carrots= ? WHERE id = ? ", user.Carrots, user.id);
        } catch (Exception e) {
            return;
        }
    }
}