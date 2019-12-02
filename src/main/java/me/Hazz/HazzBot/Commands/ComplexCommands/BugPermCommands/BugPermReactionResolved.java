package me.Hazz.HazzBot.Commands.ComplexCommands.BugPermCommands;

import me.Hazz.HazzBot.Database.Controllers.CBugPerm;
import me.Hazz.HazzBot.Database.Objects.OBugPerm;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BugPermReactionResolved extends ListenerAdapter {
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getChannel().getId().equals("621772615383515136") // Pixelmon Staff
                && !event.getChannel().getId().equals("631321780455211008") // Vanilla Staff
                && !event.getChannel().getId().equals("632380893217947658")) { // Test Bot
            return;
        }
        int RankTester = 0;
        String emojichannel = String.valueOf(event.getChannel().getIdLong());
        if (emojichannel.equals("621772615383515136")) {// Pixelmon Staff
            if (event.getReactionEmote().getName().equalsIgnoreCase("greentick")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Pixel RankHeads:::") || r.getName().equals("::Pixel Sr-Admin::") || r.getName().equals("::Pixel Admin::")) {
                        RankTester = 1;
                    }
                }
            }
        }
        if (emojichannel.equals("631321780455211008")) {// Vanilla Staff
            if (event.getReactionEmote().getName().equalsIgnoreCase("greentick")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Vanilla RankHeads:::") || r.getName().equals("::Vanilla Sr-Admin::") || r.getName().equals("::Vanilla Admin::")) {
                        RankTester = 1;
                    }
                }
            }
        }

        if (emojichannel.equals("632380893217947658")) {// TestBot
            if (event.getReactionEmote().getName().equalsIgnoreCase("greentick")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::")) {
                        RankTester = 1;
                    }
                }
            }
        }
        if (RankTester == 1) {
            OBugPerm bugperm = CBugPerm.findByID(event.getMessageIdLong(), event.getGuild());

            String guild = "";
            String logs = "";
            if (event.getGuild().getId().equals("345602641008525312")) {// Staff Pixelmon
                guild = "345602641008525312";
                logs = "621771071841894415";
            } else if (event.getGuild().getId().equals("631273365612789770")) {// Staff Vanilla
                guild = "631273365612789770";
                logs = "632382678187900959";
            } else if (event.getGuild().getId().equals("506172322332540938")) {// Bot Test
                guild = "506172322332540938";
                logs = "632391625322004490";
            }
            Date after = new Date(System.currentTimeMillis());
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            String now = formatter.format(after);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Bug and Perm Report: [#" + bugperm.id + "]", bugperm.Content + "\n⠀", false);
            embed.addField("<:greentick:591321183413338141> Bug Resolved By: ", "`" + event.getMember().getUser().getAsTag() + "` [" + now + "]", false);
            embed.setFooter(event.getGuild().getMemberById(bugperm.Author).getUser().getAsTag(), bugperm.AuthorURL);
            Date timestamp = new Date(bugperm.Timestamp);
            embed.setTimestamp(timestamp.toInstant());
            event.getChannel().deleteMessageById(event.getReaction().getMessageIdLong()).queue();
            event.getJDA().getGuildById(guild).getTextChannelById(logs).sendMessage(embed.build()).queue();
        }
    }
}
