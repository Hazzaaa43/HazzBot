package me.Hazz.HazzBot;

import me.Hazz.HazzBot.Handlers.CommandHandler;
import me.Hazz.HazzBot.Handlers.EventHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {
    JDA jda = null;

    private CommandHandler commandHandler;
    private EventHandler eventHandler;

    public DiscordBot(JDA jda) {
        this.jda = jda;
        initHandlers();
    }

    private void initHandlers() {
        commandHandler = new CommandHandler(this);
        eventHandler = new EventHandler(this);
    }


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (eventHandler.handle(event))
            return;
        if (commandHandler.handle(event))
            return;

    }
}
