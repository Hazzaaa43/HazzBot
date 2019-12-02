package me.Hazz.HazzBot.Variables;

import java.sql.SQLException;
import java.util.ArrayList;

public class WebDb {

    private static ArrayList<MySQLAdapter> connections = new ArrayList<>();
    private static int max_connections = 5;
    private static int connection = 0;

    private static MySQLAdapter defaultCon = null;

    public static MySQLAdapter get(String old) {
        if ((connections.size() - 1) < (connection + 1)) {
            connection = 0;
        } else {
            connection++;
        }
        return connections.get(connection);
    }


    public static MySQLAdapter get() {
        return get("");
    }

    public static void init() {
        connections.clear();
        int i = 1;
        while (i <= max_connections) {
            MySQLAdapter a = new MySQLAdapter("discordbot.c3lzexlsnr9c.us-east-2.rds.amazonaws.com", 3306, "hazz", "iqroUd0Awjxq", "discordbot");
            try {
                a.query("SET NAMES utf8mb4");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("COULD NOT SET utf8mb4");
            }
            connections.add(a);
            i++;
        }

        defaultCon = new MySQLAdapter("discordbot.c3lzexlsnr9c.us-east-2.rds.amazonaws.com", 3306, "hazz", "iqroUd0Awjxq", "discordbot");
        try {
            defaultCon.query("SET NAMES utf8mb4");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("COULD NOT SET utf8mb4");
        }

    }
}
