package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OBeg;
import me.Hazz.HazzBot.Variables.WebDb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class CBeg{

    public static OBeg findBy(long userId) {
        OBeg beg = new OBeg();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM CarrotBeg WHERE DiscordID = ? ", userId)) {
            if (rs.next()) {
                beg = fillRecord(rs);
            } else {
                beg.DiscordID = userId;
                insert(beg);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(beg.DiscordID == 0){
            beg.DiscordID = userId;
        }
        return beg;
    }

    private static OBeg fillRecord(ResultSet resultset) throws SQLException {
        OBeg beg = new OBeg();
        beg.id = resultset.getInt("id");
        beg.DiscordID = resultset.getLong("DiscordID");
        beg.Timestamp = resultset.getLong("Timestamp");
        return beg;
    }

    private static void insert(OBeg beg) {
        if (beg.id > 0) {
            update(beg);
            return;
        }
        try {
            beg.Timestamp = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(6);
            beg.id = WebDb.get().insert("INSERT INTO CarrotBeg(DiscordID,Timestamp) VALUES (?,?)", beg.DiscordID, beg.Timestamp);
        } catch (Exception e) {
            return;
        }
    }

    public static void update(OBeg beg) {
        if(beg.DiscordID == 0) return;
        if (beg.id == 0) {
            insert(beg);
            return;
        }
        try {
            WebDb.get().query("UPDATE  CarrotBeg SET Timestamp= ? WHERE id = ? ", beg.Timestamp, beg.id);
        } catch (Exception e) {
            return;
        }
    }
}