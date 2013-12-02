package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.Message.AffixMessage;
import net.cubespace.CloudChatBukkit.Message.WorldMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:58
 */
public class WorldChange implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        AffixMessage.send(event.getPlayer());
        WorldMessage.send(event.getPlayer());
    }
}
