package com.tll3.Lists;

import com.tll3.Misc.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Items {
    public static ItemStack miracleFruit(){
        return new ItemBuilder(Material.GOLDEN_APPLE)
                .setDisplayName("&a&lMiracle Fruit")
                .setLore(
                        "&8Reduce tu exposición por 30 y posee las","&8mismas cualidades que una Manzana Dorada")
                .setID("miraclefruit").build();
    }

    public static void addInventory(Player p,ItemStack s){
        p.getInventory().addItem(s);
    }
}
