package me.Hazz.HazzBot.Commands.ComplexCommands.TodoCommands;

import me.Hazz.HazzBot.Database.Controllers.CTodo;
import me.Hazz.HazzBot.Database.Objects.OTodo;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TodoCommand implements ICommand {

    @Override
    public String getName() {
        return "todo";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }

    @Override
    public boolean execute(String[] newArgs, GuildMessageReceivedEvent event) {
        if (!event.getChannel().getId().equals("531017256746876928") // Pixelmon Staff
                && !event.getChannel().getId().equals("631320958262837259") // Vanilla Staff
                && !event.getChannel().getId().equals("632380870795329536")){ // Test Bot
            return true;
        }
        try {
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < newArgs.length; i++) {
                content.append(newArgs[i] + " ");
            }
            if (content.length() == 0) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "todo [todo]`", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                return true;
            }

            String contents = StringUtils.abbreviate(content.toString(), 900);
            CTodo.addMessage(event.getMessage(), contents);

            TimeUnit.MILLISECONDS.sleep(100);

            OTodo todo = CTodo.findByID(event.getMessage().getIdLong(), event.getGuild());

            if (todo.id == 0){
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(new Color(0xff0000));
                error.addField("Command Failed", "Your todo caused an error!\nPlease double check your input to see if its got any broken symbols!\nCorrect Usage: `" + Info.PREFIX + "todo [todo]`", false);
                String Message = event.getMessage().getContentRaw();
                error.addField("Errored Content: ", "`" + Message + "`\nPlease copy, fix, and resend. This message will delete in 20 seconds.", false);
                error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(20, TimeUnit.SECONDS));
                return true;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0x3498db));
            embed.addField("Todo: [#" + todo.id + "]", todo.Content, false);
            embed.setFooter(event.getMember().getUser().getAsTag(), event.getAuthor().getAvatarUrl());
            Date timestamp = new Date(todo.Timestamp);
            embed.setTimestamp(timestamp.toInstant());

            event.getChannel().sendMessage(embed.build()).queue(message -> {
                todo.MessageID = message.getIdLong();
                CTodo.update(todo, event.getGuild());
                message.addReaction("✅").queue();
                message.addReaction("\uD83D\uDCDB").queue();
            });



        } catch (NullPointerException | ArrayIndexOutOfBoundsException | InterruptedException e) {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(new Color(0xff0000));
            error.addField("Missing Arguments", "Correct Usage: `" + Info.PREFIX + "todo [todo]`", false);
            error.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendMessage(error.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
        return true;
    }
}