package com.tll3.Task.Mobs;

import com.tll3.Misc.ChatUtils;
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
    int attack1 = 0;
    int attack2 = 0;
    int cooldown = 0;
    @Override
    public void run() {
        if(w.isDead() || !w.isValid()){cancel();return;}
        if(attack1 < 200 && cooldown <= 0){
            attack1++;
        }else{
            w.setInvulnerableTicks(100);
            w.getWorld().getNearbyPlayers(w.getLocation(),5,5,5).forEach(player -> {
                player.sendTitle(ChatUtils.format("&c¡Corre!"),"",0,60,0);
            });
            attack1 = 0;
            cooldown = 50;
        }

        if(attack2 < 550 && cooldown <= 0){
            attack2++;
        }else{
            if(w.getTarget() != null){
                Location loc = w.getTarget().getLocation().clone();
                AreaEffectCloud sos = (AreaEffectCloud) loc.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);
                sos.setRadius(3);
                sos.setParticle(Particle.END_ROD);
                sos.setDuration(60);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        loc.getWorld().createExplosion(w,6,true,true);

                    }
                }.runTaskLater(TLL3.getInstance(),60L);
            }
            attack2 = 0;
            cooldown = 105;
        }


        if(cooldown > 0)cooldown--;

    }
}
