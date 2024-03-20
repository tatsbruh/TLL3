package com.tll3.Lists;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Lists.CustomEntities.CustomAxolotls;
import com.tll3.Lists.CustomEntities.CustomPig;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.*;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.EntitySpider;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityFireworks;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftIronGolem;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPigZombie;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftSpider;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
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
import static com.tll3.Misc.GenericUtils.*;

public class Entities {
    /*
    * Day 5 mobs
    * */

    public static void creChr(Creeper c){
        setName(c,"&bSupercharged Creeper");
        c.setPowered(true);
        if(getDay() >= 21){
            c.setMaxFuseTicks(20);
            c.setFuseTicks(20);
        }
    }

    public static void skeAd(Skeleton s){
        setName(s,"&4Rogue Skeleton");
        setMobHealth(s,30);
        setMobDamage(s,4);
        s.setShouldBurnInDay(false);
        if(getDay() >= 21){
            setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,30).addEnchant(Enchantment.ARROW_FIRE,5).build());
        }else{
            setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,5).addEnchant(Enchantment.ARROW_FIRE,5).build());
        }
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
        if(getDay() >= 21){
            setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,15).build());
        }else{
            setMainHand(s,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,5).build());
        }
        setHead(s,new ItemStack(Material.TARGET));
        setIdentifierString(s,"razorback");
        s.setShouldBurnInDay(false);
    }


    public static void skeW(Skeleton s){
        setName(s,"#024f3cSkeleton Warden");
        setHead(s,new ItemStack(Material.BLAST_FURNACE));
        setChestplate(s,new ItemStack(Material.IRON_CHESTPLATE));
        s.setShouldBurnInDay(false);
        if(getDay() >= 14 && getDay() < 21) {
            setMainHand(s, new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 30).build());
        }else if(getDay() >= 21){
            setMainHand(s, new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 50).build());
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
        addPotionEffect(s, PotionEffectType.SPEED,0);
        s.setAggressive(true);
        setIdentifierString(s,"blackreaver");
        if(getDay() >= 14){
            setMobHealth(s,55);
            setMobDamage(s,9);
        }else{
            setMobHealth(s,35);
            setMobDamage(s,7);
        }
    }

    public static void adapSp(Spider s){
        setName(s,"&cScarlet Leech");
        addPotionEffect(s, PotionEffectType.SPEED,0);
        s.setAggressive(true);
        setIdentifierString(s,"adeptmauler");
        if(getDay() >= 14){
            setMobHealth(s,55);
            setMobDamage(s,9);
        }else{
            setMobHealth(s,35);
            setMobDamage(s,6);
        }
    }

    public static void termite(CaveSpider s){
        setName(s,"&6Termite");
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
        if(getDay() >= 28){
            setMobDamage(s,18);
        }else{
            setMobDamage(s,6);
        }
        addPotionEffect(s,PotionEffectType.SPEED,1);
        setIdentifierString(s,"starved_husk");
    }
    public static void csTerCol(CaveSpider s){
        setName(s,"&6&lColony Termite");
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
        if(getDay() >= 21) {
            setMobDamage(phantom, 9);
            phantom.setSize(19);
        }else{
            setMobDamage(phantom,4);
            phantom.setSize(15);
        }
        setIdentifierString(phantom,"duskphantom");
    }
    public static void silverday5(Silverfish silverfish){
        setName(silverfish,"&cPowerful Silverfish");
        addPotionEffect(silverfish,PotionEffectType.INCREASE_DAMAGE,14);
        addPotionEffect(silverfish,PotionEffectType.SPEED,1);
    }
    public static void drowAby(Drowned drowned){
        setName(drowned,"&bAbyssal Drowned");
        setMobDamage(drowned,6);
        setMobHealth(drowned,25);
        setMainHand(drowned,new ItemBuilder(Material.TRIDENT).addEnchant(Enchantment.IMPALING,5).addEnchant(Enchantment.CHANNELING,0).build());
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
        setName(z,"&9&lDischarger");
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
        if(getDay() >= 35) {
            setName(c, "#573e45Specimen TSAR");
            c.setPowered(true);
            c.setExplosionRadius(15);
            setMobHealth(c,25);
            setIdentifierString(c, "specimentsar");
        }else{
            setName(c, "#c991d9Unstable Creeper");
            c.setPowered(true);
            c.setExplosionRadius(5);
            setIdentifierString(c, "unstablecreeper");
        }
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
        setName(s,"&6&lStray Commando");
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
        setName(s,"#497555Nightmare Slime");
        setMobHealth(s,65);
        setMobDamage(s,35);
        s.setSize(14);
        setIdentifierString(s,"slimenightmare");
    }
    public static void neonSp(Spider s){
        setName(s,"#8cffc6Neon Mauler");
        setMobHealth(s,45);
        setMobDamage(s,8);
        setMobRange(s,100);
        addPotionEffect(s,PotionEffectType.SPEED,1);
        setIdentifierString(s,"neonspider");
    }
    public static void nwPillager(Pillager p,boolean canjoinraid){
        setName(p,"#ff1717PyroIllager");
        setMainHand(p,new ItemBuilder(Material.CROSSBOW).addEnchant(Enchantment.QUICK_CHARGE,3).addEnchant(Enchantment.MULTISHOT,1).setUnbreakable(true).build());
        setOffhand(p,rocket());
        p.setCanJoinRaid(canjoinraid);
        setIdentifierString(p,"pillagerex");
    }
    public static void nwVindicator(Vindicator p,boolean canjoinraid){
        setName(p,"#ffbb33Executioner");
        setMainHand(p,new ItemStack(Material.NETHERITE_AXE));
        p.setCanJoinRaid(canjoinraid);
        setIdentifierString(p,"vindicatorex");
    }
    public static void nwRavager(Ravager p,boolean canjoinraid){
        setName(p,"#eb420eSupreme Ravager");
        setMobHealth(p,100);
        setMobDamage(p,20);
        p.setCanJoinRaid(canjoinraid);
        setIdentifierString(p,"ravagerex");
    }
    public static void nwVex(Vex v){
        setName(v,"#78d6d0Sacred Spirit");
        setMainHand(v,new ItemBuilder(Material.NETHERITE_SWORD).addEnchant(Enchantment.FIRE_ASPECT,5).build());
        setIdentifierString(v,"vexex");
    }
    public static void windTyphoon(Breeze b){
        setName(b,"#fbfbfbW#f3fafbi#ebf9fbn#e4f9fcd #dcf8fcT#d4f7fcy#ccf6fcp#c4f5fch#bdf5fdo#b5f4fdo#adf3fdn");
        setMobHealth(b,35);
        setIdentifierString(b,"windtyphoon");
    }



    //Revenant Mobs
    public static void revZombie(Zombie z){
        setName(z,"#592929Revenant Zombie");
        setMobDamage(z,6);
        setMobHealth(z,28);
        setMobRange(z,96);
        z.setAdult();
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
        setMobHealth(z,15);
        setMobRange(z,96);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"revenant_class");
        setIdentifierString(z,"revenantspider");
    }
    public static void revCreeper(Creeper z){
        setName(z,"#592929Revenant Creeper");
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
    public static void acidFish(PufferFish f){
        setName(f,"#5e8a46Acid Pufferfish");
        setMobHealth(f,20);
        setIdentifierString(f,"acidfish");
        EntityNaturalSpawn.setCustomMobcap(f, 10, 1.10, 24, 60, true);
    }

    //dia 21
    public static void entropicDem(Ghast g){
        setName(g,"&4&lEntropic Demon");
        setMobHealth(g,55);
        setIdentifierString(g,"entropicdemon");
    }
    public static void zombpigRider(PigZombie z){
        injectHostileBehaviorToPig(z);
        setName(z,"#7a3d5bApocalyptic Hog-Rider");
        setMobHealth(z,10);
        setMainHand(z,new ItemStack(Material.CARROT_ON_A_STICK));
        WorldServer worldServer = ((CraftWorld) z.getLocation().getWorld()).getHandle();
        CustomPig r = new CustomPig(worldServer);
        r.a_(z.getLocation().getX(), z.getLocation().getY(), z.getLocation().getZ());
        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
        ((LivingEntity)r.getBukkitEntity()).addPassenger(z);
        setIdentifierString(z,"pigrider");
    }
    public static void zombpigShinobi(PigZombie z){
        injectHostileBehaviorToPig(z);
        setName(z,"#6c5f85Samurai Piglin");
        setMobHealth(z,15);
        setMainHand(z,new ItemBuilder(Material.NETHERITE_SWORD).addEnchant(Enchantment.DAMAGE_ALL,15).addEnchant(Enchantment.FIRE_ASPECT,30).build());
        addPotionEffect(z,PotionEffectType.SPEED,2);
        setIdentifierString(z,"shinobipig");
    }
    public static void zombpigAlchemist(PigZombie z){
        injectHostileBehaviorToPig(z);
        setName(z,"#857c5fAlchemist Pig");
        setMobHealth(z,25);
        setMainHand(z,new ItemBuilder(Material.SPLASH_POTION).addEnchant(Enchantment.DAMAGE_ALL,10).build());
        setIdentifierString(z,"alchpig");
        new AlqPotionThrow(z).runTaskTimer(TLL3.getInstance(),0L,1L);
    }
    public static void titaniumCreeper(Creeper c){
        setName(c,"#403e3eTitanium Creeper");
        setMobHealth(c,10);
        addPotionEffect(c,PotionEffectType.SPEED,1);
        c.setPowered(true);
        setIdentifierString(c,"titaniumcreeper");
        setIdentifierString(c,"metal_enemy");
        setKnockresist(c,1000);
    }
    public static void steelrailgunner(Skeleton s){
        setName(s,"#403e3eSteel Rail-gunner");
        setMobHealth(s,10);
        setIdentifierString(s,"steelrailgunner");
        setIdentifierString(s,"metal_enemy");
        setKnockresist(s,1000);
    }
    public static void cyberpunk(Enderman e){
        setName(e,"#403e3eCyberpunk");
        setMobHealth(e,10);
        setMobDamage(e,10);
        addPotionEffect(e,PotionEffectType.SPEED,3);
        setIdentifierString(e,"cyberpunk");
        setIdentifierString(e,"metal_enemy");
        setKnockresist(e,1000);
    }
    public static void creeperTower(Creeper c){
        int quantity = getRandomValue(5);
        chooseCreeperType(c);
        Creeper c1 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
        Creeper c2 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
        chooseCreeperType(c1);
        chooseCreeperType(c2);
        c.addPassenger(c1);
        c1.addPassenger(c2);
        switch (quantity){
            case 0 ->{
                Creeper c3 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
                chooseCreeperType(c3);
                c2.addPassenger(c3);
            }
            case 1 ->{
                Creeper c3 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
                chooseCreeperType(c3);
                Creeper c4 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
                chooseCreeperType(c4);
                c2.addPassenger(c3);
                c3.addPassenger(c4);
            }
            case 2 ->{
                Creeper c3 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
                chooseCreeperType(c3);
                Creeper c4 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
                chooseCreeperType(c4);
                Creeper c5 = (Creeper) spawnMob(c.getLocation(),EntityType.CREEPER);
                chooseCreeperType(c5);
                c2.addPassenger(c3);
                c3.addPassenger(c4);
                c4.addPassenger(c5);
            }
        }
    }

    //Dia 28
    public static void starEnderman(Enderman e){
        setName(e,"&bStarred Enderman &e&l★");
        setMobHealth(e,55);
        setMobDamage(e,15);
        setIdentifierString(e,"starredenderman");
    }
    public static void starWither(WitherSkeleton e){
        setName(e,"&bSpace Overseer &e&l★");
        setMobHealth(e,45);
        setMobDamage(e,6);
        setMainHand(e,new ItemBuilder(Material.NETHERITE_SWORD).addEnchant(Enchantment.DAMAGE_ALL,20).build());
        setIdentifierString(e,"starredwither");
        setIdentifierString(e,"barrier");
        setIdentifierInt(e,"barrier_state",0);
        new WSkeletonJetpack(e).runTaskTimer(TLL3.getInstance(),0L,1L);
    }
    public static void starPillager(Pillager e){
        setName(e,"&bInterestellar Scavenger &e&l★");
        setMobHealth(e,40);
        setMobDamage(e,6);
        setMainHand(e,new ItemBuilder(Material.CROSSBOW).addEnchant(Enchantment.QUICK_CHARGE,5).addEnchant(Enchantment.PIERCING,3).build());
        setIdentifierString(e,"starredpillager");
    }
    public static void starCreeper(Creeper e){
        setName(e,"&bProject C &e&l★");
        setMobHealth(e,25);
        e.setExplosionRadius(6);
        e.setPowered(true);
        setIdentifierString(e,"starredcreeper");
    }
    public static void starPhantom(Phantom e){ //Unused
        setName(e,"&bGeneral PrePhantom &e&l★");
        setMobHealth(e,45);
        setMobDamage(e,20);
        e.setSilent(true);
        e.setSize(5);
        setIdentifierString(e,"starredphantom");
        setIdentifierString(e,"barrier");
        setIdentifierInt(e,"barrier_state",0);
    }
    public static void lilGhoul(Zombie z){
        setName(z,"&cLi'l Ghoul");
        setMobHealth(z,35);
        setMobDamage(z,10);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        z.setBaby();
        setIdentifierString(z,"lilghoul");
    }
    public static void blazephim(Blaze z){
        setName(z,"&f&lBlazephim");
        setMobHealth(z,50);
        setMobDamage(z,50);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"blazephim");
    }
    public static void hellSymbiote(Blaze z){
        setName(z,"&b&lHellfire Symbiote");
        setMobHealth(z,50);
        setMobDamage(z,50);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"symbiote");
    }
    public static void rabbitKiller(Rabbit r){
        setMobHealth(r,55);
        addPotionEffect(r,PotionEffectType.INCREASE_DAMAGE,9);
        r.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
        setIdentifierString(r,"killerbunny");
    }




    //Dia 35
    public static void relicZombie(Zombie z){
        setName(z,"&2&lRelic Juggernaut");
        setMobHealth(z,35);
        setMobDamage(z,16);
        addPotionEffect(z,PotionEffectType.SPEED,2);
        z.setAdult();
        setIdentifierString(z,"reliczombie");
        setIdentifierString(z,"relicmob");
    }
    public static void relicSkeleton(Skeleton z){
        setName(z,"&2&lAncient Sharpshooter");
        setMobHealth(z,35);
        setMobDamage(z,16);
        setMainHand(z,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,45).build());
        setKnockresist(z,1000);
        setIdentifierString(z,"relicskeleton");
        setIdentifierString(z,"relicmob");
    }
    public static void relicVex(Vex z){
        setName(z,"&2&lXero");
        setMobHealth(z,10);
        setMainHand(z,new ItemBuilder(Material.GOLDEN_AXE).addEnchant(Enchantment.DAMAGE_ALL,35).build());
        setIdentifierString(z,"relicvex");
        setIdentifierString(z,"relicmob");
    }
    public static void doomsDay(Creeper z){
        setName(z,"&5&lDoomsday");
        setMobHealth(z,10);
        z.setExplosionRadius(7);
        z.setPowered(true);
        z.setSilent(true);
        setIdentifierString(z,"doomsday");
        setIdentifierInt(z,"burrowstate",0);
    }
    public static void deathbringer(Breeze z){
        setName(z,"&5&lDeathbringer");
        setMobHealth(z,35);
        setMobDamage(z,35);
        setIdentifierString(z,"doomsday");
        setIdentifierInt(z,"burrowstate",0);
    }
    public static void gabrielVex(Vex z){
        setName(z,"&f&lGabriel");
        setMobHealth(z,10);
        setMainHand(z,new ItemBuilder(Material.SOUL_CAMPFIRE).addEnchant(Enchantment.DAMAGE_ALL,20).build());
        setIdentifierString(z,"gabriel");
    }
    public static void antiflySkeleton(Skeleton z){
        setName(z,"&c&lAnti-air Commander");
        setMobHealth(z,45);
        setMobDamage(z,10);
        setMainHand(z,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,15).build());
        setIdentifierString(z,"anticommander");
    }


    public static void chooseCreeperType(Creeper e){
        if(getDay() >= 21 && getDay() < 28){
            int type = getRandomValue(5);
            switch (type){
                case 0 -> Entities.creChr(e);
                case 1 -> Entities.revCreeper(e);
                case 2 -> Entities.rustwalker(e);
                case 3 -> Entities.unstCr(e);
                case 4 -> Entities.titaniumCreeper(e);
            }
        }else if(getDay() >= 28 && getDay() < 35){
            int type = getRandomValue(7);
            switch (type) {
                case 0 -> Entities.creChr(e);
                case 1 -> Entities.revCreeper(e);
                case 2 -> Entities.rustwalker(e);
                case 3 -> Entities.unstCr(e);
                case 4 -> Entities.titaniumCreeper(e);
                case 5 -> Entities.vortice(e);
                case 6 -> Entities.starCreeper(e);
            }
        }
    }












    //Wasteyard Mobs
    public static void soulVg(Ghast s){
        setName(s,"#636363Wandering Vagrant");
        setMobHealth(s,25);
        setIdentifierString(s,"soulvag");
    }
    public static void scBrute(PiglinBrute c){
        setName(c,"#a84738Scorched Brute");
        c.setImmuneToZombification(true);
        setMobHealth(c,55);
        setMainHand(c,new ItemBuilder(Material.NETHERITE_AXE).setUnbreakable(true).build());
        addPotionEffect(c,PotionEffectType.SPEED,1);
        setIdentifierString(c,"scorchbeast");
    }
    public static void rustwalker(Creeper c){
        setName(c,"#ffab4aRustwalker");
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
        setMobDamage(s,20);
        s.setSize(12);
        setIdentifierString(s,"toxiccrawler");
    }













    //TLL 2 Mobs
    public static void blightedSkeleton(Skeleton s){
        setName(s,"#FEAE00B#FDAB00l#FCA900i#FBA700g#FBA400h#FAA200t#F9A000e#F89D00d #F79B00S#F69800k#F59600e#F49400l#F49100e#F38F00t#F28D00o#F18A00n");
        setMobHealth(s,40);
        setIdentifierString(s,"blightedskeleton");
        setIdentifierString(s,"tllt2");
    }
    public static void blightedEnderman(Enderman s){
        setName(s,"#D97BF7B#CF77EEl#C574E6i#BB70DEg#B26DD5h#A869CDt#9E66C5e#9462BCd #8A5FB4E#805BABn#7658A3d#6C549Be#635192r#594D8Am#4F4A82a#454679n");
        setMobHealth(s,40);
        setMobDamage(s,16);
        setIdentifierString(s,"blightedenderman");
        setIdentifierString(s,"tllt2");
    }
    public static void blightedGhast(Ghast s){
        setName(s,"#CB2D3E⋘ #CE2F3EB#D0313Dl#D3333Di#D5343Dg#D8363Dh#DA383Ct#DD3A3Ce#E03C3Cd #E23E3BG#E5403Bh#E7413Ba#EA433Bs#EC453At #EF473A⋙");
        setMobHealth(s,45);
        setIdentifierString(s,"blightedghast");
        setIdentifierString(s,"tllt2");
    }
    public static void zombieDestroyer(IronGolem s){
        CraftIronGolem craft = ((CraftIronGolem) s);
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
        setName(s,"#427C13Z#3E7F12o#398210m#35850Fb#31880Ei #2D8B0DD#288E0Be#24920As#209509t#1C9808r#179B06u#139E05c#0FA104t#0BA403o#06A701r");
        setMobHealth(s,45);
        setMobDamage(s,25);
        s.setRemoveWhenFarAway(true);
        s.setPersistent(false);
        s.setSilent(true);
        addPotionEffect(s,PotionEffectType.SPEED,0);
        setIdentifierString(s,"zombiedestroyer");
        setIdentifierString(s,"tllt2");
    }
    public static void killerscream(Vindicator s){
        setName(s,"#494949⋘ #48464AK#48434Bi#47414Cl#473E4El#463B4Fe#453850r#453651s#443352c#433053r#432D55e#422B56a#422857m #412558⋙");
        setMobHealth(s,25);
        setMobDamage(s,20);
        addPotionEffect(s,PotionEffectType.SPEED,1);
        s.setCanJoinRaid(false);
        setMainHand(s,new ItemStack(Material.NETHERITE_AXE));
        setIdentifierString(s,"killerscream");
        setIdentifierString(s,"tllt2");
    }
    public static void agileTarantula(Spider s){
        injectHostileBehaviorToSpider(s);
        setName(s,"&bTárantula Rompevientos");
        setMobHealth(s,45);
        setMobDamage(s,20);
        addPotionEffect(s,PotionEffectType.SPEED,3);
        setIdentifierString(s,"agilespider");
        setIdentifierString(s,"tllt2");
    }
    public static void vortice(Creeper s){
        setName(s,"#3494E6⋘ #3D9BE6V#47A3E6ó#50AAE5r#59B2E5t#62B9E5i#6CC0E5c#75C8E4e #7ECFE4⋙");
        setMobHealth(s,45);
        addPotionEffect(s,PotionEffectType.SPEED,3);
        addPotionEffect(s,PotionEffectType.INVISIBILITY,0);
        s.setExplosionRadius(50);
        s.setPowered(true);
        s.setMaxFuseTicks(10);
        s.setFuseTicks(10);
        setIdentifierString(s,"vortex");
        setIdentifierString(s,"tllt2");
    }









    //Primordial Mobs
    public static void primZomb(Zombie z){
        setName(z,"#672500W#6c2706i#71280dl#752a13d #7a2c19G#7f2d1fh#842f26o#88302cu#8d3232l");
        setMobHealth(z,25);
        setMobDamage(z,4);
        z.setAdult();
        setIdentifierString(z,"primordialzombie");
        setIdentifierInt(z,"primordialzombiestate",0);
        setIdentifierString(z,"behemoth");
    }
    public static void primSke(Skeleton z){
        setName(z,"&cOsteodracon Swordmaster");
        setMobHealth(z,45);
        setMobDamage(z,5);
        setMainHand(z,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,12).addEnchant(Enchantment.ARROW_FIRE,7).build());
        setIdentifierString(z,"primordialskeleton");
        setIdentifierInt(z,"primordialskeletonstate",0);
        setIdentifierString(z,"behemoth");
    }
    public static void primCre(Creeper z){
        setName(z,"&cPterodex Exodermis");
        setMobHealth(z,10);
        z.setPowered(true);
        z.setExplosionRadius(4);
        new CreeperLungeTask(z).runTaskTimer(TLL3.getInstance(),0L,1L);
        setIdentifierString(z,"primordialcreeper");
        setIdentifierString(z,"behemoth");
    }
    public static void primSpider(Spider z){
        setName(z,"&cArachnoskelin Venomancer");
        setMobHealth(z,45);
        setMobDamage(z,7);
        new SpiderLungeTask(z,true).runTaskTimer(TLL3.getInstance(),0L,1L);
        setIdentifierString(z,"primordialspider");
        setIdentifierString(z,"behemoth");
        injectHostileBehaviorToSpider(z);
    }
    public static void primEnd(Enderman z){
        setName(z,"&cCryptomorph Enderward");
        setMobHealth(z,55);
        setMobDamage(z,10);
        setIdentifierString(z,"primordialenderman");
        setIdentifierString(z,"behemoth");
    }
    public static void primSilv(Silverfish z){
        setName(z,"&cMega-fish");
        setMobHealth(z,5);
        setMobDamage(z,4);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"primordialsilverfish");
        setIdentifierString(z,"behemoth");
    }

    public static void primHusk(Husk z){
        setName(z,"&eSandreaper");
        setMobHealth(z,25);
        setMobDamage(z,4);
        setIdentifierString(z,"primordialhusk");
        setIdentifierString(z,"behemoth");
    }
    public static void primSlime(Slime z){
        setName(z,"&ePyroclastic Monarch");
        setMobHealth(z,20);
        z.setSize(10);
        setIdentifierString(z,"primordialslime");
        setIdentifierString(z,"behemoth");
    }
    public static void primBlaze(Blaze z){
        setName(z,"&eIncinder Inferno");
        setMobHealth(z,35);
        setMobDamage(z,20);
        setIdentifierString(z,"primordialblaze");
        setIdentifierString(z,"behemoth");
    }
    public static void primCave(CaveSpider z){
        setName(z,"&eSubterranean Ariant");
        setMobHealth(z,20);
        setMobDamage(z,6);
        setIdentifierString(z,"primordialcave");
        setIdentifierInt(z,"burrowstate",0);
        setIdentifierString(z,"behemoth");
    }
    public static void primRavager(Ravager z){
        setName(z,"&eCrazed Rangemaster Maraud");
        setMobHealth(z,55);
        setMobDamage(z,10);
        z.setCanJoinRaid(false);
        addPotionEffect(z,PotionEffectType.SPEED,1);
        setIdentifierString(z,"primordialravager");
        setIdentifierString(z,"behemoth");
    }
    public static void primBrute(PiglinBrute z){
        setName(z,"&6The Enforcer");
        setMobHealth(z,25);
        setMobDamage(z,9);
        z.setImmuneToZombification(true);
        setIdentifierString(z,"primordialbrute");
        setIdentifierInt(z,"burrowstate",0);
        setIdentifierString(z,"behemoth");
    }
    public static void primZoglin(Zoglin z){
        setName(z,"&6Siltusk Rampager");
        setMobHealth(z,30);
        setMobDamage(z,12);
        setIdentifierString(z,"primordialzoglin");
        setIdentifierInt(z,"burrowstate",0);
        setIdentifierInt(z,"primordialzoglinstate",0);
        setIdentifierString(z,"behemoth");
    }
    public static void primWither(WitherSkeleton z){
        setName(z,"&6THE DREADREAVER");
        setMobHealth(z,40);
        setMainHand(z,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,25).addEnchant(Enchantment.ARROW_FIRE,2).build());
        setIdentifierString(z,"primordialwither");
        setIdentifierString(z,"behemoth");
    }
    public static void primShulk(Shulker z){
        setName(z,"&6Riftshell the Wicked");
        z.setColor(DyeColor.RED);
        setMobHealth(z,20);
        setIdentifierString(z,"primordialshulker");
        setIdentifierString(z,"behemoth");
        z.setRemoveWhenFarAway(true);
        z.setPersistent(false);
    }
    public static void primGolem(IronGolem z){
        CraftIronGolem craft = ((CraftIronGolem) z);
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
        z.setRemoveWhenFarAway(true);
        z.setPersistent(false);
        setName(z,"&6Elder Colossus");
        setMobHealth(z,40);
        setMobDamage(z,16);
        setIdentifierString(z,"primordialgolem");
        setIdentifierString(z,"behemoth");
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
    public static ItemStack rocket() {
        ItemStack rorcket = new ItemStack(Material.FIREWORK_ROCKET);
        FireworkMeta f = (FireworkMeta) rorcket.getItemMeta();
        f.addEffect(FireworkEffect.builder().withColor(Color.RED).flicker(true).trail(true).build());
        f.setPower(2);
        rorcket.setItemMeta(f);
        rorcket.setAmount(120);
        return rorcket;
    }
    public static void injectHostileBehaviorToPig(PigZombie z){
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
    }
    public static void injectHostileBehaviorToSpider(Spider z){
        CraftSpider craft = ((CraftSpider) z);
        EntitySpider entityPigZombie = craft.getHandle();
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
    }

    //@alangonza7 hola



}
