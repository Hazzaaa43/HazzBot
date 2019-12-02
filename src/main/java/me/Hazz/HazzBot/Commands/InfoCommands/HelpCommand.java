package me.Hazz.HazzBot.Commands.InfoCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class HelpCommand implements ICommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("HazzBot " + Info.VERSION + " - Command Help List",
                "**Command Prefix:** `" + Info.PREFIX + "`\n`() - optional | [] required`", false);
        embed.addField("**Info Commands**",
                "\n`changelog.....................` - Shows HazzBots change log history"
                        + "\n`help..........................` - Prints a list of commands"
                        + "\n`info..........................` - Shows info about the bot and why it exists"
                        + "\n`ping..........................` - Shows bot ping to discord"
                        + "\n`serverstats...................` - Shows server information"
                        + "\n`uptime........................` - Shows the bots time since last reset"
                        + "\n`whois (User)..................` - Shows all user info"
                        + "\n⠀", false);

        embed.addField("**Fun Commands**",
                "\n`advice........................` - Gives random advice"
                        + "\n`age (User)....................` - Shows your age"
                        + "\n`coinflip......................` - Flips a coin"
                        + "\n`dadjoke.......................` - Gives a random Dad Joke"
                        + "\n`define [Word].................` - Defines a word from Urban Dictionary"
                        + "\n`howgay (User).................` - Shows your gay level"
                        + "\n`insult (User).................` - Sends a random insult"
                        + "\n`8ball [Question]..............` - Ask the magic 8-ball a question"
                        + "\n`poll [title] [1] [2] (3-10)...` - Creates a votable poll message", false);
        embed.addField("",
                        "\n`ppsize (User).................` - Shows your PP size"
                        + "\n`quote [MessageID].............` - Quotes a sent message"
                        + "\n`randomcolor...................` - Random Color with HEX and RGB"
                        + "\n`random [#1] [#2]..............` - Random Number between two numbers"
                        + "\n`rockpaperscissors/rps [R/P/S].` - Play Rock/Paper/Scissors against the bot"
                        + "\n`spam [Amount] [Time] [Msg]....` - Sends a message X times every Y seconds"
                        + "\n`speak [raw/say] [ChID] [Msg]..` - Sends a message to a channel"
                        + "\n`topic.........................` - Gives a random conversation topic"
                        + "\n`wouldyourather/wyr............` - Gives a random would you rather question"
                        + "\n⠀", false);

        embed.addField("**Currency Commands**",
                "\n`carrots.......................` - Shows information about the currency"
                        + "\n` '' add [Amount] [User]........` - Adds Carrots to a user"
                        + "\n` '' remove [Amount] [User].....` - Removes Carrots from a user"
                        + "\n` '' set [Amount] [User]........` - Sets a users Carrots"
                        + "\n` '' give [Amount] [User].......` - Gives another user Carrots"
                        + "\n` '' beg........................` - Gives you 1-100 carrots, 6 hour cooldown"
                        + "\n` '' leaderboard................` - Shows 10 highest carrot count users"
                        + "\n`fridge (User).................` - Shows the users amount of carrots"
                        + "\n`slot [Amount].................` - Gamble machine for carrots"
                        + "", false);


        if(event.getGuild().getId().equals("374350672981655571") || event.getGuild().getId().equals("345602641008525312") || event.getGuild().getId().equals("506172322332540938")){
            embed.addField("⠀\n**Complex Pixelmon Commands**",
                    "\n`suggest [Suggestion]..........` - Sends a suggestion in #server-suggestions"
                            + "\n`delsuggest [#]................` - Deletes a specific suggest from the channel"
                            + "\n`editsuggest [#] [Edit]........` - Edits a specific suggestion [STAFF]"
                            + "\n`movesuggest [#]...............` - Moves a suggest to the higher ups [ADMIN]"
                            + "\n`ecosuggest [Suggestion].......` - Sends an econ suggest in #econ-suggestions"
                            + "\n`delecosuggest [#].............` - Deletes a specific econ suggestion"
                            + "\n`editecosuggest [#] [Edit].....` - Edits a specific econ suggest [PRICER]"
                            + "\n`promote [User] [Role].........` - Adds a role to a user [T.MOD]"
                            + "\n`demote [User] [Role]..........` - Removes a role from a user [T.MOD]", false);
        }
        if(event.getGuild().getId().equals("345602641008525312") || event.getGuild().getId().equals("506172322332540938")){
            embed.addField("⠀\n**Complex Staff Commands**",
                    "\n`todo [todo]...................` - Submits a todo in #pixel-to-do"
                            + "\n`suggest [Suggestion]..........` - Sends a suggestion in #pixel-suggestions"
                            + "\n`bugperm [Bugperm].............` - Sends a report in #bug-or-perm-report"
                            + "\n`report [IGN] [Server] [Info]..` - Sends a player report in #players-to-watch"
                            + "\n`viewreport [#/IGN]............` - Views all info on a report or user"
                            + "\n`delreport [#].................` - Deletes a specific Player report [SRMOD]"
                            + "\n`editreport [#] [Edit].........` - Edits a specific Player report [SRMOD]"
                            + "\n`changereport [Old] [New]......` - Edits a specific Players IGN [SRMOD]", false);
        }

        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());

        EmbedBuilder mail = new EmbedBuilder();
        mail.setColor(new Color(0x3498db));
        mail.addField("You've Got Mail", "Check your DMs, HazzBots Help Menu has been sent to you!", false);
        mail.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        event.getChannel().sendMessage(mail.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        event.getMember().getUser().openPrivateChannel().queue((channel) -> channel.sendMessage(embed.build()).queue());

        return true;
    }
}
