package me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands;

import me.Hazz.HazzBot.Database.Controllers.CSuggestions;
import me.Hazz.HazzBot.Database.Objects.OSuggestions;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SuggestionCommand  implements ICommand {

    @Override
    public String getName() {
        return "suggest";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        if(!event.getChannel().getId().equals("560277651743309855")// Main Pixelmon
                && !event.getChannel().getId().equals("553047585519173660") // Staff Pixelmon
                && !event.getChannel().getId().equals("631291784168996894") // Main Vanilla
                && !event.getChannel().getId().equals("631325481727295488") // Staff Vanilla
                && !event.getChannel().getId().equals("632380827270905896") ){ // Bot Test
            return true;
        }

        try {
            Guild msgguild = event.getGuild();
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < newArgs.length; i++) {
                content.append(newArgs[i] + " ");
            }
            if (content.length() == 0){
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "suggest [Suggestion]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }

            String contents = StringUtils.abbreviate(content.toString(), 900);
            CSuggestions.addMessage(event.getMessage(), contents);

            TimeUnit.MILLISECONDS.sleep(100);

            OSuggestions suggestion = CSuggestions.findByID(event.getMessage().getIdLong(), msgguild);

            if (suggestion.id == 0){
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Command Failed", "Your suggestion caused an error!\nPlease double check your suggestion to see if its got any broken symbols!\nCorrect Usage: `" + Info.PREFIX + "suggest [Suggestion]`", false);
                String Message = event.getMessage().getContentRaw();
                error.addField("Errored Content: ", "`" + Message + "`\nPlease copy, fix, and resend. This message will delete in 20 seconds.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(20, TimeUnit.SECONDS));
                return true;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Suggestion: [#" + suggestion.id + "]", suggestion.Content, false);
            embed.setFooter(event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            Date timestamp = new Date(suggestion.Timestamp);
            embed.setTimestamp(timestamp.toInstant());
            String guild = "";
            String channel = "";
            if (event.getGuild().getId().equals("374350672981655571")) {// Main Pixelmon
                guild = "374350672981655571";
                channel = "615659198746722346";
            } else if (event.getGuild().getId().equals("345602641008525312")) {// Staff Pixelmon
                guild = "345602641008525312";
                channel = "451587773229432833";
            } else if (event.getGuild().getId().equals("631273255180959767")) {// Main Vanilla
                guild = "631273255180959767";
                channel = "631291677117775882";
            } else if (event.getGuild().getId().equals("631273365612789770")) {// Staff Vanilla
                guild = "631273365612789770";
                channel = "631323714142863372";
            } else if (event.getGuild().getId().equals("506172322332540938")) {// Bot Test
                guild = "506172322332540938";
                channel = "632380748380241930";
            }
            event.getChannel().sendMessage("Your suggestion has been submitted.").queue(msg -> msg.delete().queueAfter(3, TimeUnit.SECONDS));
            event.getJDA().getGuildById(guild).getTextChannelById(channel).sendMessage(embed.build()).queue(message -> {
                OSuggestions suggestions = CSuggestions.findByID(event.getMessage().getIdLong(), msgguild);
                suggestions.MessageID = message.getIdLong();
                CSuggestions.update(suggestions, msgguild);
                message.addReaction(event.getJDA().getGuildById("374350672981655571").getEmoteById("578572720749674511")).queue();
                message.addReaction(event.getJDA().getGuildById("374350672981655571").getEmoteById("578572709236178982")).queue();
            });

        }catch(NullPointerException | InterruptedException | ArrayIndexOutOfBoundsException e){
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "suggest [Suggestion]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}