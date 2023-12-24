package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.Monsoon;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static com.tll3.TLL3.playingAnim;

public class PlayerDeathListeners implements Listener {

    @EventHandler
    public void deathE(PlayerDeathEvent e){
        var p = e.getPlayer();
        p.setHealth(20);
        p.setGameMode(GameMode.SPECTATOR);
        for(Player sp : Bukkit.getOnlinePlayers()){
            doDeathAnimation(sp);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                World world = GenericUtils.getWorld();
                long time = world.getTime();
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
                if (time >= 0 && time < 12000) {
                    world.setTime(0);
                }
                if (time >= 12000) {
                    world.setTime(12000);
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        World world = GenericUtils.getWorld();
                        int time = (int) world.getTime();
                        int targetTime = 18000;

                        if (time == targetTime) {
                            int storm_time = world.isThundering() ? world.getWeatherDuration() / 20 + GenericUtils.getDay() * 900 : GenericUtils.getDay() * 900;
                            String setThunder = "weather thunder " + storm_time;
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), setThunder);
                            for (Player sp : Bukkit.getOnlinePlayers()) {
                                sp.sendTitle(ChatUtils.format("#0023ad☽ ¡Monsoon! ☽"),ChatUtils.format("#4d52d1☂ Duracion: " + GenericUtils.doTimeFormat(storm_time) + " ☂"),0,80,0);
                            }
                            Bukkit.getPluginManager().callEvent(new Monsoon.StartMonsoon(Monsoon.StartMonsoon.Cause.DEATH));

                            cancel();
                        }else{
                            world.setTime(time + 200);
                        }
                    }
                }.runTaskTimer(TLL3.getInstance(), 0L, 1L);
            }


        }.runTaskLater(TLL3.getInstance(), 50L);



    }


    public void doDeathAnimation(Player p) {
        if(!playingAnim) {
            playingAnim = true;
            new BukkitRunnable() {
                int i = 1;
                String title = "\\uE" + i + "\\";

                @Override
                public void run() {
                    i++;
                    title = "\\uE" + i + "\\";
                    p.sendTitle(title, "", 0, 80, 0);
                    if (i > 56) {
                        playingAnim = false;
                        cancel();
                    }
                }
            }.runTaskTimer(TLL3.getInstance(), 0L, 1L);
        }

    }

}
