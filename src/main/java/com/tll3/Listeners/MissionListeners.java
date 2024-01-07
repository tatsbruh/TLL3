package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import org.bukkit.Bukkit;
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
                    missionCompleted(killer,"#ff954fViolencia Innecesaria","05_violencia",5,1);
                }
            }
            if(entity.getType() == EntityType.GUARDIAN && killer != null && PlayerData.getMission(killer,"04_atlantis") != 1){
                if(PlayerData.getObjective(killer,"04guardianes") < 20){
                    PlayerData.setObjectiveCount(killer,"04guardianes",PlayerData.getObjective(killer,"04guardianes") + 1);
                }
                if(PlayerData.getObjective(killer,"04guardianes") >= 20 && PlayerData.getObjective(killer,"04elderguardian") >= 1){
                    missionCompleted(killer,"#69c5ffAnti-Atlantis","04_atlantis",10,5);
                }
            }
            if(entity.getType() == EntityType.ELDER_GUARDIAN && killer != null && PlayerData.getMission(killer,"04_atlantis") != 1){
                if(PlayerData.getObjective(killer,"04elderguardian") == 0){
                    PlayerData.setObjectiveCount(killer,"04elderguardian",PlayerData.getObjective(killer,"04elderguardian") + 1);
                }
                if(PlayerData.getObjective(killer,"04guardianes") >= 20 && PlayerData.getObjective(killer,"04elderguardian") >= 1){
                    missionCompleted(killer,"#69c5ffAnti-Atlantis","04_atlantis",10,5);
                }
            }
            if(entity.getType() == EntityType.WARDEN && killer != null && PlayerData.getMission(killer,"03_warden") != 1){
                if(PlayerData.getObjective(killer,"03warden") == 0){
                    PlayerData.setObjectiveCount(killer,"03warden",PlayerData.getObjective(killer,"03warden") + 1);
                }
                if(PlayerData.getObjective(killer,"03warden") >= 1){
                    missionCompleted(killer,"#004736Aprende y Escucha","03_warden",10,5);
                }
            }
            if(entity.getType() == EntityType.WITHER && killer != null && PlayerData.getMission(killer,"02_wither") != 1){
                if(PlayerData.getObjective(killer,"02wither") < 5){
                    PlayerData.setObjectiveCount(killer,"02wither",PlayerData.getObjective(killer,"02wither") + 1);
                }else{
                    missionCompleted(killer,"#474747Maestro Descompuesto","02_wither",15,5);
                }
            }

        }
    }


    public static void missionCompleted(Player p,String mission,String completed,int credits,int prestige){
        for(Player online : Bukkit.getOnlinePlayers()){
            online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El Jugador #fff159" + p.getName() + " &7a completado la mision &8[" + mission + "&8]"  ));
        }
        PlayerData.setMissionCount(p,completed,1);
        PlayerData.setCredits(p,PlayerData.getCredits(p) + credits);
        PlayerData.setPrestige(p,PlayerData.getPrestige(p) + prestige);
        PlayerData.setHunts(p,PlayerData.getHunts(p) + 1);
    }



}
