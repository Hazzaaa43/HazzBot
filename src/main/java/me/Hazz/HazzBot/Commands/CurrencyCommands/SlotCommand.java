package me.Hazz.HazzBot.Commands.CurrencyCommands;

import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Database.Objects.OUser;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SlotCommand implements ICommand {

    @Override
    public String getName() {
        return "slot";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"slots"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        try {
            OUser user = CUser.findBy(event.getMember().getUser().getIdLong());
            long Amount = Long.parseLong(newArgs[0]);
            if (user.Carrots < Amount) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "Not enough carrots to bet.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;


            }
            if (Amount <= 0) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Invalid Arguments", "Betting amount cant be 0 or below.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }
            user.Carrots -= Amount;
            event.getJDA().getGuildById("506172322332540938").getTextChannelById("607745957186240523").sendMessage(("Removed " + Amount + " carrots from `" + event.getMember().getUser().getAsTag()) + "`").queue();
            CUser.update(user);
            String[] slots = {"kiwi", "cherries", "crown", "seven", "melon", "carrot", "kiwi", "cherries"};
            Random one = new Random();
            int slot1 = one.nextInt(6) + 1;
            Random two = new Random();
            int slot2 = two.nextInt(6) + 1;
            Random three = new Random();
            int slot3 = three.nextInt(6) + 1;

            String slots1up = slots[slot1 - 1];
            String slots1 = slots[slot1];
            String slots1down = slots[slot1 + 1];

            String slots2up = slots[slot2 - 1];
            String slots2 = slots[slot2];
            String slots2down = slots[slot2 + 1];

            String slots3up = slots[slot3 - 1];
            String slots3 = slots[slot3];
            String slots3down = slots[slot3 + 1];

            int slotcount = 0;
            long winnings = 0;
            String finalmsg = "You Lost.. `" + Amount + "` carrots lost.";

            if (slots1.equalsIgnoreCase("carrot")) {
                slotcount += 1;
            }
            if (slots2.equalsIgnoreCase("carrot")) {
                slotcount += 1;
            }
            if (slots3.equalsIgnoreCase("carrot")) {
                slotcount += 1;
            }

            if (slotcount == 1) {
                winnings = Amount;
                finalmsg = "You got `1` Carrot(s). `" + winnings + "` carrots have been added to your fridge";
            }
            if (slotcount == 2) {
                winnings = Amount * 10;
                finalmsg = "You got `2` Carrot(s). `" + winnings + "` carrots have been added to your fridge";
            }
            if (slotcount == 3) {
                winnings = Amount * 50;
                finalmsg = "You Won the Jackpot!! `" + winnings + "` carrots have been added to your fridge";
            }
            if (slot1 == slot2) {
                if (slot2 == slot3) {
                    if (slot3 == slot1) {
                        winnings = Amount * 15;
                        finalmsg = "You Won! `" + winnings + "` carrots have been added to your fridge";
                    }
                }
            }

            user.Carrots += winnings;
            if (winnings > 0) {
                event.getJDA().getGuildById("506172322332540938").getTextChannelById("607745957186240523").sendMessage(("Added " + winnings + " carrots to `" + event.getMember().getUser().getAsTag()) + "`").queue();
            }
            CUser.update(user);
            String newtotal = "\n**New Total:** `" + user.Carrots + "`";

            String SlotOutput = "|\t:white_small_square:\t|\t:white_small_square:\t|\t:white_small_square:\t|\n|\t:white_small_square:\t|\t:white_small_square:\t|\t:white_small_square:\t|\n|\t:white_small_square:\t|\t:white_small_square:\t|\t:white_small_square:\t|\n";

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Slot Machine", SlotOutput, false);
            embed.addField("Bet: `" + Amount + "`", "⠀", false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            Thread.sleep(500);

            String SlotOutput1 = "|\t:" + slots1up + ":\t|\t:white_small_square:\t|\t:white_small_square:\t|\n|\t:" + slots1 + ":\t|\t:white_small_square:\t|\t:white_small_square:\t|\n|\t:" + slots1down + ":\t|\t:white_small_square:\t|\t:white_small_square:\t|\n";

            EmbedBuilder part1 = new EmbedBuilder();
            part1.setColor(new Color(0x3498db));
            part1.addField("Slot Machine", SlotOutput1, false);
            part1.addField("Bet: `" + Amount + "`", "⠀", false);
            part1.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            Thread.sleep(500);

            String SlotOutput2 = "|\t:" + slots1up + ":\t|\t:" + slots2up + ":\t|\t:white_small_square:\t|\n|\t:" + slots1 + ":\t|\t:" + slots2 + ":\t|\t:white_small_square:\t|\n|\t:" + slots1down + ":\t|\t:" + slots2down + ":\t|\t:white_small_square:\t|\n";

            EmbedBuilder part2 = new EmbedBuilder();
            part2.setColor(new Color(0x3498db));
            part2.addField("Slot Machine", SlotOutput2, false);
            part2.addField("Bet: `" + Amount + "`", "⠀", false);
            part2.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            Thread.sleep(500);

            String SlotOutput3 = "|\t:" + slots1up + ":\t|\t:" + slots2up + ":\t|\t:" + slots3up + ":\t|\n|\t:" + slots1 + ":\t|\t:" + slots2 + ":\t|\t:" + slots3 + ":\t|\n|\t:" + slots1down + ":\t|\t:" + slots2down + ":\t|\t:" + slots3down + ":\t|\n";
            EmbedBuilder part3 = new EmbedBuilder();
            part3.setColor(new Color(0x3498db));
            part3.addField("Slot Machine", SlotOutput3, false);
            part3.addField("Bet: `" + Amount + "`", finalmsg + newtotal, false);
            part3.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue(msg -> {
                msg.editMessage(part1.build()).queueAfter(1, TimeUnit.SECONDS);
                msg.editMessage(part2.build()).queueAfter(2, TimeUnit.SECONDS);
                msg.editMessage(part3.build()).queueAfter(3, TimeUnit.SECONDS);
                msg.delete().queueAfter(15, TimeUnit.SECONDS);
            });
        } catch (Throwable e) {
            e.printStackTrace();
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "slot [Amount]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}
