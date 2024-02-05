package com.tll3.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.tll3.Enviroments.Worlds;
import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.Entities;
import com.tll3.Lists.GUIs.HunterJournal;
import com.tll3.Lists.GUIs.staffGUI;
import com.tll3.Lists.GUIs.statsGUI;
import com.tll3.Lists.Items;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.Monsoon;
import com.tll3.TLL3;
import com.tll3.Task.BossTask;
import com.tll3.Task.MobRain;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
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
    //hola
    public void curse(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            int i = Integer.parseInt(args[0]);
            if(i <= 0)return;
            int i2 = Integer.parseInt(args[1]);
            if(i2 <= 0)return;
            PlayerData.addDataEffect(p,"curse",i,i2);
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
            int i2 = Integer.parseInt(args[1]);
            if(i2 <= 0)return;
            PlayerData.addDataEffect(p,"invulnerable",i,i2);
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Se aplico el efecto Invulnerable por " + i));
        }
    }
    @Subcommand("bleed")
    @CommandCompletion("seconds")
    @CommandPermission("staff.admin")
    @Description("you know 3")
    public void bleed(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            int i = Integer.parseInt(args[0]);
            if(i <= 0)return;
            int i2 = Integer.parseInt(args[1]);
            if(i2 <= 0)return;
            PlayerData.addDataEffect(p,"bleed",i,i2);
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Se aplico el efecto Desangrado por " + i));
        }
    }

    @Subcommand("exposure")
    @CommandCompletion("@players set|get")
    @CommandPermission("staff.admin")
    @Description("Sets or gets the exposure of someone")
    public void exposure(CommandSender sender, String[] args){
        if (sender instanceof Player p && args.length > 0){
            switch (args[0].toLowerCase()){
                case "set" ->{
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target != null){
                        int count = Integer.parseInt(args[2]);
                        if(count < 0)return;
                        PlayerData.setExposure(target,count);
                        p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eSe ha cambiado la exposicion de " + target.getName() + " a " + count));
                    }
                }
                case "get" ->{
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target != null){
                        p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eLa exposicion de  " + target.getName() + " es " + PlayerData.getExposure(target)));
                    }
                }
            }
        }else{
            TLL3.getInstance().console("LOL LMAO XD");
        }
    }

    @Subcommand("manager")
    @CommandCompletion("@players credits|prestige get|set ")
    @CommandPermission("staff.admin")
    @Description("Sets or gets the exposure of someone")
    public void manager(CommandSender sender, String[] args){
        if (sender instanceof Player p && args.length > 0){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eEse jugador no esta conectado o no se encuentra."));
                return;
            }
            switch (args[1].toLowerCase()){
                case "prestige" ->{
                    switch (args[2].toLowerCase()){
                        case "get" ->{
                            int i = Integer.parseInt(String.valueOf(PlayerData.getPrestige(target)));
                            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&ePrestigio de " + target.getName() + ": " + i));
                        }
                        case "set" ->{
                            int i = Integer.parseInt(args[3]);
                            if(i < 0)return;
                            PlayerData.setPrestige(target,i);
                            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eSe ha cambiado el prestigio de " + target.getName() + " a " + i));
                        }
                    }
                }
                case "credits" ->{
                    switch (args[2].toLowerCase()){
                        case "get" ->{
                            int i = Integer.parseInt(String.valueOf(PlayerData.getCredits(target)));
                            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eCreditos de " + target.getName() + ": " + i));
                        }
                        case "set" ->{
                            int i = Integer.parseInt(args[3]);
                            if(i < 0)return;
                            PlayerData.setCredits(target,i);
                            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eSe ha cambiado los creditos de " + target.getName() + " a " + i));
                        }
                    }
                }
            }
        }else{
            TLL3.getInstance().console("LOL LMAO XD");
        }
    }

    @Subcommand("items")
    @CommandPermission("staff.admin")
    @Description("get your items ok its ok")
    public void give(CommandSender sender,String[] args){
        if (sender instanceof Player p){
            staffGUI.itemsgui(p);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,10.0F,-2.0F);
        }
    }

    @Subcommand("monsoon")
    @CommandPermission("staff.admin")
    @Description("starts or ends a monsoon fuck tg")
    public void monsoon(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            switch (args[0].toLowerCase()){
                case "start" -> Bukkit.getPluginManager().callEvent(new Monsoon.StartMonsoon(Monsoon.StartMonsoon.Cause.COMMAND));
                case "stop" -> Bukkit.getPluginManager().callEvent(new Monsoon.StopMonsoon(Monsoon.StopMonsoon.Cause.COMMAND));
            }
        }
    }

    @Subcommand("dimension")
    @CommandCompletion("overworld|nether|end|wasteyard")
    @CommandPermission("staff.admin")
    @Description("teleports you to the dimension given do you understand")
    public void teleport(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            switch (args[0].toLowerCase()){
                case "overworld" -> p.teleport(Worlds.getOverworld().getSpawnLocation());
                case "nether" -> p.teleport(Worlds.getNether().getSpawnLocation());
                case "end" -> p.teleport(Worlds.getEnd().getSpawnLocation());
                case "wasteyard" -> p.teleport(Worlds.getWasteyard().getSpawnLocation());
                default -> p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Ingresa una Dimension valida"));
            }
        }
    }


    @Subcommand("summon")
    @CommandCompletion("zombie_ninja|zombie_arq|black_reaver|scarlet_leech|termite|colony_termite|rogue_skeleton|firemancer|razorback|rev_zombie|rev_skeleton|rev_creeper|rev_spider|rev_enderman")
    @CommandPermission("staff.admin")
    @Description("summons any mob the plugin has to offer")
    public void summon(CommandSender sender,String[] args){

        if (sender instanceof Player p && args.length > 0){
            var loc = p.getLocation();
            switch (args[0].toLowerCase()){
                case "zombie_ninja" -> Entities.zNinka((Zombie) Entities.spawnMob(p.getLocation(),EntityType.ZOMBIE));
                case "zombie_arq" -> Entities.zArqueo((Zombie) Entities.spawnMob(p.getLocation(),EntityType.ZOMBIE));
                case "black_reaver" -> Entities.blackRev((Spider) Entities.spawnMob(p.getLocation(),EntityType.SPIDER));
                case "scarlet_leech" -> Entities.adapSp((Spider) Entities.spawnMob(p.getLocation(),EntityType.SPIDER));
                case "termite" -> Entities.termite((CaveSpider) Entities.spawnMob(p.getLocation(),EntityType.CAVE_SPIDER));
                case "colony_termite" -> Entities.csTerCol((CaveSpider) Entities.spawnMob(p.getLocation(),EntityType.CAVE_SPIDER));
                case "rogue_skeleton" -> Entities.skeAd((Skeleton) Entities.spawnMob(p.getLocation(),EntityType.SKELETON));
                case "firemancer" -> Entities.skeFi((Skeleton) Entities.spawnMob(p.getLocation(),EntityType.SKELETON));
                case "razorback" -> Entities.skeRz((Skeleton) Entities.spawnMob(p.getLocation(),EntityType.SKELETON));
                case "rev_zombie" -> Entities.revZombie((Zombie) Entities.spawnMob(p.getLocation(),EntityType.ZOMBIE));
                case "rev_skeleton" -> Entities.revSkeleton((Skeleton) Entities.spawnMob(p.getLocation(),EntityType.SKELETON));
                case "rev_spider" -> Entities.revSpider((Spider) Entities.spawnMob(p.getLocation(),EntityType.SPIDER));
                case "rev_creeper" -> Entities.revCreeper((Creeper) Entities.spawnMob(p.getLocation(),EntityType.CREEPER));
                case "rev_enderman" -> Entities.revEnderman((Enderman) Entities.spawnMob(p.getLocation(),EntityType.ENDERMAN));


                default -> p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Porfavor, Ingresa un mob valido"));
            }
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
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&aSe ha cambiado el dia al dia " + day));
        }
    }



    @Subcommand("totems")
    @CommandPermission("staff.admin")
    @CommandCompletion("@players numero")
    @Description("does the fucking totem count ok")
    public void settotems(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null){
                int count = Integer.parseInt(args[1]);
                if(count < 0)return;
                PlayerData.setTotemCount(target,count);
                p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eSe ha cambiado la cantidad de totems usados de " + target.getName() + " a " + count));
            }

        }
    }



    @Subcommand("debug")
    @CommandCompletion("natural|diary|missions|stats|get_stats|reset_stats_to_default")
    @CommandPermission("staff.admin")
    @Description("debugs a ton of shit")
    public void debug(CommandSender sender,String[] args){
        if (sender instanceof Player p && args.length > 0){
            switch (args[0].toLowerCase()){
                case "natural" -> {
                    MobRain.initMobs(p.getLocation());
                }
                case "diary" -> HunterJournal.hunterDiary((Player) sender);
                case "missions" -> HunterJournal.hunterHuntsXDLOLLMAO((Player) sender);
                case "stats" -> statsGUI.showstatsupgrade((Player) sender);
                case "get_stats" ->{
                    p.sendMessage("Daño " + p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue());
                    p.sendMessage("Armor " + p.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue());
                    p.sendMessage("Speed " + p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
                }
                case "reset_stats_to_default" ->{
                    PlayerData.setExtraHealth(p,0);
                    p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1.0);
                    p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0.0);
                    p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.10000000149011612);
                }
                default -> p.sendMessage(ChatUtils.format(ChatUtils.prefix + "Tienes que ingresar un comando debug valido"));
            }
        }
    }
}
