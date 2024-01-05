package com.tll3.Lists;

import com.tll3.Misc.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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

    public static ItemStack brokenTrident(){
        return new ItemBuilder(Material.TRIDENT).setDurability(1).build();
    }

    public static ItemStack dreadClaymore(){
        return new ItemBuilder(Material.NETHERITE_SWORD)
                .setDisplayName("&cClaymore de la Perdición")
                .setID("dread_claymore")
                .setUnbreakable(true)
                .addEnchant(Enchantment.FIRE_ASPECT,2)
                .setLore(
                        "",
                        "&8Espada hecho de los restos de los &cRevenants.",
                        "&8Infligue &7Debilidad I &8en los mobs y hacen que",
                        "&8tomen &cx3 &8de daño por fuego.",
                        ""
                ).build();
    }

    public static ItemStack dreadBow(){
        return new ItemBuilder(Material.BOW)
                .setDisplayName("&cArco de la Perdición")
                .setID("dread_bow")
                .setUnbreakable(true)
                .addEnchant(Enchantment.ARROW_FIRE,1)
                .setLore(
                        "",
                        "&8Arco hecho de los restos de los &cRevenants.",
                        "&8Sus flechas infliguen &7Slowness I &8en los mobs y hacen",
                        "&8que tomen &cx3 &8de daño por fuego.",
                        ""
                ).build();
    }



    //Revenant Drops
    public static ItemStack infestedFlesh(){
        return new ItemBuilder(Material.ROTTEN_FLESH)
                .setDisplayName("&cInfested Rotten Flesh")
                .setLore(
                        "",
                        "&8Carne del &4Revenant Zombie&8, usado para",
                        "&8craftear armas y otros materiales",
                        ""
                ).setID("infested_flesh")
                .build();
    }

    public static ItemStack infestedBones(){
        return new ItemBuilder(Material.BONE)
                .setDisplayName("&cInfested Bones")
                .setLore(
                        "",
                        "&8Huesos del &4Revenant Skeleton&8, usado para",
                        "&8craftear armas y otros materiales",
                        ""
                ).setID("infested_bones")
                .build();
    }
    public static ItemStack silverStrings(){
        return new ItemBuilder(Material.COBWEB)
                .setDisplayName("&cSilver Strings")
                .setLore(
                        "",
                        "&8Telarañas del &4Revenant Spider&8, usado para",
                        "&8craftear armas y otros materiales",
                        ""
                ).setID("silver_strings").setID("unplaceable")
                .build();
    }
    public static ItemStack goldenGunpowder(){
        return new ItemBuilder(Material.GUNPOWDER)
                .setDisplayName("&cGolden Gunpowder")
                .setLore(
                        "",
                        "&8Polvora del &4Revenant Creeper&8, usado para",
                        "&8craftear armas y otros materiales",
                        ""
                ).setID("golden_gunpowder")
                .build();
    }
    public static ItemStack revenantPearl(){
        return new ItemBuilder(Material.ENDER_PEARL)
                .setDisplayName("&cRevenant Pearl")
                .setLore(
                        "",
                        "&8Perlas del &4Revenant Enderman&8, su",
                        "&8teletransporte te da &eSpeed &8y &cResistencia",
                        ""
                ).setID("revenant_pearl")
                .build();
    }



    public static void addInventory(Player p,ItemStack s){
        p.getInventory().addItem(s);
    }
}
