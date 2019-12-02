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

import static me.Hazz.HazzBot.Commands.ComplexCommands.ReportCommands.ReportCommand.getContent;
import static me.Hazz.HazzBot.Commands.ComplexCommands.ReportCommands.ReportCommand.getNumber;

public class ViewReportCommand implements ICommand {

    @Override
    public String getName() {
        return "viewreport";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"viewnote"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        if (!event.getChannel().getId().equals("374060379619459083") // Pixelmon Staff
                && !event.getChannel().getId().equals("631323140475060251") // Vanilla Staff
                && !event.getChannel().getId().equals("632380912968925196")){ // Test Bot
            return true;
        }
        try {
            int id = Integer.parseInt(newArgs[0]);
            OReport report = CReport.findByID(id, event.getGuild());

            if (report.id == 0){
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Command Failed", "Could not find report `#" + id + "` in the report database.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }

            Date date = new Date(report.Timestamp);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String timestamp = formatter.format(date);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.setTitle("Players to Watch - View Report");
            embed.setDescription("**Report:** `#" + report.id + "` \n**IGN:** `" + report.IGN + "` \n**Server:** `" + report.Server + "` \n**Info:** `" + report.Info.trim() + "` \n**When:** `" + timestamp + "` \n**Reporter:** `" + event.getGuild().getMemberById(report.Author).getUser().getAsTag() + "`");
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));

        } catch (NumberFormatException | NullPointerException f) {
            try {
                String IGN = newArgs[0].substring(0, 1).toUpperCase() + newArgs[0].substring(1).toLowerCase();
                ArrayList<OReport> report = CReport.findByIGN(IGN, event.getGuild());
                if(report.isEmpty()){
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(new Color(0xff0000));
                    error.addField("Command Failed", "Could not find any reports on `" + IGN + "` in the report database.", false);
                    error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                    return true;
                }


                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.setTitle("Players to Watch - View Report");
                embed.setDescription("**IGN:** `" + IGN + "`\n"  + getContent(report, event) + "\nNumber of Reports: `" + getNumber(report) + "`");
                event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));

            } catch (Throwable e) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "viewreport [#/IGN]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

            }
        }
        return true;
    }
}


