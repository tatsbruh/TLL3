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
        /*
        * Checks every single effect to work like Potion Effect do
        * can this be optimized? yeah
        * do i know how to optimize it? no
        * also because i want some effects to have some effects when ending so uuuh yeahg
        * */

        if(Data.has(player,"curse", PersistentDataType.STRING)){
            var time = Data.get(player,"curse_d",PersistentDataType.INTEGER);
            if(time > 0){
                player.getPersistentDataContainer().set(Data.key("curse_d"),PersistentDataType.INTEGER,time - 1);
            }else{
                player.getPersistentDataContainer().remove(Data.key("curse"));
                player.getPersistentDataContainer().remove(Data.key("curse_d"));
                player.getPersistentDataContainer().remove(Data.key("curse_e"));
            }
        }
        if(Data.has(player,"invulnerable", PersistentDataType.STRING)){
            var time = Data.get(player,"invulnerable_d",PersistentDataType.INTEGER);
            if(time > 0){
                player.getPersistentDataContainer().set(Data.key("invulnerable_d"),PersistentDataType.INTEGER,time - 1);
            }else{
                player.getPersistentDataContainer().remove(Data.key("invulnerable"));
                player.getPersistentDataContainer().remove(Data.key("invulnerable_d"));
            }
        }
    }
}
