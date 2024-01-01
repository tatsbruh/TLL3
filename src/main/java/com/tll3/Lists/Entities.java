package com.tll3.Lists;

import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftIronGolem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
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
        setMobDamage(s,4);
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,4).addEnchant(Enchantment.ARROW_FIRE,4).build());
        addPotionEffect(s,PotionEffectType.SPEED,1);
        setIdentifierString(s,"bruteskeleton");
    }
    public static void skeW(Skeleton s){
        setName(s,"#024f3cSkeleton Warden");
        setHead(s,new ItemStack(Material.BLAST_FURNACE));
        setChestplate(s,new ItemStack(Material.IRON_CHESTPLATE));
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,9).build());
    }
    public static void enrIG(IronGolem i) {
        CraftIronGolem craft = ((CraftIronGolem) i);
        EntityIronGolem entityIronGolem = craft.getHandle();
        try {
            Class<? extends EntityInsentient> cl = EntityInsentient.class;
            Field gf = cl.getDeclaredField("bO");
            gf.setAccessible(true);
            PathfinderGoalSelector goal = (PathfinderGoalSelector) gf.get(entityIronGolem);
            goal.a(0, new PathfinderGoalMeleeAttack(entityIronGolem, 1.0D, true));
            Field tf = cl.getDeclaredField("bP");
            tf.setAccessible(true);
            PathfinderGoalSelector target = (PathfinderGoalSelector) tf.get(entityIronGolem);
            target.a(0, new PathfinderGoalNearestAttackableTarget<>(entityIronGolem, EntityHuman.class, 10, true, false, null));

        } catch (Exception e) {}
        i.setRemoveWhenFarAway(true);
        setName(i,"&4Enraged Iron Golem");
    }

    public static void zNinka(Zombie z){
        setName(z,"&7Ninja Zombie");
        setMobHealth(z,25);
        setMobDamage(z,5);
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
        setName(s,"&cScarlet Leech");
        setMobHealth(s,35);
        setMobDamage(s,6);
        addPotionEffect(s, PotionEffectType.SPEED,0);
        s.setAggressive(true);
        setIdentifierString(s,"adeptmauler");
    }

    public static void termite(CaveSpider s){
        setName(s,"&6Termita");
        setMobHealth(s,15);
        addPotionEffect(s, PotionEffectType.SPEED,1);
        s.setAggressive(true);
        setIdentifierString(s,"termite");
        setIdentifierInt(s,"t_state",0);
    }

    public static void wsM(WitherSkeleton s){
        setName(s,"&eWither Swordsman");
        setMobHealth(s,25);
        setMobDamage(s,7);
        setMainHand(s,new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL,4).addEnchant(Enchantment.FIRE_ASPECT,0).build());
        setIdentifierString(s,"w_swordsman");
        addPotionEffect(s,PotionEffectType.SPEED,0);
    }
    public static void wsR(WitherSkeleton s){
        setName(s,"&eWither Archer");
        setMobHealth(s,20);
        setMobDamage(s,4);
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,5).addEnchant(Enchantment.ARROW_FIRE,5).addEnchant(Enchantment.ARROW_KNOCKBACK,1).build());
        setIdentifierString(s,"w_archer");
    }
    public static void wsT(WitherSkeleton s){
        setName(s,"&eWither Juggernaut");
        setMobHealth(s,45);
        setMobDamage(s,2);
        setMainHand(s,new ItemStack(Material.STONE_AXE));
        setChestplate(s,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3).build());
        setBoots(s,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3).build());
        setIdentifierString(s,"w_tank");
    }
    public static void wsW(WitherSkeleton s) {
        setName(s,"&eWither Sorcerer");
        setMobHealth(s,20);
        setMobDamage(s,4);
        setMainHand(s,new ItemBuilder(Material.BOW).setCustomModelData(100).build());
        setHead(s,new ItemStack(Material.TURTLE_HELMET));
        setIdentifierString(s,"w_mage");
    }
    public static void huStr(Husk s){
        setName(s,"&eStarved Husk");
        setMobHealth(s,25);
        setMobDamage(s,6);
        addPotionEffect(s,PotionEffectType.SPEED,1);
        setIdentifierString(s,"starved_husk");
    }
    public static void csTerCol(CaveSpider s){
        setName(s,"&6&lTermita de Colonia");
        setMobHealth(s,25);
        addPotionEffect(s, PotionEffectType.SPEED,2);
        s.setAggressive(true);
        setIdentifierString(s,"termite_ex");
        setIdentifierInt(s,"tex_state",0);
    }
    public static void gPower(Ghast s){
        setName(s,"&6Cataclysmic Ghast");
        setMobHealth(s,20);
        setIdentifierString(s,"cata_ghast");
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


    public static void soulVg(Ghast s){
        setName(s,"#636363Brimstone Vagrant");
        setMobHealth(s,55);
        setIdentifierString(s,"soulvag");
    }
    public static void scBrute(PiglinBrute c){
        setName(c,"#a84738Scorchbeast");
        c.setImmuneToZombification(true);
        setMobHealth(c,75);
        setMainHand(c,new ItemBuilder(Material.NETHERITE_AXE).addEnchant(Enchantment.DAMAGE_ALL,9).addEnchant(Enchantment.FIRE_ASPECT,4).setUnbreakable(true).build());
        addPotionEffect(c,PotionEffectType.SPEED,0);
        setIdentifierString(c,"scorchbeast");
    }
    public static void rustwalker(Creeper c){
        setName(c,"#ffab4aRustwalker");
        setMobHealth(c,35);
        c.setPowered(true);
        c.setMaxFuseTicks(25);
        c.setFuseTicks(25);
        c.setExplosionRadius(10);
        addPotionEffect(c,PotionEffectType.SPEED,0);
        setIdentifierString(c,"rustwalker");
    }
    public static void lostScav(Pillager p){
        setName(p,"#615e58Lost Scavenger");
        setMobHealth(p,45);
        p.setCanJoinRaid(false);
        setMainHand(p,new ItemBuilder(Material.CROSSBOW).addEnchant(Enchantment.QUICK_CHARGE,4).addEnchant(Enchantment.MULTISHOT,0).addEnchant(Enchantment.PIERCING,4).setUnbreakable(true).build());
        setOffhand(p,lostScavxd());
        addPotionEffect(p,PotionEffectType.SPEED,0);
        setIdentifierString(p,"lostscav");
    }
    public static void toxcrawl(MagmaCube s){
        setName(s,"#2e2e2eToxic Crawler");
        setMobHealth(s,60);
        setMobDamage(s,20);
        s.setSize(12);
        setIdentifierString(s,"toxiccrawler");
    }





    public static LivingEntity spawnMob(Location loc, EntityType entityType){
       return (LivingEntity) loc.getWorld().spawnEntity(loc,entityType);
    }

    public static ItemStack lostScavxd() {
        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER,180,4),false);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW,180,1),false);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING,180,0),false);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,180,1),false);
        arrow.setItemMeta(meta);
        arrow.setAmount(128);
        return arrow;
    }




}
