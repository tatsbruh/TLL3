package com.tll3.Lists.GUIs;

import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Skulls.SkullCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class guiItems {
    public static ItemStack filler(){
        return new ItemBuilder(Material.BLACK_STAINED_GLASS).setDisplayName("").setCustomModelData(99).build();
    }
    public static ItemStack infoNinja(){
        return new ItemBuilder(Material.ZOMBIE_HEAD)
                .setDisplayName("&6Ninja Zombi | &eDía 5")
                .setLore(
                        "&7Un Zombi maestro en las artes ninja, pero nunca",
                        "&7se sabe hasta donde se quedo.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Es completamente silencioso e invisible hasta que ataca",
                        "&7o hace daño a alguien, lleva una espada de hierro y puede",
                        "&7saltar bastante.",
                        "",
                        "&8'A decir verdad, nunca en mi vida he visto algo asi, agradece que",
                        "&8no te lanza shurikens o bombas de humo!'"
                ).build();
    }
    public static ItemStack infoArque(){
        return new ItemBuilder(Material.ZOMBIE_HEAD)
                .setDisplayName("&6Zombi Arqueólogo | &eDía 5")
                .setLore(
                        "&7Un aventurero muerto que se perdio en las profundidades",
                        "&7del mundo, lleva consigo su confiable pico.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Al tener un objetivo para atacar, rompe los bloques en su",
                        "&7camino, no destruye bloques con contenidos (cofres, hornos, etc)",
                        "",
                        "&8'Vas a tener que tener una base super segura para evitar ver a este",
                        "&8colega rompiendo tus defensas como si nada'"
                ).build();
    }
    public static ItemStack infoRogue(){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("&6Rogue Skeleton | &eDía 5")
                .setLore(
                        "&7Un esqueleto agresivo que no duda en sacar su",
                        "&7daga en momentos de amenaza.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Al estar en un radio cerca de su objetivo, saca una espada",
                        "&7de hierro y ataca a melee.",
                        "",
                        "&8'Mantén una distancia adecuada para evitar que estos huesitos saquen",
                        "&8su espada! son bastante veloces'"
                ).build();
    }
    public static ItemStack infoFireman(){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("&6Firemancer | &eDía 5")
                .setLore(
                        "&7Un esqueleto mago cuyo poder es lanzar bolas",
                        "&7de fuego hacia su oponente.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Lanza bolas de fuego con una explosion de nivel 4 en vez",
                        "&7de flechas",
                        "",
                        "&8'Odias los Ghasts? pues te voy diciendo que vas a odiar a este",
                        "&8tipo aun mas'"
                ).build();
    }
    public static ItemStack infoRazor(){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("&6Razorback | &eDía 5")
                .setLore(
                        "&7Un esqueleto robot cuyo arco tiene implementos",
                        "&7que hacen las flechas teledirigidas.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Dispara flechas que persiguen a los jugadores",
                        "",
                        "&8'Conoces algo llamado aim-bot? pues estos esqueletos lo",
                        "&8poseen todo el tiempo, lastima que no hay un anti-cheat'"
                ).build();
    }
    public static ItemStack infoTarantula(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVlMjQ4ZGEyZTEwOGYwOTgxM2E2Yjg0OGEwZmNlZjExMTMwMDk3ODE4MGVkYTQxZDNkMWE3YThlNGRiYTNjMyJ9fX0="))
                .setDisplayName("&6Black Tarantula | &eDía 5")
                .setLore(
                        "&7Una araña cuyas picaduras puede descomponer",
                        "&7hasta la persona mas fuerte del mundo.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Sus picaduras infligen Wither X por 5 segundos",
                        "",
                        "&8'Si no has superado tu aracnofobia aún, te digo que",
                        "&8la vas a pasar mal con este bicho'"
                ).build();
    }
    public static ItemStack infoScarlet(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVlMjQ4ZGEyZTEwOGYwOTgxM2E2Yjg0OGEwZmNlZjExMTMwMDk3ODE4MGVkYTQxZDNkMWE3YThlNGRiYTNjMyJ9fX0="))
                .setDisplayName("&6Scarlet Leech | &eDía 5")
                .setLore(
                        "&7Una araña cuyas picaduras absorben la vida",
                        "&7de su objetivo y lo usan para recuperarse.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Sus picaduras le dan Regeneración III a la araña",
                        "",
                        "&8'Dan mucho asco, lo se, pero mas asco te va dar cuando",
                        "&8mueras por estas cosas'"
                ).build();
    }
    public static ItemStack infoTermite(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNjYzRhMzJkNDVkNzRlOGIxNGVmMWZmZDU1Y2Q1ZjM4MWEwNmQ0OTk5MDgxZDUyZWFlYTEyZTEzMjkzZTIwOSJ9fX0="))
                .setDisplayName("&6Termita | &eDía 5")
                .setLore(
                        "&7Bichos cuya picadura desata una reacción interna",
                        "&7y las hacen estallar.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Al picar a un jugador, inicia un conteo de 3 segundos,",
                        "&7al terminar el conteo, crea una explosion de nivel 3",
                        "",
                        "&8'A estos bichos les encanta ser kamikazes, mantén tu distancia",
                        "&8y corre si uno te llega a picar!'"
                ).build();
    }
    public static ItemStack infoTermiteCol(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNjYzRhMzJkNDVkNzRlOGIxNGVmMWZmZDU1Y2Q1ZjM4MWEwNmQ0OTk5MDgxZDUyZWFlYTEyZTEzMjkzZTIwOSJ9fX0="))
                .setDisplayName("&6Termita de Colonia | &eDía 5")
                .setLore(
                        "&7Bichos coloniales cuya picadura desata una reacción",
                        "&7interna y las hacen estallar.",
                        "",
                        "&6&lAparece en: ",
                        "&7Mineshafts y en cualquier lugar durante una Monsoon",
                        "",
                        "&6&lComportamiento:",
                        "&7Al picar a un jugador, inicia un conteo de 3 segundos,",
                        "&7al terminar el conteo, crea una explosion de nivel 6",
                        "",
                        "&8'A estos bichos les encanta ser kamikazes, mantén tu distancia",
                        "&8y corre si uno te llega a picar!'"
                ).build();
    }
    public static ItemStack infoEnragedGolem(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTEzZjM0MjI3MjgzNzk2YmMwMTcyNDRjYjQ2NTU3ZDY0YmQ1NjJmYTlkYWIwZTEyYWY1ZDIzYWQ2OTljZjY5NyJ9fX0="))
                .setDisplayName("&6Enraged Iron Golem | &eDía 5")
                .setLore(
                        "&7Iron Golems cuya conciencia fue completamente modificada",
                        "&7para atacar jugadores.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Se acerca lentamente al jugador y realiza ataques fuertes",
                        "",
                        "&8'Estos traidores no sentirán lastima al matarte, de hecho",
                        "&8creo que ni siquiera sienten algo en lo absoluto'"
                ).build();
    }

    public static ItemStack infoSkeWarden(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTgzMDZiZjhlZTQwMzZhNmZkYjQ4NDE2NzBiMzE3Mjg5NDg1MDMxYjU2NTQ1ZDNkMWE1YzBlNTc0ZWNmZDFkNCJ9fX0="))
                .setDisplayName("&6Skeleton Warden | &eDía 5")
                .setLore(
                        "&7Esqueletos verdugos que aparecen raras veces",
                        "&7para ejecutar al jugador que los invoco.",
                        "",
                        "&6&lAparece en: &7Trampa de esqueletos",
                        "",
                        "&6&lComportamiento:",
                        "&7Lanza flechas de Poder 10",
                        "",
                        "&8'A pesar de su funcion basica, estos aparecen en conjunto",
                        "&8asi que no te confies!'"
                ).build();
    }
}
