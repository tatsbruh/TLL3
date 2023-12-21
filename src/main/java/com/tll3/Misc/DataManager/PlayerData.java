package com.tll3.Misc.DataManager;

import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerData {

    public static void addDataEffect(Player player,String name, int duration){
        var duration_name = name + "_d";
        if(Data.has(player,name, PersistentDataType.STRING)){
            Data.set(player,duration_name,PersistentDataType.INTEGER,duration);
        }else{
            Data.set(player,name,PersistentDataType.STRING,name);
            Data.set(player,duration_name,PersistentDataType.INTEGER,duration);
        }
    }

}
