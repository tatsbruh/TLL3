package com.tll3.Task.Mobs;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AshenWitherTask extends BukkitRunnable {
    private Wither w;
    public AshenWitherTask(Wither w){
        this.w = w;
    }
    @Override
    public void run() {
        if(w.isDead() || !w.isValid()){cancel();return;}
        var random = GenericUtils.getRandomValue(3);
        if(w.getInvulnerableTicks() <= 0){
        switch (random){
            case 0 ->{
                final double hea = w.getHealth();
                w.setInvulnerableTicks(250);
                w.getWorld().getNearbyPlayers(w.getLocation(),5,5,5).forEach(player -> {
                    player.sendTitle(ChatUtils.format("&c¡Corre!"),"",0,60,0);
                });
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        w.setHealth(hea);
                    }
                }.runTaskLater(TLL3.getInstance(),251L);
            }
            case 1 ->{
                if(w.getTarget() != null){
                    Location loc = w.getTarget().getLocation().clone();
                    XParticle.circle(4,10, ParticleDisplay.display(loc,Particle.SOUL_FIRE_FLAME));
                    TNTPrimed c = (TNTPrimed) loc.getWorld().spawnEntity(loc,EntityType.PRIMED_TNT);
                    c.setYield(7);
                    c.setFuseTicks(100);
                    c.setVelocity(new Vector(0,1,0));
                }
            }
            case 2 -> {
                if (w.getTarget() != null) {
                    new BukkitRunnable() {
                        int i = 0;

                        @Override
                        public void run() {
                            if (i < 2) {
                                Vector direction = w.getTarget().getLocation().toVector().subtract(w.getLocation().toVector()).normalize();
                                w.setVelocity(direction.multiply(1.5));
                                i++;
                            } else {
                                cancel();
                            }
                        }
                    }.runTaskTimer(TLL3.getInstance(), 0L, 1L);
                }
            }
        }
    }
    }
}
