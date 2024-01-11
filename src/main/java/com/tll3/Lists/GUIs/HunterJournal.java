package com.tll3.Lists.GUIs;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

import java.awt.*;

public class HunterJournal {

    public static void hunterDiary(Player player){
        var gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# 1 2 3 4 5 6 7 #",
                        "# 8 9 a b c d e #",
                        "# f g h . . . . #",
                        "# . . . . . . . #",
                        "# # # # # # # # #")
                .addIngredient('#',guiItems.filler())
                .addIngredient('1',guiItems.infoNinja())
                .addIngredient('2',guiItems.infoArque())
                .addIngredient('3',guiItems.infoRogue())
                .addIngredient('4',guiItems.infoFireman())
                .addIngredient('5',guiItems.infoRazor())
                .addIngredient('6',guiItems.infoTarantula())
                .addIngredient('7',guiItems.infoScarlet())
                .addIngredient('8',guiItems.infoTermite())
                .addIngredient('9',guiItems.infoTermiteCol())
                .addIngredient('a',guiItems.infoEnragedGolem())
                .addIngredient('b',guiItems.infoSkeWarden())
                .addIngredient('c',guiItems.infoDuskPha())
                .addIngredient('d',guiItems.infoRevZomb())
                .addIngredient('e',guiItems.infoRevSkele())
                .addIngredient('f',guiItems.infoRevSpid())
                .addIngredient('g',guiItems.infoRevCreeper())
                .addIngredient('h',guiItems.infoRevEnderman())
                .build();
        var window = Window.single()
                .setViewer(player)
                .setTitle(ChatUtils.format("&c&lAlmanaque de Hunter"))
                .setGui(gui)
                .build();
        window.open();
    }
    public static void hunterHuntsXDLOLLMAO(Player player){
        Gui gui;

        if(GenericUtils.getDay() >= 0 && GenericUtils.getDay() < 7){
            gui = Gui.normal()
                    .setStructure(
                            "# # # # R # # # #",
                            "# # a b c d e # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #")
                    .addIngredient('#',guiItems.filler2())
                    .addIngredient('R',guiItems.playerheadMission(player))
                    .addIngredient('a',guiItems.day0mission1(player))
                    .addIngredient('b',guiItems.day0mission2(player))
                    .addIngredient('c',guiItems.day0mission3(player))
                    .addIngredient('d',guiItems.day0mission4(player))
                    .addIngredient('e',guiItems.day0mission5(player))
                    .build();
        }else if(GenericUtils.getDay() >= 7 && GenericUtils.getDay() < 14){
            gui = Gui.normal()
                    .setStructure(
                            "# # # # R # # # #",
                            "# # a b c d e # #",
                            "# # f g h i j # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #")
                    .addIngredient('#',guiItems.filler2())
                    .addIngredient('R',guiItems.playerheadMission(player))
                    .addIngredient('a',guiItems.day0mission1(player))
                    .addIngredient('b',guiItems.day0mission2(player))
                    .addIngredient('c',guiItems.day0mission3(player))
                    .addIngredient('d',guiItems.day0mission4(player))
                    .addIngredient('e',guiItems.day0mission5(player))
                    .addIngredient('f',guiItems.day7mission1(player))
                    .addIngredient('g',guiItems.day7mission2(player))
                    .addIngredient('h',guiItems.day7mission3(player))
                    .addIngredient('i',guiItems.day7mission4(player))
                    .addIngredient('j',guiItems.day7mission5(player))
                    .build();
        }else{
            gui = Gui.normal()
                    .setStructure(
                            "# # # # R # # # #",
                            "# # a b c d e # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #",
                            "# # . . . . . # #")
                    .addIngredient('#',guiItems.filler2())
                    .addIngredient('R',guiItems.playerheadMission(player))
                    .build();
        }
        var window = Window.single()
                .setViewer(player)
                .setTitle(ChatUtils.format("#96000fMisiones de Hunter"))
                .setGui(gui)
                .build();
        window.open();
    }
}
