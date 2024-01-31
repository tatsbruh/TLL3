package com.tll3.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryLockListeners implements Listener {
    @EventHandler
    public void blockDrop(PlayerDropItemEvent e) {
        if (e.isCancelled()) return;
        if (e.getItemDrop().getItemStack().getType() == Material.STRUCTURE_VOID) {
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void blockClick(InventoryClickEvent e) {
        if (e.isCancelled()) return;
        if (e.getCurrentItem() != null) {
            if (e.getCurrentItem().getType() == Material.STRUCTURE_VOID) {
                e.setCancelled(true);
                if (e.getClick() == ClickType.NUMBER_KEY) {
                    e.getInventory().remove(Material.STRUCTURE_VOID);
                }
            }
        }
        if (e.getCursor() != null) {
            if (e.getCursor().getType() == Material.STRUCTURE_VOID) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.STRUCTURE_VOID) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void blockSwap(PlayerSwapHandItemsEvent e) {
        if (e.isCancelled()) return;
        if (e.getOffHandItem() != null) {
            if (e.getOffHandItem().getType() == Material.STRUCTURE_VOID) {
                e.setCancelled(true);
            }
        }
        if (e.getMainHandItem() != null) {
            if (e.getMainHandItem().getType() == Material.STRUCTURE_VOID) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void blockMove(InventoryMoveItemEvent e) {
        if (e.isCancelled()) return;
        if (e.getItem() != null) {
            if (e.getItem().getType() == Material.STRUCTURE_VOID) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void blockPickup(InventoryPickupItemEvent e) {
        if (e.isCancelled()) return;
        if (e.getItem().getItemStack() != null) {
            if (e.getItem().getItemStack().getType() == Material.STRUCTURE_VOID) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void blockDrag(InventoryDragEvent e) {
        if (!e.getNewItems().isEmpty()) {
            for (int i : e.getNewItems().keySet()) {
                ItemStack s = e.getNewItems().get(i);
                if (s != null) {
                    if (s.getType() == Material.STRUCTURE_VOID) {
                        e.getInventory().removeItem(s);
                    }
                }
            }
        }
    }

}
