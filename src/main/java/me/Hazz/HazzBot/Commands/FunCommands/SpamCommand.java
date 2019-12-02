package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpamCommand implements ICommand {

    @Override
    public String getName() {
        return "spam";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        int RankTester = 0;
        event.getMessage().delete().queue();
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("HazzBotOP") || event.getAuthor().getId().equals("141377205421867008")) {
                RankTester = 1;
            }
        }
        try {
            int Amount = Integer.parseInt(newArgs[0]);
            int Time = Integer.parseInt(newArgs[1]);

            if (Amount <= 0) {
                EmbedBuilder error2 = new EmbedBuilder();
                error2.setColor(new Color(0xff0000));
                error2.addField("Invalid Argument", "Amount of Messages is too low.", false);
                error2.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error2.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            } else if (Amount > 100) {
                EmbedBuilder error3 = new EmbedBuilder();
                error3.setColor(new Color(0xff0000));
                error3.addField("Invalid Argument", "Amount of Messages is too high.", false);
                error3.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error3.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }
            if (Time <= 0) {
                EmbedBuilder error4 = new EmbedBuilder();
                error4.setColor(new Color(0xff0000));
                error4.addField("Invalid Argument", "Time in between messages is too low.", false);
                error4.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error4.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }

            StringBuilder FinalString = new StringBuilder();
            for (int i = 2; i < newArgs.length; i++) {
                FinalString.append(newArgs[i]).append(" ");
            }


            if (RankTester == 1) {
                for (int i = Amount; i > 0; i--) {
                    event.getChannel().sendMessage(FinalString).queue();
                    Thread.sleep(Time * 1000);
                }

            } else {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("No Permission", "You do not have permission to run this command.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }


        } catch (ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException | InterruptedException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "spam [Amount] [Time] [Message]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

        }
        return true;
    }
}

