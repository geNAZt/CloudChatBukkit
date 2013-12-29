package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.12.13 15:32
 */
public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
    private CloudChatBukkitPlugin plugin;

    public PluginMessageListener(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(!channel.equals("CloudChat"))
            return;
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
            String subChannel = in.readUTF();

            if(subChannel.equals("DispatchCmd")) {
                String cmd = in.readUTF();
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd);
            }
        } catch (IOException e) {
            plugin.getLogger().warning("Could not encode Plugin Message");
            e.printStackTrace();
        }
    }
}
