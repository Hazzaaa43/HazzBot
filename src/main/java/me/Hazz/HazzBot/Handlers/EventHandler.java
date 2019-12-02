package me.Hazz.HazzBot.Handlers;

import me.Hazz.HazzBot.Commands.CurrencyCommands.MessageCarrots;
import me.Hazz.HazzBot.DiscordBot;
import me.Hazz.HazzBot.Events.BotTagMessage;
import me.Hazz.HazzBot.Events.EmbedScreenShot;
import me.Hazz.HazzBot.Loggers.PingLogger;
import me.Hazz.HazzBot.Interfaces.IEvent;
import me.Hazz.HazzBot.Loggers.LogMessages;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class EventHandler extends ListenerAdapter {
    private List<IEvent> events = new ArrayList<>();


    public boolean handle(GuildMessageReceivedEvent event) {
        for (IEvent eventz : events) {
            if (eventz.execute(event)) {
                return true;
            }
        }
        return false;
    }

    public EventHandler(DiscordBot bot) {
        events.add(new EmbedScreenShot(bot));
        events.add(new MessageCarrots(bot));
        events.add(new LogMessages(bot));
        events.add(new PingLogger(bot));
        events.add(new BotTagMessage(bot));

    }
}
