package com.tll3.Listeners;

import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.Entities;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.setter.BiomeSetter;
import net.minecraft.server.level.WorldServer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static com.tll3.Listeners.EntityNaturalSpawn.doRandomChance;
import static com.tll3.Misc.EntityHelper.*;
import static com.tll3.Misc.EntityHelper.setBoots;
import static com.tll3.Misc.GenericUtils.*;
public class GlobalListeners implements Listener {

    private static NamespacedKey getBiome(Location loc){
        return Bukkit.getUnsafe().getBiomeKey(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    private static void updateBiomeName(ChunkLoadEvent e, String key){
        Chunk chunk = e.getChunk();
        World w = e.getChunk().getWorld();
        Location loc = e.getChunk().getBlock(0,0,0).getLocation();
        String biomeKey = getBiome(loc).toString();

        if(!biomeKey.equals("afterlife:" + key)){
            System.out.println("Actualizando bioma, info:");
            System.out.println("Biome key: " + biomeKey);
            System.out.println("World: " + w.getName());
            System.out.println("Loc: " + loc);
            //CustomBiome biome1 = BiomeHandler.getBiome(new BiomeResourceKey("afterlife", "primeval_woods"));
            CustomBiome biome = BiomeHandler.getBiome(new BiomeResourceKey("afterlife", key));
            if (biome == null) return;
            BiomeSetter.of().setChunkBiome(chunk,biome,true);
        }

    }

    @EventHandler
    public void loadBiome(ChunkLoadEvent e){
        switch (e.getChunk().getWorld().getName()){
            case "world_primeval" -> updateBiomeName(e, "primeval_woods");
            case "world_dunes" -> updateBiomeName(e, "savage_dunes");
            case "world_plateau" -> updateBiomeName(e, "scorched_plateau");
        }
    }

    @EventHandler
    public void explosionE(ExplosionPrimeEvent e){
        if(e.getEntity() instanceof Wither){
            if(Data.has(e.getEntity(),"ashenwither",PersistentDataType.STRING)){
                e.setFire(true);
                e.setRadius(12.0F);
            }
        }
        if(e.getEntity() instanceof WitherSkull s){
            if(s.getShooter() instanceof Wither w){
                if(Data.has(w,"ashenwither",PersistentDataType.STRING)){
                    if(s.isCharged())e.setRadius(4);
                    else e.setRadius(2);
                }
            }
        }
    }

    public static boolean shieldBreakChance(){
        Random random = new Random();
        int chancemax = random.nextInt(100);
        if(getDay() >= 28 && getDay() < 35){
            return chancemax <= 5;
        }else if(getDay() >= 35 && getDay() < 42){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                return chancemax <= 15;
            }else{
                return chancemax <= 7;
            }
        }else if(getDay() >= 42){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                return true;
            }else{
                return chancemax <= 20;
            }
        }
        return chancemax <= 5;
    }


