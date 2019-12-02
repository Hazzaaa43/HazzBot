package me.Hazz.HazzBot.Events;

import me.Hazz.HazzBot.DiscordBot;
import me.Hazz.HazzBot.Interfaces.IEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class EmbedScreenShot implements IEvent {
    DiscordBot bot = null;

    public EmbedScreenShot(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public String getName() {
        return "embed screenshot";
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("http://i.hazzaaa.com/")) {
            String URL = event.getMessage().getContentRaw();

            if (URL.contains(".mp4")) {
                return false;
            }

            event.getMessage().delete().queue();
            if (!URL.contains(".png") && !URL.contains(".gif") && !URL.contains(".jpg")) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Incorrect Usage", "Image needs to be `.png`, `.jpg`, or `.gif`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(3, TimeUnit.SECONDS));
                return true;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("HazzBot Link Embedder", String.format("[[Image Link](%s)]", URL), false);
            embed.setImage(URL);
            embed.setFooter("Event ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(embed.build()).queue();
            return true;
        }
        return false;
    }
}
