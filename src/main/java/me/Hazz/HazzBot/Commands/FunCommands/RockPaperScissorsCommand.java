package me.Hazz.HazzBot.Commands.FunCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RockPaperScissorsCommand implements ICommand {

    @Override
    public String getName() {
        return "rockpaperscissors";
    }


    @Override
    public String[] getAliases() {
        return new String[]{"rps"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        try {
            Random random = new Random();
            int CompInput = random.nextInt(3);
            String CompOutput = "";

            if (CompInput == 0) {
                CompOutput = "Rock";
            } else if (CompInput == 1) {
                CompOutput = "Paper";
            } else if (CompInput == 2) {
                CompOutput = "Scissors";
            }

            String UserInput = newArgs[0];
            String UserOutput = "";

            if (UserInput.equalsIgnoreCase("R") || UserInput.equalsIgnoreCase("Rock")) {
                UserOutput = "Rock";
            } else if (UserInput.equalsIgnoreCase("P") || UserInput.equalsIgnoreCase("Paper")) {
                UserOutput = "Paper";
            } else if (UserInput.equalsIgnoreCase("S") || UserInput.equalsIgnoreCase("Scissors")) {
                UserOutput = "Scissors";
            }

            String Output = "";

            if (CompOutput.equals(UserOutput)) {
                Output = "Its a tie!";
            } else if (UserOutput.equals("Rock")) {
                if (CompOutput.equals("Scissors")) {
                    Output = "Rock crushes Scissors. You Win!";
                } else if (CompOutput.equals("Paper")) {
                    Output = "Paper covers Rock. You Lose!";
                }
            } else if (UserOutput.equals("Paper")) {
                if (CompOutput.equals("Rock")) {
                    Output = "Paper covers Rock. You Win!";
                } else if (CompOutput.equals("Scissors")) {
                    Output = "Scissors cuts Paper. You Lose!";
                }
            } else if (UserOutput.equals("Scissors")) {
                if (CompOutput.equals("Paper")) {
                    Output = "Scissors cuts Paper. You Win!";
                } else if (CompOutput.equals("Rock")) {
                    Output = "Rock crushes Scissors. You Lose!";
                }
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Rock Paper Scissors","You Said: `" + UserOutput + "`\n Computer Said: `" + CompOutput + "`\n\n`"+ Output + "`", false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();


    }catch(ArrayIndexOutOfBoundsException e){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "rps [Rock/R / Paper/P / Scissors/S]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }

        return true;
    }
}


