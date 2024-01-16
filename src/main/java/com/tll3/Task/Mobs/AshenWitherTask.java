package com.tll3.Task.Mobs;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import com.tll3.TLL3;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;

public class AshenWitherTask extends BukkitRunnable {
    private Wither w;
    public AshenWitherTask(Wither w){
        this.w = w;
    }
    @Override
    public void run() {
        if(w.isDead() || !w.isValid()){cancel();return;}
        if(!w.isCharged()){
        var random = GenericUtils.getRandomValue(2);
        switch (random){
            case 0 ->{
                w.setInvulnerableTicks(100);
                w.getWorld().getNearbyPlayers(w.getLocation(),5,5,5).forEach(player -> {
                    player.sendTitle(ChatUtils.format("&c¡Corre!"),"",0,60,0);
                });
            }
            case 1 ->{
                if(w.getTarget() != null){
                    Location loc = w.getTarget().getLocation().clone();
                    AreaEffectCloud sos = (AreaEffectCloud) loc.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);
                    sos.setRadius(3);
                    sos.setParticle(Particle.SOUL_FIRE_FLAME);
                    sos.setDuration(61);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sos.getLocation().getWorld().createExplosion(w,6,true,true);

                        }
                    }.runTaskLater(TLL3.getInstance(),60L);
                }
            }}
        }
    }
}
