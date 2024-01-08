package com.tll3.Lists.GUIs;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class itemstats {
    public static class healthUpgrades extends AbstractItem{

        private final Player p;
        public healthUpgrades(Player p){
            this.p = p;
        }

        @Override
        public ItemProvider getItemProvider() {
            return new ItemBuilder(new com.tll3.Misc.ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).
                    setDisplayName("&c❤ Mejoras de vida ❤")
                    .setLore(
                            "&7Cada mejora de vida otorga &8+1 &ccorazon &7extra",
                            "&6Mejoras: " + PlayerData.getUpgrade(p,"health") + "/10",
                            "&c&lCosto: " + PlayerData.getUpgradeCost(p,"health")
                    ).build());
        }
        @Override
        public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
            if(clickType.isLeftClick()){
                int cost = PlayerData.getUpgradeCost(player,"health");
                int level = PlayerData.getUpgrade(player,"health");
                int prestige = PlayerData.getPrestige(player);
                if(level < 10){
                    if(prestige >= cost){
                        PlayerData.setPrestige(player,prestige - cost);
                        PlayerData.setUpgrade(player,"health",level + 1);
                        PlayerData.setExtraHealth(player,PlayerData.getExtraHealth(player) + 1);
                        PlayerData.setUpgradeCost(player,"health",cost * 2);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,10.0F,2.0F);
                    }else{
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10.0F,-2.0F);
                        player.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cNo tienes suficiente prestigio para comprar esto!"));
                    }
                }else{
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10.0F,-2.0F);
                    player.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cLa mejora esta al nivel maximo!"));
                }
            }
            notifyWindows();
        }
    }
}
