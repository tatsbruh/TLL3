package com.tll3.Task.Bosses;

import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.SplittableRandom;

import static com.tll3.Task.Bosses.BossUtils.*;

public class Boss1Task extends BukkitRunnable {

    public enum WayfarerPhases{
        INTRO,PHASE_1,TRANSITION,PHASE_2
    }

    private Skeleton skeleton;
    private WayfarerPhases phase = WayfarerPhases.INTRO;
    public Boss1Task(Skeleton skeleton){
        this.skeleton = skeleton;
    }

    private String name = ChatUtils.format("&4&lThe Wayfarer: &7");

    private boolean introduction = false;
    private boolean introduction2 = false;
    private boolean sandattack = false;
    private int sandattacktime = 200;
    private boolean canmakeattack = true;



    //CONDICIONES DE ATAQUES


    @Override
    public void run() {
        if (skeleton.isDead() || !skeleton.isValid()) {
            cancel();
            return;
        }
        Bukkit.getConsoleSender().sendMessage(String.valueOf(phase));
        if (!introduction && phase == WayfarerPhases.INTRO) {
            introduction = true;
            freezeboss(skeleton);
            runEventLater(() -> {
                sendUniversalMessage(name + "Ready?");
                runEventLater(() -> {
                    unfreezeboss(skeleton);
                    phase = WayfarerPhases.PHASE_1;
                }, 60L);
            }, 60L);
        }

        if(phase == WayfarerPhases.PHASE_1){
            if(canmakeattack){
                if(sandattacktime > 0){
                    sandattacktime--;
                }else{
                    tickSandAttack();
                }
            }
        }
    }

    public void tickSandAttack(){
        freezeboss(skeleton);
        canmakeattack = false;
        runEventLater(()->{
            for(Player p : skeleton.getLocation().getNearbyPlayers(8,8,8)){
                Location l = p.getLocation().clone();
                FallingBlock sand = l.getWorld().spawnFallingBlock(l.add(0,5,0),Material.SAND,(byte) 0);
                sand.setCustomName("Necromancer Orb");
                sand.setDropItem(false);
                sand.setGravity(false);

                runEventLater(() ->{
                    sand.setGravity(true);
                },60L);
            }
            runEventLater(() ->{
                unfreezeboss(skeleton);
                canmakeattack = true;
                sandattacktime = 200;
            },90L);
        },60L);
    }
}
