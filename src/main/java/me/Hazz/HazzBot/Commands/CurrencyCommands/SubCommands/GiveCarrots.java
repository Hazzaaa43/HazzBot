package me.Hazz.HazzBot.Commands.CurrencyCommands.SubCommands;

import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Database.Objects.OUser;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GiveCarrots {
    public GiveCarrots(String A, Member name, GuildMessageReceivedEvent event) {
        try {
            long Amount = Long.parseLong(A);
            OUser user = CUser.findBy(event.getMember().getIdLong());

            if (user.Carrots < Amount) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "Not enough carrots to give.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }
            if (Amount <= 0) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "Amount needs to be above 0.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }
            if (name.getUser().equals(event.getMember().getUser())) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "You cant gift carrots to yourself!", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }

            OUser receiver = CUser.findBy(name.getIdLong());

            user.Carrots -= Amount;
            event.getJDA().getGuildById("506172322332540938").getTextChannelById("607745957186240523").sendMessage(("Removed " + Amount + " carrots from `" + event.getMember().getUser().getAsTag()) + "`").queue();
            CUser.update(user);

            receiver.Carrots += Amount;
            event.getJDA().getGuildById("506172322332540938").getTextChannelById("607745957186240523").sendMessage(("Added " + Amount + " carrots to `" + name.getUser().getAsTag()) + "`").queue();
            CUser.update(receiver);


            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Carrots Gifted", event.getMember().getUser().getAsMention() + " Has gifted `" + Amount + "` Carrots to " + name.getUser().getAsMention(), false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();



        } catch (Throwable e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "carrots give [Amount] [User]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
    }
}