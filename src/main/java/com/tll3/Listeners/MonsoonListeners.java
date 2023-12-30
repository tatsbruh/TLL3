package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.Files.ConfigData;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.Monsoon;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
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
import org.bukkit.event.world.WorldInitEvent;

import java.util.Objects;

public class MonsoonListeners implements Listener {

    private static BossBar bossBar;
    private static Integer TaskBossBarID = 0;
    public static void createBossBar() {
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(ChatUtils.format("#1b20b5☽ Monsoon ☽ &7| #516ebd" + getTime()), BarColor.BLUE, BarStyle.SEGMENTED_6);
        }
        int stime = GenericUtils.getWorld().getWeatherDuration();
        TaskBossBarID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TLL3.getInstance(), () -> {
            int updtime = GenericUtils.getWorld().getWeatherDuration();
            double percentage = (double) updtime / stime;
            bossBar.setTitle(ChatUtils.format("#1b20b5☽ Monsoon ☽ &7| #516ebd" + getTime()));
            bossBar.setProgress(percentage);
        }, 0L, 20L);
    }

    @EventHandler
    public void monstartE(Monsoon.StartMonsoon e){
        GenericUtils.setMonsoonActive("true");
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
        Bukkit.getOnlinePlayers().forEach(bossBar::removePlayer);
        if(TaskBossBarID != null) {
            Bukkit.getScheduler().cancelTask(TaskBossBarID);
            TaskBossBarID = null;
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "weather clear 999999");
        GenericUtils.setMonsoonActive("false");
        for(Player players : Bukkit.getOnlinePlayers()) {
            bossBar.removePlayer(players);
            players.sendMessage(ChatUtils.format(ChatUtils.prefix + "&aLos cielos se despejan de la Monsoon..."));
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
        if(!e.toThunderState() && Objects.equals(GenericUtils.getMonsoon_active(), "true")){
            Monsoon.StopMonsoon event = new Monsoon.StopMonsoon(Monsoon.StopMonsoon.Cause.NATURAL);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    private static String getTime(){
        if(GenericUtils.getWorld().getWeatherDuration() > 0){
            long segundos = (GenericUtils.getWorld().getWeatherDuration());
            return GenericUtils.doTimeFormat((int) segundos);
        }
        return " ";
    }
}
