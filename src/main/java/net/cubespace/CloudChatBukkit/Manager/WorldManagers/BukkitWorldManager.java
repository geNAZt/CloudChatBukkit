package net.cubespace.CloudChatBukkit.Manager.WorldManagers;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.WorldManager;
import org.bukkit.World;

import java.util.HashMap;

public class BukkitWorldManager implements WorldManager {
    private HashMap<String, String> worldAliases;

    public BukkitWorldManager(CloudChatBukkitPlugin plugin) {
        worldAliases = plugin.getMainConfig().Worlds;
    }

    @Override
    public String getWorldAlias(World world) {
        if(worldAliases == null) return "";

        return worldAliases.containsKey(world.getName()) ? worldAliases.get(world.getName()) : "";
    }

    @Override
    public String getWorldName(World world) {
        return world.getName();
    }
}
