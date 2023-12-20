package com.tll3.Misc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class Data {
    public static NamespacedKey key(String key) {
        return new NamespacedKey(GenericUtils.getPlugin(), key);
    }
    public static <T, Z> void set(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type, Z value) {
        Data.getDataContainer(holder).set(Data.Key(key), type, value);
    }
    private static PersistentDataContainer getDataContainer(PersistentDataHolder holder){
        return holder.getPersistentDataContainer();
    }

    public static <T, Z> Z get(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type) {
        if(!Data.has(holder, key, type)){
            return null;
        }
        return Data.getDataContainer(holder).get(Data.Key(key), type);
    }

    public static <T, Z> boolean has(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type) {
        if(holder == null || holder.getPersistentDataContainer() == null || holder.getPersistentDataContainer().get(Data.Key(key), type) == null)return false;
        return Data.getDataContainer(holder).has(Data.Key(key), type);
    }

    public static PersistentDataContainer newDataContainer(PersistentDataHolder holder){
        return holder.getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
    }


    public static <T, Z> boolean equals(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type, Z value) {
        if(!Data.has(holder, key, type)) return false;
        return Data.get(holder, key, type).equals(value) || value.equals(Data.get(holder, key, type));
    }

    public static NamespacedKey Key(String string){
        return new NamespacedKey(GenericUtils.getPlugin(), string);
    }
}
