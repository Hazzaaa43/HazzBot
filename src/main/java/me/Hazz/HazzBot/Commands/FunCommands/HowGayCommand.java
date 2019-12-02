package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import me.Hazz.HazzBot.Variables.findUserIn;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HowGayCommand implements ICommand {

    public static StringBuilder getLevel(long seed) {
        Random size = new Random(seed);
        int number = size.nextInt(100);
        StringBuilder bar = new StringBuilder();

        if (String.valueOf(seed).equals("141377205421867008")) {
            number = 100;
        }

        int ones = number % 10;
        int tens = number - ones;

        for (int i = 0; i < 10; i++) {
            if (tens >= 10) {
                bar.append("█");
                tens -= 10;
            } else {
                bar.append("⠀");
            }
        }
        final StringBuilder gay = new StringBuilder("│" + bar.toString() + "│ " + number + "% Gay");
        return gay;
    }

    @Override
    public String getName() {
        return "howgay";
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
            long seed = name.getUser().getIdLong();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField(":rainbow: " + name.getUser().getName() + "'s Gay-o-Meter", String.valueOf(getLevel(seed)), false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

            event.getChannel().sendMessage(embed.build()).queue();

        } catch (NullPointerException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "howgay (User)`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}




