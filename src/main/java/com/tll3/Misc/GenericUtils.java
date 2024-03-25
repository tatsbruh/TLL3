package com.tll3.Misc;

import com.tll3.Misc.Files.ConfigData;
import com.tll3.TLL3;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R3.attribute.CraftAttributeMap;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;

import static com.tll3.Misc.ChatUtils.format;

public class GenericUtils {
    private static final Plugin plugin = TLL3.getPlugin(TLL3.class);

    private static LocalDate actualDate = LocalDate.now();
    private static LocalDate startDate = LocalDate.parse(ConfigData.getConfig("start_date",""));
    private static @Getter int maxweatherdur = Integer.parseInt(ConfigData.getConfig("weathermaxdur","0"));
    public static @Getter String monsoon_active = ConfigData.getConfig("monsoon_active","");
    public static @Getter String typhoonactive = ConfigData.getConfig("typhoon_active","");
    public static @Getter int currentStormDuration = Integer.parseInt(ConfigData.getConfig("currentStormDur", String.valueOf(GenericUtils.getWorld().getThunderDuration())));

    public static int getDay(){
        return (int) ChronoUnit.DAYS.between(startDate, actualDate);
    };

    public static World getWorld(){
        return Bukkit.getWorld("world");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Player getRandomPlayer() {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]); //Makes an array list from all the connected players
        return onlinePlayers.length > 0 ? onlinePlayers[new Random().nextInt(onlinePlayers.length)] : null; //selects a random player from the list
    }
    public static Location getRandomLocationAroundRadius(Location center, int radius) {
        World world = center.getWorld();//gets the location (duh)
        double x = center.getX() + (new Random().nextDouble() * 2 - 1) * radius; //Creates a random X around the radius
        double z = center.getZ() + (new Random().nextDouble() * 2 - 1) * radius; //Creates a random Y around the radius

        return new Location(world, x, world.getHighestBlockYAt((int) x, (int) z), z); //returns a random location of said thing
    }
    public static void setDays(String args1) {
        int nD;
        try {
            nD = Math.max(0, Math.min(60, Integer.parseInt(args1)));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return;
        }
        LocalDate add = LocalDate.now().minusDays(nD);
        int month = add.getMonthValue();
        int day = add.getDayOfMonth();
        String s;
        if (month < 10) {
            s = add.getYear() + "-0" + month + "-";
        } else {
            s = add.getYear() + "-" + month + "-";
        }

        if (day < 10) {
            s = s + "0" + day;
        } else {
            s = s + day;
        }
        startDate = LocalDate.parse(s);
        ConfigData.setConfig("start_date",s);
    }
    public static void setMonsoonActive(String args1){
        monsoon_active = args1;
        ConfigData.setConfig("monsoon_active",args1);
    }
    public static void setVortexTyphoonActive(String args1){
        typhoonactive = args1;
        ConfigData.setConfig("typhoon_active",args1);
    }
    public static void setMaxWeatherDuration(int ticks){
        maxweatherdur = ticks;
        ConfigData.setConfig("weathermaxdur", String.valueOf(ticks));
    }

    public static void setCurrentStormDuration(int ticks){
        ConfigData.setConfig("currentStormDur", String.valueOf(ticks));
    }



    public static String doTimeFormat(int i){
        int seconds = i / 20; // Convert ticks to seconds
        int hours = seconds / 3600;
        int remainingSeconds = seconds % 3600;
        int minutes = remainingSeconds / 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds % 60);
    }
    public static Integer getRandomValue(int n) {
        return (int)(Math.random() * (double)n);
    }
    public static String damageCause(EntityDamageEvent e) {

        switch (e.getCause()) {
            case FALL:
                return format("&8[Caída]");
            case FIRE:
                return format("&8[Fuego]");
            case FREEZE:
                return format("&8[Congelado]");
            case LAVA:
                return format("&8[Lava]");
            case VOID:
                return format("&8[Vacío]");
            case MAGIC:
                return format("&8[Magia]");
            case BLOCK_EXPLOSION:
                return format("&8[Explosion]");
            case POISON:
                return format("&8[Veneno]");
            case THORNS:
                return format("&8[Espinas]");
            case WITHER:
                return format("&8[Wither]");
            case CONTACT:
                return format("&8[Contacto]");
            case CRAMMING:
                return format("&8[Cramming]");
            case DRAGON_BREATH:
                return format("&8[Aliento de Dragon]");
            case DROWNING:
                return format("&8[Ahogado]");
            case FIRE_TICK:
                return format("&8[Tick de Fuego]");
            case HOT_FLOOR:
                return format("&8[Piso Ardiente]");
            case LIGHTNING:
                return format("&8[Rayo]");
            case PROJECTILE:
                return format("&8[Proyectil]");
            case STARVATION:
                return format("&8[Hambre]");
            case SUFFOCATION:
                return format("&8[Sofocación]");
            case SONIC_BOOM:
                return format("&8[Onda supersónica]");
            case ENTITY_SWEEP_ATTACK:
            case ENTITY_ATTACK:
                if (e instanceof EntityDamageByEntityEvent) {
                    return "&8[Entidad: " + ((EntityDamageByEntityEvent) e).getDamager().getName() + "&8]";
                }
            case FALLING_BLOCK:
                return format("&8[Bloque Cayendo]");
            case FLY_INTO_WALL:
                return format("&8[Energía cinética]");
            case ENTITY_EXPLOSION:
                if (e instanceof EntityDamageByEntityEvent) {
                    return "&8[Explosión: " + ((EntityDamageByEntityEvent) e).getDamager().getName() + "&8]";
                }
            default:
                return "&8[Desconocida]";
        }
    }
    public static boolean isHostileMob(EntityType type) {
        return type == EntityType.ENDER_DRAGON || type == EntityType.WITHER || type == EntityType.BLAZE || type == EntityType.CREEPER || type == EntityType.GHAST || type == EntityType.MAGMA_CUBE || type == EntityType.SILVERFISH || type == EntityType.SKELETON || type == EntityType.SLIME || type == EntityType.ZOMBIE || type == EntityType.ZOMBIE_VILLAGER || type == EntityType.DROWNED || type == EntityType.WITHER_SKELETON || type == EntityType.WITCH || type == EntityType.PILLAGER || type == EntityType.EVOKER || type == EntityType.VINDICATOR || type == EntityType.RAVAGER || type == EntityType.VEX || type == EntityType.GUARDIAN || type == EntityType.ELDER_GUARDIAN || type == EntityType.SHULKER || type == EntityType.HUSK || type == EntityType.STRAY || type == EntityType.PHANTOM || type == EntityType.WARDEN || type == EntityType.BREEZE;
    }
}
