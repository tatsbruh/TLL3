package com.tll3.Task;

import com.tll3.Misc.DataManager.Data;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectDuration extends BukkitRunnable {
    private final Player player;
    public EffectDuration(Player player){
        this.player = player;
    }

    @Override
    public void run() {
        if(Data.has(player,"curse", PersistentDataType.STRING)){
            var time = Data.get(player,"curse_d",PersistentDataType.INTEGER);
            if(time > 0){
                player.getPersistentDataContainer().set(Data.key("curse_d"),PersistentDataType.INTEGER,time - 1);
            }else{
                player.getPersistentDataContainer().remove(Data.key("curse"));
                player.getPersistentDataContainer().remove(Data.key("curse_d"));
            }
        }
    }
}
