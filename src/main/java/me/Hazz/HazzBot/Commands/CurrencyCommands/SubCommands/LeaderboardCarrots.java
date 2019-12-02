package me.Hazz.HazzBot.Commands.CurrencyCommands.SubCommands;

import me.Hazz.HazzBot.Database.Controllers.CUser;
import me.Hazz.HazzBot.Database.Objects.OUser;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaderboardCarrots {
    public LeaderboardCarrots(GuildMessageReceivedEvent event) throws SQLException {
        Connection c = WebDb.get().getConnection();
        Statement st = c.createStatement();
        ResultSet rs=st.executeQuery("SELECT DiscordID FROM Carrots ORDER BY Carrots DESC limit 10");
        StringBuilder leadernames = new StringBuilder();
        StringBuilder leadercount = new StringBuilder();
        int i = 1;
        while(rs.next()){
            long ID = Long.parseLong(rs.getString(1));
            OUser user = CUser.findBy(ID);
            User Name = event.getJDA().getUserById(ID);
            leadernames.append( i + " - " + Name.getAsMention() + "\n");
            leadercount.append("`" + user.Carrots + "`\n");
            i++;

        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("**Carrots Leaderboard**\nUsers:", String.valueOf(leadernames), true);
        embed.addField("⠀\nCarrots:", String.valueOf(leadercount), true);
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
