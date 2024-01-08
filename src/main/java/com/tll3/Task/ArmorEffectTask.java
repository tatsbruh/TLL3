package com.tll3.Task;

import com.tll3.Misc.DataManager.PlayerData;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ArmorEffectTask extends BukkitRunnable {
    private final Player p;

    public ArmorEffectTask(Player p){
        this.p = p;
    }
    @Override
    public void run() {
        double health = 20;
        if(getFullSet(p,69)){
            health += 6;
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,0,true,false,true));
        }
        health += PlayerData.getExtraHealth(p);
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }


    public static boolean getFullSet(Player p,int custommodeldata){
        if(p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null){
            if(p.getInventory().getHelmet().hasItemMeta() && p.getInventory().getChestplate().hasItemMeta() && p.getInventory().getLeggings().hasItemMeta() && p.getInventory().getBoots().hasItemMeta()){
                if(p.getInventory().getHelmet().getItemMeta().hasCustomModelData() && p.getInventory().getChestplate().getItemMeta().hasCustomModelData() && p.getInventory().getLeggings().getItemMeta().hasCustomModelData() && p.getInventory().getBoots().getItemMeta().hasCustomModelData()){
                    return p.getInventory().getHelmet().getItemMeta().getCustomModelData() == custommodeldata && p.getInventory().getChestplate().getItemMeta().getCustomModelData() == custommodeldata && p.getInventory().getLeggings().getItemMeta().getCustomModelData() == custommodeldata && p.getInventory().getBoots().getItemMeta().getCustomModelData() == custommodeldata;
                }
            }
        }
        return false;
    }

}

