package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.FactionChatMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:54
 */
public class ChatListener implements Listener {
    private CloudChatBukkitPlugin plugin;

    public ChatListener(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        //If its already canceled here then Factions killed the message
        if(!event.isCancelled()) {
            event.setCancelled(true);

            //Check if this Server is Factions enabled
            if(plugin.isFactions()) {
                //Check in which mode the player is
                String mode = plugin.getManagers().getFactionManager().getFactionMode(event.getPlayer());

                if(mode.equals("global")) {
                    plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new FactionChatMessage("global", event.getMessage(), null, ""));
                }

                if(mode.equals("faction")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionPlayers(event.getPlayer());
                    plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new FactionChatMessage("faction", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("ally")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionAllyPlayers(event.getPlayer());
                    plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new FactionChatMessage("ally", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("allyandtruce")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionAllyAndTrucePlayers(event.getPlayer());
                    plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new FactionChatMessage("allyandtruce", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("truce")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionTrucePlayers(event.getPlayer());
                    plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new FactionChatMessage("truce", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("enemy")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionEnemyPlayers(event.getPlayer());
                    plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new FactionChatMessage("enemy", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }
            }
        }

        plugin.getManagers().getAfkManager().reset(event.getPlayer());
    }
}
