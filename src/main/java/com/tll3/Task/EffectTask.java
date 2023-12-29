package com.tll3.Task;

import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.TLL3;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectTask extends BukkitRunnable {
    private final Player player;
    public EffectTask(Player player){
        this.player = player;
    }

    int curse_time = 50;
    int consume = 1;
    @Override
    public void run() {
        if(Data.has(player,"curse", PersistentDataType.STRING)){
            TLL3.getInstance().console("lol " + curse_time + " " + consume);
            var s = Data.get(player,"curse_e",PersistentDataType.INTEGER);
            switch (s){
                case 1 -> consume = 1;
                case 2 -> consume = 2;
                case 3 -> consume = 5;
                case 4 -> consume = 10;
                default -> consume = 1;
            }
            if(curse_time > 0){
                var result = curse_time - consume;
                if(result <= 0){
                    curse_time = 0;
                }else{
                    curse_time -= consume;
                }
            }else{

                PlayerData.sumExp(player,1);
                curse_time = 50;
            }
        }
    }
}
