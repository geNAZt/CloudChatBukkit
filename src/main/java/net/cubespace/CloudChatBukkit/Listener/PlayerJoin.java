package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.AffixMessage;
import net.cubespace.PluginMessages.WorldMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:31
 */
public class PlayerJoin implements Listener {
    private CloudChatBukkitPlugin plugin;

    public PlayerJoin(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if(plugin.getConfig().getBoolean("BlockPlayerJoin", false))
            event.setJoinMessage("");

        plugin.getManagers().getAfkManager().add(event.getPlayer());

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if(plugin.getManagers().getAffixManager() != null) {
                    plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new AffixMessage(
                            plugin.getManagers().getAffixManager().getPrefix(event.getPlayer()),
                            plugin.getManagers().getAffixManager().getSuffix(event.getPlayer())
                    ));
                }

                plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new WorldMessage(
                        plugin.getManagers().getWorldManager().getWorldName(event.getPlayer().getWorld()),
                        plugin.getManagers().getWorldManager().getWorldAlias(event.getPlayer().getWorld())
                ));
            }
        }, 10);

        //Check if this Plugin handles Factions
        if(plugin.isFactions()) {
            plugin.getManagers().getFactionManager().checkFactionMode(event.getPlayer());

            if(plugin.getConfig().getBoolean("AnnounceFactionModeOnJoin", true)) {
                event.getPlayer().sendMessage("You currently Chat to: " + plugin.getManagers().getFactionManager().getFactionMode(event.getPlayer()));
            }
        }
    }
}
