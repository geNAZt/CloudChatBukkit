package net.cubespace.CloudChatBukkit.Config;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.Yamler.Config.Config;

import java.io.File;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Messages extends Config {
    public Messages(CloudChatBukkitPlugin plugin) {
        CONFIG_FILE = new File(plugin.getDataFolder(), "messages.yml");
    }

    public String Command_NotPlayer = "You can do this only as a Player";
    public String Command_AFK_Cooldown ="This command is currently on cooldown. You can use it again in %seconds Seconds";
    public String Announce_FactionMode = "You currently Chat to: %mode";
    public String Switch_FactionChat_Allies = "You now talk to your Allies";
    public String Switch_FactionChat_Faction = "You now talk to your Faction";
    public String Switch_FactionChat_AlliesAndTruces = "You now talk to your Allies and Truces";
    public String Switch_FactionChat_Truces = "You now talk to your Truces";
    public String Switch_FactionChat_Enemies = "You now talk to your Enemies";
    public String Switch_FactionChat_Global = "You now talk to the Global Chat";
    public String Towny_NotInTown = "You are not in a Town";
    public String Towny_TownNotInNation = "This Town is not in a Nation";
    public String Towny_CouldNotGetTown = "Could not get Town. Please report this to a Admin.";
}
