package me.Hazz.HazzBot.Handlers;

import me.Hazz.HazzBot.Commands.ComplexCommands.BugPermCommands.BugPermCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.DemoteCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands.DelEconSuggestCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands.EconSuggestCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands.EditEconSuggestCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.IssueCommands.IssueCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.PromoteCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.ReportCommands.*;
import me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands.DeleteSuggestionCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands.EditSuggestionCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands.MoveSuggestionCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.SuggestCommands.SuggestionCommand;
import me.Hazz.HazzBot.Commands.ComplexCommands.TodoCommands.TodoCommand;
import me.Hazz.HazzBot.Commands.CurrencyCommands.CarrotsCommand;
import me.Hazz.HazzBot.Commands.CurrencyCommands.FridgeCommand;
import me.Hazz.HazzBot.Commands.CurrencyCommands.SlotCommand;
import me.Hazz.HazzBot.Commands.FunCommands.*;
import me.Hazz.HazzBot.Commands.InfoCommands.*;
import me.Hazz.HazzBot.DiscordBot;
import me.Hazz.HazzBot.Interfaces.ICommand;
import me.Hazz.HazzBot.Variables.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandHandler {
    private List<ICommand> commands = new ArrayList<>();


    public boolean handle(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args == null || args.length == 0)
            args = new String[]{};

        ICommand cmd = null;

        for (ICommand c : commands) {
            if ((Info.PREFIX + c.getName()).equalsIgnoreCase(args[0])) {
                cmd = c;
                break;
            }
            for (String s : c.getAliases()) {
                if ((Info.PREFIX + s).equalsIgnoreCase(args[0])) {
                    cmd = c;
                    break;
                }
            }
        }
        if (cmd != null) {
            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            try {
                if (cmd.execute(newArgs, event)) {
                    if (cmd.getName().equals("spam")){
                        return true;
                    }
                    event.getMessage().delete().queue();
                    return true;
                }
            } catch (Throwable error) {
                error.printStackTrace();
                try {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(new Color(0x3498db));
                    StringBuilder errorMessage = new StringBuilder();
                    if (error.getMessage() != null) {
                        errorMessage.append("**Message:**\n");
                        errorMessage.append(error.getMessage()).append("\n\n");
                    }
                    StringBuilder stack = new StringBuilder();
                    int maxTrace = 5;
                    StackTraceElement[] stackTrace1 = error.getStackTrace();
                    for (int i = 0; i < stackTrace1.length; i++) {
                        StackTraceElement stackTrace = stackTrace1[i];
                        stack.append(stackTrace.toString()).append("\n");
                        if (i > maxTrace) {
                            break;
                        }
                    }

                    errorMessage.append("**Accompanied stacktrace:**\n```java\n\n").append(stack.toString()).append("\n\n```\n");
                    String message = errorMessage.toString();
                    message = message.length() > 1000 ? message.substring(0, 1000 - 1) : message;
                    embed.addField(new MessageEmbed.Field("I've encountered a **" + error.getClass().getName() + "**", message, false));
                    embed.setFooter("Command ran by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
                    event.getChannel().sendMessage(embed.build()).queue(msg -> msg.delete().queueAfter(7, TimeUnit.SECONDS));
                } catch (Throwable ignored) {
                }
                return true;
            }
        }

        return false;
    }

    public CommandHandler(DiscordBot m) {

        //Info
        commands.add(new ChangeLogCommand());
        commands.add(new HelpCommand());
        commands.add(new InfoCommand());
        commands.add(new PingCommand());
        commands.add(new ServerStatCommand());
        commands.add(new UptimeCommand());
        commands.add(new WhoisCommand());

        //Fun
        commands.add(new AdviceCommand());
        commands.add(new AgeCommand());
        commands.add(new CoinFlipCommand());
        commands.add(new DadJokeCommand());
        commands.add(new DefineCommand());
        commands.add(new EightBallCommand());
        commands.add(new FactCommand());
        commands.add(new HowGayCommand());
        commands.add(new InsultCommand());
        commands.add(new PollCommand());

        commands.add(new PPSizeCommand());
        commands.add(new QuoteCommand());
        commands.add(new RandomColorCommand());
        commands.add(new RandomCommand());
        commands.add(new RockPaperScissorsCommand());
        commands.add(new SpamCommand());
        commands.add(new SpeakCommand());
        commands.add(new TopicCommand());
        commands.add(new WouldYouRatherCommand());

        //Currency
        commands.add(new CarrotsCommand());
        commands.add(new FridgeCommand());
        commands.add(new SlotCommand());

        //Complex Pixelmon
        commands.add(new SuggestionCommand());
        commands.add(new DeleteSuggestionCommand());
        commands.add(new EditSuggestionCommand());
        commands.add(new MoveSuggestionCommand());
        commands.add(new EconSuggestCommand());
        commands.add(new EditEconSuggestCommand());
        commands.add(new DelEconSuggestCommand());
        commands.add(new PromoteCommand());
        commands.add(new DemoteCommand());

        //Complex Staff
        commands.add(new TodoCommand());
        commands.add(new BugPermCommand());
        commands.add(new ReportCommand());
        commands.add(new DeleteReportCommand());
        commands.add(new EditReportCommand());
        commands.add(new ViewReportCommand());
        commands.add(new ChangeReportCommand());
        commands.add(new IssueCommand());
    }
}
