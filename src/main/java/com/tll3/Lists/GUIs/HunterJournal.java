package com.tll3.Lists.GUIs;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.gui.structure.Marker;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.gui.structure.Structure;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;
import xyz.xenondevs.invui.item.impl.controlitem.TabItem;
import xyz.xenondevs.invui.window.Window;

import java.awt.*;
import java.util.Arrays;

import static com.tll3.Misc.GenericUtils.getDay;

public class HunterJournal {

    public static void hunterDiary(Player player){
        Structure day0s = new Structure(
                "x x x x x x x x x",
                "x x x x A x x x x",
                "x x x x x x x x x").addIngredient('A',guiItems.nadaDia0())
                .addIngredient('x',guiItems.filler2());
        Gui day0 = Gui.empty(9,3);
        day0.applyStructure(day0s);
        Structure day1s = new Structure(
                "a b c d e f g h i",
                "j k l n m ñ o p x",
                "x x x x x x x x x")
                .addIngredient('a',guiItems.infoNinja())
                .addIngredient('b',guiItems.infoArque())
                .addIngredient('c',guiItems.infoRogue())
                .addIngredient('d',guiItems.infoFireman())
                .addIngredient('e',guiItems.infoRazor())
                .addIngredient('f',guiItems.infoTarantula())
                .addIngredient('g',guiItems.infoScarlet())
                .addIngredient('h',guiItems.infoTermite())
                .addIngredient('i',guiItems.infoTermiteCol())
                .addIngredient('j',guiItems.infoEnragedGolem())
                .addIngredient('k',guiItems.infoSkeWarden())
                .addIngredient('l',guiItems.infoDuskPha())
                .addIngredient('n',guiItems.infoRevZomb())
                .addIngredient('m',guiItems.infoRevSkele())
                .addIngredient('ñ',guiItems.infoRevSpid())
                .addIngredient('o',guiItems.infoRevCreeper())
                .addIngredient('p',guiItems.infoRevEnderman())
                .addIngredient('x',guiItems.filler2());
        Gui day1 = Gui.empty(9,3);
        day1.applyStructure(day1s);
        Structure day2s = new Structure(
                "a b c d e f g h i",
                "j k l n m ñ o p x",
                "x x x x x x x x x")
                .addIngredient('a',guiItems.infoBlazeArmor())
                .addIngredient('b',guiItems.infoWindCharger())
                .addIngredient('c',guiItems.infoUnstCreeper())
                .addIngredient('d',guiItems.infoQuantumite())
                .addIngredient('e',guiItems.infoVoidOverseer())
                .addIngredient('f',guiItems.infoLivingShrieker())
                .addIngredient('g',guiItems.infoStrayComan())
                .addIngredient('h',guiItems.infoSlimePes())
                .addIngredient('i',guiItems.infoNeonSpider())
                .addIngredient('j',guiItems.infoLangosta())
                .addIngredient('k',guiItems.infoScorchbeast())
                .addIngredient('l',guiItems.infoRustwalker())
                .addIngredient('n',guiItems.infoBrimseeker())
                .addIngredient('m',guiItems.infoLostScav())
                .addIngredient('ñ',guiItems.infoWanderingVagrant())
                .addIngredient('o',guiItems.infoBrimstoneCube())
                .addIngredient('p',guiItems.infoAshenWither())
                .addIngredient('x',guiItems.filler2());
        Gui day2 = Gui.empty(9,3);
        day2.applyStructure(day2s);

        Gui gui = TabGui.normal()
                .setStructure(
                        "0 1 2 # # # # # #",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x"
                )
                .addIngredient('0',new MyTabItem(0,0))
                .addIngredient('1',new MyTabItem(1,7))
                .addIngredient('2',new MyTabItem(2,14))
                .addIngredient('#',guiItems.filler())
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setTabs(Arrays.asList(day0,day1,day2))
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

        if(getDay() >= 0 && getDay() < 7){
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
        }else if(getDay() >= 7 && getDay() < 14){
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
        }else if(getDay() >= 14 && getDay() < 21){
            gui = Gui.normal()
                    .setStructure(
                            "# # # # R # # # #",
                            "# # a b c d e # #",
                            "# # f g h i j # #",
                            "# # k l m n ñ # #",
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
                    .addIngredient('k',guiItems.day14mission1(player))
                    .addIngredient('l',guiItems.day14mission2(player))
                    .addIngredient('m',guiItems.day14mission3(player))
                    .addIngredient('n',guiItems.day14mission4(player))
                    .addIngredient('ñ',guiItems.day14mission5(player))
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

    public static class MyTabItem extends TabItem {

        private final int tab;
        private final int dia;

        public MyTabItem(int tab,int dia) {
            super(tab);
            this.tab = tab;
            this.dia = dia;
        }

        @Override
        public ItemProvider getItemProvider(TabGui gui) {
            if (gui.getCurrentTab() == tab) {
                return new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                        .setDisplayName(ChatUtils.format("&a&lDía " + dia));
            } else {
                return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setDisplayName(ChatUtils.format("&c&lDía " + dia));
            }
        }

    }

}
