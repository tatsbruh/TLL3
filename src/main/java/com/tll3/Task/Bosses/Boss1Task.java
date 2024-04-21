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

    private Skeleton skeleton;
    public Boss1Task(Skeleton skeleton){
        this.skeleton = skeleton;
    }

    private String name = ChatUtils.format("&4&lThe Wayfarer: &7");
    private static int stamina = 100; //STAMINA DE WAYFARER
    private static int attackcd = 60; //COOLDOWN ENTRE ATAQUES

    private boolean introduction = true; //ESTO CONTROLA SI ESTA EN SU ANIMACION DE INTRODUCCION

    private boolean aggresive = false; //ESTO CONTROLA SI ESTA EN MODO ATACAR


    //CONDICIONES DE ATAQUES




    @Override
    public void run() {
        if(skeleton.isDead() || !skeleton.isValid()){
            cancel();
            return;
        }



        if(introduction){
            introduction = false;
            freezeboss(skeleton);
            runEventLater(()->{
                sendUniversalMessage(name + "Ready?");
                runEventLater(() ->{
                    unfreezeboss(skeleton);
                    aggresive = true;
                },60L);
            },60L);
        }


        if(aggresive){
            Bukkit.getConsoleSender().sendMessage(String.valueOf(stamina));
            if(stamina < 100){
                stamina++;
            }
            if(attackcd > 0){
                attackcd--;
            }

            if(attackcd <= 0){
                chooseAttack(skeleton,stamina);
            }
        }





    }



    private static void chooseAttack(Skeleton s,int stamina){
        if(stamina > 0){
            if(stamina >= 50){
                int r1 = GenericUtils.getRandomValue(2);
                if(r1 == 1){
                    shootfire(s);
                }else{
                    summonsand(s);
                }
                attackcd = 120;
            }
        }
    }


    private static void shootfire(Skeleton s){
        stamina = stamina - 50;
        Fireball f = s.launchProjectile(Fireball.class);
        f.setYield(0);
    }

    private static void summonsand(Skeleton s) {
        s.setAI(false);
        stamina = stamina - 50;
        Location loc = ValidPlayer().getLocation().clone();
        FallingBlock fb = (FallingBlock) loc.getWorld().spawnFallingBlock(loc.add(0,4,0), Material.SAND,(byte) 0);
        runEventLater(()-> s.setAI(true),60L);
    }


}
