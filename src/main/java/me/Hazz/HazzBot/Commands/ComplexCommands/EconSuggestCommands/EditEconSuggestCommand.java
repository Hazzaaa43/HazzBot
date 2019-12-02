package me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands;

import me.Hazz.HazzBot.Database.Controllers.CEconSuggest;
import me.Hazz.HazzBot.Database.Objects.OEconSuggest;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class EditEconSuggestCommand implements ICommand {

    @Override
    public String getName() {
        return "editecosuggest";
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
        try {
            List<Role> roles2 = event.getJDA().getGuildById("345602641008525312").getMember(event.getAuthor()).getRoles();
            for (Role r : roles2) {
                if (r.getName().equals("Pricer"))  {
                    RankTester = 1;
                }
            }
        } catch (Throwable e){
            return false;
        }
        for (Role r : roles) {
            if (r.getName().equals("Admin") || r.getName().equals("Complex"))  {
                RankTester = 1;
            }
        }

        if(RankTester == 1) {
            try {
                int id = Integer.parseInt(newArgs[0]);

                StringBuilder content = new StringBuilder();
                for (int i = 1; i < newArgs.length; i++) {
                    content.append(newArgs[i] + " ");
                }
                OEconSuggest suggestion = CEconSuggest.findByid(id);

                String contents = StringUtils.abbreviate(content.toString(), 900);

                suggestion.StaffAuthor = event.getMember().getIdLong();
                suggestion.StaffContent = contents;
                suggestion.StaffTimestamp = System.currentTimeMillis();
                CEconSuggest.update(suggestion);

                Date staff = new Date(suggestion.StaffTimestamp);
                DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                String staffedit = formatter.format(staff);


                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.addField("Economy Price Suggestion: [#" + suggestion.id + "]", suggestion.Content, false);
                embed.addField("Staff Edit:", "**Edited By:** " + event.getJDA().getGuildById("374350672981655571").getMemberById(suggestion.StaffAuthor).getUser().getName() + " [" + staffedit + "]\n**Reason:** " + suggestion.StaffContent, false);
                embed.setFooter(event.getJDA().getGuildById("374350672981655571").getMemberById(suggestion.Author).getUser().getAsTag(), suggestion.AuthorURL);
                Date timestamp = new Date(suggestion.Timestamp);
                embed.setTimestamp(timestamp.toInstant());


                event.getJDA().getGuildById("374350672981655571").getTextChannelById("576141789182951426").editMessageById(suggestion.MessageID, embed.build()).queue(m -> {
                    EmbedBuilder embeds = new EmbedBuilder();
                    embeds.setColor(new Color(0x3498db));
                    embeds.addField("Suggestion Edited", "You have edited economy price suggestion #" + suggestion.id, false);
                    embeds.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(embeds.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                }, throwable -> {
                    EmbedBuilder embeds = new EmbedBuilder();
                    embeds.setColor(new Color(0xff0000));
                    embeds.addField("Incorrect Argument", "Economy Price Suggestion " + id + " doesnt exist.", false);
                    embeds.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(embeds.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                });

            } catch (NullPointerException | NumberFormatException e) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "editecosuggest [SuggestionID] [Edit]`", false);
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
