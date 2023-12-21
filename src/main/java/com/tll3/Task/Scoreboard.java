package com.tll3.Task;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.Data;
import com.tll3.Misc.ScoreHelper;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class Scoreboard extends BukkitRunnable {
    private final Player p;
    private ScoreHelper scoreHelper;

    public Scoreboard(Player p) {
        this.p = p;
    }

    @Override
    public void run() {
        scoreHelper.setTitle(ChatUtils.format("&6&lThe Last Life 3"));
        scoreHelper.setSlot(9,"§");


    }

    public void checkForEffect(){
        if(Data.has(p,"curse", PersistentDataType.STRING)){
            var time = Data.get(p,"curse_d",PersistentDataType.INTEGER);
            scoreHelper.setSlot(8,"&4&lCurse: &f" + time);
        }else{
            scoreHelper.removeSlot(8);
        }
    }

}
