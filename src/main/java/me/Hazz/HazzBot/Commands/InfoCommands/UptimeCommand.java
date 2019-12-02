package me.Hazz.HazzBot.Commands.InfoCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.getTimingString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UptimeCommand implements ICommand {

    @Override
    public String getName() {
        return "uptime";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getStartTime();

        DateFormat simple = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a z");
        String result = simple.format(uptime);

        String time = getTimingString.getTimingStringExtended(uptime);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("HazzBot - Uptime ", "**Uptime**\n`" + time + "`\n**Bot Started on:** \n `" + result + "`", false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());


        event.getChannel().sendMessage(embed.build()).queue();
        return true;
    }
}
