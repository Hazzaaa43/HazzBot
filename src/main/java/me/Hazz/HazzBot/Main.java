package me.Hazz.HazzBot;

import me.Hazz.HazzBot.Commands.ComplexCommands.BugPermCommands.BugPermReactionClosed;
import me.Hazz.HazzBot.Commands.ComplexCommands.BugPermCommands.BugPermReactionNeedsFix;
import me.Hazz.HazzBot.Commands.ComplexCommands.BugPermCommands.BugPermReactionNeedsRep;
import me.Hazz.HazzBot.Commands.ComplexCommands.BugPermCommands.BugPermReactionResolved;
import me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands.EconSuggestReactionComplete;
import me.Hazz.HazzBot.Commands.ComplexCommands.EconSuggestCommands.EconSuggestionReactionDeny;
import me.Hazz.HazzBot.Commands.ComplexCommands.TodoCommands.TodoReactionDelete;
import me.Hazz.HazzBot.Commands.ComplexCommands.TodoCommands.TodoReactionEdit;
import me.Hazz.HazzBot.Commands.ComplexCommands.TodoCommands.TodoReactionUnEdit;
import me.Hazz.HazzBot.Loggers.DeletedMessages;
import me.Hazz.HazzBot.Loggers.EditedMessages;
import me.Hazz.HazzBot.Loggers.LogDirectMessages;
import me.Hazz.HazzBot.Variables.WebDb;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.sql.Connection;

public class Main {

    private static JDA jda;

    public static void main(String[] args) throws Exception {

        WebDb.init();

        Connection c = WebDb.get().getConnection();

        //Carrots/Carrots Beg
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `CarrotBeg` (`id` int(11) NOT NULL AUTO_INCREMENT, `DiscordID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `Carrots` (`id` int(11) NOT NULL AUTO_INCREMENT, `DiscordID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Carrots`  bigint(11) DEFAULT '0', PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        //Message Logger
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `MessageLogger` (`id` int(11) NOT NULL AUTO_INCREMENT, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Guild` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `ImageName` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL, `Content` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageDate` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `ChannelID` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL, `ChannelName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        //Suggestion Tables
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `PixelmonMainSuggest` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `PixelmonStaffSuggest` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `VanillaMainSuggest` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `VanillaStaffSuggest` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `TestSuggest` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        //Economy Suggestion Table
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `EconSuggestions` (`id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `StaffContent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL, `StaffAuthor` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `StaffTimestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        //To do Tables
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `PixelmonTodo` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `VanillaTodo` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `TestTodo` ( `id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        //Bug Perm Tables
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `PixelmonBugPerm` (`id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `VanillaBugPerm` (`id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `TestBugPerm` (`id` int(11) NOT NULL AUTO_INCREMENT, `Content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `AuthorURL` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        //Report Tables
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `PixelmonReport` (`id` int(11) NOT NULL AUTO_INCREMENT, `IGN` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Server` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Info` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `VanillaReport` (`id` int(11) NOT NULL AUTO_INCREMENT, `IGN` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Server` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Info` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS `TestReport` (`id` int(11) NOT NULL AUTO_INCREMENT, `IGN` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Server` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Info` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        //Issue Tables
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS  `PixelmonIssue` (`id` int(11) NOT NULL AUTO_INCREMENT, `IGN` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL, `Type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL, `Report` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Sceenshot` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2818 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS  `VanillaIssue` (`id` int(11) NOT NULL AUTO_INCREMENT, `IGN` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL, `Type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL, `Report` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Sceenshot` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2818 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS  `TestIssue` (`id` int(11) NOT NULL AUTO_INCREMENT, `IGN` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL, `Type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL, `Report` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Sceenshot` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL, `Timestamp` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `MessageID` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, `Author` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2818 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        jda = new JDABuilder(AccountType.BOT)
                .setToken("NTI3MjU5NjgzNDgwNjY2MTIz.XUnr5A.UViKWTZrAv0qPbVPXAeHCM6rF0M") //This token is HazzBot#5519
                //.setToken("NTQ2MTgwNjQyOTI5NjM5NDg0.XWlrSw.whGyluUkktYDcfXJqIQxmYPT4j8") //This token is HazzBotTest#3168
                .build();

        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.listening("Hazz Cry Over Me"));
        jda.addEventListener(new DiscordBot(jda));
        jda.addEventListener(new TodoReactionDelete());
        jda.addEventListener(new TodoReactionEdit());
        jda.addEventListener(new TodoReactionUnEdit());

        jda.addEventListener(new BugPermReactionResolved());
        jda.addEventListener(new BugPermReactionClosed());
        jda.addEventListener(new BugPermReactionNeedsFix());
        jda.addEventListener(new BugPermReactionNeedsRep());

        jda.addEventListener(new EconSuggestReactionComplete());
        jda.addEventListener(new EconSuggestionReactionDeny());

        jda.addEventListener(new EditedMessages());
        jda.addEventListener(new DeletedMessages());
        jda.addEventListener(new LogDirectMessages());


    }
}

