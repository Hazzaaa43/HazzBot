package me.Hazz.HazzBot.Commands.InfoCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class PingCommand implements ICommand {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {


        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("HazzBot Ping", "Pinging...", false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

        EmbedBuilder em = new EmbedBuilder();
        em.setColor(new Color(0x3498db));
        em.addField("HazzBot Ping", "Ping: `" + event.getJDA().getGatewayPing() + "ms`", false);
        em.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue(msg -> msg.editMessage(em.build()).queueAfter(2, TimeUnit.SECONDS));

        return true;
    }
}
