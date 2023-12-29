package com.tll3.Misc.DataManager;

import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerData {

    /*
    * This adds a custom effect that is linked with the scoreboard and the effects task
    * for example: addDataEffect(p,"collapse",10);, this will add the collapse effect for 10 seconds
    * also, the data adds the duration itself because my brain is big har har har
    * */
    public static void addDataEffect(Player player,String name, int duration,int effect){
        var duration_name = name + "_d"; //duration data name
        var effect_name = name + "_e"; //effect data name
        if(Data.has(player,name, PersistentDataType.STRING)){ //check if you already have the effect
            Data.set(player,duration_name,PersistentDataType.INTEGER,duration);
            Data.set(player,effect_name,PersistentDataType.INTEGER,effect);
        }else{//if not, add it
            Data.set(player,name,PersistentDataType.STRING,name);
            Data.set(player,duration_name,PersistentDataType.INTEGER,duration);
            Data.set(player,effect_name,PersistentDataType.INTEGER,effect);
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
    public static void sumExp(Player p,int amount){
        if(Data.has(p,"exposure",PersistentDataType.INTEGER)){
            var d = Data.get(p,"exposure",PersistentDataType.INTEGER);
            int result = d + amount;
            if(result >= 200) {
                Data.set(p,"exposure",PersistentDataType.INTEGER,200);
            }else Data.set(p,"exposure",PersistentDataType.INTEGER,result);
        }
    }
    public static void restExp(Player p,int amount){
        if(Data.has(p,"exposure",PersistentDataType.INTEGER)){
            var d = Data.get(p,"exposure",PersistentDataType.INTEGER);
            int result = d - amount;
            if(result <= 0) {
                Data.set(p,"exposure",PersistentDataType.INTEGER,0);
            }else Data.set(p,"exposure",PersistentDataType.INTEGER,result);
        }
    }
    public static void addExposure(Player p){
        if(!Data.has(p,"exposure",PersistentDataType.INTEGER)){
            Data.set(p,"exposure",PersistentDataType.INTEGER,0);
        }
    }


    public static void setTotemCount(Player target,int i){
        Data.setPlayerData(target, "totemcount", String.valueOf(i));
    }
    public static int getTotemCount(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"totemcount","1"));
    }



}
