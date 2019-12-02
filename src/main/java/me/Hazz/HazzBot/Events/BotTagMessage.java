package me.Hazz.HazzBot.Events;

import me.Hazz.HazzBot.DiscordBot;
import me.Hazz.HazzBot.Interfaces.IEvent;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class BotTagMessage implements IEvent {
    DiscordBot bot = null;

    public BotTagMessage(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public String getName() {
        return "bot tag msg";
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent event) {
        if (event.getChannel().getId().equals("611025041253072912")){
            return false;
        }
        if(event.getMessage().getContentRaw().contains("<@527259683480666123>") || event.getMessage().getContentRaw().contains("<@!527259683480666123>")){
            event.getMessage().delete().queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("HazzBot Information", "You've tagged HazzBot!\nPlease check `"+ Info.PREFIX + "help` For all commands!\n**Command Prefix:** `" + Info.PREFIX + "`\n**HazzBot Version:** `" + Info.VERSION + "`", false);
            embed.setFooter("Event ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
        }
        return false;
    }
}