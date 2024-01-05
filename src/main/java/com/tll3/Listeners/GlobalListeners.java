package com.tll3.Listeners;

import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.Entities;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.ItemBuilder;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.projectile.WindCharge;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.tll3.Misc.GenericUtils.*;
public class GlobalListeners implements Listener {

    @EventHandler
    public void damageE(EntityDamageEvent e){
        var entity = e.getEntity();
        var reason = e.getCause();
        if(entity instanceof Player p){
            if(Data.has(p,"invulnerable", PersistentDataType.STRING)){
                e.setCancelled(true);
            }
            if(p.getGameMode() == GameMode.SPECTATOR && reason == EntityDamageEvent.DamageCause.VOID)e.setCancelled(true);
            if(getDay() >= 5){
                switch (reason){
                    case DROWNING -> e.setDamage(e.getDamage() * 2);
                    case LIGHTNING -> {
                        if(getMonsoon_active().equalsIgnoreCase("true")){
                            e.setDamage(e.getDamage() * 3);
                        }
                    }
                }
            }
        }

        if(entity instanceof Creature enemy){
            if(enemy.hasPotionEffect(PotionEffectType.BLINDNESS)){
                switch (reason){
                    case FIRE,FIRE_TICK,LAVA,HOT_FLOOR -> e.setDamage(e.getDamage() * 3);
                }
            }
        }


        if(getDay() >= 5){
            if(entity instanceof Enemy && reason == EntityDamageEvent.DamageCause.MAGIC)e.setCancelled(true);
            if(entity instanceof Enemy || entity instanceof IronGolem){
                if(e instanceof EntityDamageByEntityEvent event){
                    if (event.getDamager() instanceof Enemy) {
                        event.setCancelled(true);
                    }
                }
            }
        }



        //Basic
        if(entity instanceof Zombie z){
            if(Data.has(z,"zninja",PersistentDataType.STRING) && reason == EntityDamageEvent.DamageCause.FALL)e.setCancelled(true);
        }
        if(entity instanceof Skeleton s){
            if(Data.has(s,"firemancer",PersistentDataType.STRING) && (reason == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || reason == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))e.setCancelled(true);
        }
        if(Data.has(entity,"revenant_class",PersistentDataType.STRING)){
            switch (reason){
                case FALL,FALLING_BLOCK,SUFFOCATION,DROWNING,LAVA,THORNS,CONTACT,HOT_FLOOR -> e.setCancelled(true);
            }
        }




        //Wasteyard
        if(entity.getLocation().getWorld().getName().equalsIgnoreCase("world_wasteyard")){
            if(entity instanceof Enemy){
            if(reason == EntityDamageEvent.DamageCause.WITHER || reason == EntityDamageEvent.DamageCause.HOT_FLOOR || reason == EntityDamageEvent.DamageCause.LAVA || reason == EntityDamageEvent.DamageCause.FIRE || reason == EntityDamageEvent.DamageCause.FIRE_TICK)e.setCancelled(true);
            }
            if(entity instanceof Player){
                switch (reason){
                    case LAVA,HOT_FLOOR -> e.setDamage(e.getDamage() * 5);
                    case FIRE,FIRE_TICK -> e.setDamage(e.getDamage() * 3);
                }
            }
        }
    }

    @EventHandler
    public void blockplaceE(BlockPlaceEvent e){
        var item = e.getItemInHand();
        if(item != null){
            if(new ItemBuilder(item).hasID("unplaceable")){
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void changeporE(EntityPortalEnterEvent e){
        var entity = e.getEntity();
        if(entity instanceof Player p){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,100,0,false,false,false));
            }
        }
    }

    @EventHandler
    public void chunkthing(ChunkLoadEvent e){
            for (LivingEntity liv : Arrays.stream(e.getChunk().getEntities()).filter(entity -> entity instanceof LivingEntity).map(LivingEntity.class::cast).collect(Collectors.toList())) {
                if (liv instanceof CustomCreeper || liv instanceof CustomIronGolem
                        || liv instanceof CustomChicken || liv instanceof CustomFox
                || liv instanceof CustomPanda || liv instanceof CustomPolarBear || liv instanceof CustomSniffer
                || liv instanceof CustomMooshroom || liv instanceof CustomAxolotls) return;
                Location loc = liv.getLocation();
                if (getDay() >= 5) {
                    switch (liv.getType()){
                        case IRON_GOLEM -> Entities.enrIG((IronGolem) liv);
                        case MULE -> {
                            liv.remove();
                            SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(loc, EntityType.SKELETON_HORSE);
                            h.setTrapped(true);
                        }
                        case CHICKEN -> {
                           liv.remove();
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            CustomChicken customChicken = new CustomChicken(worldServer);
                            customChicken.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(customChicken, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case FOX -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomFox f = new CustomFox(worldServer);
                            f.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case AXOLOTL -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomAxolotls f = new CustomAxolotls(worldServer);
                            f.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case PANDA -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomPanda f = new CustomPanda(worldServer);
                            f.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case POLAR_BEAR -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomPolarBear f = new CustomPolarBear(worldServer);
                            f.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case SNIFFER -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomSniffer f = new CustomSniffer(worldServer);
                            f.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case MUSHROOM_COW -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomMooshroom f = new CustomMooshroom(worldServer);
                            f.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case ZOMBIFIED_PIGLIN -> Entities.enrPig((PigZombie) liv);
                    }

                }
        }
    }
}
