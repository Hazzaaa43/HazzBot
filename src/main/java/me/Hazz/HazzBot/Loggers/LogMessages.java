package me.Hazz.HazzBot.Loggers;

import me.Hazz.HazzBot.Database.Controllers.CMessageLogger;
import me.Hazz.HazzBot.DiscordBot;
import me.Hazz.HazzBot.Interfaces.IEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class LogMessages implements IEvent {
    DiscordBot bot = null;

    public LogMessages(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public String getName() {
        return "messagelogger";
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent event) {
        try {
            if (!event.getMember().getUser().isBot() && !event.getMessage().getContentRaw().startsWith(".") && !event.getMessage().getContentRaw().startsWith("%") && !event.getMessage().getContentRaw().startsWith("!") && !event.getMessage().getContentRaw().startsWith("?") && !event.getMessage().getContentRaw().startsWith("$")) {
                CMessageLogger.addMessage(event.getMessage());
            }
        }catch (Throwable e){
            return false;
        }
        return false;
    }
}