    @EventHandler
    public void damageE(EntityDamageEvent e){
        var entity = e.getEntity();
        var reason = e.getCause();
        if(entity instanceof Player p){
            if(Data.has(p,"invulnerable", PersistentDataType.STRING)){
                e.setCancelled(true);
            }
            if(getDay() >= 28){
                if(p.isBlocking()) {
                    if (shieldBreakChance()) {
                        if (reason == EntityDamageEvent.DamageCause.ENTITY_ATTACK || reason == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || reason == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || reason == EntityDamageEvent.DamageCause.PROJECTILE || reason == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)
                            if (p.getEquipment().getItemInMainHand().getType().equals(Material.SHIELD)) {
                                ItemStack shield = p.getEquipment().getItemInMainHand().clone();
                                p.getEquipment().getItemInMainHand().setAmount(0);
                                p.getEquipment().setItemInMainHand(shield);

                            } else if (p.getEquipment().getItemInOffHand().getType().equals(Material.SHIELD)) {
                                ItemStack shield = p.getEquipment().getItemInOffHand().clone();
                                p.getEquipment().getItemInOffHand().setAmount(0);
                                p.getEquipment().setItemInOffHand(shield);
                            }
                        p.setCooldown(Material.SHIELD, 100);
                    }
                }
            }
            if(p.getGameMode() == GameMode.SPECTATOR && reason == EntityDamageEvent.DamageCause.VOID)e.setCancelled(true);
            if(getDay() >= 7){
                switch (reason){
                    case DROWNING -> {
                        if(getDay() >= 14 && getDay() < 28) {
                            e.setDamage(e.getDamage() * 4);
                        }else if(getDay() >= 28){
                            e.setDamage(e.getDamage() * 6);
                        }else{
                            e.setDamage(e.getDamage() * 2);
                        }
                    }
                    case LIGHTNING -> {
                        if (getDay() >= 35) {
                            e.setDamage(e.getDamage() * 6);
                        }else{
                            if(getMonsoon_active().equalsIgnoreCase("true")){
                                e.setDamage(e.getDamage() * 3);
                            }
                        }
                    }
                }
            }
            if(getDay() >= 14 && getDay() < 35){
                switch (reason){
                    case STARVATION,FREEZE,SUFFOCATION -> e.setDamage(e.getDamage() * 7);
                }
            }
            if(getDay() >= 35){
                switch (reason){
                    case STARVATION,FREEZE,SUFFOCATION -> e.setDamage(e.getDamage() * 12);
                }
            }
            if(getDay() >= 21 && !p.getWorld().getName().equalsIgnoreCase("world_wasteyard")){
                switch (reason){
                    case LAVA: {
                        if(getDay() >= 42 && getMonsoon_active().equalsIgnoreCase("true")){
                            e.setDamage(e.getDamage() * 99);
                        }else{
                            e.setDamage(e.getDamage() * 1.2);
                        }
                    }
                    case HOT_FLOOR: {
                        if(getDay() >= 42){
                            e.setDamage(e.getDamage() * 99);
                        }else{
                            e.setDamage(e.getDamage() * 10);
                        }
                    }
                    case FALL: {
                        if(getDay() >= 42){
                            e.setDamage(e.getDamage() * 3);
                        }else{
                            e.setDamage(e.getDamage() * 2);
                        }
                    }
                }
            }
        }


        if(entity instanceof Creature enemy){
            if(enemy instanceof Player)return; //Por alguna razón, esto se aplicaba en los jugadores, puse esto solo para evitar inconvenientes
            if(enemy.hasPotionEffect(PotionEffectType.BLINDNESS)){
                switch (reason){
                    case FIRE,FIRE_TICK,LAVA,HOT_FLOOR -> e.setDamage(e.getDamage() * 5);
                }
            }
        }


        if(getDay() >= 7){
            if(entity instanceof Enemy && reason == EntityDamageEvent.DamageCause.MAGIC)e.setCancelled(true);
            if(entity instanceof Enemy || entity instanceof IronGolem){
                if(e instanceof EntityDamageByEntityEvent event){
                    if (event.getDamager() instanceof Enemy) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(getDay() >= 14){
            if(entity instanceof Enemy && getMonsoon_active().equalsIgnoreCase("true")){
                switch (reason){
                    case FALL,LIGHTNING -> e.setCancelled(true);
                }
            }
            if(entity instanceof Blaze){
                switch (reason){
                    case DROWNING,SUFFOCATION -> e.setCancelled(true);
                }
            }
        }
        if(getDay() >= 21){
            if(entity instanceof Enemy && entity.getWorld().getEnvironment() == World.Environment.NETHER){
                switch (reason){
                    case FIRE,FIRE_TICK,LAVA,HOT_FLOOR,WITHER,SUFFOCATION: e.setCancelled(true);
                }
            }
            if(entity instanceof Enemy){
                switch (reason){
                    case FALL -> e.setCancelled(true);
                }
            }
        }
        if(getDay() >= 42){
            if(entity instanceof Enemy){
                switch (reason){
                    case ENTITY_EXPLOSION,BLOCK_EXPLOSION -> e.setCancelled(true);
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
        if(entity instanceof Pillager p){
            if(Data.has(p,"pillagerex",PersistentDataType.STRING) && (reason == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || reason == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))e.setCancelled(true);
        }
        if(entity instanceof Slime p){
            if(Data.has(p,"primordialslime",PersistentDataType.STRING) && (reason == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || reason == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))e.setCancelled(true);
        }
        if(Data.has(entity,"revenant_class",PersistentDataType.STRING)){
            switch (reason){
                case FALL,FALLING_BLOCK,SUFFOCATION,DROWNING,LAVA,THORNS,CONTACT,HOT_FLOOR,MAGIC,SONIC_BOOM,POISON,WITHER,FIRE,FIRE_TICK,FREEZE -> e.setCancelled(true);
            }
        }
        if(Data.has(entity,"behemoth",PersistentDataType.STRING)){
            switch (reason){
                case FALL,FALLING_BLOCK,SUFFOCATION,DROWNING,LAVA,THORNS,CONTACT,HOT_FLOOR -> e.setCancelled(true);
            }
        }
        if(Data.has(entity,"relicmob",PersistentDataType.STRING) || Data.has(entity,"zenith",PersistentDataType.STRING) || Data.has(entity,"boss",PersistentDataType.STRING)){
            switch (reason){
                case FALL,FALLING_BLOCK,SUFFOCATION,DROWNING,LAVA,THORNS,CONTACT,HOT_FLOOR,MAGIC,SONIC_BOOM,POISON,WITHER,FIRE,FIRE_TICK,FREEZE,ENTITY_EXPLOSION,BLOCK_EXPLOSION,LIGHTNING,CRAMMING -> e.setCancelled(true);
            }
        }
        if(getDay() >= 35) {
            if (entity instanceof Llama || entity instanceof Goat) {
                switch (reason) {
                    case FALL, FALLING_BLOCK, SUFFOCATION, DROWNING, LAVA, THORNS, CONTACT, HOT_FLOOR, MAGIC, SONIC_BOOM, POISON, WITHER, FIRE, FIRE_TICK, FREEZE, ENTITY_EXPLOSION, BLOCK_EXPLOSION, LIGHTNING ->
                            e.setCancelled(true);
                }
            }
        }

        if(((Data.has(entity,"unstablecreeper",PersistentDataType.STRING) || Data.has(entity,"vortex",PersistentDataType.STRING))) || entity instanceof Creeper && getDay() >= 35){
            if(entity.getVehicle() == null) {
                if (reason == EntityDamageEvent.DamageCause.PROJECTILE) {
                    e.setCancelled(true);
                    entity.playEffect(EntityEffect.TELEPORT_ENDER);
                    entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10.0F, 1.0F);
                    EntityHelper.teleportEnderman(entity, entity.getLocation().getBlockX(), entity.getLocation().getBlockY(), entity.getLocation().getBlockZ(), entity.getWorld(), 64.0D);
                } else {
                    if (doRandomChance(3)) {
                        entity.playEffect(EntityEffect.TELEPORT_ENDER);
                        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10.0F, 1.0F);
                        EntityHelper.teleportEnderman(entity, entity.getLocation().getBlockX(), entity.getLocation().getBlockY(), entity.getLocation().getBlockZ(), entity.getWorld(), 64.0D);
                    }
                }
            }
        }
        if(getDay() >= 42){
            if(entity instanceof Ghast g){
                if(Data.has(g,"soulvag",PersistentDataType.STRING)){
                    if(reason == EntityDamageEvent.DamageCause.PROJECTILE)e.setCancelled(true);
                }
            }
        }




        //Wasteyard
        if(entity.getLocation().getWorld().getName().equalsIgnoreCase("world_wasteyard")){
            if(entity instanceof Enemy){
            if(reason == EntityDamageEvent.DamageCause.WITHER || reason == EntityDamageEvent.DamageCause.HOT_FLOOR || reason == EntityDamageEvent.DamageCause.LAVA || reason == EntityDamageEvent.DamageCause.FIRE || reason == EntityDamageEvent.DamageCause.FIRE_TICK)e.setCancelled(true);
            }
            if(entity instanceof Player){
                switch (reason){
                    case LAVA,HOT_FLOOR -> e.setDamage(e.getDamage() * 8);
                    case FIRE,FIRE_TICK -> e.setDamage(e.getDamage() * 3);
                }
            }
        }
    }

    @EventHandler
    public void blockplaceE(BlockPlaceEvent e){
        var item = e.getItemInHand();
        var block = e.getBlock().getType();

        if(getDay() >= 28) {
            if(getMonsoon_active().contains("true")) {
                if (block.name().toLowerCase().contains("slab")) {
                    e.setCancelled(true);
                }
            }
        }
        if(getDay() >= 42){
            if(block.name().toLowerCase().contains("cobweb")){
                e.setCancelled(true);
            }
        }

        if(item != null){
            if(new ItemBuilder(item).hasID("unplaceable")){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void furnaceE(FurnaceSmeltEvent e){
        var item = e.getSource();
        var result = e.getResult();
        if(getDay() >= 21){
            if(result != null){
                switch (result.getType()){
                    case IRON_INGOT,GOLD_INGOT,NETHERITE_SCRAP ->{
                        ItemStack newr = result;
                        newr.setType(Material.AIR);
                        e.setResult(newr);
                    }
                }
            }
        }
    }

    @EventHandler
    public void effectE(EntityPotionEffectEvent e){
        var entity = e.getEntity();
        var potion = e.getNewEffect();
        if(entity instanceof LivingEntity l){
            if(Data.has(l,"zenith",PersistentDataType.STRING)) {
                if (potion != null) {
                    if (potion.getType() == PotionEffectType.SLOW || potion.getType() == PotionEffectType.WEAKNESS || potion.getType() == PotionEffectType.LUCK) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }


    @EventHandler
    public void blockbreakE(BlockBreakEvent e){
        var p = e.getPlayer();
        var block = e.getBlock().getType();
        var loc = e.getBlock().getLocation();
        var item = p.getInventory().getItemInMainHand();


        if(item != null){
            if(item.hasItemMeta()){
               if (GenericPlayerListeners.checkItemId(item,"vulcanpickaxe")){
                   if(item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))return;
                   if((getDay() >= 28 && getMonsoon_active().equalsIgnoreCase("true")) || getDay() >= 42)return;
                   switch (block){
                       case IRON_ORE,DEEPSLATE_IRON_ORE ->{
                           e.getBlock().getDrops().clear();
                           e.getBlock().getLocation().getWorld().dropItemNaturally(loc,new ItemStack(Material.IRON_INGOT));
                       }
                       case GOLD_ORE,DEEPSLATE_GOLD_ORE ->{
                           e.getBlock().getDrops().clear();
                           e.getBlock().getLocation().getWorld().dropItemNaturally(loc,new ItemStack(Material.GOLD_INGOT));
                       }
                       case COPPER_ORE,DEEPSLATE_COPPER_ORE ->{
                           e.getBlock().getDrops().clear();
                           e.getBlock().getLocation().getWorld().dropItemNaturally(loc,new ItemStack(Material.COPPER_INGOT));
                       }
                       case ANCIENT_DEBRIS ->{
                           e.getBlock().getDrops().clear();
                           e.getBlock().getLocation().getWorld().dropItemNaturally(loc,new ItemStack(Material.NETHERITE_SCRAP));
                       }
                   }
               }
            }

        }

        if(getDay() >= 14){
        if(block == Material.BEE_NEST){
            for(int i = 0;i < 6;i++){
                WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                CustomBee r= new CustomBee(worldServer);
                r.a_(loc.getX(),loc.getY(),loc.getZ());
                worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
            }
        }}
        if(getDay() >= 21){
            if(block.name().toLowerCase().contains("bamboo")){
                e.getBlock().getLocation().createExplosion(10,true,true);
            }
        }
        if(getDay() >= 28 || getDay() < 42){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                if(block.name().toLowerCase().contains("ore") || block == Material.ANCIENT_DEBRIS){
                    e.getBlock().getDrops().clear();
                    if(getDay() >= 35){
                        if(doRandomChance(20)){
                            Silverfish f = (Silverfish) Entities.spawnMob(e.getBlock().getLocation(), EntityType.SILVERFISH);
                            f.playEffect(EntityEffect.ENTITY_POOF);
                            Entities.silverday5(f);
                        }
                    }
                }
            }
        }else if(getDay() >= 42){
            if(block.name().toLowerCase().contains("ore") || block == Material.ANCIENT_DEBRIS){
                e.getBlock().getDrops().clear();
                Silverfish f = (Silverfish) Entities.spawnMob(e.getBlock().getLocation(), EntityType.SILVERFISH);
                f.playEffect(EntityEffect.ENTITY_POOF);
                Entities.silverday5(f);
            }
        }
    }

    @EventHandler
    public void tradeE(InventoryOpenEvent e){
        if(e.isCancelled())return;
        if(e.getInventory().getType() == InventoryType.ENDER_CHEST && GenericUtils.getTyphoonactive().equalsIgnoreCase("true")){
            e.setCancelled(true);
        }
        if(e.getInventory().getType() == InventoryType.MERCHANT && getMonsoon_active().equalsIgnoreCase("true")){
            if(getDay() >= 14) {
                if (e.getPlayer().getLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
                    e.setCancelled(true);
                }
            }
        }
        if(getDay() >= 28){
            if(e.getInventory().getType() == InventoryType.MERCHANT && getTyphoonactive().equalsIgnoreCase("true")){
                e.setCancelled(true);
            }
        }
        if(getDay() >= 42){
            if((e.getInventory().getType() == InventoryType.BARREL || e.getInventory().getType() == InventoryType.SHULKER_BOX) && getMonsoon_active().equalsIgnoreCase("true")){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void changeporE(EntityPortalEnterEvent e){
        var entity = e.getEntity();
        if(entity instanceof Player p){
            if(getDay() >= 7){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,100,0,false,false,false));
            }
            }
        }
    }
    @EventHandler
    public void cancelplaceE(EntityPlaceEvent e){
        if(getDay() >= 28){
            if(e.getEntityType().name().toLowerCase().contains("boat") || e.getEntityType().name().toLowerCase().contains("cart")){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void chunkthing(ChunkLoadEvent e){
            for (LivingEntity liv : Arrays.stream(e.getChunk().getEntities()).filter(entity -> entity instanceof LivingEntity).map(LivingEntity.class::cast).collect(Collectors.toList())) {
                if (liv instanceof CustomCreeper || liv instanceof CustomIronGolem
                        || liv instanceof CustomChicken || liv instanceof CustomFox
                || liv instanceof CustomPanda || liv instanceof CustomPolarBear || liv instanceof CustomSniffer
                || liv instanceof CustomMooshroom || liv instanceof CustomAxolotls
                || liv instanceof CustomDolphin || liv instanceof CustomBee || liv instanceof CustomLlama || liv instanceof CustomGoat
                || liv instanceof CustomPig || liv instanceof CustomParrot) return;
                Location loc = liv.getLocation();
                if (getDay() >= 7) {
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
                        case DOLPHIN -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomDolphin f = new CustomDolphin(worldServer);
                            f.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(f, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case ZOMBIFIED_PIGLIN -> Entities.enrPig((PigZombie) liv);
                    }
                }
                if(getDay() >= 14){
                    switch (liv.getType()){
                        case SQUID,GLOW_SQUID ->{
                            liv.remove();
                            PufferFish f = (PufferFish) Entities.spawnMob(loc,EntityType.PUFFERFISH);
                            Entities.acidFish(f);
                        }
                        case BEE -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomBee r= new CustomBee(worldServer);
                            r.a_(loc.getX(),loc.getY(),loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case DONKEY,HORSE -> {
                            liv.remove();
                            SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(loc, EntityType.SKELETON_HORSE);
                            h.setTrapped(true);
                        }
                        case WOLF,CAT -> {
                            liv.remove();
                            Entities.unstCr((Creeper) Entities.spawnMob(loc,EntityType.CREEPER));
                        }
                        case GOAT -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomGoat r= new CustomGoat(worldServer);
                            r.a_(loc.getX(),loc.getY(),loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case LLAMA -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                            CustomLlama r= new CustomLlama(worldServer);
                            r.a_(loc.getX(),loc.getY(),loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                    }
                }
                if(getDay() >= 21){
                    switch (liv.getType()){
                        case STRIDER -> {
                            liv.remove();
                            Ghast g = (Ghast) Entities.spawnMob(loc,EntityType.GHAST);
                            if(doRandomChance(35)){
                                Entities.entropicDem(g);
                            }else{
                                Entities.gPower(g);
                            }
                        }
                        case PIG -> {
                            liv.remove();
                            PiglinBrute pg = (PiglinBrute) Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                            EntityHelper.addPotionEffect(pg,PotionEffectType.INCREASE_DAMAGE,3);
                            EntityHelper.addPotionEffect(pg,PotionEffectType.SPEED,2);
                            pg.setImmuneToZombification(true);
                            setHead(pg,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setChestplate(pg,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setLeggings(pg,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setBoots(pg,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                        }
                        case ALLAY -> {
                            liv.remove();
                            Entities.nwVex((Vex) Entities.spawnMob(loc,EntityType.VEX));
                        }
                    }
                }
                if(getDay() >= 28){
                    switch (liv.getType()){
                        case FROG,TURTLE ->{
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            CustomAxolotls r = new CustomAxolotls(worldServer);
                            r.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case WANDERING_TRADER ->{
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            CustomEvoker r = new CustomEvoker(worldServer,false);
                            r.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case TRADER_LLAMA -> {
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            CustomLlama r = new CustomLlama(worldServer);
                            r.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case RABBIT -> {
                            Entities.rabbitKiller((Rabbit) liv);
                        }
                    }
                }
                if(getDay() >= 35){
                    switch (liv.getType()){
                        case COW,SHEEP ->{
                            liv.remove();
                            Ravager r = (Ravager) Entities.spawnMob(loc,EntityType.RAVAGER);
                            Entities.nwRavager(r,false);
                        }
                    }
                }
                if(getDay() >= 42){
                    switch (liv.getType()){
                        case VILLAGER ->{
                            liv.remove();
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            CustomEvoker r = new CustomEvoker(worldServer,false);
                            r.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                        case PARROT -> {
                            liv.remove();
                            Vex v = (Vex) Entities.spawnMob(loc,EntityType.VEX);
                            Entities.nwVex(v);
                        }
                    }
                }
        }
    }
}
