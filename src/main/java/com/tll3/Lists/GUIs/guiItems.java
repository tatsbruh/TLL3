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
    public static ItemStack playerheadStats(Player p){
        return new ItemBuilder(SkullCreator.itemFromUuid(p.getUniqueId()))
                .setDisplayName("#c9124c" + p.getName())
                .setLore(
                        "&e☠ ¡Bienvenido a las Mejoras! ☠",
                        "&7Aquí puedes ver y comprar mejoras de estadísticas para ser aun mas",
                        "&7fuerte de lo normal!",
                        "",
                        "&c✪ &c&lPrestigio: &7" + PlayerData.getPrestige(p)
                ).build();
    }

    //Misiones del dia 0 - dia 6
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
                        "&7x4 &cPrestigio",
                        "&7x10 &eCreditos",
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
                        "&6&lObjetivos:",
                        "&7- Mata 20 Guardianes &8(" + PlayerData.getObjective(p,"04guardianes") + "/20)",
                        "&7- Mata 3 Guardianes Ancianos &8(" + PlayerData.getObjective(p,"04elderguardian") + "/3)",
                        "",
                        "&6&lRecompensas:",
                        "&7x14 &cPrestigio",
                        "&7x20 &eCreditos",
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
                        "&7x15 &cPrestigio",
                        "&7x30 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day0mission2(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"02_wither") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.WITHER_ROSE)
                .setDisplayName("#474747Maestro Descompuesto")
                .setLore(
                        "&6&lObjetivo:",
                        "&7- Mata 5 Withers &8(" + PlayerData.getObjective(p,"02wither") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x10 &cPrestigio",
                        "&7x18 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day0mission1(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"01_killall") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName("#ff4769Sin Piedad")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 1 Zombi &8(" + PlayerData.getObjective(p,"01zom") + "/1)",
                        "&7- Mata 1 Esqueleto &8(" + PlayerData.getObjective(p,"01ske") + "/1)",
                        "&7- Mata 1 Creeper &8(" + PlayerData.getObjective(p,"01cre") + "/1)",
                        "&7- Mata 1 Araña &8(" + PlayerData.getObjective(p,"01ara") + "/1)",
                        "&7- Mata 1 Enderman &8(" + PlayerData.getObjective(p,"01end") + "/1)",
                        "&7- Mata 1 Araña de cueva &8(" + PlayerData.getObjective(p,"01cue") + "/1)",
                        "&7- Mata 1 Bruja &8(" + PlayerData.getObjective(p,"01bru") + "/1)",
                        "&7- Mata 1 Lepisma &8(" + PlayerData.getObjective(p,"01lep") + "/1)",
                        "&7- Mata 1 Endermite &8(" + PlayerData.getObjective(p,"01mit") + "/1)",
                        "&7- Mata 1 Husk &8(" + PlayerData.getObjective(p,"01hus") + "/1)",
                        "&7- Mata 1 Stray &8(" + PlayerData.getObjective(p,"01str") + "/1)",
                        "&7- Mata 1 Drowned &8(" + PlayerData.getObjective(p,"01dro") + "/1)",
                        "&7- Mata 1 Pillager &8(" + PlayerData.getObjective(p,"01pil") + "/1)",
                        "&7- Mata 1 Vindicator &8(" + PlayerData.getObjective(p,"01vin") + "/1)",
                        "&7- Mata 1 Evoker &8(" + PlayerData.getObjective(p,"01evo") + "/1)",
                        "&7- Mata 1 Ravager &8(" + PlayerData.getObjective(p,"01rav") + "/1)",
                        "&7- Mata 1 Vex &8(" + PlayerData.getObjective(p,"01vex") + "/1)",
                        "&7- Mata 1 Phantom &8(" + PlayerData.getObjective(p,"01pha") + "/1)",
                        "",
                        "&6&lRecompensas:",
                        "&7x15 &cPrestigio",
                        "&7x30 &eCreditos",
                        "",
                        cosa
                ).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    //Fin de misiones del dia 0 - 6


    //Misiones del dia 7 - 13
    public static ItemStack day7mission1(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"11_rol") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.BOW)
                .setDisplayName("#474747Juegos de Rol")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 5 Wither Swordsman &8(" + PlayerData.getObjective(p,"11sword") + "/5)",
                        "&7- Mata 5 Wither Juggernauts &8(" + PlayerData.getObjective(p,"11jugger") + "/5)",
                        "&7- Mata 5 Wither Archers &8(" + PlayerData.getObjective(p,"11archer") + "/5)",
                        "&7- Mata 5 Wither Mages &8(" + PlayerData.getObjective(p,"11mage") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x14 &cPrestigio",
                        "&7x25 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day7mission2(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"12_demon") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.NETHERITE_SWORD)
                .setDisplayName("#8f0a00Caza de Demonios")
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 10 Revenant Zombies &8(" + PlayerData.getObjective(p,"12revzo") + "/10)",
                        "&7- Mata 10 Revenant Skeleton &8(" + PlayerData.getObjective(p,"12revsk") + "/10)",
                        "&7- Mata 10 Revenant Spiders &8(" + PlayerData.getObjective(p,"12revsp") + "/10)",
                        "&7- Mata 10 Revenant Creepers &8(" + PlayerData.getObjective(p,"12revcr") + "/10)",
                        "&7- Mata 10 Revenant Enderman &8(" + PlayerData.getObjective(p,"12reven") + "/10)",
                        "",
                        "&6&lRecompensas:",
                        "&7x20 &cPrestigio",
                        "&7x40 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day7mission3(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"13_plaga") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.FERMENTED_SPIDER_EYE)
                .setDisplayName("#17c200Epidemia Explosiva")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 10 Termitas &8(" + PlayerData.getObjective(p,"13term") + "/10)",
                        "&7- Mata 10 Termitas de Colonia &8(" + PlayerData.getObjective(p,"13colterm") + "/10)",
                        "",
                        "&6&lRecompensas:",
                        "&7x16 &cPrestigio",
                        "&7x28 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day7mission4(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"14_phantom") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.PHANTOM_MEMBRANE)
                .setDisplayName("#37456eAcechadores Nocturnos")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 5 Dusk Phantoms &8(" + PlayerData.getObjective(p,"14dusk") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x12 &cPrestigio",
                        "&7x18 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day7mission5(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"15_cheat") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.IRON_AXE)
                .setDisplayName("#ed572dAnti-cheat")
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 10 Zombis Arqueólogos &8(" + PlayerData.getObjective(p,"15arq") + "/10)",
                        "&7- Mata 10 Razorbacks &8(" + PlayerData.getObjective(p,"15raz") + "/10)",
                        "",
                        "&6&lRecompensas:",
                        "&7x14 &cPrestigio",
                        "&7x22 &eCreditos",
                        "",
                        cosa
                ).build();
    }


    //Dia 14 misiones
    public static ItemStack day14mission1(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"21_waste") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.TUFF)
                .setDisplayName("#524843De parte del Vertedero")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 5 Scorched Brutes &8(" + PlayerData.getObjective(p,"21sco") + "/5)",
                        "&7- Mata 5 Rustwalkers &8(" + PlayerData.getObjective(p,"21rus") + "/5)",
                        "&7- Mata 5 Wandering Vagrants &8(" + PlayerData.getObjective(p,"21wan") + "/5)",
                        "&7- Mata 5 Brimseekers &8(" + PlayerData.getObjective(p,"21par") + "/5)",
                        "&7- Mata 5 Lost Scavengers &8(" + PlayerData.getObjective(p,"21los") + "/5)",
                        "&7- Mata 5 Brimstone Cubes &8(" + PlayerData.getObjective(p,"21bri") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x30 &cPrestigio",
                        "&7x45 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day14mission2(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"22_blaze") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.FIRE_CHARGE)
                .setDisplayName("#fae3c3¡Vienen del Futuro! (y del pasado)")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 10 Wind Chargers &8(" + PlayerData.getObjective(p,"22wind") + "/10)",
                        "&7- Mata 10 Armored Blazes &8(" + PlayerData.getObjective(p,"22armor") + "/10)",
                        "",
                        "&6&lRecompensas:",
                        "&7x20 &cPrestigio",
                        "&7x35 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day14mission3(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"23_ash") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("#737373Desde las Cenizas")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 1 Ashen Wither &8(" + PlayerData.getObjective(p,"23ashen") + "/1)",
                        "",
                        "&6&lRecompensas:",
                        "&7x25 &cPrestigio",
                        "&7x40 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day14mission4(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"24_bee") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.HONEYCOMB)
                .setDisplayName("#255c3cÉpoca de plaga")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 20 Langostas &8(" + PlayerData.getObjective(p,"24bee") + "/20)",
                        "",
                        "&6&lRecompensas:",
                        "&7x20 &cPrestigio",
                        "&7x32 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day14mission5(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"25_llama") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.STICK)
                .setDisplayName("#ab789bEl pastor y su rebaño")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 10 Llamas &8(" + PlayerData.getObjective(p,"25lla") + "/10)",
                        "&7- Mata 10 Cabras &8(" + PlayerData.getObjective(p,"25goa") + "/10)",
                        "",
                        "&6&lRecompensas:",
                        "&7x24 &cPrestigio",
                        "&7x36 &eCreditos",
                        "",
                        cosa
                ).build();
    }

    //Day 21
    public static ItemStack day21mission1(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"31_metal") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.IRON_BLOCK)
                .setDisplayName("#737373D#717171e#707070s#6e6e6eo#6c6c6cx#6b6b6bi#696969d#686868a#666666c#646464i#636363ó#616161n")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 5 Steel Railgunners &8(" + PlayerData.getObjective(p,"31ste") + "/5)",
                        "&7- Mata 5 Titanium Creepers &8(" + PlayerData.getObjective(p,"31tit") + "/5)",
                        "&7- Mata 5 Cyberpunks &8(" + PlayerData.getObjective(p,"31cyb") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x30 &cPrestigio",
                        "&7x45 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day21mission2(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"32_mcleg") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.PORKCHOP)
                .setDisplayName("#34cbfbM#3ccefbi#44d0fbn#4cd3fbe#54d6fcc#5cd8fcr#64dbfca#6cdefcf#75e0fct #7de3fcL#85e6fce#8de8fcg#95ebfde#9deefdn#a5f0fdd#adf3fds")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 10 Piglin Shinobi &8(" + PlayerData.getObjective(p,"32pig1") + "/10)",
                        "&7- Mata 10 Alquimistas Porcinos &8(" + PlayerData.getObjective(p,"32pig2") + "/10)",
                        "&7- Mata 10 Jinetes Cerdo-pocalípticos &8(" + PlayerData.getObjective(p,"32pig3") + "/10)",
                        "",
                        "&6&lRecompensas:",
                        "&7x36 &cPrestigio",
                        "&7x54 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day21mission3(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"33_blas") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.GHAST_TEAR)
                .setDisplayName("#fbf8aa¡#fbf9b2B#fbf9bbl#fcfac3a#fcfacbs#fcfbd4f#fcfbdce#fcfce4m#fdfceci#fdfdf5a#fdfdfd!")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 5 Entropic Demons &8(" + PlayerData.getObjective(p,"33gas") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x28 &cPrestigio",
                        "&7x42 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day21mission4(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"34_prop") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.CROSSBOW)
                .setDisplayName("#572424I#592929n#5b2d2dv#5d3232a#5e3737s#603b3bi#624040ó#644545n #664949d#684e4ee #695353P#6b5757r#6d5c5co#6f6161p#716565i#736a6ae#746f6fd#767373a#787878d")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata 5 Piromaníacos &8(" + PlayerData.getObjective(p,"34ill1") + "/5)",
                        "&7- Mata 5 Verdugos &8(" + PlayerData.getObjective(p,"34ill2") + "/5)",
                        "&7- Mata 5 Ultravokers &8(" + PlayerData.getObjective(p,"34ill3") + "/5)",
                        "",
                        "&6&lRecompensas:",
                        "&7x34 &cPrestigio",
                        "&7x50 &eCreditos",
                        "",
                        cosa
                ).build();
    }
    public static ItemStack day21mission5(Player p){
        var cosa = ChatUtils.format("&c&lNo Completado");
        if(PlayerData.getMission(p,"35_lap") >= 1){
            cosa = ChatUtils.format("&a&l¡Completado!");
        }
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("#9428ffS#8f28ffe#8a29ffg#8429ffu#7f29ffn#7a29ffd#752affa #6f2affV#6a2affu#652affe#602bffl#5a2bfft#552bffa")
                .setLore(
                        "&6&lObjetivos:",
                        "&7- Mata un Revenant Skeleton &8(" + PlayerData.getObjective(p,"35ske1") + "/1)",
                        "&7- Mata un Firemancer &8(" + PlayerData.getObjective(p,"35ske2") + "/1)",
                        "&7- Mata un Rogue Skeleton &8(" + PlayerData.getObjective(p,"35ske3") + "/1)",
                        "&7- Mata un Razorback &8(" + PlayerData.getObjective(p,"35ske4") + "/1)",
                        "&7- Mata un Void Overseer &8(" + PlayerData.getObjective(p,"35ske5") + "/1)",
                        "&7- Mata un Living Shrieker &8(" + PlayerData.getObjective(p,"35ske6") + "/1)",
                        "",
                        "&6&lRecompensas:",
                        "&7x36 &cPrestigio",
                        "&7x54 &eCreditos",
                        "",
                        cosa
                ).build();
    }



    //Almanaque

    //dia 7

    public static ItemStack nadaDia0(){
        return new ItemBuilder(Material.GRAY_DYE)
                .setDisplayName("&c&l¡Nada más que moscas!")
                .setLore(
                        "&7Vuelve en dias futuros para ver mas informacion!"
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

    //Dia 14
    public static ItemStack infoBlazeArmor(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ=="))
                .setDisplayName("&6Armored Blaze | &eDía 14")
                .setLore(
                        "&7El Mismísimo 'Hovering Inferno' o 'Wildfire'",
                        "&7o simplemente 'Mob D' ha vuelto! pero con un apodo",
                        "&7diferente, seguramente para pasar desapercibido.",
                        "",
                        "&6&lAparece en:",
                        "&7- Fortalezas del Nether",
                        "&7- Cualquier lugar en el Overworld durante una Monsoon",
                        "",
                        "&6&lComportamiento:",
                        "&7Sus proyectiles explotan al impactar contra una superficie o entidad.",
                        "",
                        "&8'Yo no se nada, yo vote por el Phantom!'"
                ).build();
    }
    public static ItemStack infoWindCharger(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ=="))
                .setDisplayName("&6Wind Charger | &eDía 14")
                .setLore(
                        "&7El hermano perdido del Breezer surge de los vientos",
                        "&7para causar problemas a los sobrevivientes!",
                        "",
                        "&6&lAparece en:",
                        "&7- Fortalezas del Nether",
                        "&7- Cualquier lugar en el Overworld durante una Monsoon",
                        "",
                        "&6&lComportamiento:",
                        "&7Al impactar sus proyectiles contra una superficie, crea una onda",
                        "&7de viento que expulsa a los jugadores en su radio.",
                        "",
                        "&8'Te diría algo importante de este bicho pero falta aún una update más para considerarlo'"
                ).build();
    }
    public static ItemStack infoUnstCreeper(){
        return new ItemBuilder(Material.CREEPER_HEAD)
                .setDisplayName("&6Unstable Creeper | &eDía 14")
                .setLore(
                        "&7Un creeper cuyos genes han sido modificados de tal manera",
                        "&7que su propia materia es inestable y similar a la de un Enderman.",
                        "",
                        "&6&lAparece en:",
                        "&7- Cualquier lugar",
                        "&7- Lugares donde solían haber gatos o perros",
                        "",
                        "&6&lComportamiento:",
                        "&7Se teletransporta aleatoriamente, es inmune al daño de proyectiles",
                        "&7y tiene una explosion de nivel 5.",
                        "",
                        "&8'Tener a uno de estos cerca es un tiro de moneda, o se teletransporta a un lugar lejano",
                        "&8y te mantienes a salvo, o se teletransporta en tu cara y sufres por el resto del dia'"
                ).build();
    }
    public static ItemStack infoQuantumite(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0="))
                .setDisplayName("&6Quantumite | &eDía 14")
                .setLore(
                        "&7Un endermite cuántico que posee cargas a niveles colosales,",
                        "&7sus capacidades reales aun son desconocidas.",
                        "",
                        "&6&lAparece en:",
                        "&7- Al tirar una ender pearl con bajas chances.",
                        "",
                        "&6&lComportamiento:",
                        "&7Al golpear teletransporta al jugador a un radio de 200 bloques aleatorio.",
                        "",
                        "&8'Yo no veo a estos bichos como algo tan peligroso, capaz te pueden ayudar en alguna situación,",
                        "&8de todas formas no te recomiendo acercate a uno, a menos que te guste aparecer en lugares que nunca viste'"
                ).build();
    }
    public static ItemStack infoVoidOverseer(){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("&6Void Overseer | &eDía 14")
                .setLore(
                        "&7Un esqueleto proveniente de las profundidades del vació,",
                        "&7sus flechas están imbuidas con esencia del End.",
                        "",
                        "&6&lAparece en:",
                        "&7- Cualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Se teletransporta donde cae sus flechas, al impactar con una entidad, cambia de posición",
                        "&7con la entidad.",
                        "",
                        "&8'Estos bastardos son un dolor de cabeza, y peor cuando hay mas de una entidad junto a el, te vas a tener",
                        "&8que inventar alguna maniobra para escapar de estos bichos'"
                ).build();
    }
    public static ItemStack infoLivingShrieker(){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("&6Living Shrieker | &eDía 14")
                .setLore(
                        "&7Una abominación proveniente del Deep Dark, un esqueleto fusionado",
                        "&7con un Shrieker, pero de alguna manera no invoca un Warden.",
                        "",
                        "&6&lAparece en:",
                        "&7- Cualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Lanza ondas supersónicas imprecisas hacia su objetivo",
                        "",
                        "&8'A pesar de su obvia situación, no posee ninguna propiedad del Warden mas que sus ondas",
                        "&8supersónicas, incluso si son imprecisas, son bastante dolorosas.'"
                ).build();
    }
    public static ItemStack infoStrayComan(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM1MDk3OTE2YmMwNTY1ZDMwNjAxYzBlZWJmZWIyODcyNzdhMzRlODY3YjRlYTQzYzYzODE5ZDUzZTg5ZWRlNyJ9fX0="))
                .setDisplayName("&6Stray Commando | &eDía 14")
                .setLore(
                        "&7Este stray regresa de una pesadilla con el único objetivo de",
                        "&7asesinar a todos los sobrevivientes restantes.",
                        "",
                        "&6&lAparece en:",
                        "&7- Lugares nevados.",
                        "",
                        "&6&lComportamiento:",
                        "&7Posee un hacha con filo 20, es bastante ágil y sus golpes congelan",
                        "&7a su objetivo",
                        "",
                        "&8'Estoy seguro que varios de ustedes ya han visto a este bicho mas de una vez,",
                        "&8realmente tengo que decir algo al respecto?'"
                ).build();
    }
    public static ItemStack infoSlimePes(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M1YjM4MDU3Zjg5OTY0MjUyMzI1NDM4MjNlMGU0YTRmYzhiZGJiNDg1OTE0YWMwNGFjYTkyMzFjMmU0YzhhMSJ9fX0="))
                .setDisplayName("&6Slime de Pesadilla | &eDía 14")
                .setLore(
                        "&7Un conjunto de Slimes combinados que acechan los",
                        "&7pantanos en busca de su siguiente victima.",
                        "",
                        "&6&lAparece en:",
                        "&7- Slime chunks",
                        "&7- Pantanos",
                        "",
                        "&6&lComportamiento:",
                        "&7Saltan hacia su objetivo haciendo 35 de daño base",
                        "",
                        "&8'Ten cuidado con estas criaturas, si te atrapan de alguna manera, recuperarse",
                        "&8va a ser muy difícil'"
                ).build();
    }
    public static ItemStack infoNeonSpider(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVlMjQ4ZGEyZTEwOGYwOTgxM2E2Yjg0OGEwZmNlZjExMTMwMDk3ODE4MGVkYTQxZDNkMWE3YThlNGRiYTNjMyJ9fX0="))
                .setDisplayName("&6Araña de Neón | &eDía 14")
                .setLore(
                        "&7Una araña cuyas fauces poseen capacidades extrañas",
                        "&7que le permiten inyectar sustancias brillantes.",
                        "",
                        "&6&lAparece en:",
                        "&7- Cualquier lugar.",
                        "",
                        "&6&lComportamiento:",
                        "&7Sus picaduras infligen Glowing, el cual aumenta tu exposición.",
                        "",
                        "&8'Vigila tu exposición y no dejes que este bicho te arruine tu sigilo'"
                ).build();
    }
    public static ItemStack infoLangosta(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQyMGM5YzQzZTA5NTg4MGRjZDJlMjgxYzgxZjQ3YjE2M2I0NzhmNThhNTg0YmI2MWY5M2U2ZTEwYTE1NWYzMSJ9fX0="))
                .setDisplayName("&6Langosta | &eDía 14")
                .setLore(
                        "&7Un bicho volador que tiene propiedades muy",
                        "&7dañinas para una persona, usa su aguijón para atacar.",
                        "",
                        "&6&lAparece en:",
                        "&7- Algunas partes.",
                        "&7- Rompiendo panales de abeja",
                        "",
                        "&6&lComportamiento:",
                        "&7Sus picaduras infligen debilidad y lentitud, aparte que no mueren",
                        "&7al perder su aguijón.",
                        "",
                        "&8'No tengo idea de donde surgieron estos bichos, son una plaga molesta y ya'"
                ).build();
    }
    public static ItemStack infoAshenWither(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODg2ZGMwY2ZjYWVlY2ZlMWFiNjkxNDZlNGQ0ZjExOTA4MzcwNzZhNjdkZWMxMzVmYWJkYTYyNzFmMzc1ZDAxZiJ9fX0="))
                .setDisplayName("&6Ashen Wither | &eDía 14")
                .setLore(
                        "&7Un misterioso ente proveniente de la Wasteyard, posee la fuerza para",
                        "&7acabar con sus oponentes con facilidad.",
                        "",
                        "&6&lAparece en:",
                        "&7- Invocando a un Wither Boss en Wasteyard",
                        "",
                        "&6&lComportamiento:",
                        "&7Sus calaveras tienen explosion de nivel 2 y si están cargadas de nivel 4",
                        "&7tiene 3 ataques diferentes y tiene el doble de vida que un Wither Boss normal",
                        "",
                        "&8'Este va a ser la primera criatura fuerte que te enfrentes, preparate bien!'"
                ).build();
    }
    public static ItemStack infoScorchbeast(){
        return new ItemBuilder(Material.PIGLIN_HEAD)
                .setDisplayName("&6Scorched Brute | &eDía 14")
                .setLore(
                        "&7Un Piglin que se perdio dentro de la Wasteyard, ahora preparado",
                        "&7con la fuerza de la dimensión, busca un objetivo para atacar.",
                        "",
                        "&6&lAparece en:",
                        "&7- Wasteyard",
                        "",
                        "&6&lComportamiento:",
                        "&7Tiene velocidad 2 y posee un hacha de netherite",
                        "",
                        "&8'Un cerdo mas dentro de un vertedero infernal, acaso vi esto en otra parte?'"
                ).build();
    }
    public static ItemStack infoLostScav(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFlZTZiYjM3Y2JmYzkyYjBkODZkYjVhZGE0NzkwYzY0ZmY0NDY4ZDY4Yjg0OTQyZmRlMDQ0MDVlOGVmNTMzMyJ9fX0="))
                .setDisplayName("&6Lost Scavenger | &eDía 14")
                .setLore(
                        "&7Un Pillager que se perdio dentro de la Wasteyard, ahora buscando la manera",
                        "&7de escapar, lleva consigo flechas especiales.",
                        "",
                        "&6&lAparece en:",
                        "&7- Wasteyard",
                        "",
                        "&6&lComportamiento:",
                        "&7Tiene una ballesta poderosa con flechas de Fatiga, Glowing, Wither y Slowness.",
                        "",
                        "&8'Mas que un peligro real, estos vagabundos son mas una gran molestia por sus flechas",
                        "&8especiales, mantente lejos de ellos!'"
                ).build();
    }
    public static ItemStack infoRustwalker(){
        return new ItemBuilder(Material.CREEPER_HEAD)
                .setDisplayName("&6Rustwalker | &eDía 14")
                .setLore(
                        "&7Creaciones mecánicas con un propósito desconocido, vagan por la Wasteyard",
                        "&7buscando un objetivo para explotar.",
                        "",
                        "&6&lAparece en:",
                        "&7- Wasteyard",
                        "",
                        "&6&lComportamiento:",
                        "&7Tiene explosion de nivel 6 y tiene un fuse de 1.25 segundos",
                        "",
                        "&8'Estos robots casi no te dan tiempo a reaccionar, mantente al tanto de estas",
                        "&8chatarras!'"
                ).build();
    }
    public static ItemStack infoBrimseeker(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRmNGIzNDAxYTRkMDZhZDY2YWM4YjVjNGQxODk2MThhZTYxN2Y5YzE0MzA3MWM4YWMzOWE1NjNjZjRlNDIwOCJ9fX0="))
                .setDisplayName("&6Brimseeker | &eDía 14")
                .setLore(
                        "&7Loros esqueléticos que rondan por la Wasteyard buscando a",
                        "&7alguien a quien picotear.",
                        "",
                        "&6&lAparece en:",
                        "&7- Wasteyard",
                        "",
                        "&6&lComportamiento:",
                        "&7Vuelan hacia el jugador y atacan con 40 de daño base.",
                        "",
                        "&8'Quien dejo a sus mascotas por la Wasteyard? no pueden ir llamando al control",
                        "&8de animales?, Pensándolo bien, porfavor no hagas eso'"
                ).build();
    }
    public static ItemStack infoBrimstoneCube(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTFjOTdhMDZlZmRlMDRkMDAyODdiZjIwNDE2NDA0YWIyMTAzZTEwZjA4NjIzMDg3ZTFiMGMxMjY0YTFjMGYwYyJ9fX0="))
                .setDisplayName("&6Brimstone Cube | &eDía 14")
                .setLore(
                        "&7Cubos de azufre que merodean por la Wasteyard sin algún propósito,",
                        "&7sin embargo, son hostiles ante alguna presencia humana.",
                        "",
                        "&6&lAparece en:",
                        "&7- Wasteyard",
                        "",
                        "&6&lComportamiento:",
                        "&7Saltan hacia su objetivo teniendo 20 de daño base e infligen Wither",
                        "",
                        "&8'No entiendo porque los Magma Cubes decidieron combinarse con los restos de Azufre, pero no",
                        "&8se puede juzgar entidades sin cerebro'"
                ).build();
    }
    public static ItemStack infoWanderingVagrant(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E4YjcxNGQzMmQ3ZjZjZjhiMzdlMjIxYjc1OGI5YzU5OWZmNzY2NjdjN2NkNDViYmM0OWM1ZWYxOTg1ODY0NiJ9fX0="))
                .setDisplayName("&6Wandering Vagrant | &eDía 14")
                .setLore(
                        "&7Son criaturas que rodean los cielos oscuros de la Wasteyard,",
                        "&7nadie sabe realmente que son, ni como llegaron ahi.",
                        "",
                        "&6&lAparece en:",
                        "&7- Wasteyard",
                        "",
                        "&6&lComportamiento:",
                        "&7Lanza bolas de fuego con explosion de nivel 8",
                        "",
                        "&8'Si uno te llega a ver, preparate para correr, sus explosiones no son algo",
                        "&8de lo que te gustara afrontar'"
                ).build();
    }

    //Dia 21
    public static ItemStack infoJineteCerdo(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0="))
                .setDisplayName("&6Jinete Cerdo-pocalíptico| &eDía 21")
                .setLore(
                        "&7Zombi Piglin que por métodos desconocidos logro domesticar",
                        "&7a un cerdo del Overworld, no se sabe como llegaron ahi, o como",
                        "&7fueron domesticado, lo cierto es que pegan duro.",
                        "",
                        "&6&lAparece en:",
                        "&7- Nether",
                        "",
                        "&6&lComportamiento:",
                        "&7Avanza rápidamente hacia su objetivo y ataca.",
                        "",
                        "&8'Deberías priorizar matar al cerdo primero, te lo digo por experiencia'"
                ).build();
    }
    public static ItemStack infoPiglinShinobi(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0="))
                .setDisplayName("&6Piglin Shinobi| &eDía 21")
                .setLore(
                        "&7Zombi Piglin que al igual que el Zombi Ninja, tiene capacidades",
                        "&7sobrenaturales en el sigilo, sin embargo, este parece haberlo",
                        "&7aprendido de otra manera.",
                        "",
                        "&6&lAparece en:",
                        "&7- Nether",
                        "",
                        "&6&lComportamiento:",
                        "&7Al enfocarse en un objetivo se vuelve invisible y silencioso, sus",
                        "&7ataques lo dejaran al descubierto por unos momentos.",
                        "",
                        "&8'Te podría decir algo util contra este cerdo, pero una vez lo pierdas de",
                        "&8vista te olvidaras'"
                ).build();
    }
    public static ItemStack infoPiglinAlquimista(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0="))
                .setDisplayName("&6Alquimista Porcino | &eDía 21")
                .setLore(
                        "&7Un Zombi Piglin con una gran maestría en disciplinas científicas,",
                        "&7capaz de crear combinaciones de otro mundo, lastimosamente en el Nether",
                        "&7no hay interesados, asi que se desquita tirando pociones a lo loco.",
                        "",
                        "&6&lAparece en:",
                        "&7- Nether",
                        "",
                        "&6&lComportamiento:",
                        "&7Al tener un objetivo cercano lanza pociones de area, con efectos de",
                        "&7Wither, Instant Damage o Glowing.",
                        "",
                        "&8'Cuidado con las pociones de Glowing!, son de nivel 5 y eso hará que",
                        "&8tu exposición aumente colosalmente'"
                ).build();
    }
    public static ItemStack infoEntropicDemons(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E4YjcxNGQzMmQ3ZjZjZjhiMzdlMjIxYjc1OGI5YzU5OWZmNzY2NjdjN2NkNDViYmM0OWM1ZWYxOTg1ODY0NiJ9fX0="))
                .setDisplayName("&6Entropic Demons | &eDía 21")
                .setLore(
                        "&7Demonios flotantes que viajaron desde lo mas profundo del nether para acabar",
                        "&7aquí, grande fue su decepción cuando no notaron la minima diferencia de su",
                        "&7lugar de origen con el actual.",
                        "",
                        "&6&lAparece en:",
                        "&7- Nether",
                        "",
                        "&6&lComportamiento:",
                        "&7Tiene explosion de nivel 7, sus impactos dan Levitación.",
                        "",
                        "&8'Es un Ghast, actua como Ghast, tiene las mismas debilidades que un Ghast,",
                        "&8no necesito decirte más'"
                ).build();
    }
    public static ItemStack infoSteelRailgunner(){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setDisplayName("&6Steel Railgunner | &eDía 21")
                .setLore(
                        "&7Robot consiente que tiene una sola misión:",
                        "&7Erradicar a cualquier sobreviviente restante.",
                        "&7No dice ni dirá ninguna frase icónica",
                        "",
                        "&6&lAparece en:",
                        "&7- Cualquier Lugar",
                        "",
                        "&6&lComportamiento:",
                        "&7Sus proyectiles dejan TNT cargada al impactar, la TNT tiene explosion",
                        "&7explosion nivel 5, todo el daño no-critico recibido es reducido en un 99%.",
                        "",
                        "&8'No es un Terminator, de hecho tiene una puntería terrible, aprovecha",
                        "&8esto para hacerle frente'"
                ).build();
    }
    public static ItemStack infoTitaniumCreeper(){
        return new ItemBuilder(Material.CREEPER_HEAD)
                .setDisplayName("&6Titanium Creeper | &eDía 21")
                .setLore(
                        "&7Creeper acorazado con fuertes soportes de titanio, hecho",
                        "&7exclusivamente para protegerlo de cualquier fuente de daño,",
                        "&7esto indirectamente afecto su habilidad de explosion.",
                        "",
                        "&6&lAparece en:",
                        "&7- Cualquier Lugar",
                        "",
                        "&6&lComportamiento:",
                        "&7Su explosion es de nivel 5, y pone Glowing 5 a jugadores cercanos al explotar",
                        "&7todo el daño no-critico recibido es reducido en un 99%.",
                        "",
                        "&8'Me aterra mucho cualquier bicho que aplique Glowing 5 en un toque, y este creeper",
                        "&8no es una excepción, cuida tus alrededores al momento de enfrentártelo'"
                ).build();
    }
    public static ItemStack infoCyberpunk(){
        return new ItemBuilder(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGIzNmQ1M2ZmZjY5YTQ5YzdkNmYzOTMyZjJiMGZlOTQ4ZTAzMjIyNmQ1ZTgwNDVlYzU4NDA4YTM2ZTk1MSJ9fX0="))
                .setDisplayName("&6Cyberpunk | &eDía 21")
                .setLore(
                        "&7Cyborg altamente reforzado con una velocidad sobrenatural,",
                        "&7super dotado en acabar con su objetivo indicado.",
                        "",
                        "&6&lAparece en:",
                        "&7- Cualquier Lugar",
                        "",
                        "&6&lComportamiento:",
                        "&7Posee habilidades de un Enderman normal, pero su velocidad es mucho mas alta",
                        "&7todo el daño no-critico recibido es reducido en un 99%.",
                        "",
                        "&8'El mas fácil de los 3, que su velocidad no te intimide! con un critico basta",
                        "&8para acabar con el'"
                ).build();
    }


}
