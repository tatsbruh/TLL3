package com.tll3.Enviroments;

import com.tll3.Enviroments.Dunes.SavageDunes;
import com.tll3.Enviroments.Forest.PrimevalWoods;
import com.tll3.Enviroments.Volcano.ScorchedPlateau;
import com.tll3.Enviroments.Wasteyard.Wasteyard;
import com.tll3.Enviroments.Zero.ZeroHorizon;
import com.tll3.Misc.GenericUtils;
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
    protected static final World scorched;
    protected static final World wasteyard;
    protected static final World zero;

    static {
        overworld = getWorld("world");
        nether = getWorld("world_nether");
        end = getWorld("world_the_end");
        wasteyard = funcWasteyard();
        primeval = funcPrimeval();
        dunes = funcDunes();
        scorched = funcScorched();
        zero = funcZero();
    }

    private static World funcWasteyard(){
        if(getWorld("world_wasteyard") == null) {
            WorldCreator creator = new WorldCreator("world_wasteyard");
            creator.environment(World.Environment.NETHER).generator(new Wasteyard()).generateStructures(false).type(WorldType.AMPLIFIED);
            return creator.createWorld();
        }
        return getWorld("world_wasteyard");
    }

    private static World funcPrimeval(){
        if(getWorld("world_primeval") == null) {
            WorldCreator creator = new WorldCreator("world_primeval");
            creator.environment(World.Environment.NORMAL);
            creator.generator(new PrimevalWoods());
            creator.generateStructures(false);
            creator.type(WorldType.AMPLIFIED);
            return creator.createWorld();
        }
        return getWorld("world_primeval");
    }

    private static World funcDunes(){
        if(getWorld("world_dunes") == null) {
            WorldCreator creator = new WorldCreator("world_dunes");
            creator.environment(World.Environment.NORMAL);
            creator.generator(new SavageDunes());
            creator.generateStructures(false);
            creator.type(WorldType.AMPLIFIED);
            return creator.createWorld();
        }
        return getWorld("world_dunes");
    }

    private static World funcScorched(){
        if(getWorld("world_plateau") == null) {
            WorldCreator creator = new WorldCreator("world_plateau");
            creator.environment(World.Environment.NETHER);
            creator.generator(new ScorchedPlateau());
            creator.generateStructures(false);
            creator.type(WorldType.AMPLIFIED);
            return creator.createWorld();
        }
        return getWorld("world_plateau");
    }

    private static World funcZero(){
        if(getWorld("world_zero") == null) {
            WorldCreator creator = new WorldCreator("world_zero");
            creator.environment(World.Environment.THE_END);
            creator.generator(new ZeroHorizon());
            creator.generateStructures(false);
            creator.type(WorldType.AMPLIFIED);
            return creator.createWorld();
        }
        return getWorld("world_zero");
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
    public static World getScorched(){return scorched;}
    public static World getZero(){return zero;}

    public static void loadWorlds(){
        getWasteyard();
        getPrimeval();
        getDunes();
        getScorched();
        getZero();
    }

}
