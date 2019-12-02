package me.Hazz.HazzBot.Commands.CurrencyCommands;

import me.Hazz.HazzBot.Commands.CurrencyCommands.SubCommands.*;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import me.Hazz.HazzBot.Variables.findUserIn;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CarrotsCommand implements ICommand {

    @Override
    public String getName() {
        return "carrots";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"carrot"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) throws SQLException {

        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("HazzBotOP") || event.getAuthor().getId().equals("141377205421867008")) {
                RankTester = 1;
            }
        }

        if (newArgs.length == 0) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField(":carrot: **HazzBot Carrots Information**\nWhat are they? ",
                            "Carrots are HazzBot's Currency in Discord", false);
            embed.addField("How do I see how many carrots I have?",
                    "You can type `" + Info.PREFIX + "fridge` to see how many carrots you have.", false);
            embed.addField("How do I get them? ",
                            "- You obtain carrots by typing in channels, you have a `20%` chance each message you send to get between `1-5` carrots." +
                            "\n- You can beg for carrots using `" + Info.PREFIX + "carrots beg` and obtain between `1-100` carrots every `6 Hours`." +
                            "\n- You can gamble your carrots using `" + Info.PREFIX + "slot [Amount]` and maybe win big!", false);
            embed.addField("What are they used for? ",
                            "Although you cant use them for much currently other than gambling using slots, More are coming soon!", false);
            embed.addField("Who has the most carrots?",
                    "You can type `" + Info.PREFIX + "carrots leaderboard` to see the top 10 users.", false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
            return true;
        }
        if (newArgs[0].equalsIgnoreCase("leaderboard")){
            new LeaderboardCarrots(event);
            return true;
        }
        if (newArgs[0].equalsIgnoreCase("beg")){
            new BegCarrots(event);
            return true;
        }

        EmbedBuilder error = new EmbedBuilder();
        error.setColor(new Color(0xff0000));
        error.addField("Missing Arguments", "Correct Usage: \n`" + Info.PREFIX + "carrots [leaderboard/beg]` or\n`" + Info.PREFIX + "carrots [add/remove/set/give] [Amount] [User]`", false);
        error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        EmbedBuilder noperm = new EmbedBuilder();
        noperm.setColor(new Color(0xff0000));
        noperm.addField("No Permission", "You do not have permission to run this command.", false);
        noperm.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        try {
            String Amount = newArgs[1];
            Member name = findUserIn.findUserIn((TextChannel) event.getMessage().getChannel(), String.join(" ", newArgs[2]));
            if (name == null || name.getUser().getIdLong() == 0) {
                name = event.getMember();
            }
            if (newArgs[0].equalsIgnoreCase("add")) {
                if(RankTester == 1) {
                    new AddCarrots(Amount, name, event);
                } else {
                    event.getChannel().sendMessage(noperm.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                }
            }
            else if (newArgs[0].equalsIgnoreCase("remove")) {
                if(RankTester == 1) {
                    new RemoveCarrots(Amount, name, event);
                } else {
                    event.getChannel().sendMessage(noperm.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                }
            }
            else if (newArgs[0].equalsIgnoreCase("set")) {
                if(RankTester == 1) {
                    new SetCarrots(Amount, name, event);
                } else {
                    event.getChannel().sendMessage(noperm.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                }
            } else if (newArgs[0].equalsIgnoreCase("give")){
                new GiveCarrots(Amount, name, event);
            }
            else {
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}

