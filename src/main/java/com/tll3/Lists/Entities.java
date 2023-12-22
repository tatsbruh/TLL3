package com.tll3.Lists;

import com.tll3.Misc.DataManager.Data;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Locale;
import static com.tll3.Misc.EntityHelper.*;

public class Entities {

    public void summonBarrier(Location loc){
        EnderCrystal c = (EnderCrystal) spawnMob(loc,EntityType.ENDER_CRYSTAL);
        c.setShowingBottom(false);
        c.setInvulnerable(true);
        setIdentifierString(c,"barrier");
    }


    public static LivingEntity spawnMob(Location loc, EntityType entityType){
       return (LivingEntity) loc.getWorld().spawnEntity(loc,entityType);
    }
}
