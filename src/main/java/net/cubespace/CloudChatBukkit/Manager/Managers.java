package net.cubespace.CloudChatBukkit.Manager;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.BukkitWorldManager;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.MultiverseWorldManager;

/**
 * Created by Fabian on 02.12.13.
 */
public class Managers {
    private WorldManager worldManager;

    public Managers(CloudChatBukkitPlugin plugin) {
        //WorldManagers
        if(plugin.getServer().getPluginManager().isPluginEnabled("Multiverse-Core")) {
            worldManager = new MultiverseWorldManager(plugin);
        } else {
            worldManager = new BukkitWorldManager(plugin);
        }
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
