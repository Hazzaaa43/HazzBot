package me.Hazz.HazzBot.Commands.InfoCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ServerStatCommand implements ICommand {

    @Override
    public String getName() {
        return "serverstats";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"serverstat"};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {

        int i = event.getGuild().getMembers().size();
        int count = 0;
        int botcount = 0;
        while(i > 0){
            if (!event.getGuild().getMembers().get(i-1).getOnlineStatus().equals(OnlineStatus.OFFLINE)){
                count++;
            }
            if(event.getGuild().getMembers().get(i-1).getUser().isBot()){
                botcount++;
            }
            i--;
        }

        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0x3498db));
        embed.addField("Guild Information for " + guild.getName(), "\n:id: Guild ID: `" + guild.getId() + "`" + "\n:globe_with_meridians: Region: `" + guild.getRegion() + "`", false);
        embed.addField("Members", count + " online\n"  + guild.getMembers().size() + " in total\n" + botcount + " are bots", true);
        embed.addField("Channels",  guild.getCategories().size() + " categories\n" + guild.getTextChannels().size() + " text channels\n" + guild.getVoiceChannels().size() + " voice channels", true);
        embed.addField("Roles", guild.getRoles().size() + " roles", true);
        embed.addField("Emotes", guild.getEmotes().size() + " emotes", true);
        embed.addField("Created By", "Name: `" + guild.getOwner().getUser().getAsTag() + "`\nID: `"+ guild.getOwnerId() + "`", true);
        embed.addField("Created On", guild.getTimeCreated().format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a")), true);
        embed.addField("HazzBot Server Info","\n:oncoming_police_car: Administator: `" + guild.getSelfMember().hasPermission(Permission.ADMINISTRATOR) + "`\n:floppy_disk: Server Prefix: `" + Info.PREFIX + "`", false);
        embed.setThumbnail(event.getGuild().getIconUrl());
        embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
        return true;
    }
}

