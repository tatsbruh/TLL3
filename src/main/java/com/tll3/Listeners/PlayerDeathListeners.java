package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.Monsoon;
import com.tll3.TLL3;
import org.bukkit.*;
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
            doDeathAnimation1(sp);
            sp.sendMessage(ChatUtils.format("#63513aEl jugador &6&l" + p.getName() + " #63513aa perdido su ultima vida y su alma desata la furia de la Monsoon!"));
            sp.sendMessage(ChatUtils.format("&8Coordenadas: X - " + p.getLocation().getBlockX() + " | Y - " + p.getLocation().getBlockY() + " | Z - " + p.getLocation().getBlockZ()));
            //Aqui va los mensajes de muerte
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
                            int stormDurationInTicks = 18000; // 15 minutos en ticks
                            int storm_time = world.isThundering() ? world.getWeatherDuration() + GenericUtils.getDay() * stormDurationInTicks : GenericUtils.getDay() * stormDurationInTicks;
                            String setThunder = "weather thunder " + storm_time;
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), setThunder);
                            for (Player sp : Bukkit.getOnlinePlayers()) {
                                sp.sendMessage(setThunder);
                                sp.getLocation().getWorld().playSound(sp.getLocation(),Sound.BLOCK_END_PORTAL_SPAWN,10.0F,-1.0F); //Placeholder
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


        }.runTaskLater(TLL3.getInstance(), 100L);



    }


    public void doDeathAnimation1(Player p) {
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
            p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN,10.0F,-1.0F);//Placeholder
    }

}
