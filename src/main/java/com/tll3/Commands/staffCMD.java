package com.tll3.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.TLL3;
import com.tll3.Task.BossTask;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;

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
    @CommandCompletion("void_overseer|railgunner")
    @CommandPermission("staff.admin")
    @Description("summons any mob the plugin has to offer")
    public void summon(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            switch (args[0].toLowerCase()){
                case "void_overseer" -> Entities.voidOver((Skeleton) Entities.spawnMob(p.getLocation(), EntityType.SKELETON));
                case "railgunner" -> Entities.railGun((WitherSkeleton) Entities.spawnMob(p.getLocation(),EntityType.WITHER_SKELETON));
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
}
