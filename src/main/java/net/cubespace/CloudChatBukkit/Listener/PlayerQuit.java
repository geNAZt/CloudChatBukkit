package net.cubespace.CloudChatBukkit.Listener;


import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Command.Log;
import net.cubespace.PluginMessages.IgnoreMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 09.12.13 21:44
 */
public class PlayerQuit implements Listener {
    private CloudChatBukkitPlugin plugin;

    public PlayerQuit(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(plugin.getMainConfig().BlockPlayerQuit)
            event.setQuitMessage("");

        plugin.getManagers().getAfkManager().remove(event.getPlayer());
        plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new IgnoreMessage(false));
        Log.remove(event.getPlayer());
    }
}
