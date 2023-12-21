package com.tll3.Misc.Crafting;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import static com.tll3.Misc.Crafting.CraftingUtils.*;

public class CraftingEvents implements Listener {
    @EventHandler
    private static void removeCustomRecipeItems(CraftItemEvent e){
        if(e.getInventory().getResult() == null) return;
        boolean isShiftClick = e.isShiftClick();
        if(isShiftClick)
            removeCustomItemWithShiftClick(e.getInventory(), (Player) e.getWhoClicked());
        else
            removeCustomItemsNoShiftClick(e.getInventory());
    }

    @EventHandler
    private static void onPlayerCraftItem(PrepareItemCraftEvent e){
        if(e.getInventory().getResult() == null) return;
        checkCrafts(e.getInventory());
    }
}
