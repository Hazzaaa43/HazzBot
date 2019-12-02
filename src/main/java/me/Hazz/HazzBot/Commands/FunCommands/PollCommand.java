package me.Hazz.HazzBot.Commands.FunCommands;

import com.vdurmont.emoji.EmojiManager;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PollCommand implements ICommand {

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {


        StringBuilder content = new StringBuilder();
        int length = newArgs.length;

        for (int i = 0; i < length; i++) {
            content.append(newArgs[i] + " ");
        }


        ArrayList<String> Split = new ArrayList<String>();

        final Pattern PARSE_QUOTES = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");
        Matcher matcher = PARSE_QUOTES.matcher(content.toString());
        while(( matcher).find()) {
            String token = matcher.group(1).replace("\"", "");
            Split.add(token);

        }
        if (Split.size() < 3 || Split.size() > 10) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "poll [Title] [Opt1] [Opt2] (Opt3-10)`\n Use quotations if wanting more than 1 word for an option.\nEx: `.poll Title \"Option one\" Two Three`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));
            return true;
        }

        String title = Split.get(0);
        StringBuilder options = new StringBuilder();
        String Numbers[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "keycap_ten"};
        int val = 1;
        while (val < Split.size()) {
            options.append(":" + Numbers[val-1] +": - " + Split.get(val) + "\n");
            val++;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("Poll - " + title, String.valueOf(options), false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue(message -> {
            int vals = 1;
            while (vals < Split.size()) {
                message.addReaction((EmojiManager.getForAlias(Numbers[vals-1]).getUnicode())).queue();
                vals++;
            }
        });

        return true;
    }
}