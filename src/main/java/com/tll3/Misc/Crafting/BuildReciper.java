package com.tll3.Misc.Crafting;

import com.tll3.TLL3;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.*;

// This is from @SantiHSilva (thanks a lot!!!!!!!!!!!)
public class BuildReciper {
    protected ShapedRecipe recipe;
    protected boolean haveAmount;
    public static HashMap<ItemStack, HashMap<ItemStack, List<Integer>>> ingredientsWithAmount = new HashMap<>();
    public static List<ItemStack> listaDeIngredientesConCantidad = new ArrayList<>();

    //Temp hashmap
    protected HashMap<ItemStack, List<Integer>> materialesParaIngrediente;

    public BuildReciper(String id, ItemStack item, boolean useMoreAmount) {
        this.haveAmount = useMoreAmount;

        // Si se usa mas cantidad, se crea un hashmap para guardar los materiales
        // y se crea una lista para explorar los items mas rapido.

        if(this.haveAmount){
            this.materialesParaIngrediente = new HashMap<>();
            listaDeIngredientesConCantidad.add(item);
        }

        this.recipe = new ShapedRecipe(new NamespacedKey(TLL3.getInstance(), id), item);
    }

    public BuildReciper(String id, ItemStack item, int amount, boolean useMoreAmount){
        this.haveAmount = useMoreAmount;

        // Si se usa mas cantidad, se crea un hashmap para guardar los materiales
        // y se crea una lista para explorar los items mas rapido.

        if(this.haveAmount){
            this.materialesParaIngrediente = new HashMap<>();
            listaDeIngredientesConCantidad.add(item);
        }

        if(amount > 64) amount = 64; // No se puede poner mas de 64 items en un slot
        item.setAmount(amount);
        this.recipe = new ShapedRecipe(new NamespacedKey(TLL3.getInstance(), id), item);
    }

    public BuildReciper(String id, Material material, int amount, boolean useMoreAmount){
        this.haveAmount = useMoreAmount;

        // Si se usa mas cantidad, se crea un hashmap para guardar los materiales
        // y se crea una lista para explorar los items mas rapido.

        if(this.haveAmount){
            this.materialesParaIngrediente = new HashMap<>();
            listaDeIngredientesConCantidad.add(new ItemStack(material, amount));
        }

        if(amount > 64) amount = 64; // No se puede poner mas de 64 items en un slot
        ItemStack item = new ItemStack(material, amount);
        this.recipe = new ShapedRecipe(new NamespacedKey(TLL3.getInstance(), id), item);
    }

    private static String check(String shape){
        // Esto es para que no haya problemas con los espacios en blanco
        // en la forma de la receta, ya que si no se pone esto, el juego
        // no reconoce la receta.
        switch(shape.length()){
            case 0 -> shape = "   ";
            case 1 -> shape = shape + "  ";
            case 2 -> shape = shape + " ";
            default -> {
                return shape;
            }
        }
        return shape;
    }

    public BuildReciper setShape(String top, String mid, String bot) {
        this.recipe.shape(check(top), check(mid), check(bot));
        return this;
    }

    public BuildReciper setIngredient(char key, ItemStack item) {

        if(this.haveAmount){
            this.setIngredient(key, item, 1);
            return this;
        }

        this.recipe.setIngredient(key, item);
        return this;
    }

    public BuildReciper setIngredient(char key, ItemStack item, int amount) {

        if(!this.haveAmount){
            this.setIngredient(key, item);
            return this;
        }

        item.setAmount(amount);
        this.recipe.setIngredient(key, item);
        List<Integer> cacheSlots = new ArrayList<>();
        for (int i = 0; i < this.recipe.getShape().length; i++) {
            String shape = this.recipe.getShape()[i];
            for (int j = 0; j < shape.length(); j++) {
                if (shape.charAt(j) == key) {
                    cacheSlots.add(i * 3 + j);
                }
            }
        }
        materialesParaIngrediente.put(item, cacheSlots);
        return this;
    }

    public BuildReciper setIngredient(char key, Material item, int amount) {

        if(!this.haveAmount){
            this.setIngredient(key, item);
            return this;
        }

        ItemStack itemStack = new ItemStack(item, amount);
        itemStack.setAmount(amount);
        this.recipe.setIngredient(key, itemStack);
        List<Integer> cacheSlots = new ArrayList<>();
        for (int i = 0; i < this.recipe.getShape().length; i++) {
            String shape = this.recipe.getShape()[i];
            for (int j = 0; j < shape.length(); j++) {
                if (shape.charAt(j) == key) {
                    cacheSlots.add(i * 3 + j);
                }
            }
        }
        materialesParaIngrediente.put(itemStack, cacheSlots);
        return this;
    }

    public BuildReciper setIngredient(char key, Material material) {

        if(this.haveAmount){
            this.setIngredient(key, new ItemStack(material), 1);
            return this;
        }

        this.recipe.setIngredient(key, new ItemStack(material));
        return this;
    }

    public void register() {
        // Add recipe to server
        TLL3.getInstance().getServer().addRecipe(this.recipe);
        // Add recipe to hashmap
        if(this.haveAmount){
            ingredientsWithAmount.put(this.recipe.getResult(), this.materialesParaIngrediente);
        }
    }
}
