package me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands;

import me.Hazz.HazzBot.Database.Controllers.CSuggestions;
import me.Hazz.HazzBot.Database.Objects.OSuggestions;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MoveSuggestionCommand implements ICommand {

    @Override
    public String getName() {
        return "movesuggest";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        Guild msgguild = event.getGuild();
        int RankTester = 0;
        List<Role> roles = event.getMember().getRoles();
        for (Role r : roles) {
            if (r.getName().equals("Admin") || r.getName().equals("Manager") || r.getName().equals("::Pixel Admin::") || r.getName().equals("::Vanilla Admin::")) {
                RankTester = 1;
            }
        }


        if(!event.getChannel().getId().equals("560277651743309855")// Main Pixelmon
                && !event.getChannel().getId().equals("553047585519173660") // Staff Pixelmon
                && !event.getChannel().getId().equals("631291784168996894") // Main Vanilla
                && !event.getChannel().getId().equals("631325481727295488") // Staff Vanilla
                && !event.getChannel().getId().equals("632380827270905896") ){ // Bot Test
            return true;
        }

        if (RankTester == 1) {
            try {

                int id = Integer.parseInt(newArgs[0]);
                OSuggestions suggestion = CSuggestions.findByid(id, msgguild);

                if (suggestion.id == 0){
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(new Color(0xff0000));
                    error.addField("Incorrect Argument", "Suggestion #" + id + " does not exist.", false);
                    error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                    return true;
                }

                String server = "";

                if (event.getGuild().getId().equals("345602641008525312")) {
                    server = "From Pixelmon Staff";
                } else if (event.getGuild().getId().equals("374350672981655571")) {
                    server = "From Pixelmon Main";
                } else if (event.getGuild().getId().equals("631273365612789770")) {
                    server = "From Vanilla Staff";
                } else if (event.getGuild().getId().equals("631273255180959767")) {
                    server = "From Vanilla Main";
                } else if (event.getGuild().getId().equals("506172322332540938")) {
                    server = "From Test";
                }

                String guild = "";
                String beforeguild = "";
                String channel = "";
                if (event.getGuild().getId().equals("345602641008525312") || event.getGuild().getId().equals("374350672981655571")) {// Main/Staff Pixelmon
                    guild = "345602641008525312";
                    beforeguild = "374350672981655571";
                    channel = "615655606824730634";
                } else if (event.getGuild().getId().equals("631273255180959767") || event.getGuild().getId().equals("631273365612789770")) {// Main/Staff Vanilla
                    guild = "631273365612789770";
                    beforeguild = "631273255180959767";
                    channel = "632403079903313929";
                } else if (event.getGuild().getId().equals("506172322332540938")) {// Bot Test
                    guild = "506172322332540938";
                    channel = "632403174560366612";
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0x3498db));
                embed.addField("Suggestion: [#" + suggestion.id + "] *" + server + "*", suggestion.Content, false);
                embed.addField("Moved By:", "`" + event.getAuthor().getAsTag() + "`", false);
                embed.setFooter(event.getJDA().getGuildById(beforeguild).getMemberById(suggestion.Author).getUser().getAsTag(), suggestion.AuthorURL);
                Date timestamp = new Date(suggestion.Timestamp);
                embed.setTimestamp(timestamp.toInstant());

                event.getJDA().getGuildById(guild).getTextChannelById(channel).sendMessage(embed.build()).queue(message -> {
                    EmbedBuilder embeds = new EmbedBuilder();
                    embeds.setColor(new Color(0x3498db));
                    embeds.addField("Suggestion Moved", "You have moved suggestion #" + suggestion.id, false);
                    embeds.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(embeds.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                    event.getChannel().sendMessage(Info.PREFIX + "editsuggest " + suggestion.id + " Thanks for the suggestion!\nThis suggestion has been moved on for further Trial/Testing,\nKeep an eye out in <#399358314045243404>!\n\n__**This does not mean it will be implemented!**__").queue();
                });


                } catch (Throwable e) {
                EmbedBuilder error2 = new EmbedBuilder();
                error2.setColor(new Color(0xff0000));
                error2.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "movesuggest [SuggestionID]`", false);
                error2.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error2.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }

        } else {
            EmbedBuilder error3 = new EmbedBuilder();
            error3.setColor(new Color(0xff0000));
            error3.addField("No Permission", "You do not have permission to run this command.", false);
            error3.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error3.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}