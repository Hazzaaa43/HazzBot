package me.Hazz.HazzBot.Variables;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class findUserIn {
    public static Member findUserIn(TextChannel channel, String searchText) {
        List<Member> users = channel.getGuild().getMembers();
        List<Member> potential = new ArrayList<>();
        int smallestDiffIndex = 0, smallestDiff = -1;
        for (Member u : users) {
            String nick = u.getEffectiveName();
            if (searchText.trim().isEmpty()) {
                return null;
            }
            if (nick.equalsIgnoreCase(searchText)) {
                return u;
            }
            if (u.getUser().getId().equalsIgnoreCase(searchText))
                return u;
            if (searchText.contains(u.getUser().getId())) {
                return u;
            }
            if (nick.toLowerCase().contains(searchText.toLowerCase())) {
                potential.add(u);
                int d = Math.abs(nick.length() - searchText.length());
                if (d < smallestDiff || smallestDiff == -1) {
                    smallestDiff = d;
                    smallestDiffIndex = potential.size() - 1;
                }
            }
        }
        if (!potential.isEmpty()) {
            return potential.get(smallestDiffIndex);
        }
        return null;
    }
}