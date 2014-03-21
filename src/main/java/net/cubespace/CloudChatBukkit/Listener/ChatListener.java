package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Config.Main;
import net.cubespace.PluginMessages.ChatMessage;
import net.cubespace.PluginMessages.FactionChatMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ChatListener implements Listener {
    private CloudChatBukkitPlugin plugin;

    public ChatListener(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Main config = plugin.getMainConfig();

        //If its already canceled here then Factions killed the message
        if(!event.isCancelled()) {
            event.setCancelled(true);
            
            //Check if this Server is Factions enabled
            if(plugin.isFactions()) {
                //Check in which mode the player is
                String mode = plugin.getManagers().getFactionManager().getFactionMode(event.getPlayer());

                if(mode.equals("global")) {
                    if (config.LogChat) {
                        plugin.getLogger().info("[GLOBAL] " + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                    }

                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("global", event.getMessage(), null, ""));
                }

                if(mode.equals("faction")) {
                    if (config.LogChat) {
                        plugin.getLogger().info("[FACTION] " + plugin.getManagers().getFactionManager().getFaction(event.getPlayer()) + " / " + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                    }

                    List<String> players = plugin.getManagers().getFactionManager().getFactionPlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("faction", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("ally")) {
                    if (config.LogChat) {
                        plugin.getLogger().info("[ALLY] " + plugin.getManagers().getFactionManager().getFaction(event.getPlayer()) + " / " + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                    }

                    List<String> players = plugin.getManagers().getFactionManager().getFactionAllyPlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("ally", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("allyandtruce")) {
                    if (config.LogChat) {
                        plugin.getLogger().info("[ALLY/TRUCE] " + plugin.getManagers().getFactionManager().getFaction(event.getPlayer()) + " / " + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                    }

                    List<String> players = plugin.getManagers().getFactionManager().getFactionAllyAndTrucePlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("allyandtruce", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("truce")) {
                    if (config.LogChat) {
                        plugin.getLogger().info("[TRUCE] " + plugin.getManagers().getFactionManager().getFaction(event.getPlayer()) + " / " + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                    }

                    List<String> players = plugin.getManagers().getFactionManager().getFactionTrucePlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("truce", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("enemy")) {
                    if (config.LogChat) {
                        plugin.getLogger().info("[ENEMY] " + plugin.getManagers().getFactionManager().getFaction(event.getPlayer()) + " / " + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                    }

                    List<String> players = plugin.getManagers().getFactionManager().getFactionEnemyPlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("enemy", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                return;
            } else {
                if (config.LogChat) {
                    plugin.getLogger().info("[GLOBAL] " + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                }

                plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new ChatMessage(event.getMessage()));
            }
        }

        plugin.getManagers().getAfkManager().reset(event.getPlayer());
    }
}
