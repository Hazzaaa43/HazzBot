package me.Hazz.HazzBot.Commands.ComplexCommands.IssueCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class IssueCommand implements ICommand {

    @Override
    public String getName() {
        return "issue";
    }

    @Override
    public String[] getAliases() {
        return new String[]{""};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        if (!event.getChannel().getId().equals("646554072253464576")){ //Test Bot
            return true;
        }
        try {
            String IGN = newArgs[0].substring(0, 1).toUpperCase().replaceAll("[\\[\\](){}]", "") + newArgs[0].substring(1).toLowerCase().replaceAll("[\\[\\](){}]", "");
            String Type = newArgs[1].toUpperCase();

            StringBuilder content = new StringBuilder();
            StringBuilder url = new StringBuilder();
            for (int i = 2; i < newArgs.length; i++) {
                if (newArgs[i].toLowerCase().contains("https://") || newArgs[i].toLowerCase().contains("http://")) {
                    url.append(("\n" + newArgs[i]));
                } else {
                    content.append(newArgs[i] + " ");
                }
            }
            String Reason = content.toString();
            String Screenshot = url.toString();

            if (Reason.isEmpty()){
                throw new Throwable("Empty Issue");
            }
            if (Screenshot.isEmpty()){
                Screenshot = "\nNONE";
            }
            String Colors = "";
            if (Type.equals("GOOD")){
                Colors = "#00ff00";
            } else if (Type.equals("BAD")){
                Colors = "#ff0000";
            } else {
                Colors = "#3498db";
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.decode(Colors));
            embed.setTitle("Staff Issue Report");
            embed.addField("**Staff IGN**:", "`" + IGN + "`", true);
            embed.addField("**Type**:", "`"+ Type + "`", true);
            embed.addField("**Reason**:", "`" + Reason.trim() + "`", false);
            embed.addField("**Screenshot(s)**:", Screenshot, false);
            embed.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            embed.setTimestamp(event.getMessage().getTimeCreated().toInstant());
            event.getChannel().sendMessage(embed.build()).queue();

        } catch (Throwable e){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "issue [IGN] [ReportType] [Report] (Screenshot(s))`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));

        }


        return true;
    }
}