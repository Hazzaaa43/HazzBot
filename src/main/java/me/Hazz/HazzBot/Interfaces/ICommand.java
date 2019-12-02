package me.Hazz.HazzBot.Interfaces;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {

    String getName();

    String[] getAliases();

    boolean execute(String[] s, GuildMessageReceivedEvent event) throws Exception;
}
