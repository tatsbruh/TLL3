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
        World world = GenericUtils.getWorld();
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
        for(Player sp : Bukkit.getOnlinePlayers()){
            if(!sp.getName().equalsIgnoreCase(p.getName())) {
                doDeathAnimation1(sp);
            }else{
                doDeathAnimation2(p);
            }
            sp.sendMessage(ChatUtils.format("#63513aEl jugador &6&l" + p.getName() + " #63513aha sucumbido ante el desafió y ha perdido su ultima vida!"));
            sp.sendMessage(ChatUtils.format("&8Coordenadas: X - " + p.getLocation().getBlockX() + " | Y - " + p.getLocation().getBlockY() + " | Z - " + p.getLocation().getBlockZ() + " ("+ dimension(p.getLocation()) + ")"));
            sp.sendMessage(getDeathMessages(p));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                World world = GenericUtils.getWorld();
                long time = world.getTime();
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
                            Bukkit.getPluginManager().callEvent(new Monsoon.StartMonsoon(Monsoon.StartMonsoon.Cause.DEATH));
                            cancel();
                        }else{
                            world.setTime(time + 200);
                        }
                    }
                }.runTaskTimer(TLL3.getInstance(), 0L, 1L);
            }


        }.runTaskLater(TLL3.getInstance(), 180L);



    }


    public void doDeathAnimation1(Player p) {
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if (count <= 92) {
                    sendUnicodeTitle(Bukkit.getOnlinePlayers(), count++);
                } else {
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
            String title = ChatUtils.format("#63513aaA   #fb6c37A");
            String subtitle = ChatUtils.format("");

            @Override
            public void run() {
                i++;
                if(i > 5 && change <= 10){
                    change++;
                    i = 0;
                }
                switch (change){
                    case 0 -> title = ChatUtils.format("&8&kaA   #fb6c37A   &8&kAa");
                    case 1 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fb6c37f   &8&kAa");
                    case 2 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fded1ef#fb6c37t   &8&kAa");
                    case 3 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fcc226f#fcc226t#fb6c37e   &8&kAa");
                    case 4 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fcad2bf#fded1et#fcad2be#fb6c37r   &8&kAa");
                    case 5 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fca02df#fdd323t#fdd323e#fca02dr#fb6c37l   &8&kAa");
                    case 6 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fc972ff#fcc226t#fded1ee#fcc226r#fc972fl#fb6c37i   &8&kAa");
                    case 7 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fc9130f#fcb629t#fddb22e#fddb22r#fcb629l#fc9130i#fb6c37f   &8&kAa");
                    case 8,9,10,11 -> title = ChatUtils.format("&8&kaA   #fb6c37A#fc8c31f#fcad2bt#fdcd24e#fded1er#fdcd24l#fcad2bi#fc8c31f#fb6c37e   &8&kAa");
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
            case "tatsushiri" -> s = ChatUtils.format("&8r.");
            default -> s = ChatUtils.format("&8Descansa en paz.");
        }
        return s;
    }


    private void sendUnicodeTitle(Iterable<? extends Player> players, int count) {
        String titleText = Character.toString((char) ('\uE000' + count));
        for (Player player : players) {
            player.sendTitle("", titleText, 0, 80, 20);
        }
    }
    public static String dimension(Location location){
        return switch (location.getWorld().getName()) {
            case "world" -> "Overworld";
            case "world_nether" -> "Nether";
            case "world_the_end" -> "The End";
            case "world_wasteyard" -> "Wasteyard";
            case "world_zero" -> "Horizon Zero";
            case "world_primeval" -> "Primeval Forest";
            case "world_dunes" -> "Savage Dunes";
            case "world_plateau" -> "Scorched Plateau";
            default -> "Desconocido";
        };
    }
}
