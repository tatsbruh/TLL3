package com.tll3.Misc.Advancements;


import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.advancement.Advancement;
import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay;
import eu.endercentral.crazy_advancements.advancement.AdvancementFlag;
import eu.endercentral.crazy_advancements.advancement.AdvancementVisibility;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class NewAchievements {

    public static void registerAchievements(Player player){
        AdvancementDisplay display = new AdvancementDisplay(Material.END_CRYSTAL,"Un Nuevo Universo","Tu aventura comienza aquí.", AdvancementDisplay.AdvancementFrame.CHALLENGE, AdvancementVisibility.ALWAYS);
        display.setBackgroundTexture("textures/block/black_concrete.png");
        display.setX(1);
        display.setY(1);

        Advancement root = new Advancement(new NameKey("afterlife","first_steps"),display, AdvancementFlag.SHOW_TOAST,AdvancementFlag.DISPLAY_MESSAGE);


        AdvancementManager manager = new AdvancementManager(new NameKey("afterlife", "world_1"));
        manager.addAdvancement(root);
        manager.addPlayer(player);
    }
}
