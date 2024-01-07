package com.tll3;

import co.aikar.commands.PaperCommandManager;
import com.tll3.Commands.staffCMD;
import com.tll3.Listeners.*;
import com.tll3.Lists.Recipes;
import com.tll3.Misc.Crafting.CraftingEvents;
import com.tll3.Misc.Files.ConfigData;
import com.tll3.Misc.GenericUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

//TODO Please, promise me that you won't make a mess just like last time, okay? - 17/12/23
public final class TLL3 extends JavaPlugin {


    private static TLL3 plugin;
    public static String cf = "[Afterlife] "; //console format
    public static TLL3 getInstance(){return plugin;}
    @Override
    public void onEnable() {
        // - Say the line, Mutant!
        //*sigh*
        plugin = this;
        console(cf + "Plugin activado correctamente");
        loadListeners();
        loadCommands();
        Recipes.registerAllRecipes();
        if(Objects.equals(GenericUtils.getMonsoon_active(), "true")){
            MonsoonListeners.createBossBar();
        }

    }

    @Override
    public void onDisable() {
        console(cf + "Plugin desactivado correctamente");
    }



    public void console(String s){
        getLogger().info(s);
    }
    private void registerListeners(Listener... listeners){
        for(Listener listener : listeners){
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }


    public void loadListeners(){
        registerListeners(
                new CraftingEvents(),
                new GenericPlayerListeners(),
                new PlayerDeathListeners(),
                new GlobalListeners(),
                new GenericEntityListeners(),
                new EntityNaturalSpawn(),
                new MonsoonListeners(),
                new EntityDrops(),
                new MissionListeners()
        );
    }


    public void loadCommands(){
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new staffCMD());
    }
}
