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

    @EventHandler
    public void killmissions(EntityDeathEvent e){
        var entity = e.getEntity();
        var killer = e.getEntity().getKiller();
        if(getDay() >= 0 && getDay() < 7){
            if(entity instanceof IronGolem && killer != null && PlayerData.getMission(killer,"05_violencia") != 1){
                if(PlayerData.getObjective(killer,"05golems") < 5){
                   PlayerData.setObjectiveCount(killer,"05golems",PlayerData.getObjective(killer,"05golems") + 1);
                }else{
                    missionCompleted(killer,"#ff954fViolencia Innecesaria","05_violencia",10,4);
                }
            }
            if(entity.getType() == EntityType.GUARDIAN && killer != null && PlayerData.getMission(killer,"04_atlantis") != 1){
                if(PlayerData.getObjective(killer,"04guardianes") < 20){
                    PlayerData.setObjectiveCount(killer,"04guardianes",PlayerData.getObjective(killer,"04guardianes") + 1);
                }
                if(PlayerData.getObjective(killer,"04guardianes") >= 20 && PlayerData.getObjective(killer,"04elderguardian") >= 1){
                    missionCompleted(killer,"#69c5ffAnti-Atlantis","04_atlantis",20,14);
                }
            }
            if(entity.getType() == EntityType.ELDER_GUARDIAN && killer != null && PlayerData.getMission(killer,"04_atlantis") != 1){
                if(PlayerData.getObjective(killer,"04elderguardian") < 3){
                    PlayerData.setObjectiveCount(killer,"04elderguardian",PlayerData.getObjective(killer,"04elderguardian") + 1);
                }
                if(PlayerData.getObjective(killer,"04guardianes") >= 20 && PlayerData.getObjective(killer,"04elderguardian") >= 3){
                    missionCompleted(killer,"#69c5ffAnti-Atlantis","04_atlantis",20,14);
                }
            }
            if(entity.getType() == EntityType.WARDEN && killer != null && PlayerData.getMission(killer,"03_warden") != 1){
                if(PlayerData.getObjective(killer,"03warden") == 0){
                    PlayerData.setObjectiveCount(killer,"03warden",PlayerData.getObjective(killer,"03warden") + 1);
                }
                if(PlayerData.getObjective(killer,"03warden") >= 1){
                    missionCompleted(killer,"#004736Aprende y Escucha","03_warden",30,15);
                }
            }
            if(entity.getType() == EntityType.WITHER && killer != null && PlayerData.getMission(killer,"02_wither") != 1){
                if(PlayerData.getObjective(killer,"02wither") < 5){
                    PlayerData.setObjectiveCount(killer,"02wither",PlayerData.getObjective(killer,"02wither") + 1);
                }else{
                    missionCompleted(killer,"#474747Maestro Descompuesto","02_wither",15,5);
                }
            }

            //Sin Piedad inicio
            if(entity.getType() == EntityType.ZOMBIE && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01zom") == 0){
                    PlayerData.setObjectiveCount(killer,"01zom",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.SKELETON && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01ske") == 0){
                    PlayerData.setObjectiveCount(killer,"01ske",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.CREEPER && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01cre") == 0){
                    PlayerData.setObjectiveCount(killer,"01cre",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.SPIDER && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01ara") == 0){
                    PlayerData.setObjectiveCount(killer,"01ara",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.CAVE_SPIDER && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01cue") == 0){
                    PlayerData.setObjectiveCount(killer,"01cue",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.ENDERMAN && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01end") == 0){
                    PlayerData.setObjectiveCount(killer,"01end",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.PHANTOM && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01pha") == 0){
                    PlayerData.setObjectiveCount(killer,"01pha",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.HUSK && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01hus") == 0){
                    PlayerData.setObjectiveCount(killer,"01hus",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.STRAY && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01str") == 0){
                    PlayerData.setObjectiveCount(killer,"01str",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.DROWNED && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01dro") == 0){
                    PlayerData.setObjectiveCount(killer,"01dro",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.SILVERFISH && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01lep") == 0){
                    PlayerData.setObjectiveCount(killer,"01lep",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.ENDERMITE && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01mit") == 0){
                    PlayerData.setObjectiveCount(killer,"01mit",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.PILLAGER && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01pil") == 0){
                    PlayerData.setObjectiveCount(killer,"01pil",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.VINDICATOR && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01vin") == 0){
                    PlayerData.setObjectiveCount(killer,"01vin",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.WITCH && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01bru") == 0){
                    PlayerData.setObjectiveCount(killer,"01bru",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.EVOKER && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01evo") == 0){
                    PlayerData.setObjectiveCount(killer,"01evo",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.RAVAGER && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01rav") == 0){
                    PlayerData.setObjectiveCount(killer,"01rav",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            if(entity.getType() == EntityType.VEX && killer != null && PlayerData.getMission(killer,"01_killall") != 1){
                if(PlayerData.getObjective(killer,"01vex") == 0){
                    PlayerData.setObjectiveCount(killer,"01vex",1);
                }
                if(checkNoMercy(killer)){
                    missionCompleted(killer,"#ff4769Sin Piedad","01_killall",30,15);
                }
            }
            //Sin Piedad fin
        }else if(getDay() >= 7 && getDay() < 14){

            if(entity.getType() == EntityType.ZOMBIE && killer != null){
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
            if(entity.getType() == EntityType.SKELETON && killer != null){
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
            if(entity.getType() == EntityType.SPIDER && killer != null){
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
            if(entity.getType() == EntityType.CREEPER && killer != null){
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
            if(entity.getType() == EntityType.ENDERMAN && killer != null){
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


            if(entity.getType() == EntityType.WITHER_SKELETON && killer != null && PlayerData.getMission(killer,"11_rol") != 1) {
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
            if(entity.getType() == EntityType.CAVE_SPIDER && killer != null && PlayerData.getMission(killer,"13_plaga") != 1) {
                if(Data.has(entity,"termite", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"13term") < 10) {
                        PlayerData.setObjectiveCount(killer, "13term", PlayerData.getObjective(killer, "13term") + 1);
                    }
                    if(PlayerData.getObjective(killer,"13term") >= 10 && PlayerData.getObjective(killer,"13colterm") >= 10){
                        missionCompleted(killer,"#17c200Invasion de Plagas","13_plaga",28,16);
                    }
                }
                if(Data.has(entity,"termite_ex", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"13colterm") < 10) {
                        PlayerData.setObjectiveCount(killer, "13colterm", PlayerData.getObjective(killer, "13colterm") + 1);
                    }
                    if(PlayerData.getObjective(killer,"13term") >= 10 && PlayerData.getObjective(killer,"13colterm") >= 10){
                        missionCompleted(killer,"#17c200Invasion de Plagas","13_plaga",28,16);
                    }
                }
            }
            if(entity.getType() == EntityType.PHANTOM && killer != null && PlayerData.getMission(killer,"14_phantom") != 1) {
                if(Data.has(entity,"duskphantom", PersistentDataType.STRING)){
                    if(PlayerData.getObjective(killer,"14dusk") < 5) {
                        PlayerData.setObjectiveCount(killer, "14dusk", PlayerData.getObjective(killer, "14dusk") + 1);
                    }else{
                        missionCompleted(killer,"#37456eAcechadores Nocturnos","14_phantom",18,12);
                    }
                }
            }
        }else if(getDay() >= 14 && getDay() < 21){
            if(killer != null) {
                if (PlayerData.getMission(killer, "21_waste") != 1 && killer != null) {
                    if (checkWasteyardKills(killer)) {
                        missionCompleted(killer, "#524843De parte del Vertedero", "21_waste", 45, 30);
                        return;
                    }
                    switch (entity.getType()) {
                        case PILLAGER -> {
                            AddAndCheckKillCount(entity, "lostscav", 5, killer, "21los");
                        }
                        case MAGMA_CUBE -> {
                            AddAndCheckKillCount(entity, "toxiccrawler", 5, killer, "21bri");
                        }
                        case PARROT -> {
                            AddAndCheckKillCount(entity, "brimseeker", 5, killer, "21par");
                        }
                        case PIGLIN_BRUTE -> {
                            AddAndCheckKillCount(entity, "scorchbeast", 5, killer, "21sco");
                        }
                        case GHAST -> {
                            AddAndCheckKillCount(entity, "soulvag", 5, killer, "21wan");
                        }
                        case CREEPER -> {
                            AddAndCheckKillCount(entity, "rustwalker", 5, killer, "21rus");
                        }
                    }
                }
                if (PlayerData.getMission(killer, "22_blaze") != 1 && killer != null) {
                    if (PlayerData.getObjective(killer, "22wind") >= 10 && PlayerData.getObjective(killer, "22armor") >= 10) {
                        missionCompleted(killer, "#fae3c3¡Vienen del Futuro! (y del pasado)", "22_blaze", 35, 20);
                        return;
                    }
                    switch (entity.getType()) {
                        case BLAZE -> {
                            AddAndCheckKillCount(entity, "windcharger", 10, killer, "22wind");
                            AddAndCheckKillCount(entity, "armoredblaze", 10, killer, "22armor");
                        }
                    }
                }
                if (PlayerData.getMission(killer, "23_ash") != 1 && killer != null) {
                    if (PlayerData.getObjective(killer, "23ashen") >= 1) {
                        missionCompleted(killer, "#737373Desde las Cenizas", "23_ash", 40, 25);
                        return;
                    }
                    if (entity.getType() == EntityType.WITHER) {
                        AddAndCheckKillCount(entity, "ashenwither", 1, killer, "23ashen");
                    }
                }
                if (PlayerData.getMission(killer, "24_bee") != 1 && killer != null) {
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
                if (PlayerData.getMission(killer, "25_llama") != 1 && killer != null) {
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
        if(PlayerData.getObjective(p,"01zom") >= 1 &&
                PlayerData.getObjective(p,"01ske") >= 1 &&
                PlayerData.getObjective(p,"01cre") >= 1 &&
                PlayerData.getObjective(p,"01ara") >= 1 &&
                PlayerData.getObjective(p,"01cue") >= 1 &&
                PlayerData.getObjective(p,"01end") >= 1 &&
                PlayerData.getObjective(p,"01str") >= 1 &&
                PlayerData.getObjective(p,"01hus") >= 1 &&
                PlayerData.getObjective(p,"01dro") >= 1 &&
                PlayerData.getObjective(p,"01lep") >= 1 &&
                PlayerData.getObjective(p,"01mit") >= 1 &&
                PlayerData.getObjective(p,"01pil") >= 1 &&
                PlayerData.getObjective(p,"01vin") >= 1 &&
                PlayerData.getObjective(p,"01evo") >= 1 &&
                PlayerData.getObjective(p,"01bru") >= 1 &&
                PlayerData.getObjective(p,"01rav") >= 1 &&
                PlayerData.getObjective(p,"01vex") >= 1 &&
                PlayerData.getObjective(p,"01pha") >= 1
        )return true;

        return false;
    }
    public static boolean checkRPG(Player p){
        if(PlayerData.getObjective(p,"11sword") >= 5 &&
                PlayerData.getObjective(p,"11jugger") >= 5 &&
                PlayerData.getObjective(p,"11archer") >= 5 &&
                PlayerData.getObjective(p,"11mage") >= 5
        )return true;

        return false;
    }
    public static boolean checkDemonDeath(Player p){
        if(PlayerData.getObjective(p,"12revzo") >= 10 &&
                PlayerData.getObjective(p,"12revsk") >= 10 &&
                PlayerData.getObjective(p,"12revsp") >= 10 &&
                PlayerData.getObjective(p,"12revcr") >= 10 &&
                PlayerData.getObjective(p,"12reven") >= 10
        )return true;
        return false;
    }
    public static boolean checkWasteyardKills(Player p){
        if(PlayerData.getObjective(p,"21sco") >= 5 &&
                PlayerData.getObjective(p,"21par") >= 5 &&
                PlayerData.getObjective(p,"21rus") >= 5 &&
                PlayerData.getObjective(p,"21los") >= 5 &&
                PlayerData.getObjective(p,"21wan") >= 5 &&
                PlayerData.getObjective(p,"21bri") >= 5
        )return true;
        return false;
    }

}
