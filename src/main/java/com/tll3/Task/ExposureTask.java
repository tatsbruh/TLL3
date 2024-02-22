package com.tll3.Task;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import net.minecraft.world.entity.animal.EntityPanda;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ExposureTask extends BukkitRunnable {
    private final Player player;

    int darkness = 0;
    int glowing = 0;
    int light = 0;
    int glowingdrain = 0;
    public ExposureTask(Player p){
        this.player = p;
    }

    @Override
    public void run() {
        if(player.getGameMode() == GameMode.SPECTATOR)return;
        var exp = PlayerData.getExposure(player);
        double range = 0;
        int newexpdrain;
        if(GenericUtils.getDay() >= 21){
            if(GenericUtils.getMonsoon_active().equalsIgnoreCase("true")){
                newexpdrain = 2;
            }else{
                newexpdrain = 1;
            }
        }else{
            newexpdrain = 1;
        }
        String exposure;

        if(exp >= 155 && exp <= 200){
            exposure = ChatUtils.format("&8&l[#bf0000███☠███&8&l] &7| &4&l" + exp + "%");
            range = 60.0;
        }else if(exp >= 95 && exp <= 155){
            exposure = ChatUtils.format("&8&l[#ff5454███☢███&8&l] &7| &4&l" + exp + "%");
            range = 40.0;
        }else if(exp >= 35 && exp <= 95){
            exposure = ChatUtils.format("&8&l[#f6ff91███☹███&8&l] &7| &6&l" + exp + "%");
            range = 20.0;
        }else{
            exposure = ChatUtils.format("&8&l[#abffc1███☺███&8&l] &7| &2&l" + exp + "%");
        }

        player.sendActionBar(exposure);
        if(exp >= 35){
            if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)return;
            player.getNearbyEntities(range, range, range).forEach(entity -> {
                if (entity instanceof Enemy e ) {
                    if(e instanceof Enderman || e instanceof PigZombie)return;
                    Mob m = (Mob)e;
                    if(m.getTarget() == null){
                        m.setTarget(player);
                    }
                }
            });
        }

        if(player.getGameMode() == GameMode.SURVIVAL){
            byte luz = player.getLocation().subtract(0,1,0).getBlock().getState().getLightLevel();
            if(luz <= 0){
                if(darkness < 200){
                    darkness++;
                }else{
                    darkness = 0;
                    PlayerData.sumExp(player,newexpdrain);
                }
            }else{
                if(light < 200){
                    light++;
                }else{
                    light = 0;
                    PlayerData.restExp(player,1);
                }
            }
            if(player.hasPotionEffect(PotionEffectType.GLOWING)){
                switch (player.getPotionEffect(PotionEffectType.GLOWING).getAmplifier()){
                    case 0 -> glowingdrain = 50;
                    case 1 -> glowingdrain = 35;
                    case 2 -> glowingdrain = 25;
                    case 3 -> glowingdrain = 15;
                    case 4 -> glowingdrain = 5;
                    default -> glowingdrain = 50;
                }
                if(glowing < glowingdrain){
                    glowing++;
                }else{
                    glowing = 0;
                    PlayerData.sumExp(player,1);
                }
            }
        }

    }



}
