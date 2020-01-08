package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OIssue;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CIssues {

    private static String getTableName(String GuildID) {
        switch (GuildID) {
            case "345602641008525312":
                return "PixelmonIssue";
            case "631273365612789770":
                return "VanillaIssue";
            case "506172322332540938":
                return "TestIssue";
            default:
                return "Unknown";
        }
    }

    public static ArrayList<OIssue> findByIGN(String IGN, Guild guild) {
        ArrayList<OIssue> issue = new ArrayList<>();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE IGN = ?", IGN.substring(0,1).toUpperCase() + IGN.substring(1).toLowerCase())) {
            while (rs.next()) {
                try {
                    OIssue issue1;
                    issue1 = fillRecord(rs);
                    issue.add(issue1);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issue;
    }

    public static OIssue findByID(int id, Guild guild) {
        OIssue issue = new OIssue();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE id = ?", id)) {
            if (rs.next()) {
                issue = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issue;
    }

    private static OIssue fillRecord(ResultSet resultset) throws SQLException {
        OIssue issue = new OIssue();
        issue.id = resultset.getInt("id");
        issue.IGN = resultset.getString("IGN");
        issue.Type = resultset.getString("Type");
        issue.Report = resultset.getString("Report");
        issue.Screenshot = resultset.getString("Screenshot");
        issue.Attachments = resultset.getString("Attachments");
        issue.Timestamp = resultset.getLong("Timestamp");
        issue.MessageID = resultset.getLong("MessageID");
        issue.Author = resultset.getLong("Author");
        return issue;
    }

}
