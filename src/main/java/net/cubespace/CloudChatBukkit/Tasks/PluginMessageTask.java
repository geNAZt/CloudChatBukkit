package net.cubespace.CloudChatBukkit.Tasks;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:36
 */
public class PluginMessageTask extends BukkitRunnable {

    private final CloudChatBukkitPlugin plugin;
    private final Player player;
    private ByteArrayOutputStream bytes;

    public PluginMessageTask(CloudChatBukkitPlugin plugin, Player player, ByteArrayOutputStream bytes) {
        this.plugin = plugin;
        this.player = player;
        this.bytes = bytes;
    }

    public void run() {
        player.sendPluginMessage(this.plugin, "CloudChat", this.bytes.toByteArray());
    }

}
