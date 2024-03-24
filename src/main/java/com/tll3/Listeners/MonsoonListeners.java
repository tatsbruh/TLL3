package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.Files.ConfigData;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.Monsoon;
import com.tll3.TLL3;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class MonsoonListeners implements Listener {

    private static BossBar bossBar;
    private static boolean isMonsoonActive = Objects.equals(GenericUtils.getMonsoon_active(), "true");
    private static Integer TaskBossBarID = null;
    public static void createBossBar() {

        if (TaskBossBarID != null) {
            Bukkit.getScheduler().cancelTask(TaskBossBarID);
            TaskBossBarID = null;
        }

        if (bossBar == null) {
            if(GenericUtils.getTyphoonactive().equalsIgnoreCase("true")){
                bossBar = Bukkit.createBossBar(ChatUtils.format("#259c9a☀ #77f7f6Vortex Typhoon #259c9a☀ &7| #008a8a" + getTime()), BarColor.BLUE, BarStyle.SEGMENTED_6);
            }else {
                bossBar = Bukkit.createBossBar(ChatUtils.format("#1b20b5☽ Monsoon ☽ &7| #516ebd" + getTime()), BarColor.BLUE, BarStyle.SEGMENTED_6);
            }
        }
        TaskBossBarID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TLL3.getInstance(), () -> {
            if (!isMonsoonActive){
                Bukkit.getScheduler().cancelTask(TaskBossBarID);
                TaskBossBarID = null;
                return;
            }
            int updtime = GenericUtils.getWorld().getThunderDuration();
            if(GenericUtils.getTyphoonactive().equalsIgnoreCase("true")) {
                bossBar.setTitle(ChatUtils.format("#259c9a☀ #77f7f6Vortex Typhoon #259c9a☀ &7| #008a8a" + getTime()));
            }else{
                bossBar.setTitle(ChatUtils.format("#1b20b5☽ Monsoon ☽ &7| #516ebd" + getTime()));
            }
            int maxWeather = GenericUtils.getMaxweatherdur();
            double calculos = 1.0 - (((double) 1 / maxWeather) * (maxWeather - updtime));
            bossBar.setProgress(calculos);
        }, 0L, 20L);
    }

    @EventHandler
    public void monstartE(Monsoon.StartMonsoon e){
        isMonsoonActive = true;
        GenericUtils.setMonsoonActive("true");
        boolean isTyphoonActive = ((EntityNaturalSpawn.doRandomChance(20) || GenericUtils.getTyphoonactive().equalsIgnoreCase("true")) && GenericUtils.getDay() >= 21);
        if (isTyphoonActive)
            GenericUtils.setVortexTyphoonActive("false");
        World world = GenericUtils.getWorld();
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,true);
        int stormDurationInTicks;
        if (isTyphoonActive)
            stormDurationInTicks = 18000 * 2; // 30 minutos en ticks
        else
            stormDurationInTicks = 18000; // 15 minutos en ticks

        int storm_time =
                isMonsoonActive ?
                world.getThunderDuration() + (GenericUtils.getDay() * stormDurationInTicks)
                :
                (GenericUtils.getDay() * stormDurationInTicks);

        System.out.println("Storm time: " + storm_time);
        System.out.println("geThunderDuration old: " + world.getThunderDuration());
        System.out.println("getDay: " + GenericUtils.getDay());

        world.setStorm(true);
        world.setThundering(true);
        world.setThunderDuration(storm_time);
        //String setThunder = "weather thunder " + storm_time;
        //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), setThunder);
        for (Player sp : Bukkit.getOnlinePlayers()) {
            sp.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,60,0,false,false,false));
            sp.getLocation().getWorld().playSound(sp.getLocation(),Sound.BLOCK_END_PORTAL_SPAWN,10.0F,-1.0F); //Placeholder
            if (isTyphoonActive){
                sp.sendTitle(ChatUtils.format("#305bab☀ #5acce8¡VORTEX TYPHOON! #305bab☀"),ChatUtils.format("#4c717a☁ Duración: " + GenericUtils.doTimeFormat(storm_time) + " ☁"),0,80,20);
            } else {
                sp.sendTitle(ChatUtils.format("#0023ad☽ ¡Monsoon! ☽"), ChatUtils.format("#4d52d1☂ Duración: " + GenericUtils.doTimeFormat(storm_time) + " ☂"), 0, 80, 20);

            }
        }
        GenericUtils.setMaxWeatherDuration(storm_time);
        createBossBar();
        Bukkit.getOnlinePlayers().forEach(player -> {
            bossBar.setVisible(true);
            bossBar.addFlag(BarFlag.CREATE_FOG);
            bossBar.addFlag(BarFlag.DARKEN_SKY);
            bossBar.addPlayer(player);
        });
    }
    @EventHandler
    public void monendE(Monsoon.StopMonsoon e){
        if (!isMonsoonActive)
            return;
        isMonsoonActive = false;
        Bukkit.getLogger().info("MONSOON ACABADA, CAUSA " + e.getCause());
        Bukkit.getOnlinePlayers().forEach(bossBar::removePlayer);
        if(TaskBossBarID != null) {
            Bukkit.getScheduler().cancelTask(TaskBossBarID);
            TaskBossBarID = null;
        }
        World world = GenericUtils.getWorld();
        world.setStorm(false);
        world.setThundering(false);
        world.setThunderDuration(0);
        world.setClearWeatherDuration(Integer.MAX_VALUE);
        //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "weather clear 999999");
        GenericUtils.setMonsoonActive("false");
        GenericUtils.setVortexTyphoonActive("false");
        for(Player players : Bukkit.getOnlinePlayers()) {
            bossBar.removePlayer(players);
            players.sendMessage(ChatUtils.format(ChatUtils.prefix + "&aParece que el lamento del mundo ha llegado a su fin..."));
            players.getLocation().getWorld().playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,10.0F,2.0F);
        }
        bossBar.setVisible(false);
        GenericUtils.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
    }



    @EventHandler
    public void bossbJoin(PlayerJoinEvent e) {
        if (Objects.equals(GenericUtils.getMonsoon_active(), "true") && bossBar != null) {
            bossBar.addPlayer(e.getPlayer());
        }
    }
    @EventHandler
    public void bossbQuit(PlayerQuitEvent e){
        if(bossBar != null && bossBar.getPlayers().contains(e.getPlayer())){
            bossBar.removePlayer(e.getPlayer());
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onThunderChange(ThunderChangeEvent e){
        if(!e.toThunderState() && Objects.equals(GenericUtils.getMonsoon_active(), "true")) {
            if (e.getWorld().getThunderDuration() <= 0) {
                Bukkit.getPluginManager().callEvent(new Monsoon.StopMonsoon(Monsoon.StopMonsoon.Cause.NATURAL));
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        if(e.toWeatherState() && Objects.equals(GenericUtils.getMonsoon_active(), "false")) {
            Bukkit.getScheduler().runTask(TLL3.getInstance(), () -> e.setCancelled(true));
            Bukkit.getLogger().info("No Tormenta natural");
        }
    }
    private static String getTime(){
        if(GenericUtils.getWorld().getThunderDuration() > 0){
            long segundos = (GenericUtils.getWorld().getThunderDuration());
            return GenericUtils.doTimeFormat((int) segundos);
        }
        return " ";
    }
}
