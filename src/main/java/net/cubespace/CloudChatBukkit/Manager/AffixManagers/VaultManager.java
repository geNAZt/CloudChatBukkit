package net.cubespace.CloudChatBukkit.Manager.AffixManagers;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.AffixManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 14.12.13 20:29
 */
public class VaultManager implements AffixManager {
    private Chat chat;

    public VaultManager(CloudChatBukkitPlugin plugin) {
        RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);

        chat = chatProvider.getProvider();

        plugin.getLogger().info("Using Vault as Affix Manager");
    }

    @Override
    public String getPrefix(Player player) {
        String group = chat.getPrimaryGroup(player);

        if (chat.getPlayerPrefix(player) != null) {
            return chat.getPlayerPrefix(player);
        } else if (chat.getGroupPrefix(player.getWorld(), group) != null) {
            return chat.getGroupPrefix(player.getWorld(), group);
        }

        return "";
    }

    @Override
    public String getSuffix(Player player) {
        String group = chat.getPrimaryGroup(player);

        if (chat.getPlayerSuffix(player) != null) {
            return chat.getPlayerSuffix(player);
        } else if (chat.getGroupSuffix(player.getWorld(), group) != null) {
            return chat.getGroupSuffix(player.getWorld(), group);
        }

        return "";
    }
}
