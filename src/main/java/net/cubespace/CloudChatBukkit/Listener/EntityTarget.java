package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 15.12.13 15:56
 */
public class EntityTarget implements Listener {
    private CloudChatBukkitPlugin plugin;

    public EntityTarget(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if(event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();

            if(plugin.getManagers().getAfkManager().isAFK(player)) {
                event.setCancelled(true);
            }
        }
    }
}
