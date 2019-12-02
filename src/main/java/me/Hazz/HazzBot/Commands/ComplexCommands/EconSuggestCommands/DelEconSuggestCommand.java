package me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands;

import me.Hazz.HazzBot.Database.Controllers.CEconSuggest;
import me.Hazz.HazzBot.Database.Objects.OEconSuggest;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DelEconSuggestCommand implements ICommand {

    @Override
    public String getName() {
        return "delecosuggest";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        if (!event.getChannel().getId().equals("576141919529336834")){
            return true;
        }
        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("StaffTeam")) {
                RankTester = 1;
            }
        }
        try {

            int id = Integer.parseInt(newArgs[0]);
            OEconSuggest suggestion = CEconSuggest.findByid(id);

            if (suggestion.Author != event.getAuthor().getIdLong() && RankTester == 0) {
                if (suggestion.id == 0){
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(new Color(0xff0000));
                    error.addField("Incorrect Argument", "Economy Price Suggestion #" + id + " does not exist.", false);
                    error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                    return true;
                }
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Incorrect Author", "You are not the author of Economy Price Suggestion #" + suggestion.id + " or part of the StaffTeam", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }


            event.getJDA().getGuildById("374350672981655571").getTextChannelById("576141789182951426").deleteMessageById(suggestion.MessageID).queue(m -> {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.addField("Suggestion Deleted", "You have deleted Economy Price Suggestion #" + suggestion.id, false);
                embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }, throwable -> {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0xff0000));
                embed.addField("Incorrect Argument", "Economy Price Suggestion " + id + " doesnt exist.", false);
                embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            });

        }catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "delecosuggest [SuggestionID]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;

    }
}