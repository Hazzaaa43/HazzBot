package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class CoinFlipCommand implements ICommand {

    @Override
    public String getName() {
        return "coinflip";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"flip"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        String flip = "";
        Random size = new Random();
        int number = size.nextInt(2);
        if (number == 1) {
            flip = "Heads";
        } else if (number == 0) {
            flip = "Tails";
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("CoinFlip", "Coin Landed on `" + flip + "`", false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue();
        return true;
    }
}
