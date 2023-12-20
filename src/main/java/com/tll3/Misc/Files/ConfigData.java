package com.tll3.Misc.Files;

import com.tll3.TLL3;

// This is from @SantiHSilva (thanks a lot!!!!!!!!!!!)
public class ConfigData {
    private static final CreateFile config = new CreateFile(TLL3.getInstance(), "config.yml");

    public static void setConfig(String id, String value){
        config.setConfig(id, value);
    }

    public static String getConfig(String id, String defaultValue){
        if(config.getConfig().getString( id) == null){
            setConfig(id, defaultValue);
        }
        return config.getConfig().getString(id);
    }

}
