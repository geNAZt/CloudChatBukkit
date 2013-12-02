package net.cubespace.CloudChatBukkit.Manager.WorldManagers;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.WorldManager;
import org.bukkit.World;

/**
 * Created by Fabian on 02.12.13.
 */
public class BukkitWorldManager implements WorldManager {
    public BukkitWorldManager(CloudChatBukkitPlugin plugin) {

    }

    @Override
    public String getWorldAlias(World world) {
        return "";
    }

    @Override
    public String getWorldName(World world) {
        return world.getName();
    }
}
