package com.tll3.Misc;

import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class GenericUtils {
    private static final Plugin plugin = TLL3.getPlugin(TLL3.class);
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
}
