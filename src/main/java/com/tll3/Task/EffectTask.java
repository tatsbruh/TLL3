package com.tll3.Task;

import com.tll3.Misc.DataManager.Data;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectTask extends BukkitRunnable {
    private final Player player;
    public EffectTask(Player player){
        this.player = player;
    }

    int curse_time = 200;
    @Override
    public void run() {
        if(Data.has(player,"curse", PersistentDataType.STRING)){
            if(curse_time >= 0){
                curse_time--;
            }else{
                player.getLocation().getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, player.getLocation(),10,1,1,1,2);
                curse_time = 200;
            }
        }
    }
}
