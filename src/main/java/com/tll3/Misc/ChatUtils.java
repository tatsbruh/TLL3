package com.tll3.Misc;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    //aesthethic
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    public static String format(String s) {
        s = s.replace("#&", "#");
        if (pattern != null) {
            Matcher m = pattern.matcher(s);
            while (m.find()) {
                String cl = s.substring(m.start(), m.end());
                s = s.replace(cl, "" + net.md_5.bungee.api.ChatColor.of(cl));
                m = pattern.matcher(s);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static final String prefix = format("&8[#fb6820A#fc8725f#fca62bt#fdc430e#fde335r#fdc430l#fca62bi#fc8725f#fb6820e&8] &7► &f");

}
