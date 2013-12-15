package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Message.AffixMessage;
import net.cubespace.CloudChatBukkit.Message.WorldMessage;
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
                AffixMessage.send(event.getPlayer());
                WorldMessage.send(event.getPlayer());
            }
        }, 10);
    }
}
