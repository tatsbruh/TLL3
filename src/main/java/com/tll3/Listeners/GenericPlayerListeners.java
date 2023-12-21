package com.tll3.Listeners;

import com.tll3.TLL3;
import com.tll3.Task.EffectDuration;
import com.tll3.Task.Scoreboard;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GenericPlayerListeners implements Listener {

    @EventHandler
    public void joinL(PlayerJoinEvent e){
        var p = e.getPlayer();
        new EffectDuration(p).runTaskTimer(TLL3.getInstance(),20L,20L);
        new Scoreboard(p).runTaskTimer(TLL3.getInstance(),0L,1L);
    }

}
