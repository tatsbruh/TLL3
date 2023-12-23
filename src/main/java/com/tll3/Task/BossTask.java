package com.tll3.Task;

import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;


public class BossTask extends BukkitRunnable {

    int i = 0;
    int spawn = 0;

    public Player pene = null;

    @Override
    public void run() {
        long time = Objects.requireNonNull(Bukkit.getWorld("world")).getTime();

        if (time == 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&aEsta noche va a ser terrible..."));
            }
        }

        if (time == 13000) {
            Player rp = GenericUtils.getRandomPlayer();
            if (rp != null) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&5Raid en " + rp.getLocation().getBlockX() + "" + rp.getLocation().getBlockY() + "" + rp.getLocation().getBlockZ()));
                    pene = rp;
                }
            }
        }

        if (time >= 13000 && time < 23000) {
            if (i < 50) {
                i++;
            } else {
                if (pene != null) {
                    Location l = GenericUtils.getRandomLocationAroundRadius(pene.getLocation(), 32);
                    Entities.voidOver((Skeleton) Entities.spawnMob(l, EntityType.SKELETON));
                    i = 0;
                    spawn++;
                }
            }

            if (spawn >= 60) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&aLa Raid a finalizado!"));
                    cancel();
                    return;
                }
            }
        }
    }
}
