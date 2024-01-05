package com.tll3.Lists;

import com.tll3.Misc.Crafting.BuildReciper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static com.tll3.Misc.GenericUtils.getDay;

public class Recipes {

    public static void registerAllRecipes(){
        if(getDay() >= 5){
            dclRecipe();
            dlbRecipe();
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
                .setIngredient('N', new ItemStack(Material.NETHERITE_SWORD))
                .register();
    }
    private static void dlbRecipe(){
        new BuildReciper("dread_bow",Items.dreadBow(),1,true)
                .setShape(
                        " F ",
                        "BNS",
                        " G "
                )
                .setIngredient('F',Items.infestedFlesh(),64)
                .setIngredient('B',Items.infestedBones(),64)
                .setIngredient('S',Items.silverStrings(),64)
                .setIngredient('G',Items.goldenGunpowder(),64)
                .setIngredient('N', new ItemStack(Material.BOW))
                .register();
    }

}
