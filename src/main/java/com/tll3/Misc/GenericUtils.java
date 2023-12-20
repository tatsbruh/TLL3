package com.tll3.Misc;

import com.tll3.TLL3;
import org.bukkit.plugin.Plugin;

public class GenericUtils {
    private static final Plugin plugin = TLL3.getPlugin(TLL3.class);
    public static Plugin getPlugin() {
        return plugin;
    }

}
