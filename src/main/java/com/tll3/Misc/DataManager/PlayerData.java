package com.tll3.Misc.DataManager;

import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerData {

    /*
    * This adds a custom effect that is linked with the scoreboard and the effects task
    * for example: addDataEffect(p,"collapse",10);, this will add the collapse effect for 10 seconds
    * also, the data adds the duration itself because my brain is big har har har
    * */
    public static void addDataEffect(Player player,String name, int duration){
        var duration_name = name + "_d"; //duration data name
        if(Data.has(player,name, PersistentDataType.STRING)){ //check if you already have the effect
            Data.set(player,duration_name,PersistentDataType.INTEGER,duration);
        }else{//if not, add it
            Data.set(player,name,PersistentDataType.STRING,name);
            Data.set(player,duration_name,PersistentDataType.INTEGER,duration);
        }
    }

}
