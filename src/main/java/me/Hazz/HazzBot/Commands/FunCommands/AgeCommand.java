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

public class AgeCommand implements ICommand {

    public static Integer getAge(long seed) {
        Random number = new Random(seed * 2);
        int age = number.nextInt(100);
        if (String.valueOf(seed).equals("141377205421867008")) {
            age = 12;
        }
        return age;
    }

    @Override
    public String getName() {
        return "age";
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
            embed.addField(":older_woman: " + name.getUser().getName() + "'s Age", name.getUser().getName() + " is `" + getAge(seed) + "` years old!", false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

            event.getChannel().sendMessage(embed.build()).queue();

        } catch (NullPointerException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "age (User)`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}

