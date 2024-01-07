package com.tll3.Task.Mobs;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Misc.GenericUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.Set;

public class ArqBlockBreak extends BukkitRunnable {
    private Zombie zombie;
    public ArqBlockBreak(Zombie z){
        this.zombie = z;
    }
    @Override
    public void run() {
        Block block = zombie.getTargetBlock(null,1);
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        Block block1 = block.getLocation().subtract(0,1,0).getBlock();
        if(zombie.isDead() || !zombie.isValid()){cancel();return;}
        if(block.getType() != Material.AIR || block1.getType() != Material.AIR){
            if((getValidBlocks(block) && getValidBlocks(block1)) && zombie.getTarget() != null){
                int random = GenericUtils.getRandomValue(100);
                if(random >= 50){
                    block.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,block.getLocation(),10,1,1,1,1,block.getBlockData());
                    block1.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,block1.getLocation(),10,1,1,1,1,block1.getBlockData());
                    block.breakNaturally();
                    block1.breakNaturally();
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 1.0F);
                }else{
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 1.0F, 1.0F);
                    block.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,block.getLocation(),10,1,1,1,1,block.getBlockData());
                    block1.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK,block1.getLocation(),10,1,1,1,1,block1.getBlockData());
                }
            }

        }
    }

    public boolean getValidBlocks(Block block){
        switch (block.getType()){
            case LAVA,WATER,OBSIDIAN,BEDROCK,CHEST,FURNACE,BLAST_FURNACE,SMOKER,ENDER_CHEST,END_PORTAL_FRAME,BREWING_STAND,CHISELED_BOOKSHELF,TRAPPED_CHEST,DISPENSER,LECTERN,DROPPER: return false;
            default: return true;
        }
    }
}
