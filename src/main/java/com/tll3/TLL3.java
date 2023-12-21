package com.tll3;

import com.tll3.Misc.Crafting.CraftingEvents;
import com.tll3.Misc.Files.ConfigData;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

//TODO Please, promise me that you won't make a mess just like last time, okay? - 17/12/23
public final class TLL3 extends JavaPlugin {


    private static TLL3 plugin;
    public static String cf = "[TheLastLife] >>> "; //console format
    public static TLL3 getInstance(){return plugin;}
    @Override
    public void onEnable() {
        // - Say the line, Mutant!
        //*sigh*
        console(cf + "Plugin activado correctamente");
        ConfigData.setConfig("active","true");
        loadListeners();


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
                new CraftingEvents()
        );
    }
}
