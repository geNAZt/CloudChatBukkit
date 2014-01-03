package net.cubespace.CloudChatBukkit.Manager.WorldManagers;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.WorldManager;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class BukkitWorldManager implements WorldManager {
    private ConfigurationSection worldAliases;

    public BukkitWorldManager(CloudChatBukkitPlugin plugin) {
        worldAliases = plugin.getConfig().getConfigurationSection("Worlds");
    }

    @Override
    public String getWorldAlias(World world) {
        if(worldAliases == null) return "";

        return worldAliases.getString(world.getName(), "");
    }

    @Override
    public String getWorldName(World world) {
        return world.getName();
    }
}
