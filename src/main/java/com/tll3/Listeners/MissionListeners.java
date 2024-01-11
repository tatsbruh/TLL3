package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
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


}
