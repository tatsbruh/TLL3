package com.tll3.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
}
