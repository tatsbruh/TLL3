package com.tll3.Task;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class ExposureTask extends BukkitRunnable {
    private final Player player;
    public ExposureTask(Player p){
        this.player = p;
    }

    @Override
    public void run() {
        if(player.getGameMode() == GameMode.SPECTATOR)return;
        var exp = PlayerData.getExposure(player);
        double range = 0;
        String exposure;

        if(exp >= 155 && exp <= 200){
            exposure = ChatUtils.format("&8&l[#bf0000███☠███&8&l] &7| &4&l" + exp + "%");
            range = 60.0;
        }else if(exp >= 95 && exp <= 155){
            exposure = ChatUtils.format("&8&l[#ff5454███☢███&8&l] &7| &4&l" + exp + "%");
            range = 40.0;
        }else if(exp >= 35 && exp <= 95){
            exposure = ChatUtils.format("&8&l[#f6ff91███☹███&8&l] &7| &6&l" + exp + "%");
            range = 20.0;
        }else{
            exposure = ChatUtils.format("&8&l[#abffc1███☺███&8&l] &7| &2&l" + exp + "%");
        }

        player.sendActionBar(exposure);
        if(exp >= 35){
            if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)return;
            player.getNearbyEntities(range, range, range).forEach(entity -> {
                if (entity instanceof Enemy e) {
                    if(e instanceof Enderman || e instanceof PigZombie)return;
                    Mob m = (Mob)e;
                    if(m.getTarget() == null){
                        m.setTarget(player);
                    }
                }
            });
        }
    }



}
