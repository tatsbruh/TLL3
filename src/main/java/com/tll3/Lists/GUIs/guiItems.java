package com.tll3.Lists.GUIs;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Skulls.SkullCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class guiItems {
    public static ItemStack filler(){
        return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§").setCustomModelData(99).build();
    }
    public static ItemStack filler2(){
        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§").setCustomModelData(99).build();
    }

    public static ItemStack playerheadMission(Player p){
        return new ItemBuilder(SkullCreator.itemFromUuid(p.getUniqueId()))
                .setDisplayName("#c9124c" + p.getName())
                .setLore(
                        "&e☠ ¡Bienvenido a las Misiones! ☠",
                        "&7Aqui puedes ver lo que estas misiones piden y completarlas",
                        "&7una vez que sus pedidos esten rellenados, recompensandote con lo",
                        "&7que se ofrecio en la mision, mayormente &cPrestigio &7y &eCreditos",
                        "",
                        "&c✪ &c&lPrestigio: &7" + PlayerData.getPrestige(p),
                        "&e✦ &e&lCreditos: &7" + PlayerData.getCredits(p),
                        "&b☀ &b&lMisiones Completadas: &7" + PlayerData.getHunts(p)
                ).build();
    }

    public static ItemStack day0mission5(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"05_violencia") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.IRON_INGOT)
                .setDisplayName("#ff954fViolencia Innecesaria")
                .setLore(
                        "&6&lObjetivo:",
                        "&7- Mata 5 Iron Golems &8(" + PlayerData.getObjective(p,"05golems") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x1 &cPrestigio",
                        "&7x5 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day0mission4(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"04_atlantis") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.PRISMARINE_SHARD)
                .setDisplayName("#69c5ffAnti-Atlantis")
                .setLore(
                        "&6&lObjetivo:",
                        "&7- Mata 20 Guardianes &8(" + PlayerData.getObjective(p,"04guardianes") + "/20)",
                        "&7- Mata 1 Guardian Anciano &8(" + PlayerData.getObjective(p,"04elderguardian") + "/1)",
                        "",
                        "&6&lRecompensas:",
                        "&7x5 &cPrestigio",
                        "&7x10 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day0mission3(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"03_warden") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.ECHO_SHARD)
                .setDisplayName("#004736Aprende y Escucha")
                .setLore(
                        "&6&lObjetivo:",
                        "&7- Mata 1 Warden &8(" + PlayerData.getObjective(p,"03warden") + "/1)",
                        "",
                        "&6&lRecompensas:",
                        "&7x5 &cPrestigio",
                        "&7x10 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day0mission2(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"02_wither") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.ECHO_SHARD)
                .setDisplayName("#474747Maestro Descompuesto")
                .setLore(
                        "&6&lObjetivo:",
                        "&7- Mata 5 Withers &8(" + PlayerData.getObjective(p,"02wither") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x5 &cPrestigio",
                        "&7x15 &eCreditos",
                        "",
                        cosa
                ).build();
    }






    public static ItemStack infoNinja(){
        return new ItemBuilder(Material.ZOMBIE_HEAD)
                .setDisplayName("&6Ninja Zombi | &eDía 7")
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
                .setDisplayName("&6Zombi Arqueólogo | &eDía 7")
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
                .setDisplayName("&6Rogue Skeleton | &eDía 7")
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
                .setDisplayName("&6Firemancer | &eDía 7")
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
                .setDisplayName("&6Razorback | &eDía 7")
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
                .setDisplayName("&6Black Tarantula | &eDía 7")
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
                .setDisplayName("&6Scarlet Leech | &eDía 7")
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
                .setDisplayName("&6Termita | &eDía 7")
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
                .setDisplayName("&6Termita de Colonia | &eDía 7")
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
                .setDisplayName("&6Enraged Iron Golem | &eDía 7")
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
                .setDisplayName("&6Skeleton Warden | &eDía 7")
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
    public static ItemStack infoDuskPha(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U5NTE1M2VjMjMyODRiMjgzZjAwZDE5ZDI5NzU2ZjI0NDMxM2EwNjFiNzBhYzAzYjk3ZDIzNmVlNTdiZDk4MiJ9fX0="))
                .setDisplayName("&6Dusk Phantom | &eDía 7")
                .setLore(
                        "&7Phantoms gigantes que tienen el alma de",
                        "&7un mob en su interior.",
                        "",
                        "&6&lAparece en: &7Durante la noche al no dormir",
                        "",
                        "&6&lComportamiento:",
                        "&7Infliguen x5 de daño que un phantom normal y dejan caer",
                        "&7un mob aleatorio al morir",
                        "",
                        "&8'Hagas lo que hagas, NO mates a uno de estos encima de ti,",
                        "&8creeme que te arrepentirás'"
                ).build();
    }
    public static ItemStack infoRevZomb(){
        return new ItemBuilder(Material.ZOMBIE_HEAD)
                .setDisplayName("&6Revenant Zombie | &eDía 7")
                .setLore(
                        "&7Un zombi poseido por demonios que ronda por el",
                        "&7mundo, buscando su siguiente victima.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Al ser reducido a la mitad de su vida, gana Fuerza 5,",
                        "&7Speed 3 y Resistencia 2",
                        "",
                        "&8'Incluso si no parecen la gran cosa, enfocate en acabar",
                        "&8con estos zombis antes de que se acumulen demasiado'"
                ).build();
    }
    public static ItemStack infoRevSkele(){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("&6Revenant Skeleton | &eDía 7")
                .setLore(
                        "&7Un esqueleto poseido por demonios que busca a su",
                        "&7siguiente objetivo para lanzarle sus flechas especiales.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Cada 5 disparos lanza una flecha explosiva de nivel 4.",
                        "",
                        "&8'A pesar de su simple funcionamiento, estos esqueletos pueden",
                        "&8causar problemas si no son tratados correctamente, evita que te disparen",
                        "&8demasiado y deberías estar bien'"
                ).build();
    }
    public static ItemStack infoRevSpid(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVlMjQ4ZGEyZTEwOGYwOTgxM2E2Yjg0OGEwZmNlZjExMTMwMDk3ODE4MGVkYTQxZDNkMWE3YThlNGRiYTNjMyJ9fX0="))
                .setDisplayName("&6Revenant Spider | &eDía 7")
                .setLore(
                        "&7Una araña grande con una fuerza brutal en sus",
                        "&7mandibulas y una picadura debilitante.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Al golpear infligue Weakness I y atrapa en telarañas al jugador,",
                        "&7y a veces se lanza hacia su objetivo.",
                        "",
                        "&8'Intentar escapar de estos bichos nunca resulta en algo bueno,",
                        "&8evita sus telarañas y no te dejes atrapar por su lanzamiento!'"
                ).build();
    }
    public static ItemStack infoRevCreeper(){
        return new ItemBuilder(Material.CREEPER_HEAD)
                .setDisplayName("&6Revenant Creeper | &eDía 7")
                .setLore(
                        "&7Un creeper poderoso que busca a su proxima",
                        "&7victima para explotar.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Genera una explosion de nivel 8 y quema a los jugadores",
                        "&7que caen en su radio de explosion.",
                        "",
                        "&8'Evitar pelear con estos si no estas preparado o no reaccionas rapido",
                        "&8su explosion es devastadora y puede causarte muchos problemas!."
                ).build();
    }
    public static ItemStack infoRevEnderman(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGIzNmQ1M2ZmZjY5YTQ5YzdkNmYzOTMyZjJiMGZlOTQ4ZTAzMjIyNmQ1ZTgwNDVlYzU4NDA4YTM2ZTk1MSJ9fX0="))
                .setDisplayName("&6Revenant Enderman | &eDía 7")
                .setLore(
                        "&7Un enderman poseido que busca a una desafortunada",
                        "&7victima para acabar con ella.",
                        "",
                        "&6&lAparece en: &cCualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Se enoja si se acerca demasiado a el, sus golpes inflige",
                        "&7Slowness 2.",
                        "",
                        "&8'Esta entidad es capaz de despistar a jugadores no preparados,",
                        "&8asi que has lo posible para evitar ser asesinado por el."
                ).build();
    }
}
