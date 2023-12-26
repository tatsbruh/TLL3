package com.tll3.Lists;

import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.Guard;
import java.util.Locale;
import static com.tll3.Misc.EntityHelper.*;

public class Entities {
    /*
    * Day 5 mobs
    * */

    public static void creChr(Creeper c){
        setName(c,"&bSupercharged Creeper");
        c.setPowered(true);
    }

    public static void skeAd(Skeleton s){
        setName(s,"&4Brute Skeleton");
        setMobHealth(s,35);
        setMobDamage(s,6);
        setHead(s,new ItemStack(Material.BEACON));
        setChestplate(s,new ItemStack(Material.DIAMOND_CHESTPLATE));
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,5).addEnchant(Enchantment.ARROW_FIRE,5).build());
        addPotionEffect(s,PotionEffectType.SPEED,1);
        setIdentifierString(s,"bruteskeleton");
    }
    public static void skeW(Skeleton s){
        setName(s,"#024f3cSkeleton Warden");
        setHead(s,new ItemStack(Material.BLAST_FURNACE));
        setChestplate(s,new ItemStack(Material.IRON_CHESTPLATE));
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,9).build());
    }

    public static void zNinka(Zombie z){
        setName(z,"&7Ninja Zombie");
        setMobHealth(z,25);
        setMobDamage(z,6);
        addPotionEffect(z, PotionEffectType.INVISIBILITY,0);
        addPotionEffect(z, PotionEffectType.SPEED,2);
        addPotionEffect(z,PotionEffectType.JUMP,2);
        z.setSilent(true);
        z.setShouldBurnInDay(false);
        z.setAdult();
        setIdentifierString(z,"zninja");
    }
    public static void blackRev(Spider s){
        setName(s,"&0Black Tarantula");
        setMobHealth(s,35);
        setMobDamage(s,7);
        addPotionEffect(s, PotionEffectType.SPEED,0);
        s.setAggressive(true);
        setIdentifierString(s,"blackreaver");
    }

    public static void adapSp(Spider s){
        setName(s,"&6&lAdaptative Mauler");
        setMobHealth(s,35);
        setMobDamage(s,6);
        addPotionEffect(s, PotionEffectType.SPEED,0);
        s.setAggressive(true);
        setIdentifierString(s,"adeptmauler");
        setIdentifierInt(s,"melee",0);
        setIdentifierInt(s,"proj",0);
        setIdentifierInt(s,"fire",0);
    }

    public static void termite(CaveSpider s){
        setName(s,"&6&lTermite");
        setMobHealth(s,15);
        addPotionEffect(s, PotionEffectType.SPEED,1);
        s.setAggressive(true);
        setIdentifierString(s,"termite");
        setIdentifierInt(s,"t_state",0);
    }



    public static void voidOver(Skeleton s){
        setMobHealth(s,35);
        setMobRange(s,100);
        setHead(s,new ItemStack(Material.END_PORTAL_FRAME));
        setChestplate(s,new ItemStack(Material.NETHERITE_CHESTPLATE));
        setIdentifierString(s,"void_overseer");
    }

    public static void railGun(WitherSkeleton s){
        setMobHealth(s,35);
        setMobRange(s,100);
        setHead(s,new ItemStack(Material.TINTED_GLASS));
        setChestplate(s,new ItemStack(Material.DIAMOND_CHESTPLATE));
        setMainHand(s,new ItemStack(Material.BOW));
        setIdentifierString(s,"railgunner");
        setIdentifierInt(s,"rl_charge",0);
    }

    public static void timeS(Creeper c){
        setIdentifierString(c,"time_sand");
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
