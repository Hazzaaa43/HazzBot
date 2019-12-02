package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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

            List<Message> msg = new ArrayList<>();
            event.getGuild().getTextChannels().forEach(c -> {
                try {
                    msg.add(c.retrieveMessageById(MessageID).complete());
                } catch (Exception e) {
                }
            });

            if (!msg.isEmpty()) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.setAuthor(msg.get(0).getAuthor().getAsTag(), null, msg.get(0).getAuthor().getAvatarUrl());
                embed.setDescription(msg.get(0).getContentRaw());
                embed.setFooter("#" + msg.get(0).getChannel().getName());
                embed.setTimestamp(msg.get(0).getTimeCreated().toInstant());

                event.getChannel().sendMessage(embed.build()).queue();
            }
            if (msg.isEmpty()) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "quote [MessageID]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg2 -> msg2.delete().queueAfter(5, TimeUnit.SECONDS));
            }

        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "quote [MessageID]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

        }
        return true;
    }
}

