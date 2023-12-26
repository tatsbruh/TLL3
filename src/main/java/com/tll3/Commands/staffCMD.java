package com.tll3.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.tll3.Lists.CustomEntities.CustomCreeper;
import com.tll3.Lists.CustomEntities.CustomGuardian;
import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import com.tll3.TLL3;
import com.tll3.Task.BossTask;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;

@CommandAlias("staff")
public class staffCMD extends BaseCommand {
    @Subcommand("curse")
    @CommandCompletion("seconds")
    @CommandPermission("staff.admin")
    @Description("you know.")
    public void curse(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            int i = Integer.parseInt(args[0]);
            if(i <= 0)return;
            PlayerData.addDataEffect(p,"curse",i);
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Se aplico el efecto Maldicion por " + i));
        }
    }
    @Subcommand("invulnerable")
    @CommandCompletion("seconds")
    @CommandPermission("staff.admin")
    @Description("you know 2")
    public void invulnerable(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            int i = Integer.parseInt(args[0]);
            if(i <= 0)return;
            PlayerData.addDataEffect(p,"invulnerable",i);
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Se aplico el efecto Invulnerable por " + i));
        }
    }
    @Subcommand("summon")
    @CommandCompletion("zombie_ninja|black_reaver|adaptative_spider|termite|brute_skeleton|zenith_creeper")
    @CommandPermission("staff.admin")
    @Description("summons any mob the plugin has to offer")
    public void summon(CommandSender sender,String[] args){

        if (sender instanceof Player p && args.length > 0){
            var loc = p.getLocation();
            switch (args[0].toLowerCase()){
                case "zombie_ninja" -> Entities.zNinka((Zombie) Entities.spawnMob(p.getLocation(),EntityType.ZOMBIE));
                case "black_reaver" -> Entities.blackRev((Spider) Entities.spawnMob(p.getLocation(),EntityType.SPIDER));
                case "adaptative_spider" -> Entities.adapSp((Spider) Entities.spawnMob(p.getLocation(),EntityType.SPIDER));
                case "termite" -> Entities.termite((CaveSpider) Entities.spawnMob(p.getLocation(),EntityType.CAVE_SPIDER));
                case "brute_skeleton" -> Entities.skeAd((Skeleton) Entities.spawnMob(p.getLocation(),EntityType.SKELETON));
                case "super_guardian" ->{
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomGuardian customGuardian = new CustomGuardian(worldServer);
                    customGuardian.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(customGuardian, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case "zenith_creeper" ->{
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomCreeper cC = new CustomCreeper(worldServer);
                    cC.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(cC, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                default -> p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Porfavor, Ingresa un mob valido"));
            }
        }
    }

    @Subcommand("boss")
    @CommandPermission("staff.admin")
    @Description("do the fucking boss ok")
    public void boss(CommandSender sender,String[] args){
        if (sender instanceof Player p ){
            new BossTask().runTaskTimer(TLL3.getInstance(),0L,1L);
        }
    }
    @Subcommand("day")
    @CommandPermission("staff.admin")
    @Description("sets the day")
    public void setday(CommandSender sender,String[] args){
        if (sender instanceof Player p ){
            int day = Integer.parseInt(args[0]);

            if(day < 0)return;
            GenericUtils.setDays(String.valueOf(day));
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&aSe a cambiado el dia a " + day));
        }
    }

    @Subcommand("totems")
    @CommandPermission("staff.admin")
    @Description("does the fucking totem count ok")
    public void settotems(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null){
                int count = Integer.parseInt(args[1]);
                if(count < 0)return;
                PlayerData.setTotemCount(target,count);
            }

        }
    }

    @Subcommand("debug")
    @CommandCompletion("timeformat")
    @CommandPermission("staff.admin")
    @Description("debugs a ton of shit")
    public void debug(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            switch (args[0].toLowerCase()){
                case "timeformat" -> {
                    int ticks = Integer.parseInt(args[1]);
                    if(ticks < 0)return;
                    sender.sendMessage("Hola " + GenericUtils.doTimeFormat(ticks));
                }
                default -> p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Tienes que ingresar un comando debug valido"));
            }
        }
    }
}
