package me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands;

import me.Hazz.HazzBot.Database.Controllers.CSuggestions;
import me.Hazz.HazzBot.Database.Objects.OSuggestions;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EditSuggestionCommand  implements ICommand {

    @Override
    public String getName() {
        return "editsuggest";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        Guild msgguild = event.getGuild();
        if(!event.getChannel().getId().equals("560277651743309855")// Main Pixelmon
                && !event.getChannel().getId().equals("553047585519173660") // Staff Pixelmon
                && !event.getChannel().getId().equals("631291784168996894") // Main Vanilla
                && !event.getChannel().getId().equals("631325481727295488") // Staff Vanilla
                && !event.getChannel().getId().equals("632380827270905896") ){ // Bot Test
            return true;
        }

        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("StaffTeam") || r.getName().equals("::Pixel Admin::") || r.getName().equals("::Vanilla Admin::") || r.getName().equals("HazzBot") || r.getName().equals("HazzBotTest")) {
                RankTester = 1;
            }
        }

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

        if(RankTester == 1) {
            try {
                int id = Integer.parseInt(newArgs[0]);

                StringBuilder content = new StringBuilder();
                for (int i = 1; i < newArgs.length; i++) {
                    content.append(newArgs[i] + " ");
                }

                OSuggestions suggestion = CSuggestions.findByid(id, msgguild);
                Date after = new Date(System.currentTimeMillis());
                DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                String now = formatter.format(after);

                String contents = StringUtils.abbreviate(content.toString(), 900);

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.addField("Suggestion: [#" + suggestion.id + "]", suggestion.Content, false);
                embed.addField("Staff Edit:", "**Edited By:** " + event.getMember().getUser().getName() + " [" + now + "]\n**Reason:** " + contents, false);
                embed.setFooter(event.getJDA().getGuildById(guild).getMemberById(suggestion.Author).getUser().getAsTag(), suggestion.AuthorURL);
                Date timestamp = new Date(suggestion.Timestamp);
                embed.setTimestamp(timestamp.toInstant());

                if (event.getAuthor().getId().equals("527259683480666123")){
                    return true;
                }

                event.getJDA().getGuildById(guild).getTextChannelById(channel).editMessageById(suggestion.MessageID, embed.build()).queue(m -> {
                    EmbedBuilder embeds = new EmbedBuilder();
                    embeds.setColor(new Color(0x3498db));
                    embeds.addField("Suggestion Edited", "You have edited suggestion #" + suggestion.id, false);
                    embeds.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(embeds.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                }, throwable -> {
                    EmbedBuilder embeds = new EmbedBuilder();
                    embeds.setColor(new Color(0xff0000));
                    embeds.addField("Incorrect Argument", "Suggestion " + id + " doesnt exist.", false);
                    embeds.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(embeds.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                });

            } catch (NullPointerException | NumberFormatException e) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "editsuggest [SuggestionID] [Edit]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }
        } else {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("No Permission", "You do not have permission to run this command.", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}