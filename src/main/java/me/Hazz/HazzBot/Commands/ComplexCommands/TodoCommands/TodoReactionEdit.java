package me.Hazz.HazzBot.Commands.ComplexCommands.TodoCommands;

import me.Hazz.HazzBot.Database.Controllers.CTodo;
import me.Hazz.HazzBot.Database.Objects.OTodo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TodoReactionEdit extends ListenerAdapter {
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        if (!event.getChannel().getId().equals("531017256746876928") // Pixelmon Staff
                && !event.getChannel().getId().equals("631320958262837259") // Vanilla Staff
                && !event.getChannel().getId().equals("632380870795329536")) { // Test Bot
            return;
        }
        int RankTester = 0;
        String emojichannel = String.valueOf(event.getChannel().getIdLong());
        if (emojichannel.equals("531017256746876928")) {// Pixelmon Staff
            if (event.getReactionEmote().getName().equals("\uD83D\uDCDB")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Pixel RankHeads:::") || r.getName().equals("::Pixel Sr-Admin::") || r.getName().equals("::Pixel Admin::") || r.getName().equals("::Pixel Sr-Mod::")) {
                        RankTester = 1;
                    }
                }
            }
        }
        if (emojichannel.equals("631320958262837259")) {// Vanilla Staff
            if (event.getReactionEmote().getName().equals("\uD83D\uDCDB")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Vanilla RankHeads:::") || r.getName().equals("::Vanilla Sr-Admin::") || r.getName().equals("::Vanilla Admin::") || r.getName().equals("::Vanilla Sr-Mod::")) {
                        RankTester = 1;
                    }
                }
            }
        }

        if (emojichannel.equals("632380870795329536")) {// TestBot
            if (event.getReactionEmote().getName().equals("\uD83D\uDCDB")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::")) {
                        RankTester = 1;
                    }
                }
            }
        }
        if (RankTester == 1) {
            OTodo todo = CTodo.findByID(event.getMessageIdLong(), event.getGuild());

            Date date = new Date(todo.Timestamp);
            DateFormat formatter = new SimpleDateFormat("E ' at ' hh:mm a");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String timestamp = formatter.format(date);


            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Todo: [#" + todo.id + "]", todo.Content + "\n⠀", false);
            embed.addField("Being Worked On By:", "`" + event.getMember().getUser().getAsTag() + "`", false);
            embed.setFooter(event.getGuild().getMemberById(todo.Author).getUser().getAsTag() + " | " + timestamp, todo.AuthorURL);
            event.getChannel().editMessageById(event.getReaction().getMessageIdLong(), embed.build()).queue(message -> {
                message.clearReactions().queue();
                message.addReaction("✅").queue();
                message.addReaction("⛔").queue();
            });
        }
    }
}
