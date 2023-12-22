package com.tll3.Lists;

import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;
import static com.tll3.Misc.EntityHelper.*;

public class Entities {

    public static void voidOver(Skeleton s){
        setMobHealth(s,35);
        setHead(s,new ItemStack(Material.END_GATEWAY));

    }

    public void summonBarrier(Location loc){
        EnderCrystal c = (EnderCrystal) spawnMob(loc,EntityType.ENDER_CRYSTAL);
        c.setShowingBottom(false);
        c.setInvulnerable(true);
        setIdentifierString(c,"barrier");
        new BukkitRunnable() {
            @Override
            public void run() {
                XParticle.sphere(5,5, ParticleDisplay.display(c.getLocation(), Particle.END_ROD));
                for (Entity entity : c.getWorld().getEntities()) {
                    if (entity.getType() == EntityType.ENDER_PEARL) {
                        if (entity.getLocation().distance(c.getLocation()) <= 5) {
                            entity.playEffect(EntityEffect.SNOWBALL_BREAK);
                            Item i = entity.getLocation().getWorld().dropItem(entity.getLocation(),new ItemStack(Material.ENDER_PEARL));
                            entity.remove();
                        }
                    }
                }
            }
        }.runTaskTimer(TLL3.getInstance(),0L,1L);
    }


    public static LivingEntity spawnMob(Location loc, EntityType entityType){
       return (LivingEntity) loc.getWorld().spawnEntity(loc,entityType);
    }
}
