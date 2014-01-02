package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Message.FactionChatMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
                plugin.getPluginMessageManager().sendPluginMessage(event.getPlayer(), new FactionChatMessage(event.getMessage()));
            }
        }

        plugin.getManagers().getAfkManager().reset(event.getPlayer());
    }
}
