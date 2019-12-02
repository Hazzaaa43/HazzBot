package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomCommand implements ICommand {

    @Override
    public String getName() {
        return "random";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        try {
            int num1 = Integer.parseInt(newArgs[0]);
            int num2 = Integer.parseInt(newArgs[1]);

            int larger;
            int smaller;

            if (num1 < 0 || num2 < 0) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "Both Numbers need to be positive.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            } else if (num1 < num2) {
                smaller = num1;
                larger = num2;
            } else if (num1 > num2) {
                smaller = num2;
                larger = num1;
            } else {
                larger = num1;
                smaller = num2;
            }

            Random size = new Random();
            int random = larger - smaller;
            int number = size.nextInt(random + 1) + smaller;
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Random Number Generator", "Random number from `" + smaller + "` to `" + larger + "` is\n**Number:** `" + number + "`", false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

            event.getChannel().sendMessage(embed.build()).queue();

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "random [Number1] [Number2]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

        }
        return true;
    }
}