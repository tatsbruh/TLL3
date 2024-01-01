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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;



public class PlayerDeathListeners implements Listener {

    @EventHandler
    public void deathE(PlayerDeathEvent e){
        var p = e.getPlayer();
        p.setHealth(20);
        p.setGameMode(GameMode.SPECTATOR);

        for(Player sp : Bukkit.getOnlinePlayers()){
            if(!sp.getName().equalsIgnoreCase(p.getName())) {
                doDeathAnimation1(sp);
            }else{
                doDeathAnimation2(p);
            }
            sp.sendMessage(ChatUtils.format("#63513aEl jugador &6&l" + p.getName() + " #63513aa perdido su ultima vida y su alma desata la furia de la Monsoon!"));
            sp.sendMessage(ChatUtils.format("&8Coordenadas: X - " + p.getLocation().getBlockX() + " | Y - " + p.getLocation().getBlockY() + " | Z - " + p.getLocation().getBlockZ()));
            sp.sendMessage(getDeathMessages(p));
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
                                sp.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,60,0,false,false,false));
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


        }.runTaskLater(TLL3.getInstance(), 120L);



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
                        cancel();
                    }
                }
            }.runTaskTimer(TLL3.getInstance(), 0L, 1L);
            p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN,10.0F,-1.0F);//Placeholder
    }
    public void doDeathAnimation2(Player p) {
        new BukkitRunnable() {
            int i = 1;
            int change = 0;
            String title = ChatUtils.format("#fb6c37A");
            String subtitle = ChatUtils.format("");

            @Override
            public void run() {
                i++;
                if(i > 5 && change <= 10){
                    change++;
                    i = 0;
                }
                switch (change){
                    case 0 -> title = ChatUtils.format("#fb6c37A");
                    case 1 -> title = ChatUtils.format("#fb6c37A#fb6c37f");
                    case 2 -> title = ChatUtils.format("#fb6c37A#fded1ef#fb6c37t");
                    case 3 -> title = ChatUtils.format("#fb6c37A#fcc226f#fcc226t#fb6c37e");
                    case 4 -> title = ChatUtils.format("#fb6c37A#fcad2bf#fded1et#fcad2be#fb6c37r");
                    case 5 -> title = ChatUtils.format("#fb6c37A#fca02df#fdd323t#fdd323e#fca02dr#fb6c37l");
                    case 6 -> title = ChatUtils.format("#fb6c37A#fc972ff#fcc226t#fded1ee#fcc226r#fc972fl#fb6c37i");
                    case 7 -> title = ChatUtils.format("#fb6c37A#fc9130f#fcb629t#fddb22e#fddb22r#fcb629l#fc9130i#fb6c37f");
                    case 8,9,10,11 -> title = ChatUtils.format("#fb6c37A#fc8c31f#fcad2bt#fdcd24e#fded1er#fdcd24l#fcad2bi#fc8c31f#fb6c37e");
                }
                subtitle = getDeathMessages(p);
                if(change >= 10){
                    cancel();
                }

                p.sendTitle(title, subtitle, 0, 120, 20);

            }
        }.runTaskTimer(TLL3.getInstance(), 0L, 1L);
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN,10.0F,-1.0F);//Placeholder
    }


    public static String getDeathMessages(Player p){
        String s;
        switch (p.getName()){
            case "tatsushiri" -> s = ChatUtils.format("&8" + p.getName() + ": r");
            default -> s = ChatUtils.format("&8Rest in peace, " + p.getName());
        }
        return s;
    }

}
