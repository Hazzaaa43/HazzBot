package me.Hazz.HazzBot.Commands.ComplexCommands.ReportCommands;

import me.Hazz.HazzBot.Database.Controllers.CReport;
import me.Hazz.HazzBot.Database.Objects.OReport;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ReportCommand implements ICommand {

    @Override
    public String getName() {
        return "report";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"note"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        if (!event.getChannel().getId().equals("374060379619459083") // Pixelmon Staff
                && !event.getChannel().getId().equals("631323140475060251") // Vanilla Staff
                && !event.getChannel().getId().equals("632380912968925196")){ // Test Bot
            return true;
        }
        try {
            String IGN = newArgs[0].substring(0, 1).toUpperCase().replaceAll("[\\[\\](){}]", "") + newArgs[0].substring(1).toLowerCase().replaceAll("[\\[\\](){}]", "");
            String Server = newArgs[1].substring(0, 1).toUpperCase().replaceAll("[\\[\\](){}]", "") + newArgs[1].substring(1).replaceAll("[\\[\\](){}]", "");

            if (!Server.toLowerCase().contains("red") && !Server.toLowerCase().contains("blue") && !Server.toLowerCase().contains("yellow") && !Server.toLowerCase().contains("crystal") && !Server.toLowerCase().contains("emerald") && !Server.toLowerCase().contains("sapphire") && !Server.toLowerCase().contains("gold") && !Server.toLowerCase().contains("silver") && !Server.toLowerCase().contains("any") && !Server.toLowerCase().contains("all") && !Server.toLowerCase().contains("hub") && !Server.toLowerCase().contains("discord")){
                StringBuilder command = new StringBuilder();
                command.append(Info.PREFIX + "report");
                for (int i = 0; i < newArgs.length; i++) {
                     command.append(" " + newArgs[i]);
                }
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Incorrect Arguments", "Correct Usage: `" + Info.PREFIX + "report [IGN] [Server] [Info]`", false);
                error.addField("Errored Content", "You Sent: `" + command.toString() + "`\nPlease make sure you put the IGN and Server in the correct order!\nIf you believe this to be a mistake, Please contact Hazz!", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));
                return true;
            }

            StringBuilder content = new StringBuilder();
            for (int i = 2; i < newArgs.length; i++) {
                content.append(newArgs[i] + " ");
            }
            String Info = content.toString();

            CReport.addMessage(event.getMessage(), IGN, Server, Info);

            ArrayList<OReport> report = CReport.findByIGN(IGN, event.getGuild());

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.setTitle("Players to Watch - " + IGN.replace("_", "\\_"));
            embed.setDescription(getContent(report, event) + "\nNumber of Reports: `" + getNumber(report) + "`");
            event.getChannel().sendMessage(embed.build()).queue(message -> {
                long MessageID = report.get(0).MessageID;
                event.getChannel().deleteMessageById(MessageID).queue(msg ->{},throwable -> {return;});
                for (OReport rep : report) {
                    rep.MessageID = message.getIdLong();
                    CReport.update(rep, event.getGuild());
                }
            });

        } catch (Throwable e){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "report [IGN] [Server] [Info]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

        }

        return true;
    }


    public static StringBuilder getContent(ArrayList<OReport> report, GuildMessageReceivedEvent event) {
        StringBuilder allcontent = new StringBuilder();
        for (OReport rep : report) {
            Date date = new Date(rep.Timestamp);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String timestamp = formatter.format(date);
            String Author;
            try {
                try {
                    try {
                        Author = event.getGuild().getMemberById(rep.Author).getUser().getAsTag();
                    } catch (Throwable e) {
                        Author = event.getJDA().getGuildById("374350672981655571").getMemberById(rep.Author).getUser().getAsTag();
                    }
                } catch (Throwable e) {
                    Author = event.getJDA().getGuildById("631273255180959767").getMemberById(rep.Author).getUser().getAsTag();
                }
            } catch (Throwable e) {
                Author = "Invalid Author";
            }
            allcontent.append("\n" + rep.id + ": [" + rep.Server + "] - `" + rep.Info.trim() + "`\n Time: `" + timestamp + "` \nReporter: `" + Author + "`\n");
        }
        return allcontent;
    }

    public static int getNumber(ArrayList<OReport> report){
        return report.size();
    }
}
