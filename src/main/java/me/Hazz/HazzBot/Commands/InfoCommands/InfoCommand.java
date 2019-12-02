package me.Hazz.HazzBot.Commands.InfoCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class InfoCommand implements ICommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("HazzBot Information", "**Version:** `" + Info.VERSION + "`"
                + "\n**Creator:** `Hazzaaa#1651`"
                + "\n**Webiste:** https://hazzaaa.com"
                + "\n**Slogan:** `A bot Made by Hazz, Cried over by Hazz...`"
                + "\n**Bot Info:** HazzBot is a basic level Java Discord Bot using JDA. I also keep growing and now storing all my information in a MySQL database! I was originally created to help Hazz to learn how to code for his college degree, Im slowly growing to become stronger and stronger. I am a little rough around the edges but I keep changing to be the best I can be! I am not a public bot and dont plan to be, Purely just used to help Hazz learn on his adventures!", false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
        embed.setThumbnail("http://i.hazzaaa.com/botpfp.png");
        event.getChannel().sendMessage(embed.build()).queue();
        return true;
    }
}