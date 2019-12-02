package me.Hazz.HazzBot.Commands.CurrencyCommands.SubCommands;

import me.Hazz.HazzBot.Database.Controllers.CBeg;
import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Database.Objects.OBeg;
import me.Hazz.HazzBot.Database.Objects.OUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BegCarrots {
    public BegCarrots(GuildMessageReceivedEvent event) {

        OBeg beg =  CBeg.findBy(event.getMember().getUser().getIdLong());

        Date now = new Date();
        long ago = TimeUnit.MILLISECONDS.toHours(now.getTime() - beg.Timestamp);

        if(ago >= 6){
            Random random = new Random();
            int Amount = random.nextInt(100) + 1;
            OUser user = CUser.findBy(event.getMember().getUser().getIdLong());
            user.Carrots += Amount;
            event.getJDA().getGuildById("506172322332540938").getTextChannelById("607745957186240523").sendMessage(("Added " + Amount + " carrots to `" + event.getMember().getUser().getAsTag()) + "`").queue();
            CUser.update(user);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Carrots Begging","`" + Amount + "` carrots have been added to " + event.getMember().getUser().getAsMention() +
                    "\nfrom begging to the overlords! \n\nYou can receive between `1-100` carrots \nevery `6` hours from begging for them!", false);
            embed.setThumbnail("http://i.hazzaaa.com/carrotbegs.png");
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
            beg.Timestamp = now.getTime();
            CBeg.update(beg);

        } else {


            double between = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - beg.Timestamp);
            double togo = (360 - between);
            double doubletogo = togo/60;
            long hour = (long) doubletogo;

            String amount = " Hours";
            if (hour == 1 || hour == 0) {
                amount = " Hour";
            }

            String finalval = ("Just Over " + hour + amount);
            if ((double)hour == doubletogo) {
                 finalval = ("Exactly " + hour + amount);
            } else if (Math.round(doubletogo-hour) == 1){
                 finalval = ("Just Under " + (hour+1) + amount);
            } else if  (Math.round(doubletogo-hour) == 0){
                 finalval = ("Just Over " + hour + amount);
            }


            Date date = new Date(beg.Timestamp);
            DateFormat formatter = new SimpleDateFormat("hh:mm a z 'on' EEE, MMM d");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String timestamp = formatter.format(date);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Carrots Begging","You have already begged within\n the past 6 hours!\n\nYou last begged at: \n`" + timestamp + "`\n\nYou need to wait `" + finalval + "`\n before you can beg again!", false);
            embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            embed.setThumbnail("http://i.hazzaaa.com/carrotbegs.png");
            event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));
        }




    }
}