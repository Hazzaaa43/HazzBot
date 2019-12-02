package me.Hazz.HazzBot.Variables;

public class getTimingString {
    public static String getTimingString(long millis) {

        boolean ago = false;
        if (millis > System.currentTimeMillis()) {
            ago = false;
            millis = millis - System.currentTimeMillis();
        } else {
            ago = true;
            millis = System.currentTimeMillis() - millis;
        }

        long secs = millis / 1000;
        long mins = secs / 60;
        long hours = mins / 60;
        long days = hours / 24;
        long weeks = days / 7;

        // Dont modulus top level
        // weeks = weeks % 4;
        days = days % 7;
        hours = hours % 24;

        String out = "";

        if (weeks > 0) {
            if (!out.isEmpty())
                out += " ";
            out += weeks + " Week" + ((weeks == 1) ? "" : "s");
        }
        if (days > 0) {
            if (!out.isEmpty())
                out += " ";
            out += days + " Day" + ((days == 1) ? "" : "s");
        }
        if (hours > 0) {
            if (!out.isEmpty())
                out += " ";
            out += hours + " Hour" + ((hours == 1) ? "" : "s");
        }

        if (out.isEmpty()) {
            out = "Now";
        }

        if (out.equalsIgnoreCase("Now")) {
            return "Less Than an Hour";
        }

        if (ago) {
            return out;
        } else {
            return out + " in the future????";
        }
    }

    public static String getTimingStringExtended(long millis) {

        boolean ago = false;
        if (millis > System.currentTimeMillis()) {
            ago = false;
            millis = millis - System.currentTimeMillis();
        } else {
            ago = true;
            millis = System.currentTimeMillis() - millis;
        }

        long secs = millis / 1000;
        long mins = secs / 60;
        long hours = mins / 60;
        long days = hours / 24;
        long weeks = days / 7;

        // Dont modulus top level
        // weeks = weeks % 4;
        days = days % 7;
        hours = hours % 24;
        mins = mins % 60;
        secs = secs % 60;

        String out = "";

        if (weeks > 0) {
            if (!out.isEmpty())
                out += " ";
            out += weeks + " Week" + ((weeks == 1) ? "" : "s");
        }
        if (days > 0) {
            if (!out.isEmpty())
                out += " ";
            out += days + " Day" + ((days == 1) ? "" : "s");
        }
        if (hours > 0) {
            if (!out.isEmpty())
                out += " ";
            out += hours + " Hour" + ((hours == 1) ? "" : "s");
        }
        if (mins > 0) {
            if (!out.isEmpty())
                out += " ";
            out += mins + " Minute" + ((mins == 1) ? "" : "s");
        }
        if (secs > 0) {
            if (!out.isEmpty())
                out += " ";
            out += secs + " Second" + ((secs == 1) ? "" : "s");
        }

        if (out.isEmpty()) {
            out = "Now";
        }

        if (out.equalsIgnoreCase("Now")) {
            return "less than a minute ago!";
        }

        if (ago) {
            return out;
        } else {
            return out + " in the future????";
        }
    }
}
