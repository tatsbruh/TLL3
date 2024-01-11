package com.tll3.Lists.GUIs;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
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
                            "&6Mejoras: &7" + PlayerData.getUpgrade(p,"health") + "/10",
                            "&c&lCosto: &7" + PlayerData.getUpgradeCost(p,"health") + " de prestigio"
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
                        PlayerData.setExtraHealth(player,PlayerData.getExtraHealth(player) + 2);
                        PlayerData.setUpgradeCost(player,"health",cost + 2);
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

    public static class damageupgrades extends AbstractItem{
        private final Player p;
        public damageupgrades(Player p){
            this.p = p;
        }

        @Override
        public ItemProvider getItemProvider() {
            return new ItemBuilder(new com.tll3.Misc.ItemBuilder(Material.DIAMOND_SWORD).
                    setDisplayName("&c♆ Mejoras de daño ♆")
                    .setLore(
                            "&7Cada mejora te ofrece un pequeño bono de &c&lDaño",
                            "&6Mejoras: &7" + PlayerData.getUpgrade(p,"damage") + "/10",
                            "&c&lCosto: &7" + PlayerData.getUpgradeCost(p,"damage") + " de prestigio"
                    ).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .build());
        }
        @Override
        public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
            if(clickType.isLeftClick()){
                int cost = PlayerData.getUpgradeCost(player,"damage");
                int level = PlayerData.getUpgrade(player,"damage");
                int prestige = PlayerData.getPrestige(player);
                if(level < 10){
                    if(prestige >= cost){
                        PlayerData.setPrestige(player,prestige - cost);
                        PlayerData.setUpgrade(player,"damage",level + 1);
                        PlayerData.setUpgradeCost(player,"damage",cost + 3);
                        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue() + 0.5);
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

    public static class defenseupgrade extends AbstractItem{

        private final Player p;
        public defenseupgrade(Player p){
            this.p = p;
        }
        @Override
        public ItemProvider getItemProvider() {
            return new ItemBuilder(new com.tll3.Misc.ItemBuilder(Material.IRON_CHESTPLATE).
                    setDisplayName("&c♕ Mejoras de defensa ♕")
                    .setLore(
                            "&7Cada mejora te ofrece un pequeño bono de &b&lDefensa",
                            "&6Mejoras: &7" + PlayerData.getUpgrade(p,"defense") + "/5",
                            "&c&lCosto: &7" + PlayerData.getUpgradeCost(p,"defense") + " de prestigio"
                    ).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .build());
        }

        @Override
        public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
            if(clickType.isLeftClick()){
                int cost = PlayerData.getUpgradeCost(player,"defense");
                int level = PlayerData.getUpgrade(player,"defense");
                int prestige = PlayerData.getPrestige(player);
                if(level < 5){
                    if(prestige >= cost){
                        PlayerData.setPrestige(player,prestige - cost);
                        PlayerData.setUpgrade(player,"defense",level + 1);
                        PlayerData.setUpgradeCost(player,"defense",cost + 4);
                        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(player.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue() + 1.0);
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

    public static class speedupgrades extends AbstractItem{

        private final Player p;
        public speedupgrades(Player p){
            this.p = p;
        }
        @Override
        public ItemProvider getItemProvider() {
            return new ItemBuilder(new com.tll3.Misc.ItemBuilder(Material.FEATHER).
                    setDisplayName("&c☁ Mejoras de velocidad ☁")
                    .setLore(
                            "&7Cada mejora te ofrece un pequeño bono de &b&lVelocidad",
                            "&6Mejoras: &7" + PlayerData.getUpgrade(p,"speed") + "/5",
                            "&c&lCosto: &7" + PlayerData.getUpgradeCost(p,"speed") + " de prestigio"
                    ).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .build());
        }

        @Override
        public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
            if(clickType.isLeftClick()){
                int cost = PlayerData.getUpgradeCost(player,"speed");
                int level = PlayerData.getUpgrade(player,"speed");
                int prestige = PlayerData.getPrestige(player);
                if(level < 5){
                    if(prestige >= cost){
                        PlayerData.setPrestige(player,prestige - cost);
                        PlayerData.setUpgrade(player,"speed",level + 1);
                        PlayerData.setUpgradeCost(player,"speed",cost + 4);
                        PlayerData.setExtraSpeed(player,(PlayerData.getExtraSpeed(player) + 0.02));
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

    public static class stealthupgrade extends AbstractItem{
        private final Player p;
        public stealthupgrade(Player p){
            this.p = p;
        }
        @Override
        public ItemProvider getItemProvider() {
            return new ItemBuilder(new com.tll3.Misc.ItemBuilder(Material.ENDER_EYE).
                    setDisplayName("&c⊙ Mejoras de sigilo ⊙")
                    .setLore(
                            "&7Cada mejora te ofrece un pequeño bono de &b&lSigilo",
                            "&7El Sigilo te da beneficios para evitar los efectos de la Exposicion",
                            "&6Mejoras: &7" + PlayerData.getUpgrade(p,"stealth") + "/10",
                            "&c&lCosto: &7" + PlayerData.getUpgradeCost(p,"stealth") + " de prestigio"
                    ).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .build());
        }

        @Override
        public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
            if(clickType.isLeftClick()){
                int cost = PlayerData.getUpgradeCost(player,"stealth");
                int level = PlayerData.getUpgrade(player,"stealth");
                int prestige = PlayerData.getPrestige(player);
                if(level < 10){
                    if(prestige >= cost){
                        PlayerData.setPrestige(player,prestige - cost);
                        PlayerData.setUpgrade(player,"stealth",level + 1);
                        PlayerData.setUpgradeCost(player,"stealth",cost + 2);
                        PlayerData.setSigile(player,PlayerData.getSigile(player) + 1);
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
