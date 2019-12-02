package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpeakCommand implements ICommand {

    @Override
    public String getName() {
        return "speak";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("Admin") || r.getName().equals("HazzBotOP") || event.getAuthor().getId().equals("141377205421867008")) {
                RankTester = 1;
            }
        }
        try {
            long ChannelID = Long.parseLong(newArgs[1]);
            String prefix = null;

            if (newArgs[0].equalsIgnoreCase("raw")) {
                prefix = "";
            } else if (newArgs[0].equalsIgnoreCase("say")) {
                prefix = "**" + event.getMessage().getAuthor().getName() + "**: ";
            }
            if (prefix != null) {
                TextChannel textChannel = event.getGuild().getTextChannelById(ChannelID);

                StringBuilder FinalString = new StringBuilder();
                for (int i = 2; i < newArgs.length; i++) {
                    FinalString.append(newArgs[i]).append(" ");
                }
                if (RankTester == 1) {
                    textChannel.sendMessage(prefix + FinalString).queue();
                } else {
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(new Color(0xff0000));
                    error.addField("No Permission", "You do not have permission to run this command.", false);
                    error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                }

            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "speak [raw/say] [ChannelID] [Message]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

        }
        return true;
    }
}
