package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Database.Controllers.CMessageLogger;
import me.Hazz.HazzBot.Database.Objects.OMessageLogger;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class QuoteCommand implements ICommand {

    @Override
    public String getName() {
        return "quote";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        try {
            long MessageID = Long.parseLong(newArgs[0]);

            OMessageLogger message = CMessageLogger.findByID(MessageID);

            if (!message.Content.isEmpty()) {
                User Author = event.getJDA().getUserById(message.Author);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.setAuthor(Author.getAsTag(), null, Author.getAvatarUrl());
                embed.setDescription(message.Content);
                embed.setFooter("#" + message.ChannelName);
                Date timestamp = new Date(message.MessageDate);
                embed.setTimestamp(timestamp.toInstant());

                event.getChannel().sendMessage(embed.build()).queue();
            }
            if (message.Content.isEmpty()) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "Message has no content. Please try another MessageID", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg2 -> msg2.delete().queueAfter(5, TimeUnit.SECONDS));
            }

        } catch (Throwable e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "quote [MessageID]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

        }
        return true;
    }
}

