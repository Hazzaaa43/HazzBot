package me.Hazz.HazzBot.Commands.InfoCommands;

import me.Hazz.HazzBot.Commands.FunCommands.AgeCommand;
import me.Hazz.HazzBot.Commands.FunCommands.HowGayCommand;
import me.Hazz.HazzBot.Commands.FunCommands.PPSizeCommand;
import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import me.Hazz.HazzBot.Variables.findUserIn;
import me.Hazz.HazzBot.Variables.getTimingString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class WhoisCommand implements ICommand {

    @Override
    public String getName() {
        return "whois";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        try {
            Member name = findUserIn.findUserIn((TextChannel) event.getMessage().getChannel(), String.join(" ", newArgs));
            if (name == null || name.getUser().getIdLong() == 0) {
                name = event.getMember();
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("User Information for " + name.getUser().getAsTag(),
                    ":bust_in_silhouette: User: `" + name.getUser().getAsTag() + "` [`" + name.getOnlineStatus() + "`]"
                            + "\n:id: Discord ID: `" + name.getUser().getId() + "`"
                            + "\n:desktop: Account Created: `" + name.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a")) + "`"
                            + "\n:baby: Account Age: `" + getTimingString.getTimingString(name.getUser().getTimeCreated().toInstant().toEpochMilli()) + "`"
                            + "\n:calendar: Joined Guild: `" + name.getTimeJoined().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "` [`"
                            + getTimingString.getTimingString(name.getTimeJoined().toInstant().toEpochMilli()) + " Ago`]"

                            + "\n\n:carrot: Carrots: `" + CUser.findBy(name.getUser().getIdLong()).Carrots + "`"
                            + "\n:older_woman: Age: `" + AgeCommand.getAge(name.getUser().getIdLong()) + "`"
                            + "\n:gay_pride_flag: How Gay: `" + HowGayCommand.getLevel(name.getUser().getIdLong()) + "`"
                            + "\n:eggplant: PP Size: `" + "8" + PPSizeCommand.getSize(name.getUser().getIdLong()) + "D" + "`"
                    , false);
            embed.setThumbnail(name.getUser().getAvatarUrl());
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();

        } catch (NullPointerException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "whois (User)`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}

