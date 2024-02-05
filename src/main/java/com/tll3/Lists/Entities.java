package com.tll3.Lists;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Lists.CustomEntities.CustomAxolotls;
import com.tll3.Lists.CustomEntities.CustomPig;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.AlqPotionThrow;
import com.tll3.Task.Mobs.ArqBlockBreak;
import com.tll3.Task.Mobs.AshenWitherTask;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityFireworks;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftIronGolem;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPigZombie;
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
import static com.tll3.Misc.GenericUtils.getDay;
import static com.tll3.Misc.GenericUtils.getRandomValue;

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
        setName(silverfish,"&cSilverfish Poderoso");
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
        setName(s,"#497555Slime de Pesadilla");
        setMobHealth(s,65);
        setMobDamage(s,35);
        s.setSize(14);
        setIdentifierString(s,"slimenightmare");
    }
    public static void neonSp(Spider s){
        setName(s,"#8cffc6Araña de Neón");
        setMobHealth(s,45);
        setMobDamage(s,8);
        setMobRange(s,100);
        addPotionEffect(s,PotionEffectType.SPEED,1);
        setIdentifierString(s,"neonspider");
    }
    public static void nwPillager(Pillager p,boolean canjoinraid){
        setName(p,"#ff1717Piromaníaco");
        setMainHand(p,new ItemBuilder(Material.CROSSBOW).addEnchant(Enchantment.QUICK_CHARGE,3).addEnchant(Enchantment.MULTISHOT,1).setUnbreakable(true).build());
        setOffhand(p,rocket());
        p.setCanJoinRaid(canjoinraid);
        setIdentifierString(p,"pillagerex");
    }
    public static void nwVindicator(Vindicator p,boolean canjoinraid){
        setName(p,"#ffbb33Verdugo");
        setMainHand(p,new ItemStack(Material.NETHERITE_AXE));
        p.setCanJoinRaid(canjoinraid);
        setIdentifierString(p,"vindicatorex");
    }
    public static void nwRavager(Ravager p,boolean canjoinraid){
        setName(p,"#eb420eRavager Supremo");
        setMobHealth(p,100);
        setMobDamage(p,20);
        p.setCanJoinRaid(canjoinraid);
        setIdentifierString(p,"ravagerex");
    }
    public static void nwVex(Vex v){
        setName(v,"#78d6d0Espíritu Sagrado");
        setMainHand(v,new ItemBuilder(Material.NETHERITE_SWORD).addEnchant(Enchantment.FIRE_ASPECT,5).build());
        setIdentifierString(v,"vexex");
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
        setName(f,"#5e8a46Pez de Ácido");
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
        setName(z,"#7a3d5bJinete Cerdo-pocalíptico");
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
        setName(z,"#6c5f85Piglin Shinobi");
        setMobHealth(z,15);
        setMainHand(z,new ItemBuilder(Material.NETHERITE_SWORD).addEnchant(Enchantment.DAMAGE_ALL,15).addEnchant(Enchantment.FIRE_ASPECT,30).build());
        addPotionEffect(z,PotionEffectType.SPEED,2);
        setIdentifierString(z,"shinobipig");
    }
    public static void zombpigAlchemist(PigZombie z){
        injectHostileBehaviorToPig(z);
        setName(z,"#857c5fAlquimista Porcino");
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
        c.setMaxFuseTicks(17);
        c.setFuseTicks(17);
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



}
