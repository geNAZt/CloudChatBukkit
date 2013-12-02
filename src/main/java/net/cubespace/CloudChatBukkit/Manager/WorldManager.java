package net.cubespace.CloudChatBukkit.Manager;

import org.bukkit.World;

/**
 * Created by Fabian on 02.12.13.
 */
public interface WorldManager {
    public String getWorldAlias(World world);
    public String getWorldName(World world);
}
