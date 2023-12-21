package com.tll3.Misc.Crafting;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.tll3.Misc.Crafting.BuildReciper.ingredientsWithAmount;
import static com.tll3.Misc.Crafting.BuildReciper.listaDeIngredientesConCantidad;

// This is from @SantiHSilva (thanks a lot!!!!!!!!!!!)
public class CraftingUtils {
    public static void checkCrafts(CraftingInventory inv){
        ItemStack result = inv.getResult();
        if(isResultItemInHashMap(result)){
            inv.setResult(null);
            for(int i = 0; i < inv.getMatrix().length; i++){
                ItemStack ingredient = inv.getMatrix()[i];
                if(ingredient == null) continue;
                ItemStack ingredientOfResult = getItemOfIngredient(result, ingredient, i);
                if(ingredientOfResult == null || !isSameItemStack(ingredientOfResult, ingredient)) return;
                if(!(ingredient.getAmount() >= getAmountOfIngredient(result, ingredient, i))){
                    inv.setResult(null);
                    return;
                }
            }
            inv.setResult(result);
        }
    }

    // function to find minimum value in an unsorted
    // list in Java using Collection
    public static Integer findMin(List<Integer> list)
    {
        if (list == null || list.size() == 0) {
            return Integer.MAX_VALUE;
        }
        List<Integer> sortedlist = new ArrayList<>(list);
        Collections.sort(sortedlist);
        return sortedlist.get(0);
    }

    private static int getMinForCraftings(ItemStack result, List<ItemStack> items, int[] invMatrix){
        List<Integer> cuantosPuedoCraftear = new ArrayList<>();
        for(int i = 0; i < invMatrix.length; i++){
            ItemStack ingredient = items.get(i);
            if(ingredient == null) continue;
            ItemStack ingredientOfResult = getItemOfIngredient(result, ingredient, i);
            if(ingredientOfResult == null) continue;
            int costeDeCrafteo = ingredientOfResult.getAmount();
            int cuantosPuedoCraftearPorEsteItem = (int) Math.floor( (double) ingredient.getAmount() / costeDeCrafteo );
            cuantosPuedoCraftear.add(cuantosPuedoCraftearPorEsteItem);
        }
        return findMin(cuantosPuedoCraftear);
    }

    public static void removeCustomItemWithShiftClick(CraftingInventory inv, Player target){
        ItemStack result = inv.getResult();
        if(result == null) return;
        if(!isResultItemInHashMap(result)) return;
        inv.setResult(null); // Para que no se cree el item en el inventario
        List<ItemStack> items = cacheItems(inv);
        int[] invMatrixCopy = new int[inv.getMatrix().length];
        int cuantosPuedoCraftear = getMinForCraftings(result, items, invMatrixCopy);
        for(int i = 0; i < invMatrixCopy.length; i++) {
            ItemStack copy = items.get(i);
            if(copy == null) continue;
            ItemStack item = getItemOfIngredient(result, copy, i);
            if(item == null) continue;
            int costeDeCrafteo = item.getAmount();
            int cuantosPuedoAgregar = getAmountForAddItem(target, result);
            if(cuantosPuedoAgregar == 0){
                target.sendMessage("No tienes espacio suficiente para agregar el item haciendo shitclick");
                inv.setResult(result);
                return;
            }

            if(cuantosPuedoAgregar < cuantosPuedoCraftear){
                cuantosPuedoCraftear = cuantosPuedoAgregar;
            }

            int remove = cuantosPuedoCraftear * costeDeCrafteo;
            if (remove <= 0)
                inv.setItem(i, null);
            else
                inv.getMatrix()[i].setAmount(copy.getAmount() - remove);
        }
        ItemStack addItem = result.clone();
        addItem.setAmount(cuantosPuedoCraftear * result.getAmount());
        target.getInventory().addItem(addItem);
    }

    public static void removeCustomItemsNoShiftClick(CraftingInventory inv){
        ItemStack result = inv.getResult();
        if(isResultItemInHashMap(result)){
            List<ItemStack> items = cacheItems(inv);
            int[] invMatrixCopy = new int[inv.getMatrix().length];
            for(int i = 0; i < invMatrixCopy.length; i++) {
                ItemStack copy = items.get(i);
                if(copy == null) continue;
                ItemStack item = getItemOfIngredient(result, copy, i);
                if(item == null) continue;
                int remove = copy.getAmount() - item.getAmount() + 1;
                if (remove <= 0)
                    inv.setItem(i, null);
                else
                    inv.getMatrix()[i].setAmount(remove);
            }
        }
    }

    /**
     * Get all ItemStacks from the inventory
     *
     * @param target Player to get array of items from inventory
     * @return An array of ItemStacks from the inventory. Individual items may be null, exclude Armor slots and OffHand.
     */

