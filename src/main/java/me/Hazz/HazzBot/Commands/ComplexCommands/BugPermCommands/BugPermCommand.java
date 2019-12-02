package me.Hazz.HazzBot.Commands.ComplexCommands.BugPermCommands;

import me.Hazz.HazzBot.Database.Controllers.CBugPerm;
import me.Hazz.HazzBot.Database.Objects.OBugPerm;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BugPermCommand implements ICommand {

    @Override
    public String getName() {
        return "bugperm";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        Guild msgguild = event.getGuild();
        if (!event.getChannel().getId().equals("591320473875513350") // Pixelmon Staff
                && !event.getChannel().getId().equals("631321837216727040") // Vanilla Staff
                && !event.getChannel().getId().equals("632388830674157618")){ // Test Bot
            return true;
        }
        try {
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < newArgs.length; i++) {
                content.append(newArgs[i] + " ");
            }
            if (content.length() == 0) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "bugperm [bugperm]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }


            String contents = StringUtils.abbreviate(content.toString(), 900);
            CBugPerm.addMessage(event.getMessage(), contents);

            TimeUnit.MILLISECONDS.sleep(100);

            OBugPerm bugperm = CBugPerm.findByID(event.getMessage().getIdLong(), msgguild);

            if (bugperm.id == 0){
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Command Failed", "Your bugperm caused an error!\nPlease double check your input to see if its got any broken symbols!\nCorrect Usage: `" + Info.PREFIX + "bugperm [bugperm]`", false);
                String Message = event.getMessage().getContentRaw();
                error.addField("Errored Content: ", "`" + Message + "`\nPlease copy, fix, and resend. This message will delete in 20 seconds.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(20, TimeUnit.SECONDS));
                return true;
            }

            String guild = "";
            String channel = "";
            if (event.getGuild().getId().equals("345602641008525312")) {// Staff Pixelmon
                guild = "345602641008525312";
                channel = "621772615383515136";
            } else if (event.getGuild().getId().equals("631273365612789770")) {// Staff Vanilla
                guild = "631273365612789770";
                channel = "631321780455211008";
            } else if (event.getGuild().getId().equals("506172322332540938")) {// Bot Test
                guild = "506172322332540938";
                channel = "632380893217947658";
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Bug and Perm Report: [#" + bugperm.id + "]", bugperm.Content  + "\n⠀", false);
            embed.addField("Reaction Options", "<:greentick:591321183413338141> - Resolved\n<:greytick:591321258457825296> - Needs Replicating\n<:redtick:591321268146667523> - Needs Fixing\n⛔ - Not Replicable", false);
            embed.setFooter(event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            Date timestamp = new Date(bugperm.Timestamp);
            embed.setTimestamp(timestamp.toInstant());

            event.getJDA().getGuildById(guild).getTextChannelById(channel).sendMessage(embed.build()).queue(message -> {
                bugperm.MessageID = message.getIdLong();
                CBugPerm.update(bugperm, msgguild);
                Guild guildreact = event.getJDA().getGuildById("345602641008525312");
                message.addReaction(guildreact.getEmoteById("591321183413338141")).queue();
                message.addReaction(guildreact.getEmoteById("591321258457825296")).queue();
                message.addReaction(guildreact.getEmoteById("591321268146667523")).queue();
                message.addReaction("⛔").queue();
        });

    } catch (NullPointerException | ArrayIndexOutOfBoundsException | InterruptedException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "bugperm [bugperm]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}