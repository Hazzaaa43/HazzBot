package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

public class DefineCommand implements ICommand {

    @Override
    public String getName() {
        return "define";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        try {
            if (newArgs.length == 0) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "define [Word]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }


            StringBuilder content = new StringBuilder();
            int length = newArgs.length;
            for (int i = 0; i < length; i++) {
                content.append(newArgs[i] + " ");
            }

            URL url = new URL("http://api.urbandictionary.com/v0/define?term=" + URLEncoder.encode(String.valueOf(content)));
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            reader.close();
            JSONArray listObject = (JSONArray) jsonObject.get("list");
            JSONObject firstResult;


            if (listObject.isEmpty()) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "Input not found on Urban Dictionary,\n Please try a different word or phrase.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }

            firstResult = (JSONObject) listObject.get(0);
            EmbedBuilder embed = new EmbedBuilder();



            embed.setColor(new Color(0x3498db));
            String definition = StringUtils.abbreviate(firstResult.get("definition").toString().replaceAll("[\\[\\]’â€™]", ""), 425);
            String example =  StringUtils.abbreviate(firstResult.get("example").toString().replaceAll("[\\[\\]’â€™]", ""), 425);
            embed.addField("Urban Dictionary", "**Word**: " + firstResult.get("word") + "\n\n**Definition**: \n" + definition +  "\n\n**Example**: \n" + example + "\n\n**Link**: " + firstResult.get("permalink"), false);

            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

            event.getChannel().sendMessage(embed.build()).queue();

        } catch (IOException | ParseException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "define [Word]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}

