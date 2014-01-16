package net.cubespace.CloudChatBukkit.Manager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.AffixManagers.BungeePermsManager;
import net.cubespace.CloudChatBukkit.Manager.AffixManagers.VaultManager;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.BukkitWorldManager;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.MultiverseWorldManager;
import net.cubespace.PluginMessages.AffixMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Managers {
    private CloudChatBukkitPlugin plugin;
    private WorldManager worldManager;
    private AffixManager affixManager;
    private AFKManager afkManager;
    private FactionManager factionManager;
    private Table<String, String, String> affixTable = HashBasedTable.create();

    public Managers(final CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;

        //WorldManagers
        if(plugin.getServer().getPluginManager().isPluginEnabled("Multiverse-Core")) {
            worldManager = new MultiverseWorldManager(plugin);
        } else {
            worldManager = new BukkitWorldManager(plugin);
        }

        //AffixManagers
        if(setupChat()) {
            affixManager = new VaultManager(plugin);
        } else if (plugin.getServer().getPluginManager().isPluginEnabled("BungeePermsBukkit")) {
            affixManager = new BungeePermsManager(plugin);
        }

        //FactionManager
        if(plugin.isFactions()) {
            factionManager = new FactionManager(plugin);
        }

        //AFKManager
        afkManager = new AFKManager(plugin);

        //Shedule the reset of Affixes
        if(affixManager != null) {
            plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    //Add new Players to the Table
                    for(Player player : plugin.getServer().getOnlinePlayers()) {
                        affixTable.put(player.getName(), plugin.getManagers().getAffixManager().getPrefix(player), plugin.getManagers().getAffixManager().getSuffix(player));
                    }

                    //Check for Changes
                    for(Table.Cell<String, String, String> tableCell : HashBasedTable.create(affixTable).cellSet()) {
                        if(plugin.getServer().getPlayerExact(tableCell.getRowKey()) == null) {
                            affixTable.remove(tableCell.getRowKey(), tableCell.getColumnKey());
                            continue;
                        }

                        Player player = plugin.getServer().getPlayerExact(tableCell.getRowKey());
                        String prefix = plugin.getManagers().getAffixManager().getPrefix(player);
                        String suffix = plugin.getManagers().getAffixManager().getSuffix(player);
                        if(!tableCell.getColumnKey().equals(prefix) || !tableCell.getValue().equals(suffix)) {
                            plugin.getPluginMessageManager("CloudChat").sendPluginMessage(player, new AffixMessage(
                                prefix,
                                suffix
                            ));

                            affixTable.row(tableCell.getRowKey()).remove(prefix);
                            affixTable.row(tableCell.getRowKey()).put(prefix, suffix);
                        }
                    }
                }
            }, 200, 200);
        } else {
            plugin.getLogger().info("=== It seems that you don't have any Affix Provider installed ===");
            plugin.getLogger().info("===  You will not see any Prefix or suffix inside CloudChat   ===");
            plugin.getLogger().info("===              To fix this install Vault                    ===");
        }
    }

    private boolean setupChat() {
        try {
            Class.forName("net.milkbowl.vault.chat.Chat");
            RegisteredServiceProvider<net.milkbowl.vault.chat.Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
            return chatProvider != null && chatProvider.getProvider() != null;
        } catch (ClassNotFoundException e) { }

        return false;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
    public AffixManager getAffixManager() {
        return affixManager;
    }
    public AFKManager getAfkManager() {
        return afkManager;
    }

    public FactionManager getFactionManager() {
        return factionManager;
    }
}
