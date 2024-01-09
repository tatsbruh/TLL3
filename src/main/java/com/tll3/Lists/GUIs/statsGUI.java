package com.tll3.Lists.GUIs;

import com.tll3.Misc.ChatUtils;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public class statsGUI {
    public static void showstatsupgrade(Player player){
        var gui = Gui.normal()
                .setStructure(
                        "# # # # R # # # #",
                        "# . . . . . . . #",
                        "# . A . . . D . #",
                        "# F . B . C . E #",
                        "# . . . . . . . #",
                        "# # # # # # # # #")
                .addIngredient('#',guiItems.filler2())
                .addIngredient('A', new itemstats.healthUpgrades(player))
                .build();
        var window = Window.single()
                .setViewer(player)
                .setTitle(ChatUtils.format("#96000f¡Sube de nivel!"))
                .setGui(gui)
                .build();
        window.open();
    }
}
