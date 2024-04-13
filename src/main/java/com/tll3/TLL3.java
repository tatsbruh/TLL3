package com.tll3;

import co.aikar.commands.PaperCommandManager;
import com.tll3.Commands.staffCMD;
import com.tll3.Listeners.*;
import com.tll3.Lists.Recipes;
import com.tll3.Misc.Advancements.NewAchievements;
import com.tll3.Misc.Crafting.CraftingEvents;
import com.tll3.Misc.Files.ConfigData;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.World.Biomes;
import com.tll3.Task.MobRain;
import me.outspending.biomesapi.nms.NMSHandler;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.BiomeBase;
import org.bukkit.Bukkit;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static com.tll3.Enviroments.Worlds.loadWorlds;

//TODO Please, promise me that you won't make a mess just like last time, okay? - 17/12/23
public final class TLL3 extends JavaPlugin {
    private static TLL3 plugin;
    public static TLL3 getInstance(){return plugin;}
    @Override
    public void onEnable() {
        plugin = this;
        Biomes.registerAllBiomes();
        console("Plugin activado correctamente");
        loadWorlds();
        loadListeners();
        loadCommands();
        Recipes.registerAllRecipes();
        if(GenericUtils.getDay() >= 21){
            new MobRain().runTaskTimer(this,0L,1L);
        }
        if(Objects.equals(GenericUtils.getMonsoon_active(), "true")){
            MonsoonListeners.createBossBar();
            MonsoonListeners.addAllToBossbar();
        }
    }
    @Override
    public void onDisable() {
        if(MonsoonListeners.TaskBossBarID != null){
            Bukkit.getScheduler().cancelTask(MonsoonListeners.TaskBossBarID);
            MonsoonListeners.removeAllBossbar();
        }
        console("Plugin desactivado correctamente");
    }
    public void console(String s){
        getLogger().info(s);
    }
    private void registerListeners(Listener... listeners){
        for(Listener listener : listeners){
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
    public void loadListeners(){
        registerListeners(
                new CraftingEvents(),
                new GenericPlayerListeners(),
                new PlayerDeathListeners(),
                new GlobalListeners(),
                new GenericEntityListeners(),
                new EntityNaturalSpawn(),
                new MonsoonListeners(),
                new EntityDrops(),
                new MissionListeners(),
                new InventoryLockListeners()
        );
    }
    public void loadCommands(){
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new staffCMD());
    }



}
