package me.Hazz.HazzBot.Commands.FunCommands;

import com.vdurmont.emoji.EmojiManager;
import me.Hazz.HazzBot.Interfaces.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WouldYouRatherCommand implements ICommand {

    @Override
    public String getName() {
        return "wouldyourather";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"wyr"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        try {
            Document doc = Jsoup.connect("https://www.wouldyouratherquestions.com").get();
            String RandomA = doc.select("#randoma").text();
            String RandomB = doc.select("#randomb").text();


            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Would you Rather...", ":regional_indicator_a: `" + RandomA + "` \n **OR** \n:regional_indicator_b:  `" + RandomB.replaceAll("\\?", "") + "`" , false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue(message -> {
                message.addReaction((EmojiManager.getForAlias("regional_indicator_symbol_a").getUnicode())).queue();
                message.addReaction((EmojiManager.getForAlias("regional_indicator_symbol_b").getUnicode())).queue();
            });

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
