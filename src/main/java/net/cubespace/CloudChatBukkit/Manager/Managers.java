package net.cubespace.CloudChatBukkit.Manager;

import net.cubespace.CloudChatBukkit.API.Event.AffixPreSendEvent;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.AffixManagers.BungeePermsManager;
import net.cubespace.CloudChatBukkit.Manager.AffixManagers.VaultManager;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.BukkitWorldManager;
import net.cubespace.CloudChatBukkit.Manager.WorldManagers.MultiverseWorldManager;
import net.cubespace.PluginMessages.AffixMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;

public class Managers {
    private CloudChatBukkitPlugin plugin;
    private WorldManager worldManager;
    private AffixManager affixManager;
    private AFKManager afkManager;
    private FactionManager factionManager;
    private TownyManager townyManager;
    private HashMap<String, String> affixTable = new HashMap<String, String>();

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

        if(plugin.isTowny()) {
            townyManager = new TownyManager(plugin);
        }

        //AFKManager
        afkManager = new AFKManager(plugin);

        //Shedule the reset of Affixes
        if(affixManager != null) {
            plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    //Add new Players to the Table
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (!affixTable.containsKey(player.getName())) {
                            affixTable.put(player.getName(), plugin.getManagers().getAffixManager().getPrefix(player) + "/" + plugin.getManagers().getAffixManager().getSuffix(player));
                        }

                        String newPair = plugin.getManagers().getAffixManager().getPrefix(player) + "/" + plugin.getManagers().getAffixManager().getSuffix(player);

                        if (!affixTable.get(player.getName()).equals(newPair)) {
                            String prefix = plugin.getManagers().getAffixManager().getPrefix(player);
                            String suffix = plugin.getManagers().getAffixManager().getSuffix(player);
                            String town = (plugin.isTowny()) ? plugin.getManagers().getTownyManager().getTown(player) : "";
                            String nation = (plugin.isTowny()) ? plugin.getManagers().getTownyManager().getNation(player) : "";
                            String faction = (plugin.isFactions()) ? plugin.getManagers().getFactionManager().getFaction(player) : "";
                            String group = plugin.getManagers().getAffixManager().getGroup(player);

                            AffixPreSendEvent affixPreSendEvent = new AffixPreSendEvent(
                                    prefix,
                                    suffix,
                                    faction,
                                    nation,
                                    town,
                                    group
                            );

                            plugin.getServer().getPluginManager().callEvent(affixPreSendEvent);
                            if (!affixPreSendEvent.isCancelled()) {
                                plugin.getPluginMessageManager("CloudChat").sendPluginMessage(player, new AffixMessage(
                                        affixPreSendEvent.getPrefix(),
                                        affixPreSendEvent.getSuffix(),
                                        affixPreSendEvent.getTown(),
                                        affixPreSendEvent.getNation(),
                                        affixPreSendEvent.getFaction(),
                                        affixPreSendEvent.getGroup()
                                ));

                                affixTable.put(player.getName(), newPair);
                            }
                        }
                    }

                    //Check for Changes
                    for (String player : new HashMap<String, String>(affixTable).keySet()) {
                        if (Bukkit.getPlayerExact(player) == null) {
                            affixTable.remove(player);
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

    public TownyManager getTownyManager() {
        return townyManager;
    }
}
