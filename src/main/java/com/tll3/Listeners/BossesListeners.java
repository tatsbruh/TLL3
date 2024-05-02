package com.tll3.Listeners;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.material.MaterialData;

public class BossesListeners implements Listener {

    @EventHandler
    public void onChange(EntityChangeBlockEvent event){
        if(event.getEntity() instanceof FallingBlock){
            if(event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("Necromancer Orb")) {
                event.getEntity().getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,event.getEntity().getLocation(),50,0,0,0,1,new MaterialData(Material.SAND));
                for(Player p : event.getEntity().getLocation().getNearbyPlayers(3,3,3)){
                    p.damage(20);
                }

                event.setCancelled(true);
                event.getEntity().remove();
            }
        }
    }


    public boolean checkName(Entity entity, String s){
        return (entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase(s));
    }
}
