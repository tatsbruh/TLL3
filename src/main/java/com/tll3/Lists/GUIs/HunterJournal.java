package com.tll3.Lists.GUIs;

import xyz.xenondevs.invui.gui.Gui;

public class HunterJournal {

    public void s(){
        var gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# . . . . . . . #",
                        "# . . . . . . . #",
                        "# . . . . . . . #",
                        "# . . . . . . . #",
                        "# # # # # # # # #")
                .addIngredient('#',guiItems.filler())
                .build();
    }
}
