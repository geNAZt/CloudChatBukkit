package net.cubespace.CloudChatBukkit.Message;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Tasks.PluginMessageTask;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 13:06
 */
public class AFKMessage {
    private static CloudChatBukkitPlugin plugin;

    public static void init(CloudChatBukkitPlugin plugin) {
        AFKMessage.plugin = plugin;
    }

    public static void send(Player player, boolean afk) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bStream);

        try {
            output.writeUTF("AFK");
            output.writeUTF(player.getName());
            output.writeBoolean(afk);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new PluginMessageTask(plugin, player, bStream).runTaskLater(plugin, 1);
    }
}
