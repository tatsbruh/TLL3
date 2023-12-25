package com.tll3.Misc;

import com.tll3.Misc.Files.ConfigData;
import com.tll3.TLL3;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class GenericUtils {
    private static final Plugin plugin = TLL3.getPlugin(TLL3.class);

    private static LocalDate actualDate = LocalDate.now();
    private static LocalDate startDate = LocalDate.parse(ConfigData.getConfig("start_date",""));
    public static @Getter String monsoon_active = ConfigData.getConfig("monsoon_active","");

    public static int getDay(){
        return (int) ChronoUnit.DAYS.between(startDate, actualDate);
    };

    public static World getWorld(){
        return Bukkit.getWorld("world");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Player getRandomPlayer() {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]); //Makes an array list from all the connected players
        return onlinePlayers.length > 0 ? onlinePlayers[new Random().nextInt(onlinePlayers.length)] : null; //selects a random player from the list
    }
    public static Location getRandomLocationAroundRadius(Location center, int radius) {
        World world = center.getWorld();//gets the location (duh)
        double x = center.getX() + (new Random().nextDouble() * 2 - 1) * radius; //Creates a random X around the radius
        double z = center.getZ() + (new Random().nextDouble() * 2 - 1) * radius; //Creates a random Y around the radius

        return new Location(world, x, world.getHighestBlockYAt((int) x, (int) z), z); //returns a random location of said thing
    }
    public static void setDays(String args1) {
        int nD;
        try {
            nD = Math.max(0, Math.min(60, Integer.parseInt(args1)));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return;
        }
        LocalDate add = LocalDate.now().minusDays(nD);
        int month = add.getMonthValue();
        int day = add.getDayOfMonth();
        String s;
        if (month < 10) {
            s = add.getYear() + "-0" + month + "-";
        } else {
            s = add.getYear() + "-" + month + "-";
        }

        if (day < 10) {
            s = s + "0" + day;
        } else {
            s = s + day;
        }
        startDate = LocalDate.parse(s);
        ConfigData.setConfig("start_date",s);
    }
    public static void setMonsoonActive(String args1){
        monsoon_active = args1;
        ConfigData.setConfig("monsoon_active",args1);
    }



    public static String doTimeFormat(int i){
        int seconds = i / 20; // Convert ticks to seconds
        int hours = seconds / 3600;
        int remainingSeconds = seconds % 3600;
        int minutes = remainingSeconds / 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds % 60);
    }
}
