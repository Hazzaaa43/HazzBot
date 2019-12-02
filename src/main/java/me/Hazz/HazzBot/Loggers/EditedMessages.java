package me.Hazz.HazzBot.Loggers;

import me.Hazz.HazzBot.Database.Controllers.CMessageLogger;
import me.Hazz.HazzBot.Database.Objects.OMessageLogger;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EditedMessages extends ListenerAdapter {
    public void onGuildMessageUpdate (GuildMessageUpdateEvent event) {
        try {
            long ID = event.getMessageIdLong();
            Connection c = WebDb.get().getConnection();
            Statement st = c.createStatement();
            ResultSet resultset=st.executeQuery("SELECT * FROM MessageLogger WHERE MessageID="+ ID);
            while(resultset.next()) {
                OMessageLogger message = new OMessageLogger();
                message.id = resultset.getInt("id");
                message.MessageID = resultset.getLong("MessageID");
                message.Guild = resultset.getLong("Guild");
                message.Author = resultset.getLong("Author");
                message.Content = resultset.getString("Content");
                message.ImageName = resultset.getString("ImageName");
                message.MessageDate = resultset.getLong("MessageDate");
                message.ChannelID = resultset.getLong("ChannelID");
                message.ChannelName = resultset.getString("ChannelName");

                Date date = new Date(message.MessageDate);
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy']` `['HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String timestamp = formatter.format(date);

                event.getJDA().getGuildById("506172322332540938").getTextChannelById("609869630504435754").sendMessage("`[" + timestamp + "]` :page_facing_up: " + event.getJDA().getGuildById(message.Guild).getMemberById(message.Author).getUser().getAsTag() + " [ID:" +message.Author  + "]'s message has been edited in <#" + message.ChannelID + ">:").queue();



                String OldMessage = StringUtils.abbreviate(message.Content, 1000);
                String NewMessage = StringUtils.abbreviate(event.getMessage().getContentDisplay(), 1000);

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x00ff00));
                embed.addField("Old:", "`" + OldMessage + "`" , true);
                embed.addField("New:", "`" + NewMessage + "`" , true);
                event.getJDA().getGuildById("506172322332540938").getTextChannelById("609869630504435754").sendMessage(embed.build()).queue();

                CMessageLogger.editMessage(event.getMessage());

            }
        } catch (Throwable e) {
        }
    }
}

