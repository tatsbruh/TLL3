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
        scoreHelper.setTitle(ChatUtils.format("&4&l☢ ⚠ ☢"));
        scoreHelper.setSlot(9,"&7▄██████▄");
        scoreHelper.setSlot(8,"&7█  &c&l01: 00  &7█");
        scoreHelper.setSlot(7,"&7▀██████▀");

    }
}
