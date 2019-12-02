package me.Hazz.HazzBot.Commands.InfoCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class ChangeLogCommand implements ICommand {

    @Override
    public String getName() {
        return "changelog";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("HazzBot Change Log", "**Current Version:** `" + Info.VERSION + "`", false);
        embed.addField("\nVersion `3.5.3` [11/08]", "- Added a wrong order error catch to report\n- moved reports from fields to description to allow more characters\n- added a few error catches for logging deleted messages\n- caught an issue with ping logger not sending correctly", false);
        embed.addField("\nVersion `3.5.2` [10/21]", "- Fixed a few bugged things with carrots.\n- Changed carrots saves from intergers to long, meaning it wont max", false);
        embed.addField("\nVersion `3.5.1` [10/16]", "- Updated how timestamps are dealt with on all server commands\n- Fixed `movesuggest` not being able to find the author when moving \n- Added message logger to a new thread to stop it from hardlocking the bot\n- Changed how a few small messages looked\n- Added a error catch for move suggestions if it doesnt exist\n- Fixed a few issues with `carrots give`\n- Updated the `quote` message with new formatting\n- Fixed a few Unknown Message Errors\n- Cleaned up a few sections of the report class\n- Added `fact` command", false);
        embed.addField("\nVersion `3.5` [10/11]", "- Completely revamped how suggestions, todo, bugperm, and report works database wise. All now in its own table, counters will be server specific\n- Added `carrots give` command to gift others carrots.", false);
        embed.addField("\nVersion `3.4.5` [9/30]", "- Removed that very custom emote reaction from pings as it was getting annoying\n- now checks permisisons for `@everyone` and `@here` pings as it would log even if user didnt have perm to tag all\n- fixed the issue with players to watch where if the reporter was no longer staff it would error\n- made underscores save with a blackslash to stop markdown\n- Fixed a weird emote error with econ suggestion reactions", false);
        embed.addField("\nVersion `3.4.4` [9/24]", "- Added a very custom emote reaction to when Hazz recieves any ping\n- Fixed mp4 links not working correctly with the embed message", false);
        embed.addField("\nVersion `3.4.3` [9/23]", "- Fixed a few issues with ping logger\n- Added `promote` and `demote` commands for pixelmon discord\n- Added new error messages for suggest and ecosuggest when it cant save the data\n- Made viewreport tell you if it could find anything.\n- Removed the need for pricer in the main discord, now looks in the staff discord for the role", false);
        embed.addField("\nVersion `3.4.2` [9/22]", "- Added Roles, `@here`, and `@everyone` to Hazz's Ping Logger", false);
        embed.addField("\nVersion `3.4.1` [9/12]", "- Added `changereport` Commands for Staff Discord\n - Fixed a big error with commands not logging correctly", false);
        embed.addField("\nVersion `3.4` [9/10]", "- Added `report`, `viewreport`, `edireport`, and `delreport` Commands for Staff Discord", false);
        embed.addField("\nVersion `3.3` [9/9]", "- Added `ecosuggest`, `editecosuggest`, and `delecosuggest` Commands for Main Discord", false);
        embed.addField("\nVersion `3.2` [9/5]", "- Added `todo` and `bugperm` Commands for Staff Discord \n- Added new Help sections", false);
        embed.addField("\nVersion `3.1.1` [8/31]", "- Updated to JDA 4.0, fixed all issues", false);
        embed.addField("Version `3.1` [8/30]", "- Remade Complex Suggestions, Adding 4 new commands [`suggest`, `editsuggest`, `delsuggest`, `movesuggest`]"
                + "\n- Added `changelog` Command"
                + "\n- Added `serverstats` Command"
                + "\n- Added `advice` Command"
                + "\n- Added `dadjoke` Command"
                + "\n- Added `define` Command"
                + "\n- Added `8ball` Command"
                + "\n- Added `poll` Command"
                + "\n- Added `randomcolor` Command"
                + "\n- Added `rockpaperscissors/rps` Command"
                + "\n- Added `topic` Command"
                + "\n- Added `wouldyourather/wyr` Command"
                + "\nCheck `" + Info.PREFIX + "help` for command information and parameters"
                + "\n- Fixed Many Errors to do with logging suggestions/messages"
                + "\n- Fixed a few NullPointers here and there", false);
        embed.addField("\nVersion `3.0` [7/5]", "- Completely remade HazzBot from Python to JDA 3.8.3", false);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
        return true;
    }
}

