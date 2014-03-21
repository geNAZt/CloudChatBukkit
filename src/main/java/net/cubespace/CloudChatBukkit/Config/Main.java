package net.cubespace.CloudChatBukkit.Config;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.Yamler.Config.Comment;
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
    @Comment("Cooldown in seconds")
    public Integer Cooldown_AFKCommand = 0;
    @Comment("Relay chat into logs")
    public Boolean LogChat = false;
//JR start
//Removed now-useless local config
//JR end
    public HashMap<String, String> Worlds = new HashMap<String, String>(){{
        put("testWorld", "TW");
    }};
    //End of if
    public boolean AnnounceFactionModeOnJoin = true;
    public String DefaultFactionMode = "global";
    public HashMap<String, String> FactionModes = new HashMap<String, String>(){{
        put("geNAZt", "faction");
    }};
}
