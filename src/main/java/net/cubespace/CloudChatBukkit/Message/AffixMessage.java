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
public class AffixMessage {
    private static CloudChatBukkitPlugin plugin;

    public static void init(CloudChatBukkitPlugin plugin) {
        AffixMessage.plugin = plugin;
    }

    public static void send(Player player) {
        String group = plugin.getChat().getPrimaryGroup(player);
        String prefix = "";
        String suffix = "";

        if (plugin.getChat().getPlayerPrefix(player) != null) {
            prefix = plugin.getChat().getPlayerPrefix(player);
        } else if (plugin.getChat().getGroupPrefix(player.getWorld(), group) != null) {
            prefix = plugin.getChat().getGroupPrefix(player.getWorld(), group);
        }
        if (plugin.getChat().getPlayerSuffix(player) != null) {
            suffix = plugin.getChat().getPlayerSuffix(player);
        } else if (plugin.getChat().getGroupSuffix(player.getWorld(), group) != null) {
            suffix = plugin.getChat().getGroupSuffix(player.getWorld(), group);
        }

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bStream);

        try {
            output.writeUTF("Affix");
            output.writeUTF(player.getName());
            output.writeUTF(prefix);
            output.writeUTF(suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new PluginMessageTask(plugin, player, bStream).runTaskLater(plugin, 1);
    }
}
