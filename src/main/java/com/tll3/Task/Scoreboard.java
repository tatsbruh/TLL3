package com.tll3.Task;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.ScoreHelper;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class Scoreboard extends BukkitRunnable {
    private final Player p;
    private ScoreHelper scoreHelper;

    public Scoreboard(Player p) {
        this.p = p;
        scoreHelper = new ScoreHelper(p);
    }

    @Override
    public void run() {
        scoreHelper.setTitle(ChatUtils.format("&4&k|||&6The Last Life 3&4&k|||"));
        checkForEffectSlot1();
        checkForEffectSlot2();

    }

    public void checkForEffectSlot1(){
        if(Data.has(p,"curse", PersistentDataType.STRING)){
            var timeInSeconds = Data.get(p, "curse_d", PersistentDataType.INTEGER);
            var minutes = timeInSeconds / 60;
            var seconds = timeInSeconds % 60;
            var formattedTime = String.format("%02d:%02d", minutes, seconds);
            scoreHelper.setSlot(9, "&4&lCurse: &f" + formattedTime);
        }else{
            scoreHelper.removeSlot(9);
        }
    }
    public void checkForEffectSlot2(){
        if(Data.has(p,"invulnerable", PersistentDataType.STRING)){
            var timeInSeconds = Data.get(p, "invulnerable_d", PersistentDataType.INTEGER);
            var minutes = timeInSeconds / 60;
            var seconds = timeInSeconds % 60;
            var formattedTime = String.format("%02d:%02d", minutes, seconds);
            scoreHelper.setSlot(8, "&7&lInvulnerable: &f" + formattedTime);
        }else{
            scoreHelper.removeSlot(8);
        }
    }
}
