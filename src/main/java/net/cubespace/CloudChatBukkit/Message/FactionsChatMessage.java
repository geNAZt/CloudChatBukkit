package net.cubespace.CloudChatBukkit.Message;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Tasks.PluginMessageTask;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 22.12.13 12:02
 */
public class FactionsChatMessage {
    private static CloudChatBukkitPlugin plugin;

    public static void init(CloudChatBukkitPlugin plugin) {
        FactionsChatMessage.plugin = plugin;
    }

    public static void send(Player player, String message) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bStream);

        try {
            output.writeUTF("FactionChat");
            output.writeUTF(player.getName());
            output.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new PluginMessageTask(plugin, player, bStream).runTaskLater(plugin, 1);
    }
}
