package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.API.Event.AffixPreSendEvent;
import net.cubespace.CloudChatBukkit.API.Event.WorldPreSendEvent;
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
        if(plugin.getManagers().getAffixManager() != null) {
            String town = (plugin.isTowny()) ? plugin.getManagers().getTownyManager().getTown(event.getPlayer()) : "";
            String nation = (plugin.isTowny()) ? plugin.getManagers().getTownyManager().getNation(event.getPlayer()) : "";
            String faction = (plugin.isFactions()) ? plugin.getManagers().getFactionManager().getFaction(event.getPlayer()) : "";

            AffixPreSendEvent affixPreSendEvent = new AffixPreSendEvent(
                    plugin.getManagers().getAffixManager().getPrefix(event.getPlayer()),
                    plugin.getManagers().getAffixManager().getSuffix(event.getPlayer()),
                    faction,
                    nation,
                    town,
                    plugin.getManagers().getAffixManager().getGroup(event.getPlayer())
            );

            plugin.getServer().getPluginManager().callEvent(affixPreSendEvent);
            if (!affixPreSendEvent.isCancelled()) {
                plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new AffixMessage(
                        affixPreSendEvent.getPrefix(),
                        affixPreSendEvent.getSuffix(),
                        affixPreSendEvent.getTown(),
                        affixPreSendEvent.getNation(),
                        affixPreSendEvent.getFaction(),
                        affixPreSendEvent.getGroup()
                ));
            }
        }

        WorldPreSendEvent worldPreSendEvent = new WorldPreSendEvent(
                plugin.getManagers().getWorldManager().getWorldName(event.getPlayer().getWorld()),
                plugin.getManagers().getWorldManager().getWorldAlias(event.getPlayer().getWorld())
        );

        plugin.getServer().getPluginManager().callEvent(worldPreSendEvent);

        if (!worldPreSendEvent.isCancelled()) {
            plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getPlayer(), new WorldMessage(
                    worldPreSendEvent.getWorld(),
                    worldPreSendEvent.getAlias()
            ));
        }

        plugin.getManagers().getAfkManager().reset(event.getPlayer());
    }
}
