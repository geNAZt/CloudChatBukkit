package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.PacketHandler;
import com.iKeirNez.PluginMessageApiPlus.PacketListener;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.DispatchCmdMessage;
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
}
