package com.tll3.Lists.GUIs;

import com.tll3.Misc.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class guiItems {
    public static ItemStack filler(){
        return new ItemBuilder(Material.BLACK_STAINED_GLASS).setDisplayName("").setCustomModelData(99).build();
    }
    public static ItemStack store(){
        return new ItemBuilder(Material.IRON_SWORD).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                .setDisplayName("&e&lTienda")
                .setLore(
                        "&a¡Aqui puedes comprar objetos y demas usando tu",
                        "&e&lPrestigio &aobtenido de las misiones!",
                        ""
                ).build();
    }
}
