package net.cubespace.CloudChatBukkit.Manager;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.AffixManagers.BungeePermsManager;
import net.cubespace.CloudChatBukkit.Manager.AffixManagers.VaultManager;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.BukkitWorldManager;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.MultiverseWorldManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Created by Fabian on 02.12.13.
 */
public class Managers {
    private CloudChatBukkitPlugin plugin;
    private WorldManager worldManager;
    private AffixManager affixManager;
    private Chat chat = null;

    public Managers(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;

        //WorldManagers
        if(plugin.getServer().getPluginManager().isPluginEnabled("Multiverse-Core")) {
            worldManager = new MultiverseWorldManager(plugin);
        } else {
            worldManager = new BukkitWorldManager(plugin);
        }

        //AffixManagers
        if(setupChat()) {
            affixManager = new VaultManager(plugin, chat);
        } else if (plugin.getServer().getPluginManager().isPluginEnabled("BungeePermsBukkit")) {
            affixManager = new BungeePermsManager(plugin);
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);

        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
    public AffixManager getAffixManager() {
        return affixManager;
    }
}
