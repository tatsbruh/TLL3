package com.tll3.Enviroments;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class Worlds {
    protected static final World overworld;
    protected static final World nether;
    protected static final World end;
    protected static final World wasteyard;
    static {
        overworld = getWorld("world");
        nether = getWorld("world_nether");
        end = getWorld("world_the_end");
        wasteyard = funcWasteyard();
    }
    private static World funcWasteyard(){
        WorldCreator creator = new WorldCreator("world_wasteyard");
        creator.environment(World.Environment.NETHER).generator(new Wasteyard()).generateStructures(false).type(WorldType.AMPLIFIED);
        if(getWorld("world_wasteyard") == null) {
            return creator.createWorld();
        }
        return getWorld("world_wasteyard");
    }
    public static World getWorld(String name) {
        return Bukkit.getWorld(name);
    }
    public static World getNether() {
        return nether;
    }
    public static World getOverworld() {
        return overworld;
    }
    public static World getEnd() {
        return end;
    }
    public static World getWasteyard(){return wasteyard;}

}
