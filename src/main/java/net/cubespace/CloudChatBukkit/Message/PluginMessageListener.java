package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.PacketHandler;
import com.iKeirNez.PluginMessageApiPlus.PacketListener;
import net.cubespace.CloudChatBukkit.CloudChatBukkitCommandSender;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.DispatchCmdMessage;
import net.cubespace.PluginMessages.DispatchScmdMessage;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 02.01.14 04:26
 */
public class PluginMessageListener implements PacketListener {
    private CloudChatBukkitPlugin plugin;

    public PluginMessageListener(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @PacketHandler
    public void onDispatchCmdMessage(DispatchCmdMessage dispatchCmdMessage){
        Player player = dispatchCmdMessage.getSender().getBukkitPlayer();

        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), dispatchCmdMessage.getCommand());
    }

    @PacketHandler
    public void onDispatchCmdMessage(DispatchScmdMessage dispatchScmdMessage){
        Player player = dispatchScmdMessage.getSender().getBukkitPlayer();

        plugin.getLogger().info("Issuing SCMD: " + dispatchScmdMessage.getCommand());
        plugin.getServer().dispatchCommand(new CloudChatBukkitCommandSender(plugin, dispatchScmdMessage.getScmdSessionId()), dispatchScmdMessage.getCommand());
    }
}
