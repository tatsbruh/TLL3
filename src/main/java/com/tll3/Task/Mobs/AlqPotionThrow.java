package com.tll3.Task.Mobs;

import com.tll3.Misc.GenericUtils;
import org.bukkit.Material;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AlqPotionThrow extends BukkitRunnable {
    private PigZombie pigZombie;
    public AlqPotionThrow(PigZombie pigZombie){
        this.pigZombie = pigZombie;
    }
    int potioninterval = 0;
    @Override
    public void run() {
        if(pigZombie.isDead() || !pigZombie.isValid()){cancel();return;}
        if(pigZombie.getTarget() != null){
            Player p = (Player) pigZombie.getTarget();
            if(p.getLocation().distanceSquared(pigZombie.getLocation()) <= Math.pow(6, 2)) {
                if (potioninterval < 100) {
                    potioninterval++;
                } else {
                    chooserandomPotion(pigZombie);
                    potioninterval = 0;
                }
            }
        }
    }


    public static void chooserandomPotion(PigZombie z){
        var random = GenericUtils.getRandomValue(3);
        switch (random) {
            case 0 -> {
                ItemStack p = new ItemStack(Material.LINGERING_POTION);
                PotionMeta pt = (PotionMeta) p.getItemMeta();
                pt.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 200, 4, true, false, true), true);
                p.setItemMeta(pt);
                ThrownPotion t = z.launchProjectile(ThrownPotion.class);
                t.setItem(p);
            }
            case 1 ->{
                ItemStack p = new ItemStack(Material.LINGERING_POTION);
                PotionMeta pt = (PotionMeta) p.getItemMeta();
                pt.addCustomEffect(new PotionEffect(PotionEffectType.HARM,1,2,true,false,true),true);
                p.setItemMeta(pt);
                ThrownPotion t = z.launchProjectile(ThrownPotion.class);
                t.setItem(p);
            }
            case 2 ->{
                ItemStack p = new ItemStack(Material.LINGERING_POTION);
                PotionMeta pt = (PotionMeta) p.getItemMeta();
                pt.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING,200,2,true,false,true),true);
                p.setItemMeta(pt);
                ThrownPotion t = z.launchProjectile(ThrownPotion.class);
                t.setItem(p);
            }
        }
    }
}
