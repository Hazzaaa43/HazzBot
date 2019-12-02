package me.Hazz.HazzBot.Interfaces;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface IEvent {

    String getName();

    boolean execute(GuildMessageReceivedEvent event);
}