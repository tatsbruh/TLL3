package com.tll3.Listeners;

import com.tll3.Lists.Entities;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
import com.tll3.TLL3;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;

public class GenericEntityListeners implements Listener {

    @EventHandler
    public void shoowbowE(EntityShootBowEvent e) {
        var entity = e.getEntity();
        var bow = e.getBow();
        var projectile = e.getProjectile();

        if (entity instanceof Skeleton s) {
            if (Data.has(s, "void_overseer", PersistentDataType.STRING)) {
                EntityHelper.setIdentifierString(projectile, "void");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!projectile.isValid() || projectile.isOnGround() || projectile.isDead() || projectile.isInWater())
                            cancel();
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
                }.runTaskTimer(TLL3.getInstance(), 0L, 1L);
            }
        }
        if (entity instanceof WitherSkeleton s) {
            if (Data.has(s, "railgunner", PersistentDataType.STRING)) {
                var charge = Data.get(s, "rl_charge", PersistentDataType.INTEGER);
                if (charge < 2) {
                    s.getLocation().getWorld().spawnParticle(
                            Particle.FLAME,
                            projectile.getLocation(),
                            20,
                            0,
                            0,
                            0,
                            0.1
                    );
                    e.setCancelled(true);
                    s.getLocation().getWorld().playSound(s.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 10.0F, -1.0F);
                    Data.set(s, "rl_charge", PersistentDataType.INTEGER, charge + 1);
                } else {
                    s.getLocation().getWorld().spawnParticle(
                            Particle.FLAME,
                            projectile.getLocation(),
                            20,
                            0,
                            0,
                            0,
                            -0.1
                    );
                    s.getLocation().getWorld().playSound(s.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 10.0F, 2.0F);
                    Data.set(s, "rl_charge", PersistentDataType.INTEGER, 0);
                    WitherSkull w = (WitherSkull) projectile.getLocation().getWorld().spawnEntity(entity.getLocation().add(0, 1, 0), EntityType.WITHER_SKULL);
                    w.setShooter(s);
                    e.setProjectile(w);
                    w.setCharged(true);
                    new BukkitRunnable() {
                        int i = 0;

                        @Override
                        public void run() {
                            if (!w.isValid() || w.isDead() || w.isOnGround()) {
                                cancel();
                                return;
                            }
                            if (i++ > 60) {
                                w.getLocation().createExplosion(s, 7, true, true);
                                w.remove();
                                cancel();
                            }
                        }
                    }.runTaskTimer(TLL3.getInstance(), 0L, 1L);

                }
            }
        }
    }

    @EventHandler
    public void projhitE(ProjectileHitEvent e) {
        var proj = e.getEntity();
        var source = e.getEntity().getShooter();
        var hen = e.getHitEntity();
        var hbl = e.getHitBlock();

        if(source instanceof Zombie z){
            if(Data.has(z,"zninja",PersistentDataType.STRING)){
                if(hen != null){
                    hen.getLocation().getWorld().playSound(hen.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,10.0F,1.0F);
                    hen.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE,hen.getLocation(),10,1,1,1,1);
                    hen.getWorld().getNearbyEntities(hen.getLocation(),3,3,3).stream()
                            .filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity)entity)
                            .forEach(player ->{
                                if(player instanceof Player c){
                                    c.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,60,0,true,false,true));
                                    c.damage(z.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue(),z);
                                }
                            });
                }
                if(hbl != null){
                    hbl.getLocation().getWorld().playSound(hbl.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,10.0F,1.0F);
                    hbl.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE,hbl.getLocation(),10,1,1,1,1);
                    hbl.getWorld().getNearbyEntities(hbl.getLocation(),3,3,3).stream()
                            .filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity)entity)
                            .forEach(player ->{
                                if(player instanceof Player c){
                                    c.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,60,0,true,false,true));
                                    c.damage(z.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue(),z);
                                }
                            });
                }
            }
        }

        if (source instanceof Skeleton s) {
            if (Data.has(s, "void_overseer", PersistentDataType.STRING)) {
                if (hen != null) {
                    Location og = s.getLocation().clone();
                    Location pl = hen.getLocation().clone();
                    s.playEffect(EntityEffect.TELEPORT_ENDER);
                    hen.playEffect(EntityEffect.TELEPORT_ENDER);
                    s.teleport(pl);
                    if(hen instanceof Player p) s.setTarget((p));
                    hen.teleport(og);
                }
                if (hbl != null) {
                    Location lol = hbl.getLocation().add(0, 1, 0);
                    s.playEffect(EntityEffect.TELEPORT_ENDER);
                    s.teleport(lol);
                }
            }
        }
        if (source instanceof WitherSkeleton s) {
            if (Data.has(s, "railgunner", PersistentDataType.STRING)) {
                if (hen != null) {
                    hen.getLocation().createExplosion(s, 7, true, true);
                }
                if (hbl != null) {
                    hbl.getLocation().createExplosion(s, 7, true, true);
                }
            }
        }


    }


    @EventHandler
    public void explodeE(EntityExplodeEvent e){
        var entity = e.getEntity();
        var loc = e.getLocation();

        if(entity instanceof Creeper c){
            if(Data.has(c,"time_sand",PersistentDataType.STRING)){
                loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE,loc,10,1,1,1,1);
                Creeper cr = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                Entities.timeS(cr);
                EntityHelper.setMobHealth(cr, (int) c.getHealth());
                e.setCancelled(true);
            }
        }

    }


    @EventHandler
    public void targetE(EntityTargetLivingEntityEvent e){
        var target = e.getTarget();
        var origin = e.getEntity();

        if(origin instanceof Zombie z && target instanceof Player){
            if(Data.has(z,"zninja",PersistentDataType.STRING)){
                if(z.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                    z.removePotionEffect(PotionEffectType.INVISIBILITY);
                    z.setSilent(true);
                    z.getLocation().getWorld().spawnParticle(
                            Particle.SMOKE_NORMAL,
                            z.getLocation(),
                            20,
                            0,
                            0,
                            0,
                            -0.1
                    );
                }
                BukkitRunnable r = new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        if(z.getTarget() == null || z.isDead() || !z.isValid() || z.getTarget().isDead()){cancel();return;}
                        if(i < 120){
                            i++;
                        }else{
                            Snowball s = z.launchProjectile(Snowball.class);
                            s.setShooter(z);
                            i = 0;
                        }
                    }
                };
                r.runTaskTimer(TLL3.getInstance(),0L,1L);

            }
        }

        if(origin instanceof Enemy && target instanceof Enemy){
            e.setCancelled(true);
        }
    }
}
