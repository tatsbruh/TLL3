package com.tll3.Listeners;

import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
import com.tll3.TLL3;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GenericEntityListeners implements Listener {

    @EventHandler
    public void shoowbowE(EntityShootBowEvent e){
        var entity = e.getEntity();
        var bow = e.getBow();
        var projectile = e.getProjectile();

        if(entity instanceof Skeleton s){
            if(Data.has(s,"void_overseer", PersistentDataType.STRING)){
                EntityHelper.setIdentifierString(projectile,"void");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(!projectile.isValid() || projectile.isOnGround() || projectile.isDead() || projectile.isInWater())cancel();
                        projectile.getLocation().getWorld().spawnParticle(
                                Particle.END_ROD,
                                projectile.getLocation(),
                                1,
                                0,
                                0,
                                0,
                                0
                        );
                    }
                }.runTaskTimer(TLL3.getInstance(),0L,1L);
            }
        }
        if (entity instanceof WitherSkeleton s){
            if(Data.has(s,"railgunner",PersistentDataType.STRING)){
                var charge = Data.get(s,"rl_charge",PersistentDataType.INTEGER);
                if(charge < 3){
                    s.getLocation().getWorld().spawnParticle(
                            Particle.FLAME,
                            projectile.getLocation(),
                            30,
                            0,
                            0,
                            0,
                            1
                    );
                    e.setCancelled(true);
                    s.getLocation().getWorld().playSound(s.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED,10.0F,2.0F);
                    Data.set(s,"rl_charge",PersistentDataType.INTEGER, charge + 1);
                }else{
                    s.getLocation().getWorld().spawnParticle(
                            Particle.FLAME,
                            projectile.getLocation(),
                            30,
                            0,
                            0,
                            0,
                            -1
                    );
                    s.getLocation().getWorld().playSound(s.getLocation(),Sound.ENTITY_ZOMBIE_VILLAGER_CURE,10.0F,2.0F);
                    Data.set(s,"rl_charge",PersistentDataType.INTEGER,0);
                    WitherSkull w = (WitherSkull) projectile.getLocation().getWorld().spawnEntity(entity.getLocation().add(0,1,0),EntityType.WITHER_SKULL);
                    w.setShooter(s);
                    e.setProjectile(w);
                    w.setVelocity(w.getVelocity().multiply(0.5));
                    new BukkitRunnable() {
                        int i = 0;
                        @Override
                        public void run() {
                            if(!w.isValid() || w.isDead() || w.isOnGround()){cancel();return;}
                            if( i++ > 100){
                                w.getLocation().createExplosion(s,7,true,true);
                                w.remove();
                                cancel();
                            }
                        }
                    }.runTaskTimer(TLL3.getInstance(),0L,1L);

                }
            }
        }
    }

    @EventHandler
    public void projhitE(ProjectileHitEvent e){
        var proj = e.getEntity();
        var source = e.getEntity().getShooter();
        var hen = e.getHitEntity();
        var hbl = e.getHitBlock();


        if(source instanceof Skeleton s){
            if(Data.has(s,"void_overseer",PersistentDataType.STRING)){
                if(hen != null){
                    Location og = s.getLocation().clone();
                    Location pl = hen.getLocation().clone();
                    s.playEffect(EntityEffect.TELEPORT_ENDER);
                    hen.playEffect(EntityEffect.TELEPORT_ENDER);
                    s.teleport(pl);
                    s.setTarget((LivingEntity) hen);
                    hen.teleport(og);
                }
                if(hbl != null){
                    Location lol = hbl.getLocation().add(0,1,0);
                    s.playEffect(EntityEffect.TELEPORT_ENDER);
                    s.teleport(lol);
                }
            }
        }
        if(source instanceof WitherSkeleton s){
            if(Data.has(s,"railgunner",PersistentDataType.STRING)){
                if(hen != null){
                    hen.getLocation().createExplosion(s,7,true,true);
                }
                if(hbl != null){
                    hbl.getLocation().createExplosion(s,7,true,true);
                }
            }
        }


    }
}
