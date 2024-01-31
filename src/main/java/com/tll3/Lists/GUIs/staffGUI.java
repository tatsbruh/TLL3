package com.tll3.Lists.GUIs;

import com.tll3.Lists.Armors;
import com.tll3.Lists.Items;
import com.tll3.Misc.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.window.Window;

public class staffGUI {
    public static void itemsgui(Player player){
        var gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# 1 2 3 4 5 6 7 #",
                        "# 8 9 0 a b c d #",
                        "# e f g h i j k #",
                        "# . . . . . . . #",
                        "# # # # # # # # #")
                .addIngredient('#',guiItems.filler())
                .addIngredient('1', new giveItem(Items.dreadClaymore()))
                .addIngredient('2',new giveItem(Items.dreadBow()))
                .addIngredient('3',new giveItem(Items.infestedFlesh()))
                .addIngredient('4',new giveItem(Items.infestedBones()))
                .addIngredient('5',new giveItem(Items.goldenGunpowder()))
                .addIngredient('6',new giveItem(Items.silverStrings()))
                .addIngredient('7',new giveItem(Items.revenantPearl()))
                .addIngredient('8',new giveItem(Items.miracleFruit()))
                .addIngredient('9',new giveItem(Armors.drHelm()))
                .addIngredient('0',new giveItem(Armors.drChest()))
                .addIngredient('a',new giveItem(Armors.drLegs()))
                .addIngredient('b',new giveItem(Armors.drBoots()))
                .addIngredient('c',new giveItem(Items.invultome()))
                .addIngredient('d',new giveItem(Items.brimstoneTrident()))
                .addIngredient('e',new giveItem(Items.vulcanitePickaxe()))
                .addIngredient('f',new giveItem(Items.vulcaniteAxe()))
                .addIngredient('g',new giveItem(Items.vulcaniteShovel()))
                .addIngredient('h',new giveItem(Items.vulcaniteHoe()))
                .addIngredient('i',new giveItem(Items.vortexExtinction()))
                .build();
        var window = Window.single()
                .setViewer(player)
                .setTitle(ChatUtils.format("&8Items del server"))
                .setGui(gui)
                .build();
        window.open();
    }


    public static class giveItem extends AbstractItem {
        private final ItemStack itemStack;
        public giveItem(ItemStack itemStack){
            this.itemStack = itemStack;
        }

        @Override
        public ItemProvider getItemProvider() {
            return new ItemBuilder(itemStack);
        }

        @Override
        public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
            if(clickType == ClickType.LEFT) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,10.0F,-1.0F);
                Items.addInventory(player, itemStack);
            }
            notifyWindows();
        }

    }
}
