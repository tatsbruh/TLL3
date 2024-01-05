package com.tll3.Lists;

import com.tll3.Misc.Crafting.BuildReciper;
import org.bukkit.Material;

import static com.tll3.Misc.GenericUtils.getDay;

public class Recipes {

    public static void registerAllRecipes(){
        if(getDay() >= 5){
            dclRecipe();
        }
    }

    private static void dclRecipe(){
        new BuildReciper("dread_claymore",Items.dreadClaymore(),1,true)
                .setShape(
                        " F ",
                        "BNS",
                        " G "
                )
                .setIngredient('F',Items.infestedFlesh(),64)
                .setIngredient('B',Items.infestedBones(),64)
                .setIngredient('S',Items.silverStrings(),64)
                .setIngredient('G',Items.goldenGunpowder(),64)
                .setIngredient('N', Material.NETHERITE_SWORD)
                .register();
    }

}
