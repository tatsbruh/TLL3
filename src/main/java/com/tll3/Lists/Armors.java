package com.tll3.Lists;

import com.tll3.Misc.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Armors {

    public static ItemStack drHelm(){
        return new ItemBuilder(Material.NETHERITE_HELMET)
                .setDisplayName("#966200Dreadnought Helmet")
                .setUnbreakable(true)
                .setID("dread_helmet")
                .setCustomModelData(69)
                .setLore(
                        "&7Un casco acorazado, hecho con materiales finos y",
                        "&7resistentes",
                        "",
                        "&cTener el set completo te otorgara:",
                        "&7- &63 corazones de vida extra",
                        "&7- Resistencia permanente"
                        ).build();
    }
    public static ItemStack drChest(){
        return new ItemBuilder(Material.NETHERITE_CHESTPLATE)
                .setDisplayName("#966200Dreadnought Chestplate")
                .setUnbreakable(true)
                .setID("dread_chestplate")
                .setCustomModelData(69)
                .setLore(
                        "&7Una pechera acorazada, hecha con materiales finos y",
                        "&7resistentes",
                        "",
                        "&cTener el set completo te otorgara:",
                        "&7- &63 corazones de vida extra",
                        "&7- Resistencia permanente"
                ).build();
    }
    public static ItemStack drLegs(){
        return new ItemBuilder(Material.NETHERITE_LEGGINGS)
                .setDisplayName("#966200Dreadnought Leggings")
                .setUnbreakable(true)
                .setID("dread_leggings")
                .setCustomModelData(69)
                .setLore(
                        "&7Unos pantalones acorazados, hechos con materiales finos y",
                        "&7resistentes",
                        "",
                        "&cTener el set completo te otorgara:",
                        "&7- &63 corazones de vida extra",
                        "&7- Resistencia permanente"
                ).build();
    }
    public static ItemStack drBoots(){
        return new ItemBuilder(Material.NETHERITE_BOOTS)
                .setDisplayName("#966200Dreadnought Boots")
                .setUnbreakable(true)
                .setID("dread_boots")
                .setCustomModelData(69)
                .setLore(
                        "&7Unas botas acorazadas, hechas con materiales finos y",
                        "&7resistentes",
                        "",
                        "&cTener el set completo te otorgara:",
                        "&7- &63 corazones de vida extra",
                        "&7- Resistencia permanente"
                ).build();
    }




}
