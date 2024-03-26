package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.DataManager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import static com.tll3.Misc.GenericUtils.*;

public class MissionListeners implements Listener {

    private static Player killer;

    private static int getObjective(String objective){
        return PlayerData.getObjective(killer,objective);
    }

    private static int getMission(String mission){
        return PlayerData.getMission(killer,mission);
    }

    private static void upgradeMissionProgress(String mission){
        PlayerData.setObjectiveCount(killer,mission,getObjective(mission) + 1);
    }

    private static void checkSinPiedadMissions(LivingEntity entity, EntityType tipomob, String objetivo){
        if(entity.getType() == tipomob && getMission("01_killall") != 1){
            if(getObjective(objetivo) == 0)
                PlayerData.setObjectiveCount(killer,objetivo,1);
            if(checkNoMercy(killer))
                missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
        }
    }

    @EventHandler
    public void killmissions(EntityDeathEvent e){
        var entity = e.getEntity();
        var killer = e.getEntity().getKiller();

        if (killer == null)
            return;

        MissionListeners.killer = killer;
        System.out.println("Killer: " + killer.getName());

        if(getDay() >= 0 && getDay() < 7){

            if(entity instanceof IronGolem && getMission("05_violencia") != 1){
                if(getObjective("05golems")<5)
                    upgradeMissionProgress("05golems");
                else
                    missionCompleted(killer,"#ff954fViolencia Innecesaria","05_violencia",10,4);
            }

            if(entity.getType() == EntityType.GUARDIAN && getMission("04_atlantis") != 1){
                if(getObjective("04guardianes") < 20)
                    upgradeMissionProgress("04guardianes");
                if(getObjective("04guardianes") >= 20 && getObjective("04elderguardian") >= 1)
                    missionCompleted(killer,"#69c5ffAnti-Atlantis","04_atlantis",20,14);
            }

            if(entity.getType() == EntityType.ELDER_GUARDIAN && getMission("04_atlantis") != 1){
                if(getObjective("04elderguardian") < 3)
                    upgradeMissionProgress("04elderguardian");
                if(getObjective("04guardianes") >= 20 && getObjective("04elderguardian") >= 3)
                    missionCompleted(killer,"#69c5ffAnti-Atlantis","04_atlantis",20,14);
            }

            if(entity.getType() == EntityType.WARDEN && getMission("03_warden") != 1){
                if(getObjective("03warden") == 0)
                    upgradeMissionProgress("03warden");
                if(getObjective("03warden") >= 1){
                    missionCompleted(killer,"#004736Aprende y Escucha","03_warden",30,15);
                }
            }
            if(entity.getType() == EntityType.WITHER  && getMission("02_wither") != 1){
                if(getObjective("02wither") < 5)
                    upgradeMissionProgress("02wither");
                else
                    missionCompleted(killer,"#474747Maestro Descompuesto","02_wither",15,5);
            }

            //Sin Piedad inicio

            checkSinPiedadMissions(entity,EntityType.ZOMBIE,"01zom");
            checkSinPiedadMissions(entity,EntityType.SKELETON,"01ske");
            checkSinPiedadMissions(entity,EntityType.CREEPER,"01cre");
            checkSinPiedadMissions(entity,EntityType.SPIDER,"01ara");
            checkSinPiedadMissions(entity,EntityType.CAVE_SPIDER,"01cue");
            checkSinPiedadMissions(entity,EntityType.ENDERMAN,"01end");
            checkSinPiedadMissions(entity,EntityType.PHANTOM,"01pha");
            checkSinPiedadMissions(entity,EntityType.HUSK,"01hus");
            checkSinPiedadMissions(entity,EntityType.STRAY,"01str");
            checkSinPiedadMissions(entity,EntityType.DROWNED,"01dro");
            checkSinPiedadMissions(entity,EntityType.SILVERFISH,"01lep");
            checkSinPiedadMissions(entity,EntityType.ENDERMITE,"01mit");
            checkSinPiedadMissions(entity,EntityType.PILLAGER,"01pil");
            checkSinPiedadMissions(entity,EntityType.VINDICATOR,"01vin");
            checkSinPiedadMissions(entity,EntityType.WITCH,"01bru");
            checkSinPiedadMissions(entity,EntityType.EVOKER,"01evo");
            checkSinPiedadMissions(entity,EntityType.RAVAGER,"01rav");
            checkSinPiedadMissions(entity,EntityType.VEX,"01vex");

            //Sin Piedad fin
        }else if(getDay() >= 7 && getDay() < 14){

            if(entity.getType() == EntityType.ZOMBIE){
                if(PlayerData.getMission(killer,"12_demon") != 1){
                    if(Data.has(entity,"revenantzombie", PersistentDataType.STRING)){
                        if(PlayerData.getObjective(killer,"12revzo") < 10) {
                            PlayerData.setObjectiveCount(killer, "12revzo", PlayerData.getObjective(killer, "12revzo") + 1);
                        }
                    }
                    if(checkDemonDeath(killer)){
                        missionCompleted(killer,"#8f0a00Caza de Demonios","12_demon",40,20);
                    }
                }
                if(PlayerData.getMission(killer,"15_cheat") != 1){
                    if(Data.has(entity,"dead_arq",PersistentDataType.STRING)){
                        if(PlayerData.getObjective(killer,"15arq") < 10) {
                            PlayerData.setObjectiveCount(killer, "15arq", PlayerData.getObjective(killer, "15arq") + 1);
                        }
                        if(PlayerData.getObjective(killer,"15arq") >= 10 && PlayerData.getObjective(killer,"15raz") >= 10){
                            missionCompleted(killer,"#ed572dAnti-cheat","15_cheat",22,14);
                        }
                    }
                }
            }
            if(entity.getType() == EntityType.SKELETON){
                if(PlayerData.getMission(killer,"12_demon") != 1){
                    if(Data.has(entity,"revenantskeleton", PersistentDataType.STRING)){
                        if(PlayerData.getObjective(killer,"12revsk") < 10) {
                            PlayerData.setObjectiveCount(killer, "12revsk", PlayerData.getObjective(killer, "12revsk") + 1);
                        }
                    }
                    if(checkDemonDeath(killer)){
                        missionCompleted(killer,"#8f0a00Caza de Demonios","12_demon",40,20);
                    }
                }
                if(PlayerData.getMission(killer,"15_cheat") != 1){
                    if(Data.has(entity,"razorback",PersistentDataType.STRING)){
                        if(PlayerData.getObjective(killer,"15raz") < 10) {
                            PlayerData.setObjectiveCount(killer, "15raz", PlayerData.getObjective(killer, "15raz") + 1);
                        }
                        if(PlayerData.getObjective(killer,"15arq") >= 10 && PlayerData.getObjective(killer,"15raz") >= 10){
                            missionCompleted(killer,"#ed572dAnti-cheat","15_cheat",22,14);
                        }
                    }
                }
            }
            if(entity.getType() == EntityType.SPIDER){
                if(PlayerData.getMission(killer,"12_demon") != 1){
                    if(Data.has(entity,"revenantspider", PersistentDataType.STRING)){
                        if(PlayerData.getObjective(killer,"12revsp") < 10) {
                            PlayerData.setObjectiveCount(killer, "12revsp", PlayerData.getObjective(killer, "12revsp") + 1);
                        }
                    }
                    if(checkDemonDeath(killer)){
                        missionCompleted(killer,"#8f0a00Caza de Demonios","12_demon",40,20);
                    }
                }
            }
            if(entity.getType() == EntityType.CREEPER){
                if(PlayerData.getMission(killer,"12_demon") != 1){
                    if(Data.has(entity,"revenantcreeper", PersistentDataType.STRING)){
                        if(PlayerData.getObjective(killer,"12revcr") < 10) {
                            PlayerData.setObjectiveCount(killer, "12revcr", PlayerData.getObjective(killer, "12revcr") + 1);
                        }
                    }
                    if(checkDemonDeath(killer)){
                        missionCompleted(killer,"#8f0a00Caza de Demonios","12_demon",40,20);
                    }
                }
            }
            if(entity.getType() == EntityType.ENDERMAN){
                if(PlayerData.getMission(killer,"12_demon") != 1){
                    if(Data.has(entity,"revenantenderman", PersistentDataType.STRING)){
                        if(PlayerData.getObjective(killer,"12reven") < 10) {
                            PlayerData.setObjectiveCount(killer, "12reven", PlayerData.getObjective(killer, "12reven") + 1);
                        }
                    }
                    if(checkDemonDeath(killer)){
                        missionCompleted(killer,"#8f0a00Caza de Demonios","12_demon",40,20);
                    }
                }
            }


            if(entity.getType() == EntityType.WITHER_SKELETON && PlayerData.getMission(killer, "11_rol") != 1) {
                if(Data.has(entity,"w_swordsman", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"11sword") < 5) {
                        PlayerData.setObjectiveCount(killer, "11sword", PlayerData.getObjective(killer, "11sword") + 1);
                    }
                    if(checkRPG(killer)){
                        missionCompleted(killer,"#474747Juegos de Rol","11_rol",25,14);
                    }
                }
                if(Data.has(entity,"w_archer", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"11archer") < 5) {
                        PlayerData.setObjectiveCount(killer, "11archer", PlayerData.getObjective(killer, "11archer") + 1);
                    }
                    if(checkRPG(killer)){
                        missionCompleted(killer,"#474747Juegos de Rol","11_rol",25,14);
                    }
                }
                if(Data.has(entity,"w_tank", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"11jugger") < 5) {
                        PlayerData.setObjectiveCount(killer, "11jugger", PlayerData.getObjective(killer, "11jugger") + 1);
                    }
                    if(checkRPG(killer)){
                        missionCompleted(killer,"#474747Juegos de Rol","11_rol",25,14);
                    }
                }
                if(Data.has(entity,"w_mage", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"11mage") < 5) {
                        PlayerData.setObjectiveCount(killer, "11mage", PlayerData.getObjective(killer, "11mage") + 1);
                    }
                    if(checkRPG(killer)){
                        missionCompleted(killer,"#474747Juegos de Rol","11_rol",25,14);
                    }
                }
            }
            if(entity.getType() == EntityType.CAVE_SPIDER && PlayerData.getMission(killer, "13_plaga") != 1) {
                if(Data.has(entity,"termite", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"13term") < 10) {
                        PlayerData.setObjectiveCount(killer, "13term", PlayerData.getObjective(killer, "13term") + 1);
                    }
                    if(PlayerData.getObjective(killer,"13term") >= 10 && PlayerData.getObjective(killer,"13colterm") >= 10){
                        missionCompleted(killer,"#17c200Epidemia Explosiva","13_plaga",28,16);
                    }
                }
                if(Data.has(entity,"termite_ex", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"13colterm") < 10) {
                        PlayerData.setObjectiveCount(killer, "13colterm", PlayerData.getObjective(killer, "13colterm") + 1);
                    }
                    if(PlayerData.getObjective(killer,"13term") >= 10 && PlayerData.getObjective(killer,"13colterm") >= 10){
                        missionCompleted(killer,"#17c200Epidemia Explosiva","13_plaga",28,16);
                    }
                }
            }
            if(entity.getType() == EntityType.PHANTOM && PlayerData.getMission(killer, "14_phantom") != 1) {
                if(Data.has(entity,"duskphantom", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"14dusk") < 5) {
                        PlayerData.setObjectiveCount(killer, "14dusk", PlayerData.getObjective(killer, "14dusk") + 1);
                    }else{
                        missionCompleted(killer,"#37456eAcechadores Nocturnos","14_phantom",18,12);
                    }
                }
            }
        }else if(getDay() >= 14 && getDay() < 21){
            if (PlayerData.getMission(killer, "21_waste") != 1) {
                if (checkWasteyardKills(killer)) {
                    missionCompleted(killer, "#524843De parte del Vertedero", "21_waste", 45, 30);
                } else {
                    switch (entity.getType()) {
                        case PILLAGER -> AddAndCheckKillCount(entity, "lostscav", 5, killer, "21los");
                        case MAGMA_CUBE -> AddAndCheckKillCount(entity, "toxiccrawler", 5, killer, "21bri");
                        case PARROT -> AddAndCheckKillCount(entity, "brimseeker", 5, killer, "21par");
                        case PIGLIN_BRUTE -> AddAndCheckKillCount(entity, "scorchbeast", 5, killer, "21sco");
                        case GHAST -> AddAndCheckKillCount(entity, "soulvag", 5, killer, "21wan");
                        case CREEPER -> AddAndCheckKillCount(entity, "rustwalker", 5, killer, "21rus");
                    }
                }
            }
            if (PlayerData.getMission(killer, "22_blaze") != 1) {
                if(entity.getType() == EntityType.BLAZE){
                if (PlayerData.getObjective(killer, "22wind") >= 10 && PlayerData.getObjective(killer, "22armor") >= 10) {
                    missionCompleted(killer, "#fae3c3¡Vienen del Futuro! (y del pasado)", "22_blaze", 35, 20);

                }else{
                    AddAndCheckKillCount(entity, "windcharger", 10, killer, "22wind");
                    AddAndCheckKillCount(entity, "armoredblaze", 10, killer, "22armor");
                    }
                }
            }
            if (PlayerData.getMission(killer, "23_ash") != 1) {
                if (PlayerData.getObjective(killer, "23ashen") >= 1) {
                    missionCompleted(killer, "#737373Desde las Cenizas", "23_ash", 40, 25);
                }
                if (entity.getType() == EntityType.WITHER) {
                    if (PlayerData.getObjective(killer, "23ashen") >= 1) {
                        AddAndCheckKillCount(entity, "ashenwither", 1, killer, "23ashen");
                        missionCompleted(killer, "#737373Desde las Cenizas", "23_ash", 40, 25);
                    }
                }
            }
            if (PlayerData.getMission(killer, "24_bee") != 1) {
                if (PlayerData.getObjective(killer, "24bee") >= 20) {
                    missionCompleted(killer, "#255c3cÉpoca de plaga", "24_bee", 32, 20);
                    return;
                }
                if (entity.getType() == EntityType.BEE) {
                    if (PlayerData.getObjective(killer, "24bee") < 20) {
                        PlayerData.setObjectiveCount(killer, "24bee", PlayerData.getObjective(killer, "24bee") + 1);
                    }
                }
            }
            if (PlayerData.getMission(killer, "25_llama") != 1) {
                if (PlayerData.getObjective(killer, "25lla") >= 10 && PlayerData.getObjective(killer, "25goa") >= 10) {
                    missionCompleted(killer, "#ab789bEl pastor y su rebaño", "25_llama", 36, 24);
                    return;
                }
                if (entity.getType() == EntityType.GOAT) {
                    if (PlayerData.getObjective(killer, "25lla") < 10) {
                        PlayerData.setObjectiveCount(killer, "25lla", PlayerData.getObjective(killer, "25lla") + 1);
                    }
                }
                if (entity.getType() == EntityType.LLAMA) {
                    if (PlayerData.getObjective(killer, "25goa") < 10) {
                        PlayerData.setObjectiveCount(killer, "25goa", PlayerData.getObjective(killer, "25goa") + 1);
                    }
                }
            }
        }else if(getDay() >= 21 && getDay() < 28){
            if (PlayerData.getMission(killer, "31_metal") != 1) {
                if (entity.getType() == EntityType.SKELETON || entity.getType() == EntityType.CREEPER || entity.getType() == EntityType.ENDERMAN) {
                    if (PlayerData.getObjective(killer, "31ste") >= 5 && PlayerData.getObjective(killer, "31tit") >= 5 && PlayerData.getObjective(killer, "31cyb") >= 5) {
                        missionCompleted(killer, "#737373D#717171e#707070s#6e6e6eo#6c6c6cx#6b6b6bi#696969d#686868a#666666c#646464i#636363ó#616161n", "31_metal", 45, 30);
                    } else {
                        AddAndCheckKillCount(entity, "steelrailgunner", 5, killer, "31ste");
                        AddAndCheckKillCount(entity, "cyberpunk", 5, killer, "31cyb");
                        AddAndCheckKillCount(entity, "titaniumcreeper", 5, killer, "31tit");
                    }
                }
            }
            if (PlayerData.getMission(killer, "32_mcleg") != 1) {
                if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN) {
                    if (PlayerData.getObjective(killer, "32pig1") >= 10 && PlayerData.getObjective(killer, "32pig2") >= 10 && PlayerData.getObjective(killer, "32pig3") >= 10) {
                        missionCompleted(killer, "#34cbfbM#3ccefbi#44d0fbn#4cd3fbe#54d6fcc#5cd8fcr#64dbfca#6cdefcf#75e0fct #7de3fcL#85e6fce#8de8fcg#95ebfde#9deefdn#a5f0fdd#adf3fds", "32_mcleg", 54, 36);
                    } else {
                        AddAndCheckKillCount(entity, "pigrider", 10, killer, "32pig3");
                        AddAndCheckKillCount(entity, "shinobipig", 10, killer, "32pig1");
                        AddAndCheckKillCount(entity, "alchpig", 10, killer, "32pig2");
                    }
                }
            }
            if (PlayerData.getMission(killer, "33_blas") != 1) {
                if (entity.getType() == EntityType.GHAST) {
                    if (PlayerData.getObjective(killer, "33gas") >= 5) {
                        missionCompleted(killer, "#fbf8aa¡#fbf9b2B#fbf9bbl#fcfac3a#fcfacbs#fcfbd4f#fcfbdce#fcfce4m#fdfceci#fdfdf5a#fdfdfd!", "33_blas", 42, 28);
                    } else {
                        AddAndCheckKillCount(entity, "entropicdemon", 5, killer, "33gas");
                    }
                }
            }
            if (PlayerData.getMission(killer, "34_prop") != 1) {
                if (entity.getType() == EntityType.PILLAGER || entity.getType() == EntityType.VINDICATOR || entity.getType() == EntityType.EVOKER) {
                    if (PlayerData.getObjective(killer, "34ill1") >= 5 && PlayerData.getObjective(killer, "34ill2") >= 5 && PlayerData.getObjective(killer, "34ill3") >= 5) {
                        missionCompleted(killer, "#572424I#592929n#5b2d2dv#5d3232a#5e3737s#603b3bi#624040ó#644545n #664949d#684e4ee #695353P#6b5757r#6d5c5co#6f6161p#716565i#736a6ae#746f6fd#767373a#787878d", "34_prop", 50, 34);
                    } else {
                        AddAndCheckKillCount(entity, "pillagerex", 5, killer, "34ill1");
                        AddAndCheckKillCount(entity, "vindicatorex", 5, killer, "34ill2");
                        AddAndCheckKillCount(entity, "evokerex", 5, killer, "34ill3");
                    }
                }
            }
            if (PlayerData.getMission(killer, "35_lap") != 1) {
                if (entity.getType() == EntityType.SKELETON) {
                    if (checkLap2(killer)) {
                        missionCompleted(killer, "#9428ffS#8f28ffe#8a29ffg#8429ffu#7f29ffn#7a29ffd#752affa #6f2affV#6a2affu#652affe#602bffl#5a2bfft#552bffa", "35_lap", 54, 36);
                    } else {
                        AddAndCheckKillCount(entity, "revenantskeleton", 1, killer, "35ske1");
                        AddAndCheckKillCount(entity, "firemancer", 1, killer, "35ske2"); //Firemancer
                        AddAndCheckKillCount(entity, "bruteskeleton", 1, killer, "35ske3"); //Rogue Skeleton
                        AddAndCheckKillCount(entity, "razorback", 1, killer, "35ske4");
                        AddAndCheckKillCount(entity, "void_overseer", 1, killer, "35ske5");
                        AddAndCheckKillCount(entity, "livshriek", 1, killer, "35ske6");
                    }
                }
            }
        }
    }


    public static void missionCompleted(Player p,String mission,String completed,int credits,int prestige){
        for(Player online : Bukkit.getOnlinePlayers()){
            online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador #fff159" + p.getName() + " &7ha completado la mision &8[" + mission + "&8]"));
            online.getLocation().getWorld().playSound(online.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,10.0F,-1.0F);
        }
        p.getLocation().getWorld().playSound(p.getLocation(),Sound.UI_TOAST_CHALLENGE_COMPLETE,10.0F,2.0F);
        PlayerData.setMissionCount(p,completed,1);
        PlayerData.setCredits(p,PlayerData.getCredits(p) + credits);
        PlayerData.setPrestige(p,PlayerData.getPrestige(p) + prestige);
        PlayerData.setHunts(p,PlayerData.getHunts(p) + 1);
    }

    public static void AddAndCheckKillCount(LivingEntity liv,String data, int maxkills,Player killer,String objective){
        if(Data.has(liv,data,PersistentDataType.STRING)){
            if(PlayerData.getObjective(killer,objective) < maxkills) {
                PlayerData.setObjectiveCount(killer, objective, PlayerData.getObjective(killer, objective) + 1);
            }
        }

    }

    public static boolean checkNoMercy(Player p){
        return checkMisions(p, 1,
                "01zom", "01ske", "01cre", "01ara", "01cue",
                "01end", "01str", "01hus", "01dro", "01lep", "01mit",
                "01pil", "01vin", "01evo", "01bru", "01rav", "01vex",
                "01pha"
        );
    }
    public static boolean checkRPG(Player p){
        return checkMisions(p, 5,
                "11sword", "11jugger", "11archer", "11mage"
        );
    }
    public static boolean checkDemonDeath(Player p){
        return checkMisions(p, 10,
                "12revzo", "12revsk", "12revsp", "12revcr", "12reven"
        );
    }
    public static boolean checkWasteyardKills(Player p){
        return checkMisions(p, 5,
                "21sco", "21par", "21rus", "21los", "21wan", "21bri"
        );
    }
    public static boolean checkLap2(Player p){
        return checkMisions(p, 1,
                    "35ske1", "35ske2", "35ske3", "35ske4", "35ske5", "35ske6"
                );
    }

    private static boolean checkMisions(Player p, int umbral, String... misiones){
        for(String mision : misiones){
            if(PlayerData.getMission(p,mision) >= umbral){
                return false;
            }
        }
        return true;
    }

}
