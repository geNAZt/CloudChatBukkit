package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class EntityDamage implements Listener {
    private CloudChatBukkitPlugin plugin;

    public EntityDamage(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(plugin.getManagers().getAfkManager().isAFK(player)) {
                event.setCancelled(true);
            }
        }
    }
}
