package me.Hazz.HazzBot.Database.Controllers;

import me.Hazz.HazzBot.Database.Objects.OReport;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CReport {

    private static String getTableName(String GuildID) {
        switch (GuildID) {
            case "345602641008525312":
                return "PixelmonReport";
            case "631273365612789770":
                return "VanillaReport";
            case "506172322332540938":
                return "TestReport";
            default:
                return "Unknown";
        }
    }

    public static ArrayList<OReport> findByIGN(String IGN, Guild guild) {
        ArrayList<OReport> report = new ArrayList<>();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE IGN = ?", IGN.substring(0,1).toUpperCase() + IGN.substring(1).toLowerCase())) {
            while (rs.next()) {
                try {
                    OReport report1;
                    report1 = fillRecord(rs);
                    report.add(report1);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    public static OReport findByID(int id, Guild guild) {
        OReport report = new OReport();
        String GuildID = guild.getId();
        try (ResultSet rs = WebDb.get().select("SELECT * FROM " + getTableName(GuildID) + " WHERE id = ?", id)) {
            if (rs.next()) {
                report = fillRecord(rs);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    private static OReport fillRecord(ResultSet resultset) throws SQLException {
        OReport report = new OReport();
        report.id = resultset.getInt("id");
        report.IGN = resultset.getString("IGN");
        report.Server = resultset.getString("Server");
        report.Info = resultset.getString("Info");
        report.Timestamp = resultset.getLong("Timestamp");
        report.MessageID = resultset.getLong("MessageID");
        report.Author = resultset.getLong("Author");
        return report;
    }

    public static void addMessage(Message message, String name, String server, String content) {
        try {
            if (message.getChannelType() != ChannelType.TEXT) {
                return;
            }
            OReport report = new OReport();
            report.IGN = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            report.Server = server;
            report.Info = content.replaceAll("[”“„»«‘’…•—§‰°₽¥€¢£₩¿]", "\'").replace("_", "\\_");
            report.Timestamp = System.currentTimeMillis();
            report.MessageID = message.getIdLong();
            report.Author = message.getMember().getIdLong();
            insert(report, message.getGuild());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void insert(OReport report, Guild guild) {
        try {
            String GuildID = guild.getId();
            report.id = WebDb.get().insert("INSERT INTO " + getTableName(GuildID) + "(IGN, Server, Info, Timestamp, MessageID, Author) VALUES (?,?,?,?,?,?)",
                    report.IGN, report.Server, report.Info, report.Timestamp, report.MessageID, report.Author);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(OReport record, Guild guild){
        if (record.id == 0){
            insert(record, guild);
            return;
        }
        try{
            String GuildID = guild.getId();
            WebDb.get().query("UPDATE " + getTableName(GuildID) + " SET IGN = ?, Server = ?, Info = ?, Timestamp = ?, MessageID = ?, Author = ? WHERE id = ?", record.IGN, record.Server, record.Info, record.Timestamp, record.MessageID, record.Author, record.id);
        } catch (Exception e) {
            return;
        }
    }

    public static void delete(OReport record, Guild guild){
        try{
            String GuildID = guild.getId();
            WebDb.get().query("DELETE FROM " + getTableName(GuildID) + " WHERE id = ?", record.id);
        } catch (Exception e) {
            return;
        }
    }
}