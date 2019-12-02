package me.Hazz.HazzBot.Commands.CurrencyCommands;

import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Database.Objects.OUser;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import me.Hazz.HazzBot.Variables.findUserIn;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class FridgeCommand implements ICommand {

    @Override
    public String getName() {
        return "fridge";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {


        Member name = findUserIn.findUserIn((TextChannel) event.getMessage().getChannel(), String.join(" ", newArgs));
        if (name == null || name.getUser().getIdLong() == 0) {
            name = event.getMember();
        }
        OUser user = CUser.findBy(name.getUser().getIdLong());
        long Amount = user.Carrots;
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("Carrots Fridge Total", name.getUser().getName() + " has `" + Amount + "` carrots in their Fridge.\nCheck " + Info.PREFIX +"help for ways to use them!", false);
        embed.setFooter("Command ran by " + name.getUser().getAsTag(), name.getUser().getAvatarUrl());
        embed.setThumbnail("http://i.hazzaaa.com/Carrotop.png");
        event.getChannel().sendMessage(embed.build()).queue();


        return true;
    }
}

