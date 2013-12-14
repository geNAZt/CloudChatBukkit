package net.cubespace.CloudChatBukkit.Manager.AffixManagers;

import net.alpenblock.bungeeperms.bukkit.BungeePerms;
import net.alpenblock.bungeeperms.bukkit.User;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.AffixManager;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 14.12.13 20:26
 */
public class BungeePermsManager implements AffixManager {
    private CloudChatBukkitPlugin plugin;

    public BungeePermsManager(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Using BungeePermsBukkit as Affix Manager");
    }

    @Override
    public String getPrefix(Player player) {
        BungeePerms bungeePerms = (BungeePerms) plugin.getServer().getPluginManager().getPlugin("BungeePermsBukkit");
        if(bungeePerms == null) {
            return "";
        }

        User user = bungeePerms.getPermissionsManager().getUser(player.getName());
        if(user == null) {
            return "";
        }

        return user.getGroups().get(0).getPrefix();
    }

    @Override
    public String getSuffix(Player player) {
        BungeePerms bungeePerms = (BungeePerms) plugin.getServer().getPluginManager().getPlugin("BungeePermsBukkit");
        if(bungeePerms == null) {
            return "";
        }

        User user = bungeePerms.getPermissionsManager().getUser(player.getName());
        if(user == null) {
            return "";
        }

        return user.getGroups().get(0).getSuffix();
    }
}
