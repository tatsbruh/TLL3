package com.tll3.Misc.Advancements;



import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class NewAchievements {
    public static void awardAdvancement(Player p, NamespacedKey id) {
        AdvancementProgress progress = p.getAdvancementProgress(Bukkit.getAdvancement(id));
        for(String criteria : progress.getRemainingCriteria())
            progress.awardCriteria(criteria);
    }
}
