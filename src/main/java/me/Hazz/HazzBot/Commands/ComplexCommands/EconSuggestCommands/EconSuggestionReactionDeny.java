package me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands;

import me.Hazz.HazzBot.Database.Controllers.CEconSuggest;
import me.Hazz.HazzBot.Database.Objects.OEconSuggest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EconSuggestionReactionDeny extends ListenerAdapter {
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getChannel().getId().equals("576141789182951426")) {
            return;
        }
        int RankTester = 0;
        try {
            String emojichannel = String.valueOf(event.getChannel().getIdLong());
            String channel = "576141789182951426";
            if (emojichannel.equals(channel)) {
                if (event.getReactionEmote().getId().equals("578572709236178982")) {

                    List<Role> roles = event.getMember().getRoles();
                    try {
                        List<Role> roles2 = event.getJDA().getGuildById("345602641008525312").getMember(event.getUser()).getRoles();
                        for (Role r : roles2) {
                            if (r.getName().equals("Pricer")) {
                                RankTester = 1;
                            }
                        }
                    } catch (Throwable e) {
                        return;
                    }
                    for (Role r : roles) {
                        if (r.getName().equals("Admin") || r.getName().equals("Complex")) {
                            RankTester = 1;
                        }
                    }

                    if (RankTester == 1) {
                        OEconSuggest suggestion = CEconSuggest.findByID(event.getMessageIdLong());

                        Date staff = new Date(suggestion.StaffTimestamp);
                        Date current = new Date(System.currentTimeMillis());
                        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                        String staffedit = formatter.format(staff);
                        String reaction = formatter.format(current);

                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setColor(new Color(0x3498db));
                        embed.addField("Economy Price Suggestion: [#" + suggestion.id + "]", suggestion.Content, false);
                        if (!suggestion.StaffContent.isEmpty()) {
                            embed.addField("Staff Edit:", "**Edited By:** " + event.getJDA().getGuildById("374350672981655571").getMemberById(suggestion.StaffAuthor).getUser().getName() + " [" + staffedit + "]\n**Reason:** " + suggestion.StaffContent, false);
                        }
                        embed.addField("<:downvote:578572709236178982> Denied:", "`" + event.getMember().getUser().getAsTag() + "` [" + reaction + "] \nCheck <#576141648514252800> For all Changes!", false);
                        embed.setFooter(event.getJDA().getGuildById("374350672981655571").getMemberById(suggestion.Author).getUser().getAsTag(), suggestion.AuthorURL);
                        Date timestamp = new Date(suggestion.Timestamp);
                        embed.setTimestamp(timestamp.toInstant());
                        event.getChannel().editMessageById(event.getReaction().getMessageIdLong(), embed.build()).queue(message -> {
                            message.clearReactions().queue();
                        });
                    }
                }
            }
        } catch (Throwable e) {
            return;
        }
    }
}