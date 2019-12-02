package me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands;

import me.Hazz.HazzBot.Database.Controllers.CSuggestions;
import me.Hazz.HazzBot.Database.Objects.OSuggestions;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeleteSuggestionCommand  implements ICommand {

    @Override
    public String getName() {
        return "delsuggest";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        Guild msgguild = event.getGuild();

        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("StaffTeam") || r.getName().equals("::Pixel Admin::") || r.getName().equals("::Vanilla Admin::")) {
                RankTester = 1;
            }
        }

        if(!event.getChannel().getId().equals("560277651743309855")// Main Pixelmon
                && !event.getChannel().getId().equals("553047585519173660") // Staff Pixelmon
                && !event.getChannel().getId().equals("631291784168996894") // Main Vanilla
                && !event.getChannel().getId().equals("631325481727295488") // Staff Vanilla
                && !event.getChannel().getId().equals("632380827270905896") ){ // Bot Test
            return true;
        }
        try {
            String guild = "";
            String channel = "";
            if (event.getGuild().getId().equals("374350672981655571")) {// Main Pixelmon
                guild = "374350672981655571";
                channel = "615659198746722346";
            } else if (event.getGuild().getId().equals("345602641008525312")) {// Staff Pixelmon
                guild = "345602641008525312";
                channel = "451587773229432833";
            } else if (event.getGuild().getId().equals("631273255180959767")) {// Main Vanilla
                guild = "631273255180959767";
                channel = "631291677117775882";
            } else if (event.getGuild().getId().equals("631273365612789770")) {// Staff Vanilla
                guild = "631273365612789770";
                channel = "631323714142863372";
            } else if (event.getGuild().getId().equals("506172322332540938")) {// Bot Test
                guild = "506172322332540938";
                channel = "632380748380241930";
            }

            int id = Integer.parseInt(newArgs[0]);
            OSuggestions suggestion = CSuggestions.findByid(id, msgguild);

            if (suggestion.Author != event.getAuthor().getIdLong() && RankTester == 0) {
                if (suggestion.id == 0){
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(new Color(0xff0000));
                    error.addField("Incorrect Argument", "Suggestion #" + id + " does not exist.", false);
                    error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                    return true;
                }
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Incorrect Author", "You are not the author of suggestion #" + suggestion.id + " or part of the StaffTeam", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }


            event.getJDA().getGuildById(guild).getTextChannelById(channel).deleteMessageById(suggestion.MessageID).queue(m -> {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.addField("Suggestion Deleted", "You have deleted suggestion #" + suggestion.id, false);
                embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }, throwable -> {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0xff0000));
                embed.addField("Incorrect Argument", "Suggestion " + id + " doesnt exist.", false);
                embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            });

        }catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "delsuggest [SuggestionID]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;

    }
}