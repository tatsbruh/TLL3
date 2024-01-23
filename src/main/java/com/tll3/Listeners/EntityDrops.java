package com.tll3.Listeners;

import com.tll3.Lists.Items;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.GenericUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static com.tll3.Misc.GenericUtils.getDay;

public class EntityDrops implements Listener {
    //ac
    @EventHandler
    public void mobdropscontroller(EntityDeathEvent e){
        var entity = e.getEntity();
        var killer = e.getEntity().getKiller();
        var drops = e.getDrops();
        if(killer == null)return;
        if(entity instanceof WitherSkeleton s){
            if(s.getLocation().getWorld().getEnvironment() == World.Environment.NORMAL){
                drops.clear();
            }
        }
        if(entity instanceof Blaze s){
            if(s.getLocation().getWorld().getEnvironment() == World.Environment.NORMAL){
                drops.clear();
            }
        }
        if(entity instanceof Drowned d){
            if(Data.has(d,"abyssdrow",PersistentDataType.STRING)){
                drops.clear();
                drops.add(dropAmmountWithChance(Items.brokenTrident(),1,3));
            }
        }
        if(entity instanceof Zombie z){
            if(Data.has(z,"revenantzombie", PersistentDataType.STRING)){
                drops.clear();
                drops.add(dropAmmountWithChance(Items.infestedFlesh(),GenericUtils.getRandomValue(8),50));
            }
        }
        if(entity instanceof Skeleton s){
            if(Data.has(s,"revenantskeleton",PersistentDataType.STRING)){
                drops.clear();
                drops.add(dropAmmountWithChance(Items.infestedBones(),GenericUtils.getRandomValue(8),50));
            }
        }
        if(entity instanceof Creeper c){
            if(Data.has(c,"revenantcreeper",PersistentDataType.STRING)){
                drops.clear();
                drops.add(dropAmmountWithChance(Items.goldenGunpowder(),GenericUtils.getRandomValue(8),50));
            }
        }
        if(entity instanceof Spider s){
            if(Data.has(s,"revenantspider",PersistentDataType.STRING)){
                drops.clear();
                drops.add(dropAmmountWithChance(Items.silverStrings(),GenericUtils.getRandomValue(8),50));
            }
        }
        if(entity instanceof Enderman enderman){
            if(Data.has(enderman,"revenantenderman",PersistentDataType.STRING)){
                drops.clear();
                drops.add(dropAmmountWithChance(Items.revenantPearl(),GenericUtils.getRandomValue(8),50));
            }
        }





    }


    public static ItemStack dropAmmountWithChance(ItemStack itemStack,int amount,int chance){
        if(GenericUtils.getRandomValue(100) <= chance){
            itemStack.setAmount(amount);
            return itemStack;
        }else return new ItemStack(Material.AIR);

    }





}
