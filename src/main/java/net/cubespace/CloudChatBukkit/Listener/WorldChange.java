package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.AffixMessage;
import net.cubespace.PluginMessages.WorldMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:58
 */
public class WorldChange implements Listener {
    private CloudChatBukkitPlugin plugin;

    public WorldChange(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new AffixMessage(
                plugin.getManagers().getAffixManager().getPrefix(event.getPlayer()),
                plugin.getManagers().getAffixManager().getSuffix(event.getPlayer())
        ));

        plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new WorldMessage(
                plugin.getManagers().getWorldManager().getWorldName(event.getPlayer().getWorld()),
                plugin.getManagers().getWorldManager().getWorldAlias(event.getPlayer().getWorld())
        ));

        plugin.getManagers().getAfkManager().reset(event.getPlayer());
    }
}
