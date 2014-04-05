package net.cubespace.CloudChatBukkit.Manager;

import org.bukkit.entity.Player;

/**
 * Created by Fabian on 02.12.13.
 */
public interface AffixManager {
    public String getPrefix(Player player);
    public String getSuffix(Player player);
    public String getGroup(Player player);
}
