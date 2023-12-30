package com.tll3.Listeners;

import com.tll3.Lists.CustomEntities.CustomChicken;
import com.tll3.Lists.CustomEntities.CustomCreeper;
import com.tll3.Lists.CustomEntities.CustomIronGolem;
import com.tll3.Lists.Entities;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
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
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.persistence.PersistentDataType;
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
        }

        if(entity instanceof Blaze && reason == EntityDamageEvent.DamageCause.DROWNING){
            e.setCancelled(true);
        }

        if(entity instanceof Zombie z){
            if(Data.has(z,"zninja",PersistentDataType.STRING) && reason == EntityDamageEvent.DamageCause.FALL)e.setCancelled(true);
        }

        if(entity instanceof Enemy && reason == EntityDamageEvent.DamageCause.MAGIC)e.setCancelled(true);

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


        if(entity instanceof Enemy || entity instanceof IronGolem){
          if(e instanceof EntityDamageByEntityEvent event){
              if (event.getDamager() instanceof Enemy) {
                  event.setCancelled(true);
              }
          }
        }
    }


    @EventHandler
    public void chunkthing(ChunkLoadEvent e){

            for (LivingEntity liv : Arrays.stream(e.getChunk().getEntities()).filter(entity -> entity instanceof LivingEntity).map(LivingEntity.class::cast).collect(Collectors.toList())) {
                if(liv instanceof CustomCreeper || liv instanceof CustomIronGolem
                || liv instanceof CustomChicken)return;
                Location loc = liv.getLocation();
                if(getDay() >= 5){
                    if(liv instanceof IronGolem i){
                        Entities.enrIG(i);
                    }
                    if(liv instanceof Mule m){
                        m.remove();
                        SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(liv.getLocation(), EntityType.SKELETON_HORSE);
                        h.setTrapped(true);
                    }
                    if(liv instanceof Chicken c){
                        c.remove();
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomChicken customChicken = new CustomChicken(worldServer);
                        customChicken.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(customChicken, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }

                if(getDay() >= 15) {
                if (liv instanceof Pig p) {
                    p.remove();
                    PiglinBrute pg = (PiglinBrute) Entities.spawnMob(p.getLocation(), EntityType.PIGLIN_BRUTE);
                    pg.setImmuneToZombification(true);
                }
                if (liv instanceof Cow w) {
                    w.remove();
                    Ravager r = (Ravager) Entities.spawnMob(w.getLocation(), EntityType.RAVAGER);
                    r.setCanJoinRaid(false);
                }
                if (liv instanceof Sheep s) {
                    s.remove();
                    Blaze c = (Blaze) Entities.spawnMob(s.getLocation(), EntityType.BLAZE);
                }
                if (liv instanceof Horse || liv instanceof Donkey) {
                    liv.remove();
                    SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(liv.getLocation(), EntityType.SKELETON_HORSE);
                    h.setTrapped(true);
                }
            }
        }
    }


}
