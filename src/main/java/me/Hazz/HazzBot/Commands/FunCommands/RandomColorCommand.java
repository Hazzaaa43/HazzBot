package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class RandomColorCommand implements ICommand {

    @Override
    public String getName() {
        return "randomcolor";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"color"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        Random random = new Random();
        int intred = random.nextInt(256);
        int intgreen = random.nextInt(256);
        int intblue = random.nextInt(256);

        String strred = Integer.toHexString(intred).toUpperCase();
        String strgreen = Integer.toHexString(intgreen).toUpperCase();
        String strblue = Integer.toHexString(intblue).toUpperCase();

        if (strred.length() == 1) {
            strred = "0" + strred;
        }
        if (strgreen.length() == 1) {
            strgreen = "0" + strgreen;
        }
        if (strblue.length() == 1) {
            strblue = "0" + strblue;
        }

        String RGB = ("RGB: `" + intred + ", " + intgreen + ", " + intblue + "`");
        String HEX = ("HEX: `#" + strred + strgreen  + strblue + "`");


        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(intred, intgreen, intblue));
        embed.addField("Random Color Generator", RGB + "\n" + HEX, false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();

        return true;
    }
}

