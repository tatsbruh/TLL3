package com.tll3.Task;

import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public static final int extinctionvortexLockedSlots[] = {9,18,27,0,40,13,22,31,4,17,26,35,8};


    public static void lockPlayerSlots(Player p){
        if(GenericUtils.getDay() < 21) return;
        if(p.getGameMode() == GameMode.SPECTATOR || p.isDead() || !p.isOnline())return;

        int slot;
        if(GenericUtils.getDay() >= 21){
            for (int i = 0; i < extinctionvortexLockedSlots.length; i++){
                slot = extinctionvortexLockedSlots[i];
                if(PlayerData.getItemConsumed(p,"vortex_extinction") >= 1){
                    unlockSlotNumber(p,slot);
                }else{
                    lockSlotNumber(p,slot);
                }
            }
        }


    }


    private static void lockSlotNumber(Player p, int slot){
        ItemStack item = p.getInventory().getItem(slot);
        if(item != null){
            if(item.getType() != Material.AIR && item.getType() != Material.STRUCTURE_VOID){
                p.getWorld().dropItem(p.getLocation(),item.clone());
            }
            p.getInventory().setItem(slot,lockedVoid());
        }else{
            p.getInventory().setItem(slot,lockedVoid());
        }
    }

    private static void unlockSlotNumber(Player p,int slot){
        ItemStack item = p.getInventory().getItem(slot);
        if(item != null && item.getType() == Material.STRUCTURE_VOID){
            p.getInventory().clear(slot);
        }
    }



    public static ItemStack lockedVoid(){
        return new ItemBuilder(Material.STRUCTURE_VOID)
                .setID("void")
                .setCustomModelData(981188)
                .setDisplayName("§")
                .setAmount(1)
                .build();
    }
}