    private static List<ItemStack> getInventoryContentNoArmorAndOffHand(Player target){
        List<ItemStack> items = new ArrayList<>(Arrays.asList(target.getInventory().getContents()));
        //remove armor slots and offhand slot 36-41
        //remover los ultimos 5
        for(int i = 0; i < 5; i++){
            items.remove(items.size() - 1);
        }
        return items;
    }

    /**
     * Returns all ItemStacks from the CraftingInventory
     *
     * @param inv CraftingInventory to get items
     * @return An array of ItemStacks from the inventory. Individual items may be null.
     */

    private static List<ItemStack> cacheItems(CraftingInventory inv){
        return new ArrayList<>(Arrays.asList(inv.getMatrix()));
    }

    /**
     * Detect is same item stack (ignore amount)
     *
     * @param item1 Item to compare to item2
     * @param item2 Item to compare to item1
     * @return true if is same item stack, false if not
     */

    private static boolean isSameItemStack(ItemStack item1, ItemStack item2){
        ItemStack copy1 = item1.clone();
        ItemStack copy2 = item2.clone();
        copy1.setAmount(1);
        copy2.setAmount(1);
        //broadcast("is equals?: " + copy1.equals(copy2));
        return copy1.equals(copy2);
    }

    /**
     * Detect if item is stackeable of max 16
     *
     * @param item Item to detect
     * @return true if is stackeable of max 16, false if not
     */

    private static boolean isItemNotStackable16(ItemStack item){
        return item.getMaxStackSize() == 16;
    }

    /**
     * Detect if item is not stackeable
     * @param item Item to detect
     * @return true if is stackeable of max 1, false if not
     */

    private static boolean isNotStackable(ItemStack item){
        return item.getMaxStackSize() == 1;
    }

    /**
     * Get amount for add item depends of item stackable
     *
     * @param target Player to detect the amount of space
     *               in the inventory
     * @param itemForAdd Item to detect on the inventory
     * @return amount for add item
     */

    private static int getAmountForAddItem(Player target, ItemStack itemForAdd){
        int amount = 0;
        if(isNotStackable(itemForAdd)){
            // Si el item no es stackeable, solo buscara
            // los items de aire en el inventario
            for(ItemStack item : getInventoryContentNoArmorAndOffHand(target)){
                if(item == null) amount++;
            }
            return amount;
        }
        if(isItemNotStackable16(itemForAdd)){
            // Si el item es stackeable de 16, solo buscara
            // los items de aire en el inventario y los items
            // que coincidan con el item que se va a agregar
            // restando la cantidad de items que ya tiene
            for(ItemStack item : getInventoryContentNoArmorAndOffHand(target)){
                if(item == null) amount += 16;
                else if(isSameItemStack(item, itemForAdd)) amount += 16 - item.getAmount();
            }
            return amount;
        }
        // Si el item es stackeable de 64, solo buscara
        // los items de aire en el inventario y los items
        // que coincidan con el item que se va a agregar
        // restando la cantidad de items que ya tiene
        for(ItemStack item : getInventoryContentNoArmorAndOffHand(target)){
            if(item == null){
                amount += 64;
            }
            else if(isSameItemStack(item, itemForAdd)){
                amount += 64 - item.getAmount();
            }
        }
        return amount;
    }

    /**
     * Detects if the item is in the hashmap where it is stored
     * the items that have ingredients that are greater than 1.
     *
     * @param result Item to detect in hashmap
     * @return true if the item is in the hashmap, false if not
     */

    private static boolean isResultItemInHashMap(ItemStack result){
        return listaDeIngredientesConCantidad.contains(result);
    }

    /**
     * Detects if the ingredient is in the hashmap where it is stored
     * the items that have ingredients that are greater than 1.
     *
     * @param result Item to detect in hashmap
     * @param ingredient Ingredient to detect in hashmap
     * @param slot Slot of CraftingInventory to detect in hashmap
     *             (0-8)
     * @return item if the ingredient is in the hashmap, null if not
     */

    private static ItemStack getItemOfIngredient(ItemStack result, ItemStack ingredient, int slot){
        Set<Map.Entry<ItemStack, List<Integer>>> entry = ingredientsWithAmount.get(result).entrySet();
        for(Map.Entry<ItemStack, List<Integer>> entry1 : entry){
            if(entry1.getKey().getType().equals(ingredient.getType())){
                if(entry1.getValue().contains(slot)){
                    return entry1.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Get amount of ingredient
     *
     * @param result Item to detect in hashmap
     * @param ingredient Ingredient to detect in hashmap
     * @param slot Slot of CraftingInventory to detect in hashmap
     *             (0-8)
     * @return amount of ingredient if the ingredient is in the hashmap, -1 if not found
     */

    private static Integer getAmountOfIngredient(ItemStack result, ItemStack ingredient, int slot){
        ItemStack item = getItemOfIngredient(result, ingredient, slot);
        if(item == null || !isSameItemStack(item,ingredient)) return -1;
        return item.getAmount();
    }
}
