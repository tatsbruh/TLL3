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



    public static void setItemConsumed(Player target,String item,int number){
        Data.setPlayerData(target, "consumed_" + item, String.valueOf(number));
    }
    public static int getItemConsumed(Player target,String item){
        return Integer.parseInt(Data.getPlayerData(target,"consumed_" + item,"0"));
    }


    public static void setItemCooldown(Player target,String item,int number){
        Data.setPlayerData(target, "cooldown_" + item, String.valueOf(number));
    }
    public static int getItemCooldown(Player target,String item){
        return Integer.parseInt(Data.getPlayerData(target,"cooldown_" + item,"0"));
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


    public static void setMissionCount(Player target,String mission,int number){
        Data.setPlayerMissionData(target, "missions." + mission, String.valueOf(number));
    }
    public static int getMission(Player target,String mission){
        return Integer.parseInt(Data.getPlayerMissionData(target,"missions." + mission,"0"));
    }

    public static void setObjectiveCount(Player target,String mission,int number){
        Data.setPlayerMissionData(target, "objectives." + mission, String.valueOf(number));
    }
    public static int getObjective(Player target,String mission){
        return Integer.parseInt(Data.getPlayerMissionData(target,"objectives." + mission,"0"));
    }

    public static void setTotemCount(Player target,int i){
        Data.setPlayerData(target, "totemcount", String.valueOf(i));
    }
    public static int getTotemCount(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"totemcount","1"));
    }
    public static int getHunts(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"completed_hunts","0"));
    }
    public static void setHunts(Player target,int i){
        Data.setPlayerData(target, "completed_hunts", String.valueOf(i));
    }
    public static int getPrestige(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"prestige","0"));
    }
    public static void setPrestige(Player target,int i){
        Data.setPlayerData(target, "prestige", String.valueOf(i));
    }
    public static int getCredits(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"credits","0"));
    }
    public static void setCredits(Player target,int i){
        Data.setPlayerData(target, "credits", String.valueOf(i));
    }
    public static int getExtraHealth(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"extrahealth","0"));
    }
    public static void setExtraHealth(Player target,int i){
        Data.setPlayerData(target, "extrahealth", String.valueOf(i));
    }
    public static int getNegativeHealth(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"negativehealth","0"));
    }
    public static void setNegativeHealth(Player target,int i){
        Data.setPlayerData(target, "negativehealth", String.valueOf(i));
    }
    public static double getExtraSpeed(Player target){
        return Double.parseDouble(Data.getPlayerData(target,"extraspeed","0.0"));
    }
    public static void setExtraSpeed(Player target,double i){
        Data.setPlayerData(target, "extraspeed", String.valueOf(i));
    }
    public static int getSigile(Player target){
        return Integer.parseInt(Data.getPlayerData(target,"sigilo","0"));
    }
    public static void setSigile(Player target,int i){
        Data.setPlayerData(target, "sigilo", String.valueOf(i));
    }
    public static void setUpgrade(Player target,String upgrade,int number){
        Data.setPlayerData(target, "upgrade_" + upgrade, String.valueOf(number));
    }
    public static int getUpgrade(Player target,String upgrade){
        return Integer.parseInt(Data.getPlayerData(target,"upgrade_" + upgrade,"0"));
    }
    public static void setUpgradeCost(Player target,String upgrade,int number){
        Data.setPlayerData(target, "upgrade_cost" + upgrade, String.valueOf(number));
    }
    public static int getUpgradeCost(Player target,String upgrade){
        return Integer.parseInt(Data.getPlayerData(target,"upgrade_cost" + upgrade,"2"));
    }

}
