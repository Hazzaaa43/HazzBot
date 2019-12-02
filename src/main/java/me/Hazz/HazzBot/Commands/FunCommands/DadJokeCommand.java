package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DadJokeCommand implements ICommand {

    @Override
    public String getName() {
        return "dadjoke";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        try {
            Document doc = Jsoup.connect("http://icanhazdadjoke.com").get();
            String DadJoke = doc.select("p.subtitle").text();


            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Dad Joke Generator", "`" + DadJoke + "`", false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();

        } catch (IOException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Command Failed", "Contact Bot Author", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}
