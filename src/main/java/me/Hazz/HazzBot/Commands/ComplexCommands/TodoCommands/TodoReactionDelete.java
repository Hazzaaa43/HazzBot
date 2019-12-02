package me.Hazz.HazzBot.Commands.ComplexCommands.TodoCommands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class TodoReactionDelete extends ListenerAdapter {
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getChannel().getId().equals("531017256746876928") // Pixelmon Staff
                && !event.getChannel().getId().equals("631320958262837259") // Vanilla Staff
                && !event.getChannel().getId().equals("632380870795329536")) { // Test Bot
            return;
        }

        int RankTester = 0;
        String emojichannel = String.valueOf(event.getChannel().getIdLong());
        if (emojichannel.equals("531017256746876928")) {// Pixelmon Staff
            if (event.getReactionEmote().getName().equals("✅")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Pixel RankHeads:::") || r.getName().equals("::Pixel Sr-Admin::") || r.getName().equals("::Pixel Admin::") || r.getName().equals("::Pixel Sr-Mod::")) {
                        RankTester = 1;
                    }
                }
            }
        }
        if (emojichannel.equals("631320958262837259")) {// Vanilla Staff
            if (event.getReactionEmote().getName().equals("✅")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::") || r.getName().equals(":::Vanilla RankHeads:::") || r.getName().equals("::Vanilla Sr-Admin::") || r.getName().equals("::Vanilla Admin::") || r.getName().equals("::Vanilla Sr-Mod::")) {
                        RankTester = 1;
                    }
                }
            }
        }

        if (emojichannel.equals("632380870795329536")) {// TestBot
            if (event.getReactionEmote().getName().equals("✅")) {
                List<Role> roles = event.getMember().getRoles();
                for (Role r : roles) {
                    if (r.getName().equals(":::Manager:::")) {
                        RankTester = 1;
                    }
                }
            }
        }
        if (RankTester == 1) {
            event.getChannel().deleteMessageById(event.getReaction().getMessageIdLong()).queue();
        }
    }
}
