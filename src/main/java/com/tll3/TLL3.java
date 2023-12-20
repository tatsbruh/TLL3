package com.tll3;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

//TODO Please, promise me that you won't make a mess just like last time, okay? - 17/12/23
public final class TLL3 extends JavaPlugin {

    public static String cf = "[TheLastLife] > "; //console format

    @Override
    public void onEnable() {
        // - Say the line, Mutant!
        //*sigh*
        console(cf + "Plugin activado correctamente");



    }

    @Override
    public void onDisable() {
        console(cf + "Plugin desactivado correctamente");
    }



    public void console(String s){
        getLogger().info(s);
    }
}
