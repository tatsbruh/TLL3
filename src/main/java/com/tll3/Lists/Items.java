package com.tll3.Lists;

import com.tll3.Misc.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Items {
    public static ItemStack miracleFruit(){
        return new ItemBuilder(Material.GOLDEN_APPLE)
                .setDisplayName("&aFruta Milagrosa")
                .setLore(
                        "&8Reduce tu exposición por 30 y posee las","&8mismas cualidades que una Manzana Dorada")
                .setID("miraclefruit").build();
    }
    public static ItemStack invultome(){
        return new ItemBuilder(Material.BOOK)
                .setDisplayName("&8Tomo de la Inmortalidad")
                .setLore(
                        "&7Dentro de estas paginas se encuentra un hechizo",
                        "&7que te permite obtener el efecto de &8&lInvulnerable&7,",
                        "&7que te hace &cinmune &7a cualquier fuente de daño por 15 segundos.",
                        "",
                        "&8Cooldown de 5 minutos."
                )
                .addEnchant(Enchantment.FROST_WALKER,1)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .setID("invulnerable_tome")
                .setCustomModelData(12344)
                .build();
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
                        "&8tomen &cx5 &8de daño por fuego.",
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
                        "&8que tomen &cx5 &8de daño por fuego.",
                        ""
                ).build();
    }


    //Dia 14 items
    public static ItemStack brimstoneTrident(){
        return new ItemBuilder(Material.TRIDENT).
                setDisplayName("#6b1e34Tridente de Azufre")
                .setID("brimstonetrident")
                .setUnbreakable(true)
                .addEnchant(Enchantment.RIPTIDE,5)
                .setLore(
                        "&8'Imbuido con el ardor del azufre de un vertedero infernal'",
                        "&7Al usar el tridente dentro de &6Lava &7te permite usarlo como si",
                        "&7estuvieras en agua, aparte que te deja usarlo libremente en el",
                        "&4Nether &7durante una &bMonsoon&7."
                ).build();
    }
    public static ItemStack vulcanitePickaxe(){
        return new ItemBuilder(Material.NETHERITE_PICKAXE)
                .setDisplayName("#6b1e34Pico de Vulcanite")
                .setUnbreakable(true)
                .setID("vulcanpickaxe")
                .addEnchant(Enchantment.DIG_SPEED,7)
                .setLore(
                        "&8'Reforzado con materiales infernales, su contacto puede causar",
                        "&8quemaduras de tercer grado'",
                        "&7Aparte de su excelente velocidad al minar, algunos ores dejaran su",
                        "&7lingote o material cocinado en vez del material crudo al ser picado con",
                        "&7esta herramienta."
                ).build();
    }
    public static ItemStack vulcaniteAxe(){
        return new ItemBuilder(Material.NETHERITE_AXE)
                .setDisplayName("#6b1e34Hacha de Vulcanite")
                .setUnbreakable(true)
                .setID("vulcanaxe")
                .addEnchant(Enchantment.DIG_SPEED,7)
                .setLore(
                        "&8'Reforzado con materiales infernales, su contacto puede causar",
                        "&8quemaduras de tercer grado'",
                        "&7Tiene una excelente velocidad al talar arboles."
                ).build();
    }
    public static ItemStack vulcaniteShovel(){
        return new ItemBuilder(Material.NETHERITE_SHOVEL)
                .setDisplayName("#6b1e34Pala de Vulcanite")
                .setUnbreakable(true)
                .setID("vulcanshovel")
                .addEnchant(Enchantment.DIG_SPEED,7)
                .setLore(
                        "&8'Reforzado con materiales infernales, su contacto puede causar",
                        "&8quemaduras de tercer grado'",
                        "&7Tiene una excelente velocidad al excavar arena y demás."
                ).build();
    }
    public static ItemStack vulcaniteHoe(){
        return new ItemBuilder(Material.NETHERITE_HOE)
                .setDisplayName("#6b1e34Azada de Vulcanite")
                .setUnbreakable(true)
                .setID("vulcanhoe")
                .addEnchant(Enchantment.DIG_SPEED,7)
                .setLore(
                        "&8'Reforzado con materiales infernales, su contacto puede causar",
                        "&8quemaduras de tercer grado'",
                        "&7Enserio gastaste tu Vulcanite en esto? bueno, igual que las demás",
                        "&7herramientas, tiene una excelente velocidad al sacar hojas y plantas."
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
