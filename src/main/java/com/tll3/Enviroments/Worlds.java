package com.tll3.Enviroments;

import com.tll3.Enviroments.Dunes.SavageDunes;
import com.tll3.Enviroments.Forest.PrimevalWoods;
import com.tll3.Enviroments.Wasteyard.Wasteyard;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class Worlds {
    protected static final World overworld;
    protected static final World nether;
    protected static final World end;
    protected static final World primeval;
    protected static final World dunes;
    protected static final World wasteyard;
    static {
        overworld = getWorld("world");
        nether = getWorld("world_nether");
        end = getWorld("world_the_end");
        wasteyard = funcWasteyard();
        primeval = funcPrimeval();
        dunes = funcDunes();
    }
    private static World funcWasteyard(){
        WorldCreator creator = new WorldCreator("world_wasteyard");
        creator.environment(World.Environment.NETHER).generator(new Wasteyard()).generateStructures(false).type(WorldType.AMPLIFIED);
        if(getWorld("world_wasteyard") == null) {
            return creator.createWorld();
        }
        return getWorld("world_wasteyard");
    }
    private static World funcPrimeval(){
        WorldCreator creator = new WorldCreator("world_primeval");
        creator.environment(World.Environment.NORMAL);
        creator.generator(new PrimevalWoods());
        creator.generateStructures(false);
        creator.type(WorldType.AMPLIFIED);
        if(getWorld("world_primeval") == null) {
            return creator.createWorld();
        }
        return getWorld("world_primeval");
    }
    private static World funcDunes(){
        WorldCreator creator = new WorldCreator("world_dunes");
        creator.environment(World.Environment.NORMAL);
        creator.generator(new SavageDunes());
        creator.generateStructures(false);
        creator.type(WorldType.AMPLIFIED);
        if(getWorld("world_dunes") == null) {
            return creator.createWorld();
        }
        return getWorld("world_dunes");
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
    public static World getPrimeval(){return primeval;}
    public static World getDunes(){return dunes;}

}
