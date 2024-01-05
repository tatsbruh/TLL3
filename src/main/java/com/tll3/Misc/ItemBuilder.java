package com.tll3.Misc;

import com.tll3.Misc.DataManager.Data;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder {
    protected ItemStack is;

    protected ItemMeta im;
    protected LeatherArmorMeta leather;

    public ItemBuilder() {
        this(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilder(ItemStack itemStack) {
        this.is = itemStack;
    }

    public ItemBuilder setAmount(int amount) {
        this.is.setAmount(amount);
        return this;
    }
    public ItemBuilder setDurability(int durability) {
        this.is.setDurability((short) durability);
        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        this.im = this.is.getItemMeta();
        this.im.setCustomModelData(data);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setID(String id) {
        this.im = this.is.getItemMeta();
        this.im.getPersistentDataContainer().set(new NamespacedKey(GenericUtils.getPlugin(), "id"), PersistentDataType.STRING, id);
        this.is.setItemMeta(this.im);
        return this;
    }

    public boolean hasID(String id) {
        return this.is.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(GenericUtils.getPlugin(), "id"), PersistentDataType.STRING);
    }

    public ItemBuilder setUnbreakable(boolean b) {
        this.im = this.is.getItemMeta();
        this.im.setUnbreakable(b);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        this.im = this.is.getItemMeta();
        this.im.setDisplayName(ChatUtils.format(name));
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.im = this.is.getItemMeta();
        if (this.im instanceof EnchantmentStorageMeta) {
            ((EnchantmentStorageMeta) this.im).addStoredEnchant(enchantment, level, true);
        } else {
            this.im.addEnchant(enchantment, level, true);
        }
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addEnchants(Map<Enchantment, Integer> enchantments) {
        this.im = this.is.getItemMeta();
        if (!enchantments.isEmpty())
            for (Enchantment ench : enchantments.keySet())
                this.im.addEnchant(ench, enchantments.get(ench), true);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemflag) {
        this.im = this.is.getItemMeta();
        this.im.addItemFlags(itemflag);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.im = this.is.getItemMeta();
        this.im.setLore(lore);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.im = this.is.getItemMeta();
        List<String> finalLore = new ArrayList<>();
        for (String s : lore) finalLore.add(ChatUtils.format(s));

        this.im.setLore(finalLore);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addUnplaceableTag(){
        this.im = is.getItemMeta();
        this.im.getPersistentDataContainer().set(Data.key("can_place"), PersistentDataType.STRING, "false");
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addAttributeModifier (Attribute attribute, AttributeModifier attributeModifier) {

        this.im = this.is.getItemMeta();
        this.im.addAttributeModifier(attribute, attributeModifier);
        this.is.setItemMeta(this.im);
        return this;

    }

    public boolean hasData(ItemStack s, String id, PersistentDataType type) {
        this.im = this.is.getItemMeta();
        boolean h = im.getPersistentDataContainer().has(Data.key(id), type);
        this.is.setItemMeta(this.im);

        return h;
    }

    public ItemBuilder addAttributeModifier (Attribute attribute, double value, AttributeModifier.Operation operation, EquipmentSlot slot) {

        this.im = this.is.getItemMeta();
        this.im.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getKey().getNamespace(), value, operation, slot));
        this.is.setItemMeta(this.im);
        return this;

    }
    public ItemBuilder setLeatherColor(int red,int green,int blue){
        this.leather = (LeatherArmorMeta) this.is.getItemMeta();
        this.leather.setColor(Color.fromRGB(red,green,blue));
        this.is.setItemMeta(this.leather);
        return this;
    }




    public ItemStack build() {
        return this.is;
    }
}
