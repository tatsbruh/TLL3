package com.tll3.Task.Bosses;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public class BossHealthbar extends BukkitRunnable {
    private LivingEntity livingEntity;
    private BossBar bossBar;
    public BossHealthbar(TLL3 plugin, LivingEntity livingEntity, String title, BarColor barColor, BarStyle barStyle, BarFlag... barFlag){
        this.livingEntity = livingEntity;
        bossBar = plugin.getServer().createBossBar(ChatUtils.format(title),barColor,barStyle,barFlag);
    }
    @Override
    public void run() {
        if(!livingEntity.isDead()){
            for (Player player : Bukkit.getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }
            bossBar.setProgress(livingEntity.getHealth() / livingEntity.getMaxHealth());
        }else{
            List<Player> players = bossBar.getPlayers();
            for (Player player : players) {
                bossBar.removePlayer(player);
            }
            bossBar.setVisible(false);
            cancel();
        }
    }
}
