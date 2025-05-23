package com.tll3.Listeners;

import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.CustomEntities.Others.MiniCyclone;
import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import com.tll3.Misc.Particles.ParticleDisplay;
import com.tll3.Misc.Particles.XParticle;
import com.tll3.TLL3;
import com.tll3.Task.MobRain;
import com.tll3.Task.Mobs.ArqBlockBreak;
import com.tll3.Task.Mobs.HomingTask;
import io.papermc.paper.event.entity.EntityMoveEvent;
import io.papermc.paper.event.entity.WardenAngerChangeEvent;
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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

import static com.tll3.Misc.GenericUtils.*;

public class GenericEntityListeners implements Listener {


    @EventHandler
    public void damageentityE(EntityDamageByEntityEvent e){
        var target = e.getEntity();
        var damager = e.getDamager();

        //IF THE DAMAGER IS THE PLAYER ITSELF
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
                if(Data.has(l,"barrier",PersistentDataType.STRING)){
                    var state = Data.get(l,"barrier_state",PersistentDataType.INTEGER);
                    if(state == 0){
                        if(e.getDamage() <= 10){
                            e.setCancelled(true);
                            var loc = l.getLocation();
                            createBarrierEffect(loc);
                            loc.getWorld().playSound(loc,Sound.ITEM_SHIELD_BLOCK,10.0F,2.0F);
                        }else{
                            e.setCancelled(true);
                            var loc = l.getLocation();
                            loc.getWorld().playSound(loc,Sound.ITEM_SHIELD_BREAK,10.0F,2.0F);
                            loc.getWorld().playSound(loc,Sound.BLOCK_GLASS_BREAK,10.0F,2.0F);
                            p.getLocation().getWorld().spawnParticle(Particle.END_ROD,p.getLocation(),25,0,0,0,1);
                            Data.set(l,"barrier_state",PersistentDataType.INTEGER,1);
                        }
                    }
                }

                if(item.hasItemMeta()) {
                    if (p.hasPotionEffect(PotionEffectType.LUCK)) return; //Efecto de maldicion en jugadores
                    if (new ItemBuilder(item).hasID("dread_claymore")) {
                        l.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0, false, false, false));
                        l.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0, false, false, false));
                    }
                }
            }

            if(target instanceof Zombie z){
                if(Data.has(z,"revenantzombie",PersistentDataType.STRING)){
                    var result = z.getHealth() - e.getFinalDamage();
                    if(getDay() >= 42){
                        if(result < 24){
                            int g = Data.get(z,"revzom_state",PersistentDataType.INTEGER);
                            if(g == 0){
                                Data.set(z,"revzom_state",PersistentDataType.INTEGER,1);
                                z.getWorld().playSound(z.getLocation(),Sound.ENTITY_ENDER_DRAGON_GROWL,10.0F,2.0F);
                                EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,5);
                                EntityHelper.addPotionEffect(z,PotionEffectType.DAMAGE_RESISTANCE,2);
                                EntityHelper.addPotionEffect(z,PotionEffectType.SPEED,3);
                            }
                        }
                    }else{
                        if(result < 14){
                            int g = Data.get(z,"revzom_state",PersistentDataType.INTEGER);
                            if(g == 0){
                                Data.set(z,"revzom_state",PersistentDataType.INTEGER,1);
                                z.getWorld().playSound(z.getLocation(),Sound.ENTITY_ENDER_DRAGON_GROWL,10.0F,2.0F);
                                EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,3);
                                EntityHelper.addPotionEffect(z,PotionEffectType.DAMAGE_RESISTANCE,1);
                                EntityHelper.addPotionEffect(z,PotionEffectType.SPEED,2);
                            }
                        }
                    }
                }
                if(Data.has(z,"primordialzombie",PersistentDataType.STRING)){
                    var result = z.getHealth() - e.getFinalDamage();
                    if(result <= 0){
                        int g = Data.get(z,"primordialzombiestate",PersistentDataType.INTEGER);
                        if(g == 0){
                            Data.set(z,"primordialzombiestate",PersistentDataType.INTEGER,1);
                            e.setCancelled(true);
                            z.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,4,false,false,false));
                            z.getWorld().playSound(z.getLocation(),Sound.ITEM_TRIDENT_THUNDER,10.0F,2.0F);
                            p.getLocation().getWorld().spawnParticle(Particle.GUST_EMITTER,p.getLocation(),1,0,0,0,0);
                            p.getLocation().getWorld().spawnParticle(Particle.FLASH,p.getLocation(),1,0,0,0,0);
                            EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,2);
                            EntityHelper.addPotionEffect(z,PotionEffectType.SPEED,3);
                        }
                    }
                }
                if(Data.has(z,"lilghoul",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(35) && !z.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
                        e.setCancelled(true);
                        p.getLocation().getWorld().spawnParticle(Particle.GUST_EMITTER,p.getLocation(),1,0,0,0,0);
                        z.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,60,4,false,false,false));
                    }
                }
            }
            if(target instanceof Zoglin z){
                if(Data.has(z,"primordialzoglin",PersistentDataType.STRING)){
                    var result = z.getHealth() - e.getFinalDamage();
                    if(result <= 15){
                        int g = Data.get(z,"primordialzoglinstate",PersistentDataType.INTEGER);
                        if(g == 0){
                            Data.set(z,"primordialzoglinstate",PersistentDataType.INTEGER,1);
                            EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,1);
                        }
                    }
                }
            }
            if(target instanceof Skeleton s){
                if(Data.has(s,"primordialskeleton",PersistentDataType.STRING)){
                    var result = s.getHealth() - e.getFinalDamage();
                    if(result < 25){
                        int g = Data.get(s,"primordialskeletonstate",PersistentDataType.INTEGER);
                        if(g == 0){
                            Data.set(s,"primordialskeletonstate",PersistentDataType.INTEGER,1);
                            s.getWorld().playSound(s.getLocation(),Sound.ITEM_ARMOR_EQUIP_GOLD,10.0F,2.0F);
                            EntityHelper.setMainHand(s,new ItemBuilder(Material.GOLDEN_SWORD).addEnchant(Enchantment.DAMAGE_ALL,5).addEnchant(Enchantment.FIRE_ASPECT,5).build());
                        }
                    }
                }
            }
        }

        //IF THE DAMAGER IS THE ARROW OF A PLAYER
        if(damager instanceof Arrow a){
            if(a.getShooter() instanceof Player p){
                if(target instanceof LivingEntity l){
                    if(Data.has(l,"metal_enemy",PersistentDataType.STRING)){
                        l.getWorld().playSound(l.getLocation(),Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR,10.0F,2.0F);
                        e.setDamage(0.01);
                    }
                    if(Data.has(l,"barrier",PersistentDataType.STRING)){
                        e.setCancelled(true);
                        var loc = l.getLocation();
                        createBarrierEffect(loc);
                        loc.getWorld().playSound(loc,Sound.ITEM_SHIELD_BLOCK,10.0F,2.0F);
                    }
                }
                if(target instanceof Zombie z){
                    if(Data.has(z,"revenantzombie",PersistentDataType.STRING)){
                        var result = z.getHealth() - e.getFinalDamage();
                        if(getDay() >= 42){
                            if(result < 24){
                                int g = Data.get(z,"revzom_state",PersistentDataType.INTEGER);
                                if(g == 0){
                                    Data.set(z,"revzom_state",PersistentDataType.INTEGER,1);
                                    z.getWorld().playSound(z.getLocation(),Sound.ENTITY_ENDER_DRAGON_GROWL,10.0F,2.0F);
                                    EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,5);
                                    EntityHelper.addPotionEffect(z,PotionEffectType.DAMAGE_RESISTANCE,2);
                                    EntityHelper.addPotionEffect(z,PotionEffectType.SPEED,3);
                                }
                            }
                        }else{
                            if(result < 14){
                                int g = Data.get(z,"revzom_state",PersistentDataType.INTEGER);
                                if(g == 0){
                                    Data.set(z,"revzom_state",PersistentDataType.INTEGER,1);
                                    z.getWorld().playSound(z.getLocation(),Sound.ENTITY_ENDER_DRAGON_GROWL,10.0F,2.0F);
                                    EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,3);
                                    EntityHelper.addPotionEffect(z,PotionEffectType.DAMAGE_RESISTANCE,1);
                                    EntityHelper.addPotionEffect(z,PotionEffectType.SPEED,2);
                                }
                            }
                        }
                    }
                    if(Data.has(z,"primordialzombie",PersistentDataType.STRING)){
                        var result = z.getHealth() - e.getFinalDamage();
                        if(result <= 0){
                            int g = Data.get(z,"primordialzombiestate",PersistentDataType.INTEGER);
                            if(g == 0){
                                Data.set(z,"primordialzombiestate",PersistentDataType.INTEGER,1);
                                e.setCancelled(true);
                                z.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,4,false,false,false));
                                z.getWorld().playSound(z.getLocation(),Sound.ITEM_TRIDENT_THUNDER,10.0F,2.0F);
                                z.getLocation().getWorld().spawnParticle(Particle.GUST_EMITTER,z.getLocation(),1,0,0,0,0);
                                z.getLocation().getWorld().spawnParticle(Particle.FLASH,z.getLocation(),1,0,0,0,0);
                                EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,2);
                                EntityHelper.addPotionEffect(z,PotionEffectType.SPEED,3);
                            }
                        }
                    }
                    if(Data.has(z,"lilghoul",PersistentDataType.STRING)){
                        if(EntityNaturalSpawn.doRandomChance(35) && !z.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
                            e.setCancelled(true);
                            z.getLocation().getWorld().spawnParticle(Particle.GUST_EMITTER,z.getLocation(),1,0,0,0,0);
                            z.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,60,4,false,false,false));
                        }
                    }
                }
                if(target instanceof Zoglin z){
                    if(Data.has(z,"primordialzoglin",PersistentDataType.STRING)){
                        var result = z.getHealth() - e.getFinalDamage();
                        if(result <= 15){
                            int g = Data.get(z,"primordialzoglinstate",PersistentDataType.INTEGER);
                            if(g == 0){
                                Data.set(z,"primordialzoglinstate",PersistentDataType.INTEGER,1);
                                EntityHelper.addPotionEffect(z,PotionEffectType.INCREASE_DAMAGE,1);
                            }
                        }
                    }
                }
                if(target instanceof Skeleton s){
                    if(Data.has(s,"primordialskeleton",PersistentDataType.STRING)){
                        var result = s.getHealth() - e.getFinalDamage();
                        if(result < 25){
                            int g = Data.get(s,"primordialskeletonstate",PersistentDataType.INTEGER);
                            if(g == 0){
                                Data.set(s,"primordialskeletonstate",PersistentDataType.INTEGER,1);
                                s.getWorld().playSound(s.getLocation(),Sound.ITEM_ARMOR_EQUIP_GOLD,10.0F,2.0F);
                                EntityHelper.setMainHand(s,new ItemBuilder(Material.GOLDEN_SWORD).addEnchant(Enchantment.DAMAGE_ALL,5).addEnchant(Enchantment.FIRE_ASPECT,5).build());
                            }
                        }
                    }
                }
            }
        }


        //THE REST OF THE FUCKING THINGS
        if(target instanceof Player p){
            if(p.isBlocking())return;
            if(damager instanceof LivingEntity l){ //Efecto de maldicion en mobs
                if(l.hasPotionEffect(PotionEffectType.LUCK))return;
            }
            if(Data.has(p,"invulnerable",PersistentDataType.STRING))return;
            if(getDay() >= 42 && damager instanceof Animals){
                p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,200,0,true,false,true));
            }
            if(damager instanceof Husk s){
                if(Data.has(s,"starved_husk",PersistentDataType.STRING)){
                    var amount = p.getFoodLevel() - e.getFinalDamage();
                    if(amount < 0){p.setFoodLevel(0);return;}
                    p.setFoodLevel((int) amount);
                    if(getDay() >= 28){
                        s.addPotionEffect(new PotionEffect(PotionEffectType.HARM,1,0,false,false,false));
                    }
                }
                if(Data.has(s,"primordialhusk",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(20)){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200,3,true,false,true));
                    }
                }
            }
            if(damager instanceof Endermite en){
                if(Data.has(en,"tesla",PersistentDataType.STRING)){
                    EntityHelper.teleportEnderman(p,p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ(),p.getWorld(),100.0D);
                }
            }
            if(damager instanceof Stray s){
                if(Data.has(s,"commandskeleton",PersistentDataType.STRING)){
                    if(getDay() >= 28){
                        p.setFreezeTicks(400);
                    }else{
                        p.setFreezeTicks(200);
                    }
                }
            }
            if(damager instanceof CustomDolphin || damager instanceof Dolphin){
                p.setVelocity(new Vector(0, -2, 0));
            }
            if(damager instanceof CustomAxolotls || damager instanceof Axolotl){
                e.setCancelled(true);
                damager.getWorld().createExplosion(damager.getLocation(),8,true,true);
            }
            if(damager instanceof Slime s){
                if(Data.has(s,"primordialslime",PersistentDataType.STRING)){
                    s.getLocation().createExplosion(3,true,true);
                }
            }
            if(damager instanceof MagmaCube m){
                if(Data.has(m,"toxiccrawler",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,200,4));
                }
            }
            if(damager instanceof Piglin piglin){
                if(getDay() >= 42){
                    if(EntityNaturalSpawn.doRandomChance(20)){
                        piglin.getWorld().playSound(piglin.getLocation(),Sound.EVENT_RAID_HORN,10.0F,2.0F);
                        p.getLocation().getWorld().spawnParticle(Particle.SONIC_BOOM,p.getLocation(),10,0,1,0,0.5);
                        p.getNearbyEntities(10, 10, 10).forEach(entity -> {
                                if (entity instanceof Creature c) {
                                    if(c.getTarget() == null){
                                        c.setTarget(p);
                                    }
                                }
                        });
                    }
                }
            }
            if(damager instanceof Parrot parrot){
                if(Data.has(parrot,"brimseeker",PersistentDataType.STRING)){
                    if(getDay() >= 42){
                        if(EntityNaturalSpawn.doRandomChance(40)){
                            createSurge(p.getLocation(),20);
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,200,0,true,false,true));
                    }
                }
            }
            if(damager instanceof Enderman enderman){
                if(getDay() >= 21){
                    int tpchance = 20;
                    if(getDay() >= 28 && getDay() < 42){
                        tpchance = 40;
                    }else if(getDay() >= 42) {
                        tpchance = 65;
                    }else if(Data.has(enderman,"zenithenderman",PersistentDataType.STRING)){
                        tpchance = 50;
                    }else{
                        tpchance = 20;
                    }
                if(EntityNaturalSpawn.doRandomChance(tpchance)) {
                    var list = enderman.getNearbyEntities(15, 15, 15);
                    var mob = list.get(new Random().nextInt(list.size()));
                    if (GenericUtils.isHostileMob(mob.getType())) {
                        var l = enderman.getLocation().clone();
                        p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 10.0F, -1.0F);
                        enderman.teleport(mob.getLocation());
                        new BukkitRunnable() {
                            public void run() {
                                p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 10.0F, -1.0F);
                                enderman.teleport(l);
                                mob.teleport(l);
                                mob.playEffect(EntityEffect.TELEPORT_ENDER);
                                enderman.playEffect(EntityEffect.TELEPORT_ENDER);
                            }
                        }.runTaskLater(TLL3.getInstance(), 5L);
                    }
                }
                }
                if(Data.has(enderman,"revenantenderman",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,150,1,true,false,true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,150,0,true,false,true));
                }
                if(Data.has(enderman,"zenithenderman",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(30)){
                        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,10.0F,-1.0F);
                        p.getLocation().getWorld().spawnParticle(Particle.DAMAGE_INDICATOR,p.getLocation(),25,1,1,1,0.3);
                        e.setDamage(e.getDamage() * 3);
                    }
                    Bukkit.getScheduler().runTaskLaterAsynchronously(TLL3.getInstance(), () -> {
                        p.setNoDamageTicks(0);
                        p.setLastDamage(Integer.MAX_VALUE);
                    }, 2L);
                }
                if(Data.has(enderman,"blightedenderman",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, true, true, true));
                }
                if(Data.has(enderman,"primordialenderman",PersistentDataType.STRING)){
                    p.setFreezeTicks(p.getFreezeTicks() + 100);
                }
                if(Data.has(enderman,"relicenderman",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,200,0,true,false,true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,1,true,false,true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,0,true,false,true));
                }
            }
            if(damager instanceof WitherSkeleton s){
                //nothing yet
            }
            if(damager instanceof Vex s){
                if(Data.has(s,"relicvex",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,40,0,true,false,true));
                }
                if(Data.has(s,"gabriel",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,3,true,false,true));
                }
            }
            if(damager instanceof Creeper s){
                if(Data.has(s,"starredcreeper",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,60,9,true,false,true));
                }
                if(Data.has(s,"reliccreeper",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,120,0,true,false,true));
                }
            }
            if(damager instanceof Vindicator v){
                if(Data.has(v,"vindicatorex",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(20)){
                        dropMainOrOff(p);
                    }
                }
                if(Data.has(v,"killerscream",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,200,0,true,false,true));
                }
            }
            if(damager instanceof PigZombie z){
                if(Data.has(z,"shinobipig",PersistentDataType.STRING)){
                    z.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,60,0,false,false,false));
                }
            }
            if(damager instanceof PiglinBrute b){
                if(Data.has(b,"primordialbrute",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,200,2,true,false,true));
                }
            }
            if(damager instanceof Zoglin b){
                if(Data.has(b,"primordialzoglin",PersistentDataType.STRING)){
                    Bukkit.getScheduler().runTaskLaterAsynchronously(TLL3.getInstance(), () -> {
                        p.setNoDamageTicks(0);
                        p.setLastDamage(Integer.MAX_VALUE);
                    }, 2L);
                }
            }
            if(damager instanceof PufferFish f){
                if(Data.has(f,"acidfish",PersistentDataType.STRING)){
                    if(getDay() >= 35){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.HARM,1,4,false,false,false));
                    }else{
                        p.addPotionEffect(new PotionEffect(PotionEffectType.HARM,1,2,false,false,false));
                    }
                }
            }
            if(damager instanceof Zombie z){
                int critchance = 10;
                if(getDay() >= 28){
                    if(getDay() < 35) {
                        if (getMonsoon_active().equalsIgnoreCase("true")) {
                            critchance = 20;
                        }
                    }else if(getDay() >= 35){
                        critchance = 50;
                    }
                }
                if(EntityNaturalSpawn.doRandomChance(10)){
                    if(getDay() >= 21){
                       PlayerData.addDataEffect(p,"bleed",40,0);
                    }else{
                       PlayerData.addDataEffect(p,"bleed",20,0);
                    }
                }
                if(getDay() >= 21){
                    if(EntityNaturalSpawn.doRandomChance(critchance)){
                        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,10.0F,1.0F);
                        p.getLocation().getWorld().spawnParticle(Particle.CRIT,p.getLocation(),50,1,1,1,0.5);
                        e.setDamage(e.getDamage() * 2);
                    }
                }
                if(Data.has(z,"reliczombie",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,80,0,true,false,true));
                }
            }
            if(damager instanceof Fireball f){
                if(Data.has(f,"entropicfireball",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,100,5,true,false,true));
                }
            }
            if(damager instanceof Skeleton s){
                if(Data.has(s,"primordialskeleton",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(10)){
                        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,10.0F,1.0F);
                        p.getLocation().getWorld().spawnParticle(Particle.CRIT,p.getLocation(),25,1,1,1,0.3);
                        e.setDamage(e.getDamage() * 2);
                    }
                }
            }
            if(damager instanceof IronGolem s){
                if(Data.has(s,"primordialgolem",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(30)){
                        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,10.0F,-1.0F);
                        p.getLocation().getWorld().spawnParticle(Particle.DAMAGE_INDICATOR,p.getLocation(),25,1,1,1,0.3);
                        e.setDamage(e.getDamage() * 3);
                    }
                }else{
                    if(getDay() >= 28){
                        if(EntityNaturalSpawn.doRandomChance(20)){
                            p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,10.0F,1.0F);
                            p.getLocation().getWorld().spawnParticle(Particle.CRIT,p.getLocation(),50,1,1,1,0.5);
                            e.setDamage(e.getDamage() * 2);
                        }
                    }
                }
            }
            if(damager instanceof Ravager r){
                if(Data.has(r,"primordialravager",PersistentDataType.STRING)){
                    if(EntityNaturalSpawn.doRandomChance(20)){
                        createSurge(p.getLocation(),10);
                    }
                }
            }
            if(damager instanceof Spider s){
                if(Data.has(s,"blackreaver",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,100,9));
                }
                if(Data.has(s,"neonspider",PersistentDataType.STRING)) {
                    if (getDay() >= 28) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 150, 2, true, false, true));
                    }else{
                        p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,150,0,true,false,true));
                    }
                }
                if(Data.has(s,"adeptmauler",PersistentDataType.STRING)){
                    s.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,60,2,false,false,false));
                }
                if(Data.has(s,"revenantspider",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,0,true,false,true));
                    Block block = p.getLocation().getBlock();
                    if(getValidBlocks(block) && block.getType() != Material.COBWEB){
                        block.getWorld().setType(block.getLocation(),Material.COBWEB);
                    }
                }
                if(Data.has(s,"primordialspider",PersistentDataType.STRING)){
                    Random random = new Random();
                    int chance = random.nextInt(4);
                    switch (chance){
                        case 0 ->p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,150,0,true,false,true));
                        case 1 ->p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,150,0,true,false,true));
                        case 2 ->p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,150,0,true,false,true));
                        case 3 ->p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,150,0,true,false,true));
                    }
                }
                if(Data.has(s,"zenithspider",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,100,9));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,0,true,false,true));
                    s.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,60,2,false,false,false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 150, 2, true, false, true));
                    Block block = p.getLocation().getBlock();
                    if(getValidBlocks(block) && block.getType() != Material.COBWEB){
                        block.getWorld().setType(block.getLocation(),Material.COBWEB);
                    }
                    if(EntityNaturalSpawn.doRandomChance(10)){
                        p.playEffect(EntityEffect.VILLAGER_ANGRY);
                        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_PLAYER_BURP,10.0F,-1.0F);
                        clearBuffs(p);
                    }
                }
                if(Data.has(s,"termite",PersistentDataType.STRING)){
                    var state = Data.get(s,"t_state",PersistentDataType.INTEGER);
                    if(state == 0){
                        if(getDay() >= 28){
                            s.getLocation().getWorld().playSound(s.getLocation(),Sound.ENTITY_CREEPER_PRIMED,10.0F,2.0F);
                            Data.set(s,"t_state",PersistentDataType.INTEGER,1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if(s.isDead() || !s.isValid()){cancel();return;}
                                    s.getLocation().getWorld().createExplosion(s,6,false,true);
                                    s.remove();
                                }
                            }.runTaskLater(TLL3.getInstance(),30L);
                        }else{
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
                }
                if(Data.has(s,"termite_ex",PersistentDataType.STRING)){
                    var state = Data.get(s,"tex_state",PersistentDataType.INTEGER);
                    if(state == 0){
                        if(getDay() >= 28){
                            Data.set(s,"tex_state",PersistentDataType.INTEGER,1);
                            s.getLocation().getWorld().playSound(s.getLocation(),Sound.ENTITY_CREEPER_PRIMED,10.0F,2.0F);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if(s.isDead() || !s.isValid()){cancel();return;}
                                    s.getLocation().getWorld().createExplosion(s,9,false,true);
                                    s.remove();
                                }
                            }.runTaskLater(TLL3.getInstance(),30L);
                        }else{
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
                if(Data.has(s,"primordialcave",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,0,true,false,true));
                }
            }
        }
    }

    @EventHandler
    public void takehitE(EntityDamageEvent e){
        var entity = e.getEntity();
        var reason = e.getCause();
        if(entity instanceof LivingEntity l){
            if(l.hasPotionEffect(PotionEffectType.LUCK))return;
        }
        if(entity instanceof Slime s){
            if(reason == EntityDamageEvent.DamageCause.ENTITY_ATTACK || reason == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK || reason == EntityDamageEvent.DamageCause.PROJECTILE){
                if(Data.has(s,"primordialslime",PersistentDataType.STRING)){
                    s.getLocation().createExplosion(3,true,true);
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
        if(entity.hasPotionEffect(PotionEffectType.LUCK))return;

        if(entity instanceof Illusioner || entity instanceof CustomIllusioner){
            if (Data.has(entity, "illusionerex", PersistentDataType.STRING)) {
                int random = GenericUtils.getRandomValue(5);
                switch (random){
                    case 0 ->EntityHelper.setIdentifierString(projectile, "rev_explosion");
                    case 1 ->{
                        Fireball f = entity.launchProjectile(Fireball.class);
                        f.setYield(6);
                        e.setProjectile(f);
                    }
                    case 2 ->{
                        entity.getWorld().playSound(entity.getLocation(),Sound.ENTITY_WARDEN_SONIC_BOOM,5.0F,1.0F);
                        Arrow a = (Arrow) projectile;
                        if(getDay() >= 21){
                            a.setDamage(40);
                        }else{
                            a.setDamage(20);
                        }
                        for(Player online : Bukkit.getOnlinePlayers()){
                            online.hideEntity(TLL3.getInstance(),a);
                        }
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                a.setGravity(false);
                            }
                        }.runTaskLater(TLL3.getInstance(),2L);
                        new BukkitRunnable() {
                            int i = 0;
                            @Override
                            public void run() {
                                if(a.isOnGround() || !a.isValid() || a.isDead() ){a.remove(); cancel(); return;}
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
                    case 3 ->EntityHelper.setIdentifierString(projectile,"steeltnt");
                    case 4 ->{
                        EntityHelper.setIdentifierString(projectile,"lol");
                        new HomingTask(projectile).runTaskTimer(TLL3.getInstance(),10L,1L);
                    }
                }
            }
        }

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
            if(getDay() >= 42){
                if(Data.has(p,"lostscav",PersistentDataType.STRING)){
                    EntityHelper.setIdentifierString(projectile,"lol");
                    new HomingTask(projectile).runTaskTimer(TLL3.getInstance(),10L,1L);
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
            if(getDay() >= 42){
                if(projectile instanceof Arrow arrow) {
                    if (p.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                        int level = p.getPotionEffect(PotionEffectType.WEAKNESS).getAmplifier();
                        switch (level) {
                            case 0:
                                arrow.setDamage(arrow.getDamage() - 1.5);
                            case 1:
                                arrow.setDamage(arrow.getDamage() - 3);
                            case 2:
                                arrow.setDamage(arrow.getDamage() - 4.5);
                            case 3:
                                arrow.setDamage(arrow.getDamage() - 6);
                            case 4:
                                arrow.setDamage(arrow.getDamage() - 6.5);
                            case 5, 6, 7, 8, 9, 10:
                                arrow.setDamage(0);
                        }
                    }
                }
                if(projectile instanceof ThrownPotion t){
                    if (EntityNaturalSpawn.doRandomChance(20)) {
                        t.getEffects().clear();
                    }
                }
            }
            if(bow.hasItemMeta()){
            if(new ItemBuilder(bow).hasID("dread_bow")){
                EntityHelper.setIdentifierString(projectile,"dread");
            }
            }
        }



        if (entity instanceof Skeleton s) {
            if(Data.has(s,"zenithskeleton",PersistentDataType.STRING)){
                EntityHelper.setIdentifierString(projectile,"fefe");
                if(Math.random() < 0.2){
                    EntityHelper.setIdentifierString(projectile, "rev_explosion");
                }
                new HomingTask(projectile).runTaskTimer(TLL3.getInstance(),10L,1L);
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
                   a.setDamage(40);
                }else{
                   a.setDamage(20);
                }
                for(Player online : Bukkit.getOnlinePlayers()){
                    online.hideEntity(TLL3.getInstance(),a);
                }
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        a.setGravity(false);
                    }
                }.runTaskLater(TLL3.getInstance(),2L);
                new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        if(a.isOnGround() || !a.isValid() || a.isDead() ){a.remove(); cancel(); return;}
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
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        a.setGravity(false);
                    }
                }.runTaskLater(TLL3.getInstance(),2L);
                new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        if(a.isOnGround() || !a.isValid() || a.isDead() ){a.remove(); cancel(); return;}
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
                    f.setYield(5);
                }else {
                    f.setYield(3);
                }
                e.setProjectile(f);
            }
            if(Data.has(s,"razorback",PersistentDataType.STRING)){
                EntityHelper.setIdentifierString(projectile,"lol");
                new HomingTask(projectile).runTaskTimer(TLL3.getInstance(),10L,1L);
            }
            if(Data.has(s,"anticommander",PersistentDataType.STRING)){
                EntityHelper.setIdentifierString(projectile,"fefe");
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
            if(c.getTarget() == null && (((Data.has(c,"unstablecreeper",PersistentDataType.STRING) || Data.has(c,"vortex",PersistentDataType.STRING))) || entity instanceof Creeper && getDay() >= 35)){
                if(chance <= 5 && c.getVehicle() == null){
                    c.playEffect(EntityEffect.TELEPORT_ENDER);
                    c.getWorld().playSound(c.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,10.0F,1.0F);
                    EntityHelper.teleportEnderman(c,c.getLocation().getBlockX(),c.getLocation().getBlockY(),c.getLocation().getBlockZ(),c.getWorld(),64.0D);
                }
            }
        }
    }

    @EventHandler
    public void explodeE(EntityExplodeEvent e){
        var entity = e.getEntity();
        var loc = e.getLocation();
        if(entity instanceof LivingEntity l){
            if(l.hasPotionEffect(PotionEffectType.LUCK))return;
        }
        if(entity instanceof Creeper c){
            if(Data.has(c,"revenantcreeper",PersistentDataType.STRING)){
                if(getDay() >= 42){
                    loc.getNearbyPlayers(13).forEach(player -> {
                        player.setFireTicks(600);
                    });
                }else{
                    loc.getNearbyPlayers(8).forEach(player -> {
                        player.setFireTicks(200);
                    });
                }
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
        if(entity.hasPotionEffect(PotionEffectType.LUCK))return;
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
        if(entity instanceof MagmaCube n){
            if(getDay() >= 42 && Data.has(n,"toxiccrawler",PersistentDataType.STRING)){
                n.getWorld().createExplosion(n.getLocation(),7,true,true);
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

        if(shooter instanceof LivingEntity l){
            if(l.hasPotionEffect(PotionEffectType.LUCK))return;
        }

        if(shooter instanceof Drowned d){
            if(proj instanceof Trident t){
                if(Data.has(d,"abyssdrow",PersistentDataType.STRING)){
                    if(getDay() >= 28){
                        t.setDamage(t.getDamage() * 5);
                    }else{
                        t.setDamage(t.getDamage() * 3);
                    }
                    EntityHelper.setIdentifierString(t,"lolxd");
                }
            }
        }
        if(shooter instanceof Player p){
            if(proj instanceof EnderPearl enderPearl){
                if(Data.has(p,"curse", PersistentDataType.STRING)) {
                    if(EntityNaturalSpawn.doRandomChance(GenericUtils.getPanicRNGValue(p))) {
                        p.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND);
                        p.playSound(p.getLocation(),Sound.ITEM_SHIELD_BREAK,10.0F,-1.0F);
                        double randomX = (Math.random() * 2 - 1) * 2;
                        double randomY = (Math.random() * 2 - 1) * 2;
                        double randomZ = (Math.random() * 2 - 1) * 2;
                        Vector randomVelocity = new Vector(randomX, randomY, randomZ);
                        enderPearl.setVelocity(enderPearl.getVelocity().add(randomVelocity));
                    }
                }
                var item1 = p.getInventory().getItemInMainHand();
                var item2 = p.getInventory().getItemInOffHand();
                if(item1.hasItemMeta() || item2.hasItemMeta()){
                if(new ItemBuilder(item1).hasID("revenant_pearl") || new ItemBuilder(item2).hasID("revenant_pearl")){
                    EntityHelper.setIdentifierString(enderPearl,"rev_pearl");
                }
            }
            }
        }
        if(shooter instanceof Witch){
            if(proj instanceof ThrownPotion t){
                ItemStack r = new ItemStack(Material.SPLASH_POTION);
                PotionMeta b = (PotionMeta) r.getItemMeta();
                b.addCustomEffect(new PotionEffect(PotionEffectType.HARM,0,4),true);
                r.setItemMeta(b);
                t.setItem(r);
            }
        }

        if(shooter instanceof Ghast g){
            if(proj instanceof Fireball s){
                if(Data.has(g,"cata_ghast",PersistentDataType.STRING)){
                    s.setYield(4);
                }
                if(Data.has(g,"blightedghast",PersistentDataType.STRING)){
                    s.setYield(9);
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

        if(getDay() >= 7){
            if(source instanceof Enemy){
                if(hen != null){
                    if(hen instanceof Enemy){
                        e.setCancelled(true);
                    }
                }
            }
        }

        if(source instanceof Player p){
            if(proj instanceof EnderPearl enderPearl){
                if(getDay() >= 28) {
                    if (!Data.has(enderPearl, "worm_pearl", PersistentDataType.STRING)) {
                        if(EntityNaturalSpawn.doRandomChance(20)){
                            Entities.quanmite((Endermite) Entities.spawnMob(p.getLocation(),EntityType.ENDERMITE));
                        }
                    }
                }
                if(Data.has(enderPearl,"rev_pearl",PersistentDataType.STRING)){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100,0,true,false,true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,0,true,false,true));
                }
            }
        }

        if(source instanceof LivingEntity l){
            if(l.hasPotionEffect(PotionEffectType.LUCK))return;
        }
        if(hen instanceof Player p){
            if(Data.has(p,"invulnerable",PersistentDataType.STRING))return;
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
                if(hen instanceof Player){
                    a.remove();
                }
            }
            if(Data.has(a,"fefe",PersistentDataType.STRING)){
                if(hen instanceof Player p){
                    a.remove();
                    p.setCooldown(Material.ELYTRA,600); //30 seg de cooldown
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

        if(source instanceof Shulker s){
            if (Data.has(s, "eldritchmodule", PersistentDataType.STRING)) {
                if (hen != null) {
                    if(hen instanceof Player p){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,250,0,true,false,true));
                    }
                }
                if (hbl != null) {
                    hbl.getLocation().createExplosion(s,7,true,true);
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
            if(Data.has(s,"relicskeleton",PersistentDataType.STRING)){
                if(hen instanceof Player l){
                    l.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,140,0,true,false,true));
                }
            }
            if (Data.has(s, "blightedskeleton", PersistentDataType.STRING)) {
                if (hen != null) {
                    hen.getLocation().createExplosion(s,3,true,true);
                    hen.getWorld().strikeLightning(hen.getLocation());
                    proj.remove();
                }
                if (hbl != null) {
                    hbl.getLocation().createExplosion(s,3,true,true);
                    hbl.getWorld().strikeLightning(hbl.getLocation());
                    proj.remove();;
                }
            }
            if(Data.has(s,"primordialskeleton",PersistentDataType.STRING)){
                if(hen instanceof Player p){
                    if(proj instanceof Arrow r){
                        if(EntityNaturalSpawn.doRandomChance(10)){
                            p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,10.0F,1.0F);
                            p.getLocation().getWorld().spawnParticle(Particle.CRIT,p.getLocation(),50,1,1,1,0.5);
                            r.setDamage(r.getDamage() * 2);
                        }
                    }
                }
            }
        }

        if(source instanceof Shulker s){
            if (Data.has(s, "primordialshulker", PersistentDataType.STRING)) {
                if (hen != null) {
                    if(hen instanceof LivingEntity l){
                        l.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,200,0,true,false,true));
                    }
                }
            }
        }


        if(source instanceof WitherSkeleton s){
            if (Data.has(s, "primordialwither", PersistentDataType.STRING)) {
                if (hen != null) {
                    if(hen instanceof LivingEntity l){
                        l.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200,1,true,false,true));
                        if(EntityNaturalSpawn.doRandomChance(20)){
                            createSurge(l.getLocation(),20);
                        }
                    }
                }
                if (hbl != null) {
                    if(EntityNaturalSpawn.doRandomChance(20)){
                        createSurge(hbl.getLocation(),20);
                    }
                }
            }
        }



        if(source instanceof Blaze z){
            if(Data.has(z,"zenithblaze",PersistentDataType.STRING)){
                if (hen != null) {
                    hen.getWorld().strikeLightning(hen.getLocation());
                    hen.getLocation().createExplosion((Entity) source,3,true,true);
                    if (hen instanceof Player p) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0, true, false, true));
                    }
                }
                if (hbl != null) {
                    hbl.getWorld().strikeLightning(hbl.getLocation());
                    hbl.getLocation().createExplosion((Entity) source,3,true,true);
                }
            }
            if(Data.has(z,"windcharger",PersistentDataType.STRING)){
                if (hen != null) {
                    hen.getWorld().strikeLightning(hen.getLocation());
                }
                if (hbl != null) {
                    hbl.getWorld().strikeLightning(hbl.getLocation());
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
            if(Data.has(z,"blazephim",PersistentDataType.STRING)){
                if (hen != null) {
                    if(hen instanceof Player p){
                        p.damage(6 * p.getHealth(),z);
                    }
                }
            }
            if(Data.has(z,"symbiote",PersistentDataType.STRING)){
                if (hen != null) {
                    if(hen instanceof Player p){
                        EntityHelper.teleportEnderman(p, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getWorld(), 128.0D);
                    }
                }

            }
            if(Data.has(z,"primordialblaze",PersistentDataType.STRING)){
                if (hen != null) {
                    if(hen instanceof LivingEntity l){
                        l.damage(20,z);
                        l.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,200,0,true,false,true));
                    }
                }
                if (hbl != null) {
                    for(Player nearby : hbl.getLocation().getNearbyPlayers(5,5,5)) {
                        nearby.damage(20, z);
                        nearby.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,200,0,true,false,true));
                    }
                }
            }
        }
        if(source instanceof Breeze z){
            if(Data.has(z,"windtyphoon",PersistentDataType.STRING)){
                if (hen != null) {
                    if(hen instanceof LivingEntity l){
                        l.damage(20,z);
                    }
                }
            }
            if(Data.has(z,"deathbringer",PersistentDataType.STRING)){
                if (hen != null) {
                    if(hen instanceof LivingEntity l){
                        l.damage(40,z);
                        l.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,2,true,false,true));
                        l.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,100,4,true,false,true));
                    }
                }
            }
        }
        if(source instanceof Pillager p){
            if(Data.has(p,"starredpillager",PersistentDataType.STRING)){
                if (hen != null) {
                    if(hen instanceof Player ene){
                        Vector direction = p.getLocation().toVector().subtract(ene.getLocation().toVector()).normalize();
                        ene.setVelocity(direction.multiply(2));
                    }
                }
            }
        }
    }




    @EventHandler
    public void targetE(EntityTargetLivingEntityEvent e){
        var target = e.getTarget();
        var origin = e.getEntity();

        if(getDay() >= 7){
            if(origin instanceof Enemy && (target instanceof Enemy || target instanceof Animals || target instanceof Fish)){
                e.setCancelled(true);
            }
            if(target instanceof IronGolem && origin instanceof Enemy)e.setCancelled(true);
            if(target instanceof Enemy && origin instanceof IronGolem)e.setCancelled(true);
        }

        if(origin instanceof LivingEntity l){
            if(l.hasPotionEffect(PotionEffectType.LUCK))return;
        }


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
           if(origin instanceof CaveSpider s){
               if(Data.has(s,"primordialcave",PersistentDataType.STRING)){
                   int g = Data.get(s,"burrowstate",PersistentDataType.INTEGER);
                   if(g == 0) {
                           new BukkitRunnable() {
                               @Override
                               public void run() {
                                   Data.set(s, "burrowstate", PersistentDataType.INTEGER, 1);
                                   burrowStart(s, (Player) target);
                               }
                           }.runTaskLater(TLL3.getInstance(), 100L);

                   }
               }
           }
            if(origin instanceof PiglinBrute s){
                if(Data.has(s,"primordialbrute",PersistentDataType.STRING)){
                    int g = Data.get(s,"burrowstate",PersistentDataType.INTEGER);
                    if(g == 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Data.set(s, "burrowstate", PersistentDataType.INTEGER, 1);
                                    burrowStart(s, (Player) target);
                                }
                            }.runTaskLater(TLL3.getInstance(), 100L);

                    }
                }
            }
            if(origin instanceof Zoglin s){
                if(Data.has(s,"primordialzoglin",PersistentDataType.STRING)){
                    int g = Data.get(s,"burrowstate",PersistentDataType.INTEGER);
                    if(g == 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Data.set(s, "burrowstate", PersistentDataType.INTEGER, 1);
                                    burrowStart(s, (Player) target);
                                }
                            }.runTaskLater(TLL3.getInstance(), 100L);

                    }
                }
            }
            if(origin instanceof Creeper s){
                if(Data.has(s,"doomsday",PersistentDataType.STRING)){
                    int g = Data.get(s,"burrowstate",PersistentDataType.INTEGER);
                    if(g == 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Data.set(s, "burrowstate", PersistentDataType.INTEGER, 1);
                                    burrowStart(s, (Player) target);
                                }
                            }.runTaskLater(TLL3.getInstance(), 100L);
                    }
                }
            }
            if(origin instanceof Breeze s){
                if(Data.has(s,"deathbringer",PersistentDataType.STRING)){
                    int g = Data.get(s,"burrowstate",PersistentDataType.INTEGER);
                    if(g == 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Data.set(s, "burrowstate", PersistentDataType.INTEGER, 1);
                                    burrowStart(s, (Player) target);
                                }
                            }.runTaskLater(TLL3.getInstance(), 100L);
                    }
                }
            }
            if(origin instanceof Skeleton s){
                if(Data.has(s,"minicyclone_zombie",PersistentDataType.STRING)){
                    int g = Data.get(s,"burrowstate",PersistentDataType.INTEGER);
                    if(g == 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Data.set(s, "burrowstate", PersistentDataType.INTEGER, 1);
                                    burrowStart(s, (Player) target);
                                }
                            }.runTaskLater(TLL3.getInstance(), 100L);
                    }
                }
            }if(origin instanceof Silverfish s){
                if(Data.has(s,"powerfish",PersistentDataType.STRING)){
                    int g = Data.get(s,"burrowstate",PersistentDataType.INTEGER);
                    if(g == 0) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Data.set(s, "burrowstate", PersistentDataType.INTEGER, 1);
                                burrowStart(s, (Player) target);
                            }
                        }.runTaskLater(TLL3.getInstance(), 100L);
                    }
                }
            }
        }
    }

    @EventHandler
    public void wardenTargetAB(WardenAngerChangeEvent e){
        var entity = e.getEntity();
        var target = e.getTarget();
        if(getDay() >= 7){
            if(target instanceof Creature && e.getNewAnger() >= 0){
                e.setNewAnger(0);
                e.setCancelled(true);
            }
        }
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
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&c&lHas dejado caer un item de tu mano principal!"));
            p.getInventory().setItem(EquipmentSlot.HAND,null);
        }else{
            if(off == null && off.getType() == Material.AIR)return;
            var clone = off.clone();
            p.getWorld().dropItemNaturally(p.getLocation(),clone);
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&c&lHas dejado caer un item de tu mano secundaria!"));
            p.getInventory().setItem(EquipmentSlot.OFF_HAND,null);
        }
    }


    public boolean getValidBlocks(Block block){
        switch (block.getType()){
            case LAVA,WATER,OBSIDIAN,BEDROCK,CHEST,FURNACE,BLAST_FURNACE,SMOKER,ENDER_CHEST,END_PORTAL_FRAME,BREWING_STAND,CHISELED_BOOKSHELF,TRAPPED_CHEST,DISPENSER,LECTERN,DROPPER: return false;
            default: return true;
        }
    }

    public void burrowStart(LivingEntity l,Player target){
        BukkitRunnable runnable = new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                @NotNull Location cloned_location = target.getLocation().clone();
                if(l.isDead() || !l.isValid())cancel();
                if(i < 50){
                    i++;
                    if(l.hasAI()) l.setAI(false);
                    if(!l.isSilent()) l.setSilent(true);
                    if(l.hasGravity()) l.setGravity(false);
                    if(!l.isInvulnerable()) l.setInvulnerable(true);
                    if(!l.isCollidable()) l.setCollidable(false);
                    l.teleport(l.getLocation().subtract(0,0.1,0));
                    l.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,l.getLocation(),10,1,1,1,1, Material.DIRT.createBlockData());
                }else{
                    l.setInvisible(true);
                    burrowMove(l,cloned_location);
                    cancel();
                }
            }
        };
        runnable.runTaskTimer(TLL3.getInstance(),0L,1L);
    }
    public void burrowMove(LivingEntity l, Location loc){
        BukkitRunnable runnable = new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if(l.isDead() || !l.isValid())cancel();
                if(i < 100){
                    Vector dir = loc.toVector().subtract(l.getLocation().toVector()).normalize();
                    Vector dirXZ = new Vector(dir.getX(), 0, dir.getZ()).normalize();
                    Location movement = l.getLocation().add(dirXZ.multiply(2));
                    l.teleport(movement);
                    i++;
                }else{
                    burrowPopout(l,loc);
                    cancel();
                }
            }
        };
        runnable.runTaskTimer(TLL3.getInstance(),0L,1L);
    }
    public void burrowPopout(LivingEntity l,Location loc){
        BukkitRunnable runnable = new BukkitRunnable() {
            int targetY = (int) loc.getY();
            int i = 0;
            @Override
            public void run() {
                l.setInvisible(false);
                double currentY = l.getLocation().getY();
                if (currentY < targetY) {
                    i++;
                    l.teleport(l.getLocation().add(0, 0.1, 0));
                    l.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,l.getLocation(),10,1,1,1,1, Material.DIRT.createBlockData());
                } else if (currentY > targetY) {
                    i++;
                    l.teleport(l.getLocation().subtract(0, 0.1, 0));
                    l.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,l.getLocation(),10,1,1,1,1, Material.DIRT.createBlockData());
                }
                if (Math.abs(currentY - targetY) < 0.1 || i >= 700) { //the "i" is a fail-safe, so if a mob gets stuck in the middle of the code above it will unstuck himself
                    l.setAI(true);
                    l.setGravity(true);
                    l.setSilent(false);
                    l.setInvulnerable(false);
                    l.setCollidable(true);
                    cancel();
                }
            }
        };
        runnable.runTaskTimer(TLL3.getInstance(),0L,1L);
    }
    private void createSurge(Location p,int damage){
        Random random = new Random();
        var thing = (random.nextDouble() * 2 - 1) * 5;
        double rXP = p.getX() + thing;
        double rZP = p.getZ() + thing;
        double ye = p.getY();
        Location surgeloc = new Location(p.getWorld(),rXP,ye,rZP);
        surgeloc.getWorld().playSound(surgeloc,Sound.ENTITY_BLAZE_SHOOT,10.0F,2.0F);
        ArmorStand ar = (ArmorStand) Entities.spawnMob(surgeloc,EntityType.ARMOR_STAND);
        ar.setCustomNameVisible(false);
        ar.setCustomName(ChatUtils.format("&b&lSurge"));
        ar.setInvulnerable(true);
        ar.setCollidable(false);
        ar.setInvisible(true);
        ar.setBasePlate(false);
        ar.setVisualFire(true);
        ar.setGravity(false);
        ar.setAI(false);
        ar.setSilent(true);
        BukkitRunnable br1 = new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if(ar.isDead() || !ar.isValid()){cancel();return;}
                if(i < 90){
                    XParticle.circle(2, 20, ParticleDisplay.display(ar.getLocation(), Particle.SOUL_FIRE_FLAME));
                    i++;
                }else{
                    ar.remove();
                    surgeloc.getWorld().playSound(surgeloc,Sound.ENTITY_BLAZE_SHOOT,10.0F,-2.0F);
                    cancel();
                }
            }
        };
        br1.runTaskTimer(TLL3.getInstance(),0L,1L);
        BukkitRunnable br2 = new BukkitRunnable() {
            @Override
            public void run() {
                if(ar.isDead() || !ar.isValid()){cancel();return;}
                ar.getWorld().getNearbyEntities(ar.getLocation(), 2, 2, 2).stream()
                        .filter(entitys -> entitys instanceof LivingEntity)
                        .map(entitys -> (LivingEntity) entitys)
                        .forEach(around -> {
                            if (around instanceof Player) {
                                ((Player) around).damage(damage,ar);
                            }
                        });
            }
        };
        br2.runTaskTimer(TLL3.getInstance(),0L,5L);
    }
    private static void createBarrierEffect(Location loc){
        XParticle.circle(2, 3, 1, 30, 0, ParticleDisplay.display(loc.add(0,2.5,0),Particle.END_ROD));
        XParticle.circle(2, 3, 1, 30, 0, ParticleDisplay.display(loc.add(0,2,0),Particle.END_ROD));
        XParticle.circle(2, 3, 1, 30, 0, ParticleDisplay.display(loc.add(0,1.5,0),Particle.END_ROD));
        XParticle.circle(2, 3, 1, 30, 0, ParticleDisplay.display(loc.add(0,1,0),Particle.END_ROD));
        XParticle.circle(2, 3, 1, 30, 0, ParticleDisplay.display(loc.add(0,0.5,0),Particle.END_ROD));
        XParticle.circle(2, 3, 1, 30, 0, ParticleDisplay.display(loc.clone(),Particle.END_ROD));
    }

    private static void clearBuffs(Player p){
        for (PotionEffect effect : p.getActivePotionEffects()){
            if(effect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE))p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            if(effect.getType().equals(PotionEffectType.INCREASE_DAMAGE))p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            if(effect.getType().equals(PotionEffectType.SPEED))p.removePotionEffect(PotionEffectType.SPEED);
            if(effect.getType().equals(PotionEffectType.ABSORPTION))p.removePotionEffect(PotionEffectType.ABSORPTION);
            if(effect.getType().equals(PotionEffectType.REGENERATION))p.removePotionEffect(PotionEffectType.REGENERATION);
            if(effect.getType().equals(PotionEffectType.FIRE_RESISTANCE))p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            if(effect.getType().equals(PotionEffectType.SLOW_FALLING))p.removePotionEffect(PotionEffectType.SLOW_FALLING);
            if(effect.getType().equals(PotionEffectType.FAST_DIGGING))p.removePotionEffect(PotionEffectType.FAST_DIGGING);
            if(effect.getType().equals(PotionEffectType.HEALTH_BOOST))p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            if(effect.getType().equals(PotionEffectType.SATURATION))p.removePotionEffect(PotionEffectType.SATURATION);
            if(effect.getType().equals(PotionEffectType.INVISIBILITY))p.removePotionEffect(PotionEffectType.INVISIBILITY);
            if(effect.getType().equals(PotionEffectType.JUMP))p.removePotionEffect(PotionEffectType.JUMP);
            if(effect.getType().equals(PotionEffectType.NIGHT_VISION))p.removePotionEffect(PotionEffectType.NIGHT_VISION);

        }
    }
}
