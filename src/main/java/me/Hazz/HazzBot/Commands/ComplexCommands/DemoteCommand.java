package me.Hazz.HazzBot.Commands.ComplexCommands;

import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import me.Hazz.HazzBot.Variables.findUserIn;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DemoteCommand implements ICommand {

    @Override
    public String getName() {
        return "demote";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        if (!event.getChannel().getId().equals("547289535990464512")){
            return true;
        }
        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("StaffTeam") && !r.getName().equals("Helper")) {
                RankTester = 1;
            }
        }
        if (RankTester == 1) {
            try {
                Member name = findUserIn.findUserIn(event.getJDA().getGuildById("374350672981655571").getTextChannelById("489159509617410050"), String.join(" ", newArgs[0]));
                String role = newArgs[1].toLowerCase();

                Guild guild = event.getJDA().getGuildById("374350672981655571");
                if (role.equals("ace")) {
                    Role rank = guild.getRoleById("545662169224839169");
                    guild.removeRoleFromMember(name, rank).queue();
                } else if(role.equals("elite")) {
                    Role rank = guild.getRoleById("545662270261297153");
                    guild.removeRoleFromMember(name, rank).queue();
                } else if(role.equals("master")) {
                    Role rank = guild.getRoleById("545662381662011434");
                    guild.removeRoleFromMember(name, rank).queue();
                } else if(role.equals("legend")) {
                    Role rank = guild.getRoleById("545662459600437252");
                    guild.removeRoleFromMember(name, rank).queue();
                } else if(role.equals("champion")) {
                    Role rank = guild.getRoleById("545662567197048853");
                    guild.removeRoleFromMember(name, rank).queue();
                } else if(role.equals("omega")) {
                    Role rank = guild.getRoleById("408723766676291602");
                    guild.removeRoleFromMember(name, rank).queue();
                } else if(role.equals("elite4")) {
                    Role rank = guild.getRoleById("585289345209073684");
                    guild.removeRoleFromMember(name, rank).queue();
                } else if(role.equals("pokemaster")) {
                    Role rank = guild.getRoleById("587810264192385025");
                    guild.removeRoleFromMember(name, rank).queue();
                } else {
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(new Color(0xff0000));
                    error.addField("Command Failed", "Entered role does not exist or is not able to be added!.\nAllowed Roles:\nAce, Elite, Master, Legend, Champion, Omega, Elite4, Pokemaster", false);
                    error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                    return true;
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.addField("Command Successful", "`" + role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase() + "` has been removed from `" + name.getUser().getAsTag() + "`", false);
                embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
                event.getChannel().sendMessage(embed.build()).queue();


            } catch (Throwable e) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "demote [User] [Role]`\nAllowed Roles:\nAce, Elite, Master, Legend, Champion, Omega, Elite4, Pokemaster", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }
        } else {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("No Permission", "You do not have permission to run this command.", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }

        return true;
    }
}