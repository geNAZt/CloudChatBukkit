package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 15.12.13 15:53
 */
public class EntityDamage implements Listener {
    private CloudChatBukkitPlugin plugin;

    public EntityDamage(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();

            if(plugin.getManagers().getAfkManager().isAFK(player)) {
                event.setCancelled(true);
            }
        }
    }
}
