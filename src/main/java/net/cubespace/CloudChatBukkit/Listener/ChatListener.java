package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.ChatMessage;
import net.cubespace.PluginMessages.FactionChatMessage;
import net.cubespace.PluginMessages.SendChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
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

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        //If its already canceled here then Factions killed the message
        if(!event.isCancelled()) {
            event.setCancelled(true);

            //Check if this Server is Factions enabled
            if(plugin.isFactions()) {
                //Check in which mode the player is
                String mode = plugin.getManagers().getFactionManager().getFactionMode(event.getPlayer());

                if(mode.equals("global")) {
                    if(!checkLocalChat(event)) {
                        plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("global", event.getMessage(), null, ""));
                    }
                }

                if(mode.equals("faction")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionPlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("faction", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("ally")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionAllyPlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("ally", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("allyandtruce")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionAllyAndTrucePlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("allyandtruce", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("truce")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionTrucePlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("truce", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                if(mode.equals("enemy")) {
                    List<String> players = plugin.getManagers().getFactionManager().getFactionEnemyPlayers(event.getPlayer());
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new FactionChatMessage("enemy", event.getMessage(), players, plugin.getManagers().getFactionManager().getFaction(event.getPlayer())));
                }

                return;
            } else {
                if(!checkLocalChat(event)) {
                    plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new ChatMessage(event.getMessage()));
                }
            }
        }

        plugin.getManagers().getAfkManager().reset(event.getPlayer());
    }

    private boolean checkLocalChat(AsyncPlayerChatEvent event) {
        //Check if Server has Local Chat
        if(plugin.getConfig().getBoolean("LocalChat", false)) {
            //Check which range to use (WorldRange > GlobalRange)
            int range = plugin.getConfig().getInt("GlobalRange");

            if(plugin.getConfig().getInt("WorldRanges." + event.getPlayer().getWorld().getName(), 0) > 0) {
                range = plugin.getConfig().getInt("WorldRanges." + event.getPlayer().getWorld().getName(), 0);
            }

            if(range > 0) {
                //Get all Entities nearby
                List<Entity> entities = event.getPlayer().getNearbyEntities(range, range, range);
                ArrayList<String> sendTo = new ArrayList<String>();

                sendTo.add(event.getPlayer().getName());
                for(Entity entity : entities) {
                    if(entity instanceof Player) {
                        sendTo.add(((Player) entity).getName());
                    }
                }

                plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new SendChatMessage(event.getMessage(), sendTo));
                return true;
            }
        }

        return false;
    }
}
