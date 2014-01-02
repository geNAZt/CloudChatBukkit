package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.PacketManager;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;
import com.iKeirNez.PluginMessageApiPlus.implementations.BukkitPacketManager;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.entity.Player;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.12.13 13:29
 */
public class PluginMessageManager {
    private PacketManager packetManager;
    private CloudChatBukkitPlugin plugin;
    private LinkedBlockingQueue<IPluginMessage> queue = new LinkedBlockingQueue<IPluginMessage>();

    public PluginMessageManager(CloudChatBukkitPlugin plugin, String channel) {
        this.plugin = plugin;

        packetManager = new BukkitPacketManager(plugin, channel);

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new PluginMessageTask(this, plugin, channel));
    }

    public synchronized void sendPluginMessage(Player player, StandardPacket packet) {
        queue.add(new PluginMessage(player, packet));
    }

    public LinkedBlockingQueue<IPluginMessage> getQueue() {
        return queue;
    }

    public synchronized PacketManager getPacketManager() {
        return packetManager;
    }
}
