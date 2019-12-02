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

public class BugPermReactionNeedsRep extends ListenerAdapter {
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getChannel().getId().equals("621772615383515136") // Pixelmon Staff
                && !event.getChannel().getId().equals("631321780455211008") // Vanilla Staff
                && !event.getChannel().getId().equals("632380893217947658")){ // Test Bot
            return;
        }
        int RankTester = 0;
        String emojichannel = String.valueOf(event.getChannel().getIdLong());
        if (emojichannel.equals("621772615383515136")) {// Pixelmon Staff
            if (event.getReactionEmote().getName().equalsIgnoreCase("greytick")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Pixel RankHeads:::")) {
                        RankTester = 1;
                    }
                }
            }
        }
        if (emojichannel.equals("631321780455211008")) {// Vanilla Staff
            if (event.getReactionEmote().getName().equalsIgnoreCase("greytick")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Vanilla RankHeads:::")) {
                        RankTester = 1;
                    }
                }
            }
        }

        if (emojichannel.equals("632380893217947658")) {// TestBot
            if (event.getReactionEmote().getName().equalsIgnoreCase("greytick")) {
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

            Date after = new Date(System.currentTimeMillis());
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            String now = formatter.format(after);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Bug and Perm Report: [#" + bugperm.id + "]", bugperm.Content + "\n⠀", false);
            embed.addField("<:greytick:591321258457825296> Needs Replicating: ", "`" + event.getMember().getUser().getAsTag() + "` [" + now + "]" + "\n⠀", false);
            embed.addField("Reaction Options", "<:greentick:591321183413338141> - Resolved\n<:redtick:591321268146667523> - Needs Fixing\n⛔ - Not Replicable", false);
            embed.setFooter(event.getGuild().getMemberById(bugperm.Author).getUser().getAsTag(), bugperm.AuthorURL);
            Date timestamp = new Date(bugperm.Timestamp);
            embed.setTimestamp(timestamp.toInstant());
            event.getChannel().editMessageById(event.getReaction().getMessageIdLong(), embed.build()).queue(message -> {
                message.clearReactions().queue();
                message.addReaction(event.getJDA().getGuildById("345602641008525312").getEmoteById("591321183413338141")).queue();
                message.addReaction(event.getJDA().getGuildById("345602641008525312").getEmoteById("591321268146667523")).queue();
                message.addReaction("⛔").queue();
            });
        }
    }
}
