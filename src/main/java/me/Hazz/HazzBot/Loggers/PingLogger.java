package me.Hazz.HazzBot.Loggers;

import me.Hazz.HazzBot.DiscordBot;
import me.Hazz.HazzBot.Interfaces.IEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.List;

public class PingLogger implements IEvent {
    DiscordBot bot = null;

    public PingLogger(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public String getName() {
        return "ping logger";
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent event) {

        try {
            String msg = event.getMessage().getContentRaw();

            List<Role> userRoles = event.getGuild().getMemberById("141377205421867008").getRoles();
            List<Role> mentionedRoles = event.getMessage().getMentionedRoles();


            if (mentionedRoles.isEmpty() && !msg.contains("<@!141377205421867008>") && !msg.contains("@here") && !msg.contains("@everyone")) {
                return false;
            }

            int tagged = 0;
            for (int i = 0; i < mentionedRoles.size(); i++) {
                if (userRoles.contains(mentionedRoles.get(i))) {
                    tagged = 1;
                }
            }

            boolean PermCheck = event.getMember().hasPermission(Permission.MESSAGE_MENTION_EVERYONE);
            if (msg.contains("<@!141377205421867008>") || (msg.contains("@here") && PermCheck) || (msg.contains("@everyone") && PermCheck) || tagged == 1) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                String content = StringUtils.abbreviate(event.getMessage().getContentDisplay(), 1000);
                embed.addField("Ping Manager", "[[Message Link](" + event.getMessage().getJumpUrl() + ")]\n**Sender:** " + event.getAuthor().getAsTag() + "\n\n**From Discord:** " + event.getGuild().getName() + "\n**From Channel:** " + event.getChannel().getAsMention(), false);
                embed.addField("**Original Message:**", content, false);


                event.getJDA().getGuildById("506172322332540938").getTextChannelById("618954027832705027").sendMessage(embed.build()).queue();
            }
        }catch (Throwable e){
        }
        return false;
    }
}
