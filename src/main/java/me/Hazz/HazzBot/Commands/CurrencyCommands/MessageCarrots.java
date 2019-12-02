package me.Hazz.HazzBot.Commands.CurrencyCommands;

import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Database.Objects.OUser;
import me.Hazz.HazzBot.DiscordBot;
import me.Hazz.HazzBot.Interfaces.IEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class MessageCarrots implements IEvent {
    DiscordBot bot = null;

    public MessageCarrots(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public String getName() {
        return "carrot message";
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent event) {
        try {
            if(event.getMessage().getContentRaw().startsWith(".")){
                return false;
            }
            Random msg = new Random();
            int percent = msg.nextInt(5) + 1;
            Random msg2 = new Random();
            int amount = msg2.nextInt(5) + 1;
            if (event.getMember().getUser().isBot()) {
                return false;
            }
            if (percent == 1) {
                OUser user = CUser.findBy(event.getMember().getUser().getIdLong());
                user.Carrots += amount;
                CUser.update(user);
                event.getJDA().getGuildById("506172322332540938").getTextChannelById("607745957186240523").sendMessage(("Added " + amount + " carrots to `" + event.getMember().getUser().getAsTag()) + "`").queue();
            }
        }catch (NullPointerException e){
            e.getMessage();
            e.getCause();
        }
            return false;
    }
}