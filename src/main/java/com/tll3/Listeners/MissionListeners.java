package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
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
        }
    }


    public static void missionCompleted(Player p,String mission,String completed,int credits,int prestige){
        for(Player online : Bukkit.getOnlinePlayers()){
            online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El Jugador #fff159" + p.getName() + " &7a completado la mision &8[" + mission + "&8]"  ));
        }
        PlayerData.setMissionCount(p,completed,1);
        PlayerData.setCredits(p,credits);
        PlayerData.setPrestige(p,prestige);
        PlayerData.setHunts(p,PlayerData.getHunts(p) + 1);
    }


}
