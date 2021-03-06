package me.Hazz.HazzBot.Commands.CurrencyCommands.SubCommands;

import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Database.Objects.OUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RemoveCarrots {
    public RemoveCarrots(String A, Member name, GuildMessageReceivedEvent event) {

        long Amount = Long.parseLong(A);

        if(Amount <= 0){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Invalid Arguments", "Amount needs to be above 0.", false);
            error.setFooter("Command ran by " + name.getUser().getAsTag(), name.getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }
        OUser user = CUser.findBy(name.getUser().getIdLong());
        long Carrots = user.Carrots;
        long finalvalue = Carrots - Amount;

        if(finalvalue < 0){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Invalid Arguments", "Final value needs to be above 0", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }
        user.Carrots -= Amount;
        event.getJDA().getGuildById("506172322332540938").getTextChannelById("607745957186240523").sendMessage(("Removed " + Amount + " carrots from `" + event.getMember().getUser().getAsTag()) + "`").queue();
        CUser.update(user);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("Carrots Removed", "`" + Amount + "` carrots removed from " + name.getUser().getAsMention() + "'s Fridge.\n **New Total: `" + user.Carrots + "`** carrots ", false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
