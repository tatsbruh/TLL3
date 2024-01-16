package com.tll3.Lists;

import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.ArqBlockBreak;
import com.tll3.Task.Mobs.AshenWitherTask;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftIronGolem;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPigZombie;
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
import static com.tll3.Misc.GenericUtils.getDay;

public class Entities {
    /*
    * Day 5 mobs
    * */

    public static void creChr(Creeper c){
        setName(c,"&bSupercharged Creeper");
        c.setPowered(true);
    }

    public static void skeAd(Skeleton s){
        setName(s,"&4Rogue Skeleton");
        setMobHealth(s,30);
        setMobDamage(s,4);
        s.setShouldBurnInDay(false);
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,4).addEnchant(Enchantment.ARROW_FIRE,4).build());
        addPotionEffect(s,PotionEffectType.SPEED,1);
        setIdentifierString(s,"bruteskeleton");
    }

    public static void skeFi(Skeleton s){
        setName(s,"&6&lFiremancer");
        setMobHealth(s,25);
        setHead(s,new ItemBuilder(Material.LEATHER_HELMET).setLeatherColor(255, 156, 25).build());
        addPotionEffect(s,PotionEffectType.FIRE_RESISTANCE,0);
        setIdentifierString(s,"firemancer");
        s.setShouldBurnInDay(false);
    }
    public static void skeRz(Skeleton s){
        setName(s,"&0Razorback");
        setMobHealth(s,25);
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,4).build());
        setHead(s,new ItemStack(Material.TARGET));
        setIdentifierString(s,"razorback");
        s.setShouldBurnInDay(false);
    }


    public static void skeW(Skeleton s){
        setName(s,"#024f3cSkeleton Warden");
        setHead(s,new ItemStack(Material.BLAST_FURNACE));
        setChestplate(s,new ItemStack(Material.IRON_CHESTPLATE));
        s.setShouldBurnInDay(false);
        if(getDay() >= 14){
            setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,30).build());
        }else{
            setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,10).build());
        }
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
        i.setPersistent(false);
        setName(i,"&4Enraged Iron Golem");
        if(getDay() >= 14){
            addPotionEffect(i,PotionEffectType.INCREASE_DAMAGE,1);
            addPotionEffect(i,PotionEffectType.SPEED,1);
        }
    }
    public static void enrPig(PigZombie z){
        CraftPigZombie craft = ((CraftPigZombie) z);
        EntityPigZombie entityPigZombie = craft.getHandle();
        try {
            Class<? extends EntityInsentient> cl = EntityInsentient.class;
            Field gf = cl.getDeclaredField("bO");
            gf.setAccessible(true);
            PathfinderGoalSelector goal = (PathfinderGoalSelector) gf.get(entityPigZombie);
            goal.a(0, new PathfinderGoalMeleeAttack(entityPigZombie, 1.0D, true));
            Field tf = cl.getDeclaredField("bP");
            tf.setAccessible(true);
            PathfinderGoalSelector target = (PathfinderGoalSelector) tf.get(entityPigZombie);
            target.a(0, new PathfinderGoalNearestAttackableTarget<>(entityPigZombie, EntityHuman.class, 10, true, false, null));

        } catch (Exception e) {}
        z.setRemoveWhenFarAway(true);
        setName(z,"&4Enraged Zombie Piglin");
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
    public static void zArqueo(Zombie z){
        setName(z,"&6Zombie Archaeologist");
        setMobHealth(z,25);
        setMobDamage(z,5);
        setMainHand(z,new ItemStack(Material.IRON_PICKAXE));
        addPotionEffect(z, PotionEffectType.SPEED,0);
        setMobRange(z,100);
        z.setAdult();
        z.setShouldBurnInDay(false);
        setIdentifierString(z,"dead_arq");
        new ArqBlockBreak(z).runTaskTimer(TLL3.getInstance(), 20L, 35L);
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
        if(getDay() >= 14){
            setName(s,"&eWither Swordsman+");
            setMobHealth(s,35);
            setMobDamage(s,9);
            setMainHand(s,new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL,20).addEnchant(Enchantment.FIRE_ASPECT,10).build());
            setIdentifierString(s,"w_swordsman");
            addPotionEffect(s,PotionEffectType.SPEED,1);
        }else{
        setName(s,"&eWither Swordsman");
        setMobHealth(s,25);
        setMobDamage(s,7);
        setMainHand(s,new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL,4).addEnchant(Enchantment.FIRE_ASPECT,0).build());
        setIdentifierString(s,"w_swordsman");
        addPotionEffect(s,PotionEffectType.SPEED,0);
        }
    }
    public static void wsR(WitherSkeleton s){
        if(getDay() >= 14) {
            setName(s,"&eWither Archer+");
            setMobHealth(s,40);
            setMobDamage(s,4);
            setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,35).addEnchant(Enchantment.ARROW_FIRE,15).addEnchant(Enchantment.ARROW_KNOCKBACK,10).build());
            setIdentifierString(s,"w_archer");
        }else{
        setName(s,"&eWither Archer");
        setMobHealth(s,20);
        setMobDamage(s,4);
        setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,5).addEnchant(Enchantment.ARROW_FIRE,5).addEnchant(Enchantment.ARROW_KNOCKBACK,1).build());
        setIdentifierString(s,"w_archer");
        }
    }
    public static void wsT(WitherSkeleton s){
        if(getDay() >= 14){
            setName(s,"&eWither Juggernaut+");
            setMobHealth(s,65);
            setMobDamage(s,4);
            setMainHand(s,new ItemStack(Material.NETHERITE_AXE));
            setChestplate(s,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,5).build());
            setBoots(s,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,5).build());
            setIdentifierString(s,"w_tank");
        }else{
        setName(s,"&eWither Juggernaut");
        setMobHealth(s,45);
        setMobDamage(s,2);
        setMainHand(s,new ItemStack(Material.STONE_AXE));
        setChestplate(s,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3).build());
        setBoots(s,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3).build());
        setIdentifierString(s,"w_tank");
        }
    }
    public static void wsW(WitherSkeleton s) {
        if(getDay() >= 14){
            setName(s,"&eWither Sorcerer+");
            setMobHealth(s,50);
            setMobDamage(s,4);
            setMainHand(s,new ItemBuilder(Material.BOW).setCustomModelData(100).build());
            setHead(s,new ItemBuilder(Material.TURTLE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.THORNS,10).setUnbreakable(true).build());
            setIdentifierString(s,"w_mage");
        }else{
        setName(s,"&eWither Sorcerer");
        setMobHealth(s,20);
        setMobDamage(s,4);
        setMainHand(s,new ItemBuilder(Material.BOW).setCustomModelData(100).build());
        setHead(s,new ItemStack(Material.TURTLE_HELMET));
        setIdentifierString(s,"w_mage");
        }
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
        addPotionEffect(s, PotionEffectType.SPEED,0);
        s.setAggressive(true);
        setIdentifierString(s,"termite_ex");
        setIdentifierInt(s,"tex_state",0);
    }
    public static void gPower(Ghast s){
        setName(s,"&6Cataclysmic Ghast");
        setMobHealth(s,20);
        setIdentifierString(s,"cata_ghast");
    }
    public static void phanD(Phantom phantom){
        setName(phantom,"&7Dusk Phantom");
        setMobDamage(phantom,4);
        phantom.setSize(15);
        setIdentifierString(phantom,"duskphantom");
    }
    public static void silverday5(Silverfish silverfish){
        setName(silverfish,"&cSilverfish Poderoso");
        addPotionEffect(silverfish,PotionEffectType.INCREASE_DAMAGE,14);
        addPotionEffect(silverfish,PotionEffectType.SPEED,1);
    }
    public static void drowAby(Drowned drowned){
        setName(drowned,"&bAbyssal Drowned");
        setMobDamage(drowned,6);
        setMobHealth(drowned,25);
        setMainHand(drowned,new ItemBuilder(Material.TRIDENT).addEnchant(Enchantment.IMPALING,9).addEnchant(Enchantment.CHANNELING,0).build());
        setIdentifierString(drowned,"abyssdrow");
    }




    //Mobs dia 14
    public static void ashenWither(Wither w){
        setName(w,"#4a4745Ashen Wither");
        setMobHealth(w,600);
        w.setCanTravelThroughPortals(false);
        w.setInvulnerableTicks(350);
        setIdentifierString(w,"ashenwither");
        new AshenWitherTask(w).runTaskTimer(TLL3.getInstance(),20L,450L);
    }
    public static void windChar(Blaze z){
        setName(z,"&f&lWind Charger");
        z.setRemoveWhenFarAway(true);
        setMobHealth(z,40);
        setIdentifierString(z,"windcharger");
    }
    public static void armorBlaze(Blaze z){
        setName(z,"#824205Armored Blaze");
        z.setRemoveWhenFarAway(true);
        setMobHealth(z,40);
        setIdentifierString(z,"armoredblaze");
    }
    public static void unstCr(Creeper c){
        setName(c,"#c991d9Unstable Creeper");
        c.setPowered(true);
        c.setExplosionRadius(5);
        setIdentifierString(c,"unstablecreeper");
    }
    public static void voidOver(Skeleton s){
        setMobHealth(s,35);
        setName(s,"#3e574bVoid Overseer");
        setMobRange(s,100);
        setHead(s,new ItemStack(Material.END_PORTAL_FRAME));
        setIdentifierString(s,"void_overseer");
    }
    public static void livingSh(Skeleton s){
        setMobHealth(s,35);
        setName(s,"#0d4034Living Shrieker");
        setMobRange(s,100);
        setHead(s,new ItemStack(Material.SCULK_SHRIEKER));
        setIdentifierString(s,"livshriek");
    }
    public static void quanmite(Endermite e){
        setName(e,"#72498cQuantummite");
        setMobRange(e,95);
        setIdentifierString(e,"tesla");
    }
    public static void piglGr(Piglin e){
        setName(e,"#f0e811Greedy Piglin");
        setMobRange(e,95);
        setHead(e,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        setChestplate(e,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        setLeggings(e,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        setBoots(e,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
        setIdentifierString(e,"greedypiglin");
        addPotionEffect(e,PotionEffectType.INCREASE_DAMAGE,4);
    }
    public static void strayCom(Stray s){
        setName(s,"&c&lStray Comandante");
        setMobRange(s,120);
        setHead(s,new ItemStack(Material.GOLDEN_HELMET));
        setChestplate(s,new ItemStack(Material.GOLDEN_CHESTPLATE));
        setLeggings(s,new ItemStack(Material.GOLDEN_LEGGINGS));
        setBoots(s,new ItemStack(Material.GOLDEN_BOOTS));
        setMainHand(s,new ItemBuilder(Material.GOLDEN_AXE).addEnchant(Enchantment.DAMAGE_ALL,20).setUnbreakable(true).build());
        addPotionEffect(s,PotionEffectType.SPEED,3);
        addPotionEffect(s,PotionEffectType.JUMP,3);
        setIdentifierString(s,"commandskeleton");
    }
    public static void slimeNight(Slime s){
        setName(s,"#497555Slime de Pesadilla");
        setMobHealth(s,65);
        setMobDamage(s,10);
        s.setSize(14);
        setIdentifierString(s,"slimenightmare");
    }



    //Revenant Mobs
    public static void revZombie(Zombie z){
        setName(z,"#592929Revenant Zombie");
        setMobDamage(z,6);
        setMobHealth(z,28);
        setMobRange(z,96);
        addPotionEffect(z,PotionEffectType.SPEED,0);
        setIdentifierString(z,"revenant_class");
        setIdentifierString(z,"revenantzombie");
        setIdentifierInt(z,"revzom_state",0);
    }
    public static void revSkeleton(Skeleton z){
        setName(z,"#592929Revenant Skeleton");
        setMobHealth(z,32);
        setMobRange(z,96);
        setMainHand(z,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,19).addEnchant(Enchantment.ARROW_KNOCKBACK,2).addEnchant(Enchantment.ARROW_FIRE,2).build());
        setIdentifierString(z,"revenant_class");
        setIdentifierString(z,"revenantskeleton");
        setIdentifierInt(z,"revske_amount",0); //Controla las veces que ha disparado
    }
    public static void revSpider(Spider z){
        setName(z,"#592929Revenant Spider");
        setMobHealth(z,35);
        setMobDamage(z,8);
        setMobRange(z,96);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"revenant_class");
        setIdentifierString(z,"revenantspider");
    }
    public static void revCreeper(Creeper z){
        setName(z,"#592929Revenant Creeper");
        setMobHealth(z,35);
        z.setExplosionRadius(8);
        z.setPowered(true);
        setMobRange(z,96);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"revenant_class");
        setIdentifierString(z,"revenantcreeper");
    }
    public static void revEnderman(Enderman z){
        setName(z,"#592929Revenant Enderman");
        setMobHealth(z,50);
        setMobDamage(z,10);
        setMobRange(z,96);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"revenant_class");
        setIdentifierString(z,"revenantenderman");
    }





    //Wasteyard Mobs
    public static void soulVg(Ghast s){
        setName(s,"#636363Wandering Vagrant");
        setMobHealth(s,25);
        setIdentifierString(s,"soulvag");
    }
    public static void scBrute(PiglinBrute c){
        setName(c,"#a84738Scorched Beast");
        c.setImmuneToZombification(true);
        setMobHealth(c,55);
        setMainHand(c,new ItemBuilder(Material.NETHERITE_AXE).setUnbreakable(true).build());
        addPotionEffect(c,PotionEffectType.SPEED,1);
        setIdentifierString(c,"scorchbeast");
    }
    public static void rustwalker(Creeper c){
        setName(c,"#ffab4aRustwalker");
        setMobHealth(c,35);
        c.setPowered(true);
        c.setMaxFuseTicks(25);
        c.setFuseTicks(25);
        c.setExplosionRadius(5);
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
        setName(s,"#2e2e2eBrimstone Cube");
        setMobHealth(s,35);
        setMobDamage(s,12);
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
