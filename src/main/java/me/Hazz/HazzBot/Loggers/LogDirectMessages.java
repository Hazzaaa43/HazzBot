package me.Hazz.HazzBot.Loggers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class LogDirectMessages extends ListenerAdapter {
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {

        String timestamp = (event.getMessage().getTimeCreated().format(DateTimeFormatter.ofPattern(("MM/dd/yyyy']` `['HH:mm:ss"))));

        if(event.getMessage().getContentRaw().isEmpty()){
            return;
        } else {
            event.getJDA().getGuildById("506172322332540938").getTextChannelById("611025041253072912").sendMessage("`[" + timestamp + "]` :mailbox_with_mail: " + event.getAuthor().getAsTag() + "[ID:" + event.getAuthor().getId() + "]'s sent a Direct message to <@!527259683480666123>" ).queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.setDescription(event.getMessage().getContentRaw());
            event.getJDA().getGuildById("506172322332540938").getTextChannelById("611025041253072912").sendMessage(embed.build()).queue();
        }
    }
}
