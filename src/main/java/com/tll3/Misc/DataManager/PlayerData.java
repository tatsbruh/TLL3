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

    public static void setExposure(Player p, int amount){
        if(Data.has(p,"exposure",PersistentDataType.INTEGER)){
            Data.set(p,"exposure",PersistentDataType.INTEGER,amount);
        }
    }
    public static int getExposure(Player p){
        if(Data.has(p,"exposure",PersistentDataType.INTEGER)){
            return Data.get(p,"exposure",PersistentDataType.INTEGER);
        }
        return 0;
    }
    public static void addExposure(Player p){
        if(!Data.has(p,"exposure",PersistentDataType.INTEGER)){
            Data.set(p,"exposure",PersistentDataType.INTEGER,200);
        }
    }


    public static void setTotemCount(Player target,int i){
        Data.setPlayerData(target, "totemcount", String.valueOf(i));
    }
    public static int getTotemCount(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"totemcount","1"));
    }



}
