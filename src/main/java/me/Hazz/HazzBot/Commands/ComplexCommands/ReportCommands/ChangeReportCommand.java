package me.Hazz.HazzBot.Commands.ComplexCommands.ReportCommands;

import me.Hazz.HazzBot.Database.Controllers.CReport;
import me.Hazz.HazzBot.Database.Objects.OReport;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static me.Hazz.HazzBot.Commands.ComplexCommands.ReportCommands.ReportCommand.getContent;
import static me.Hazz.HazzBot.Commands.ComplexCommands.ReportCommands.ReportCommand.getNumber;

public class ChangeReportCommand implements ICommand {

    @Override
    public String getName() {
        return "changereport";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"changenote"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        if (!event.getChannel().getId().equals("374060379619459083") // Pixelmon Staff
                && !event.getChannel().getId().equals("631323140475060251") // Vanilla Staff
                && !event.getChannel().getId().equals("632380912968925196")){ // Test Bot
            return true;
        }
        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Pixel RankHeads:::") || r.getName().equals("::Pixel Sr-Admin::") || r.getName().equals("::Pixel Admin::") || r.getName().equals("::Pixel Sr-Mod::") || r.getName().equals(":::Vanilla RankHeads:::") || r.getName().equals("::Vanilla Sr-Admin::") || r.getName().equals("::Vanilla Admin::") || r.getName().equals("::Vanilla Sr-Mod::")) {
                RankTester = 1;
            }
        }
        if (RankTester == 1) {
            try {
                String oldIGN = newArgs[0].substring(0, 1).toUpperCase() + newArgs[0].substring(1).toLowerCase();
                String newIGN = newArgs[1].substring(0, 1).toUpperCase() + newArgs[1].substring(1).toLowerCase();

                ArrayList<OReport> reports = CReport.findByIGN(oldIGN, event.getGuild());
                for (OReport rep : reports){
                    rep.IGN = newIGN;
                    CReport.update(rep, event.getGuild());
                }

                ArrayList<OReport> report = CReport.findByIGN(newIGN, event.getGuild());
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.addField("Players to Watch - " + newIGN.replace("_", "\\_"), getContent(report, event) + "\nNumber of Reports: `" + getNumber(report) + "`", false);
                event.getChannel().sendMessage(embed.build()).queue(message -> {
                    long MessageID = report.get(0).MessageID;
                    event.getChannel().deleteMessageById(MessageID).queue(m -> {
                    }, throwable -> {
                    });
                    for (OReport rep : report) {
                        rep.MessageID = message.getIdLong();
                        CReport.update(rep, event.getGuild());
                    }
                });

            } catch (Throwable e) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "changereport [OldIGN] [NewIGN]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }
        } else {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("No Permission", "You do not have permission to run this command.", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}