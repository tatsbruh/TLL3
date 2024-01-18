package com.tll3.Task;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.TLL3;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.inventoryaccess.util.ReflectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;


public class TabManager extends BukkitRunnable {
    private final Player p;
    public TabManager(Player p){
        this.p = p;
    }
    @Override
    public void run() {
        String footerinquestion1 = "";
        String footerinquestion2 = "";
        String footerinquestion3 = "";


        if(Data.has(p,"curse", PersistentDataType.STRING)){
            var timeInSeconds = Data.get(p, "curse_d", PersistentDataType.INTEGER);
            var minutes = timeInSeconds / 60;
            var seconds = timeInSeconds % 60;
            var formattedTime = String.format("%02d:%02d", minutes, seconds);

            var effect = Data.get(p,"curse_e",PersistentDataType.INTEGER);
            String lol = "";
            switch (effect){
                case 1 ->lol = "";
                case 2 -> lol = " II";
                case 3 -> lol = " III";
                case 4 -> lol = " IV";
                default -> lol = " I";
            }
            footerinquestion1 =  ChatUtils.format("&4&lPánico" + lol +": &f" + formattedTime);
        }else{
            footerinquestion1 = "";
        }
        if(Data.has(p,"invulnerable", PersistentDataType.STRING)){
            var timeInSeconds = Data.get(p, "invulnerable_d", PersistentDataType.INTEGER);
            var minutes = timeInSeconds / 60;
            var seconds = timeInSeconds % 60;
            var formattedTime = String.format("%02d:%02d", minutes, seconds);
           footerinquestion2 =  ChatUtils.format("&7&lInvulnerable: &f" + formattedTime);
        }else{
            footerinquestion2 = "";
        }
        if(Data.has(p,"bleed", PersistentDataType.STRING)){
            var timeInSeconds = Data.get(p, "bleed_d", PersistentDataType.INTEGER);
            var minutes = timeInSeconds / 60;
            var seconds = timeInSeconds % 60;
            var formattedTime = String.format("%02d:%02d", minutes, seconds);
            footerinquestion3 = ChatUtils.format("&cDesangrado: &f" + formattedTime);
        }else{
            footerinquestion3 = "";
        }

        p.setPlayerListHeader(ChatUtils.format("&8&kaA   #fb6c37A#fc8c31f#fcad2bt#fdcd24e#fded1er#fdcd24l#fcad2bi#fc8c31f#fb6c37e   &8&kAa"));
        p.setPlayerListFooter(footerinquestion1 + "  " + footerinquestion2 + "  " + footerinquestion3);
    }

}
