package net.cubespace.CloudChatBukkit.Config;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.Yamler.Config.Config;

import java.io.File;
import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Main extends Config {
    public Main(CloudChatBukkitPlugin plugin) {
        CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
    }

    public boolean BlockPlayerJoin = false;
    public boolean BlockPlayerQuit = false;
    public boolean HandleAFK = false;
    public Integer AutoAFK = 0;

    public boolean LocalChat = false;
    public Integer GlobalRange = 0;
    public HashMap<String, Integer> WorldRanges = new HashMap<String, Integer>(){{
        put("testWorld", 10);
    }};

    public HashMap<String, String> Worlds = new HashMap<String, String>(){{
        put("testWorld", "TW");
    }};

    public boolean AnnounceFactionModeOnJoin = true;
    public String DefaultFactionMode = "global";
    public HashMap<String, String> FactionModes = new HashMap<String, String>(){{
        put("geNAZt", "faction");
    }};
}
