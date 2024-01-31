package com.tll3.Listeners;

import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import com.tll3.TLL3;
import com.tll3.Task.MobRain;
import com.tll3.Task.Mobs.ArqBlockBreak;
import com.tll3.Task.Mobs.HomingTask;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.server.level.WorldServer;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Fire;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;

import static com.tll3.Misc.GenericUtils.*;

public class GenericEntityListeners implements Listener {


    @EventHandler
    public void damageentityE(EntityDamageByEntityEvent e){
        var target = e.getEntity();
        var damager = e.getDamager();
        if(damager instanceof Player p){
            var item = p.getInventory().getItemInMainHand();
            if(target instanceof LivingEntity l){

                if(Data.has(l,"metal_enemy",PersistentDataType.STRING)){
                    if(e.isCritical()){
                        l.getWorld().playSound(l.getLocation(),Sound.ENTITY_IRON_GOLEM_HURT,10.0F,2.0F);
                    }else{
                        l.getWorld().playSound(l.getLocation(),Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR,10.0F,2.0F);
                        e.setDamage(0.01);
                    }
                }

                if(item.hasItemMeta()){
                if(new ItemBuilder(item).hasID("dread_claymore")){
                    l.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,200,0,false,false,false));
                    l.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,200,0,false,false,false));
                }
                }
            }

            if(target instanceof Zombie z){
                if(Data.has(z,"revenantzombie",PersistentDataType.STRING)){
                    var result = z.getHealth() - e.getFinalDamage();
                    if(result < 14){
                        int g = Data.get(z,"revzom_state",PersistentDataType.INTEGER);
                        if(g == 0){
                            Data.set(z,"revzom_state",PersistentDataType.INTEGER,1);
                            EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,4);
                            EntityHelper.addPotionEffect(z,PotionEffectType.DAMAGE_RESISTANCE,1);
                            EntityHelper.addPotionEffect(z,PotionEffectType.SPEED,2);
                        }
                    }
                }
            }
        }

        if(target instanceof Player p){
            if(damager instanceof Husk s){
                if(Data.has(s,"starved_husk",PersistentDataType.STRING)){
                    var amount = p.getFoodLevel() - e.getFinalDamage();
                    if(amount < 0){p.setFoodLevel(0);return;}
                    p.setFoodLevel((int) amount);
                }
            }
            if(damager instanceof Endermite en){
                if(Data.has(en,"tesla",PersistentDataType.STRING)){
                    EntityHelper.teleportEnderman(p,p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ(),p.getWorld(),100.0D);
                }
            }
            if(damager instanceof Stray s){
                if(Data.has(s,"commandskeleton",PersistentDataType.STRING)){
                    p.setFreezeTicks(200);
                }
            }
            if(damager instanceof CustomDolphin || damager instanceof Dolphin){
                p.setVelocity(new Vector(0, -2, 0));
            }
            if(damager instanceof CustomAxolotls || damager instanceof Axolotl){
                e.setCancelled(true);
                damager.getWorld().createExplosion(damager.getLocation(),8,true,true);
            }

            if(damager instanceof MagmaCube m){
                if(Data.has(m,"toxiccrawler",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,200,4));
                }
            }
            if(damager instanceof Enderman enderman){
                if(EntityNaturalSpawn.doRandomChance(20)){
                    var list = enderman.getNearbyEntities(15,15,15);
                    var mob = list.get(new Random().nextInt(list.size()));
                    if(GenericUtils.isHostileMob(mob.getType())){
                        var l = enderman.getLocation().clone();
                        p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 10.0F, -1.0F);
                        enderman.teleport(mob.getLocation());
                        new BukkitRunnable(){
                            public void run(){
                                p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 10.0F, -1.0F);
                                enderman.teleport(l);
                                mob.teleport(l);
                                mob.playEffect(EntityEffect.TELEPORT_ENDER);
                                enderman.playEffect(EntityEffect.TELEPORT_ENDER);
                            }
                        }.runTaskLater(TLL3.getInstance(),5L);
                    }
                }
                if(Data.has(enderman,"revenantenderman",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,150,1,true,false,true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,150,0,true,false,true));
                }
            }
            if(damager instanceof Vindicator v){
                if(Data.has(v,"vindicatorex",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(20)){
                        dropMainOrOff(p);
                    }
                }
            }
            if(damager instanceof PigZombie z){
                if(Data.has(z,"shinobipig",PersistentDataType.STRING)){
                    z.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,60,0,false,false,false));
                }
            }
            if(damager instanceof PufferFish f){
                if(Data.has(f,"acidfish",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HARM,1,2,false,false,false));
                }
            }
            if(damager instanceof Zombie z){
                if(EntityNaturalSpawn.doRandomChance(10)){
                    if(getDay() >= 21){
                      PlayerData.addDataEffect(p,"bleed",40,0);
                    }else{
                      PlayerData.addDataEffect(p,"bleed",20,0);
                    }
                }
                if(getDay() >= 21){
                    if(EntityNaturalSpawn.doRandomChance(10)){
                        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,10.0F,1.0F);
                        p.getLocation().getWorld().spawnParticle(Particle.CRIT,p.getLocation(),50,1,1,1,0.5);
                        e.setDamage(e.getDamage() * 2);
                    }
                }
            }
            if(damager instanceof Fireball f){
                if(Data.has(f,"entropicfireball",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,100,5,true,false,true));
                }
            }
            if(damager instanceof Spider s){
                if(Data.has(s,"blackreaver",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,100,9));
                }
                if(Data.has(s,"neonspider",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,150,0,true,false,true));
                }
                if(Data.has(s,"adeptmauler",PersistentDataType.STRING)){
                    s.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,60,2,false,false,false));
                }
                if(Data.has(s,"revenantspider",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,0,true,false,true));
                    Block block = p.getLocation().getBlock();
                    if(getValidBlocks(block)){
                        block.getWorld().setType(block.getLocation(),Material.COBWEB);
                    }
                }
                if(Data.has(s,"termite",PersistentDataType.STRING)){
                    var state = Data.get(s,"t_state",PersistentDataType.INTEGER);
                    if(state == 0){
                        s.getLocation().getWorld().playSound(s.getLocation(),Sound.ENTITY_CREEPER_PRIMED,10.0F,2.0F);
                        Data.set(s,"t_state",PersistentDataType.INTEGER,1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(s.isDead() || !s.isValid()){cancel();return;}
                                s.getLocation().getWorld().createExplosion(s,3,false,true);
                                s.remove();
                            }
                        }.runTaskLater(TLL3.getInstance(),60L);
                    }
                }
                if(Data.has(s,"termite_ex",PersistentDataType.STRING)){
                    var state = Data.get(s,"tex_state",PersistentDataType.INTEGER);
                    if(state == 0){
                        Data.set(s,"tex_state",PersistentDataType.INTEGER,1);
                        s.getLocation().getWorld().playSound(s.getLocation(),Sound.ENTITY_CREEPER_PRIMED,10.0F,2.0F);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(s.isDead() || !s.isValid()){cancel();return;}
                                s.getLocation().getWorld().createExplosion(s,6,false,true);
                                s.remove();
                            }
                        }.runTaskLater(TLL3.getInstance(),60L);
                    }
                }
            }
        }
    }

    @EventHandler
    public void vehicleenE(VehicleEnterEvent e){
        var entity = e.getEntered();
        if(entity instanceof Enemy && getDay() >= 7){
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void shoowbowE(EntityShootBowEvent e) {
        var entity = e.getEntity();
        var bow = e.getBow();
        var projectile = e.getProjectile();

        if(entity instanceof Pillager p){
            if(getDay() >= 7){
                if(projectile instanceof Arrow arrow){
                arrow.setDamage(arrow.getDamage() * 3);
            }
            }
            if(Data.has(p,"pillagerex",PersistentDataType.STRING)){
                if(projectile instanceof Firework f){
                    EntityHelper.setIdentifierString(f,"pillagerex");
                }
            }
        }
        if(entity instanceof Piglin p){
            if(getDay() >= 21){
                if(projectile instanceof Arrow arrow){
                    arrow.setDamage(arrow.getDamage() * 6);
                }
            }
        }

        if(entity instanceof Player p){
            if(bow.hasItemMeta()){
            if(new ItemBuilder(bow).hasID("dread_bow")){
                EntityHelper.setIdentifierString(projectile,"dread");
            }
            }
        }



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
            if(Data.has(s,"livshriek",PersistentDataType.STRING)){
                s.getWorld().playSound(s.getLocation(),Sound.ENTITY_WARDEN_SONIC_BOOM,10.0F,1.0F);
                Arrow a = (Arrow) projectile;
                if(getDay() >= 21){
                   a.setDamage(60);
                }else{
                   a.setDamage(40);
                }
                a.setGravity(false);
                for(Player online : Bukkit.getOnlinePlayers()){
                    PacketPlayOutEntityDestroy packed = new PacketPlayOutEntityDestroy(a.getEntityId());
                    ((CraftPlayer)online).getHandle().c.a(packed,null);
                }
                new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        if(a.isOnGround() || !a.isValid() || a.isDead()){cancel();return;}
                        if(i < 400){
                            a.getWorld().spawnParticle(Particle.SONIC_BOOM,a.getLocation(),1);
                            i++;
                        }else{
                            a.remove();
                            cancel();
                        }
                    }
                }.runTaskTimer(TLL3.getInstance(),0L,1L);
            }
            if(Data.has(s,"steelrailgunner",PersistentDataType.STRING)){
                Arrow a = (Arrow) projectile;
                EntityHelper.setIdentifierString(a,"steeltnt");
                a.setGravity(false);
                new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        if(a.isOnGround() || !a.isValid() || a.isDead()){cancel();return;}
                        if(i < 400){
                            a.getWorld().spawnParticle(Particle.SMOKE_LARGE,a.getLocation(),1);
                            i++;
                        }else{
                            a.remove();
                            cancel();
                        }
                    }
                }.runTaskTimer(TLL3.getInstance(),0L,1L);
            }
            if(Data.has(s,"firemancer",PersistentDataType.STRING)){
                Fireball f = s.launchProjectile(Fireball.class);
                if(getDay() >= 21) {
                    f.setYield(6);
                }else {
                    f.setYield(4);
                }
                e.setProjectile(f);
            }
            if(Data.has(s,"razorback",PersistentDataType.STRING)){
                EntityHelper.setIdentifierString(projectile,"lol");
                new HomingTask(projectile).runTaskTimer(TLL3.getInstance(),10L,1L);
            }
            if(Data.has(s,"revenantskeleton",PersistentDataType.STRING)){
                int g = Data.get(s,"revske_amount",PersistentDataType.INTEGER);
                if(getDay() >= 21){
                    EntityHelper.setIdentifierString(projectile, "rev_explosion");
                }else{
                    if (g < 5) {
                        Data.set(s, "revske_amount", PersistentDataType.INTEGER, g + 1);
                    } else {
                        EntityHelper.setIdentifierString(projectile, "rev_explosion");
                        Data.set(s, "revske_amount", PersistentDataType.INTEGER, 0);
                    }
                }
            }

        }
        if (entity instanceof WitherSkeleton s) {
            if(Data.has(s,"w_mage",PersistentDataType.STRING)){
                Vector arrowVelocity = projectile.getVelocity().clone().normalize();
                e.setCancelled(true);
                new BukkitRunnable(){
                    int i = 0;
                    @Override
                    public void run() {
                        if(s.isDead() || !s.isValid()){cancel();return;}
                        if(i < 25){
                            i++;
                            double offsetDistance = 1.5;
                            Vector offset = arrowVelocity.multiply(offsetDistance);
                            var loc = projectile.getLocation().clone().add(offset);
                            EvokerFangs s = loc.getWorld().spawn(loc.add(0,-2,0), EvokerFangs.class);
                        }else{
                            cancel();
                        }
                    }
                }.runTaskTimer(TLL3.getInstance(),0L,5L);
            }
        }
    }

    @EventHandler
    public void moveE(EntityMoveEvent e){
        var entity = e.getEntity();
        Random random = new Random();
        int chance = random.nextInt(3000);
        if(entity instanceof Creeper c){
            if(c.getTarget() == null && Data.has(c,"unstablecreeper",PersistentDataType.STRING)){
                if(chance <= 5 && c.getVehicle() == null){
                    c.playEffect(EntityEffect.TELEPORT_ENDER);
                    c.getWorld().playSound(c.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,10.0F,1.0F);
                    EntityHelper.teleportEnderman(c,c.getLocation().getBlockX(),c.getLocation().getBlockY(),c.getLocation().getBlockZ(),c.getWorld(),64.0D);
                }
            }
        }
        if(getDay() >= 7){
        if(entity instanceof Enderman end){
            for (Player player : end.getWorld().getPlayers()) {
                int range = getDay() >= 21 ? 8 : 4;
                if (player.getLocation().distanceSquared(end.getLocation()) <= Math.pow(range, 2)) {
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        end.setTarget(player);
                    }
                }
            }
        }
        }
    }

    @EventHandler
    public void explodeE(EntityExplodeEvent e){
        var entity = e.getEntity();
        var loc = e.getLocation();
        if(entity instanceof Creeper c){
            if(Data.has(c,"revenantcreeper",PersistentDataType.STRING)){
                loc.getNearbyPlayers(8).forEach(player -> {
                    player.setFireTicks(200);
                });
            }
            if(Data.has(c,"titaniumcreeper",PersistentDataType.STRING)) {
                loc.getNearbyPlayers(6).forEach(player -> {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,200,4,true,false,true));
                });
            }
        }
    }


    @EventHandler
    public void entdieE(EntityDeathEvent e){
        var entity = e.getEntity();
        var killer = e.getEntity().getKiller();
        var loc = e.getEntity().getLocation().clone();
        if(killer == null)return;
        if(entity instanceof Phantom p){
            if(Data.has(p,"duskphantom",PersistentDataType.STRING)) {
                if (getDay() >= 21) {
                    for(int i = 0; i < 4; i++){
                        MobRain.initMobs(loc);
                    }
                } else {
                    returnMob(loc);
                }
            }
        }
        if(entity instanceof Bat b){
            if(getDay() >= 21 && getMonsoon_active().equalsIgnoreCase("true")){
                b.getWorld().createExplosion(b.getLocation(),7,true,true);
            }
        }
        if(entity instanceof Witch w){
            if(getDay() >= 21){
                AreaEffectCloud r = (AreaEffectCloud) Entities.spawnMob(w.getLocation().clone(),EntityType.AREA_EFFECT_CLOUD);
                r.setSource(w);
                r.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING,200,4,false,false,false),true);
            }
        }
    }

    @EventHandler
    public void projlaunchE(ProjectileLaunchEvent e){
        var proj = e.getEntity();
        var shooter = e.getEntity().getShooter();
        var loc = e.getLocation();


        if(shooter instanceof Drowned d){
            if(proj instanceof Trident t){
                if(Data.has(d,"abyssdrow",PersistentDataType.STRING)){
                    t.setDamage(t.getDamage() * 5);
                    EntityHelper.setIdentifierString(t,"lolxd");
                }
            }
        }
        if(shooter instanceof Player p){
            if(proj instanceof EnderPearl enderPearl){
                var item1 = p.getInventory().getItemInMainHand();
                var item2 = p.getInventory().getItemInOffHand();
                if(item1.hasItemMeta() || item2.hasItemMeta()){
                if(new ItemBuilder(item1).hasID("revenant_pearl") || new ItemBuilder(item2).hasID("revenant_pearl")){
                    EntityHelper.setIdentifierString(enderPearl,"rev_pearl");
                }
            }
            }
        }

        if(shooter instanceof Ghast g){
            if(proj instanceof Fireball s){
                if(Data.has(g,"cata_ghast",PersistentDataType.STRING)){
                    s.setYield(4);
                }
                if(Data.has(g,"soulvag",PersistentDataType.STRING)){
                    s.setYield(8);
                }
                if(Data.has(g,"entropicdemon",PersistentDataType.STRING)){
                    s.setYield(7);
                    EntityHelper.setIdentifierString(s,"entropicfireball");
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

        if(source instanceof Player p){
            if(proj instanceof EnderPearl enderPearl){
                if(Data.has(enderPearl,"rev_pearl",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100,1,true,false,true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,0,true,false,true));
                }
            }
        }

        if(proj instanceof Firework f){
            if(Data.has(f,"pillagerex",PersistentDataType.STRING)){
                if (hen != null) {
                    hen.getLocation().createExplosion((Entity) source,4,true,true);
                }
                if (hbl != null) {
                    hbl.getLocation().createExplosion((Entity) source,4,true,true);
                }
            }
        }


        if(proj instanceof Arrow a){
            if(Data.has(a,"lol",PersistentDataType.STRING)){
                if(hen instanceof Player p){
                    if(p.isBlocking())a.remove();
                }
            }
            if(Data.has(a,"rev_explosion",PersistentDataType.STRING)){
                if (hen != null) {
                    hen.getLocation().createExplosion((Entity) source,2,false,true);
                    proj.remove();
                }
                if (hbl != null) {
                    hbl.getLocation().createExplosion((Entity) source,2,false,true);
                    proj.remove();
                }
            }
            if(Data.has(a,"steeltnt",PersistentDataType.STRING)){
                if (hen != null) {
                    TNTPrimed n = (TNTPrimed) Entities.spawnMob(hen.getLocation(),EntityType.PRIMED_TNT);
                    n.setYield(6);
                    n.setFuseTicks(80);
                    n.setVelocity(new Vector(0,1,0));
                    proj.remove();
                }
                if (hbl != null) {
                    TNTPrimed n = (TNTPrimed) Entities.spawnMob(hbl.getLocation(),EntityType.PRIMED_TNT);
                    n.setYield(6);
                    n.setFuseTicks(80);
                    n.setVelocity(new Vector(0,1,0));
                    proj.remove();
                }
            }
            if(Data.has(a,"dread",PersistentDataType.STRING)){
                if(hen instanceof LivingEntity l){
                    l.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200,0,false,false,false));
                    l.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,200,0,false,false,false));
                }
            }
        }

        if(proj instanceof Trident t){
            if(Data.has(t,"lolxd",PersistentDataType.STRING)){
                t.remove();
                if (hen != null) {
                    hen.getLocation().getWorld().strikeLightning(hen.getLocation());
                }
                if (hbl != null) {
                    hbl.getLocation().getWorld().strikeLightning(hbl.getLocation());
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


        if(source instanceof Blaze z){
            if(Data.has(z,"windcharger",PersistentDataType.STRING)){
                if (hen != null) {
                    hen.getWorld().spawnParticle(
                            Particle.EXPLOSION_HUGE,
                            hen.getLocation(),
                            10,
                            0.5,
                            0.5,
                            0.5,
                            1
                    );
                    hen.getWorld().playSound(hen.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,10.0F,2.0F);
                    hen.getWorld().getNearbyPlayers(
                            hen.getLocation(),5,5,5
                    ).forEach(player -> {
                        Vector direction = player.getLocation().toVector().subtract(hen.getLocation().toVector()).normalize();
                        player.setVelocity(new Vector(-direction.getX(), 1.0, -direction.getZ()));
                    });
                }
                if (hbl != null) {
                    hbl.getWorld().spawnParticle(
                            Particle.EXPLOSION_HUGE,
                            hbl.getLocation(),
                            10,
                            0.5,
                            0.5,
                            0.5,
                            1
                    );
                    hbl.getWorld().playSound(hbl.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,10.0F,2.0F);
                    hbl.getWorld().getNearbyPlayers(
                            hbl.getLocation(),5,5,5
                    ).forEach(player -> {
                        Vector direction = player.getLocation().toVector().subtract(hbl.getLocation().toVector()).normalize();
                        player.setVelocity(new Vector(-direction.getX(), 1.0, -direction.getZ()));
                    });
                }
            }
            if(Data.has(z,"armoredblaze",PersistentDataType.STRING)){
                if (hen != null) {
                    hen.getLocation().createExplosion((Entity) source,3,true,true);
                }
                if (hbl != null) {
                    hbl.getLocation().createExplosion((Entity) source,3,true,true);
                }
            }
        }
    }




    @EventHandler
    public void targetE(EntityTargetLivingEntityEvent e){
        var target = e.getTarget();
        var origin = e.getEntity();

        if(target instanceof Player p){
           if(origin instanceof Enemy && (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR))e.setCancelled(true);
           if(origin instanceof Zombie z){
            if(Data.has(z,"zninja",PersistentDataType.STRING)){
                if(z.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                       z.getLocation().getWorld().playSound(z.getLocation(),Sound.ENTITY_PLAYER_ATTACK_SWEEP,10.0F,2.0F);
                       EntityHelper.setMainHand(z,new ItemStack(Material.IRON_SWORD));
                       z.removePotionEffect(PotionEffectType.INVISIBILITY);
                       z.setSilent(true);
                       z.getLocation().getWorld().spawnParticle(
                            Particle.SMOKE_NORMAL,
                            z.getLocation(),
                            20,
                            1,
                            1,
                            1,
                            -0.1
                       );
                   }
               }

           }
           if(origin.getType() == EntityType.ZOMBIFIED_PIGLIN){
               if(Data.has(origin,"shinobipig",PersistentDataType.STRING)){
                   PigZombie z = (PigZombie) origin;
                   if(!z.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                       EntityHelper.addPotionEffect(z,PotionEffectType.INVISIBILITY,0);
                       z.setSilent(true);
                   }
               }
           }
           if(origin instanceof Skeleton s){
               if(Data.has(s,"bruteskeleton",PersistentDataType.STRING)) {
                   double distance = s.getLocation().distance(p.getLocation());
                   if (distance < 10.0) {
                       s.getWorld().playSound(s.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 10.0F, 1.0F);
                       if(getDay() >= 21){
                           EntityHelper.setMainHand(s, new ItemBuilder(Material.NETHERITE_AXE).addEnchant(Enchantment.DAMAGE_ALL, 30).build());
                       }else {
                           EntityHelper.setMainHand(s, new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 5).build());
                       }
                   } else {
                       s.getWorld().playSound(s.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 10.0F, 1.0F);
                       if (getDay() >= 21) {
                           EntityHelper.setMainHand(s, new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 30).addEnchant(Enchantment.ARROW_FIRE, 5).build());
                       }else{
                           EntityHelper.setMainHand(s, new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 5).addEnchant(Enchantment.ARROW_FIRE, 5).build());
                       }
                   }
               }
           }
        }
        if(origin instanceof Enemy && (target instanceof Enemy || target instanceof Animals || target instanceof Fish)){
            e.setCancelled(true);
        }

        if(target instanceof IronGolem && origin instanceof Enemy)e.setCancelled(true);
        if(target instanceof Enemy && origin instanceof IronGolem)e.setCancelled(true);

    }

    public static void returnMob(Location loc){
        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
        int r = GenericUtils.getRandomValue(11);
        switch (r){
            case 0 -> {Zombie entity = (Zombie) Entities.spawnMob(loc,EntityType.ZOMBIE); EntityNaturalSpawn.chooseZombieClass1(entity);}
            case 1 -> {Skeleton entity = (Skeleton) Entities.spawnMob(loc,EntityType.SKELETON); EntityNaturalSpawn.chooseSkeletonClass1(entity);}
            case 2 -> {Spider entity = (Spider) Entities.spawnMob(loc,EntityType.SPIDER); Entities.blackRev(entity);}
            case 3 -> {Creeper entity = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER); Entities.creChr(entity);}
            case 4 -> {Enderman entity = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);}
            case 5 -> {
                Witch entity = (Witch) Entities.spawnMob(loc,EntityType.WITCH);
            }
            case 6 -> {
                Drowned entity = (Drowned) Entities.spawnMob(loc,EntityType.DROWNED);
                Entities.drowAby(entity);
            }
            case 7 ->{
                Husk entity = (Husk) Entities.spawnMob(loc,EntityType.HUSK); Entities.huStr(entity);
            }
            case 8 -> {CaveSpider entity = (CaveSpider) Entities.spawnMob(loc,EntityType.CAVE_SPIDER); Entities.csTerCol(entity);
            }
            case 9 -> {Silverfish entity = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH); Entities.silverday5(entity);}
            case 10 -> {
                Stray entity = (Stray) Entities.spawnMob(loc,EntityType.STRAY);
                if(getDay() >= 14){
                    Entities.strayCom(entity);
                }
            }
        }
    }

    public static void dropMainOrOff(Player p){
        ItemStack hand = p.getInventory().getItemInMainHand();
        ItemStack off = p.getInventory().getItemInOffHand();
        if(Math.random() < 0.5){
            if(hand == null && hand.getType() == Material.AIR)return;
            var clone = hand.clone();
            p.getWorld().dropItemNaturally(p.getLocation(),clone);
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&c&lTe han robado un item de tu mano principal!"));
            p.getInventory().setItem(EquipmentSlot.HAND,null);
        }else{
            if(off == null && off.getType() == Material.AIR)return;
            var clone = off.clone();
            p.getWorld().dropItemNaturally(p.getLocation(),clone);
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&c&lTe han robado un item de tu mano secundaria!"));
            p.getInventory().setItem(EquipmentSlot.OFF_HAND,null);
        }
    }


    public boolean getValidBlocks(Block block){
        switch (block.getType()){
            case LAVA,WATER,OBSIDIAN,BEDROCK,CHEST,FURNACE,BLAST_FURNACE,SMOKER,ENDER_CHEST,END_PORTAL_FRAME,BREWING_STAND,CHISELED_BOOKSHELF,TRAPPED_CHEST,DISPENSER,LECTERN,DROPPER: return false;
            default: return true;
        }
    }
    
}
