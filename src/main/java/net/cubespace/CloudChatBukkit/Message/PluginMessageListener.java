package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.PacketHandler;
import com.iKeirNez.PluginMessageApiPlus.PacketListener;
import net.cubespace.CloudChatBukkit.CloudChatBukkitCommandSender;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.AFKMessage;
import net.cubespace.PluginMessages.DispatchCmdMessage;
import net.cubespace.PluginMessages.DispatchScmdMessage;
import net.cubespace.PluginMessages.SetNickMessage;

import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PluginMessageListener implements PacketListener {
    private CloudChatBukkitPlugin plugin;
    private HashMap<String, CloudChatBukkitCommandSender> cloudChatBukkitCommandSender = new HashMap<String, CloudChatBukkitCommandSender>();

    public PluginMessageListener(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @PacketHandler
    public void onDispatchCmdMessage(DispatchCmdMessage dispatchCmdMessage){
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), dispatchCmdMessage.getCommand());
    }

    @PacketHandler
    public void onDispatchCmdMessage(DispatchScmdMessage dispatchScmdMessage){
        String cmd = dispatchScmdMessage.getCommand().split(" ")[0];

        if(!cloudChatBukkitCommandSender.containsKey(cmd)) {
            cloudChatBukkitCommandSender.put(cmd, new CloudChatBukkitCommandSender(plugin));
        }

        cloudChatBukkitCommandSender.get(cmd).setScmdId(dispatchScmdMessage.getScmdSessionId());

        plugin.getLogger().info("Issuing SCMD: " + dispatchScmdMessage.getCommand());
        plugin.getServer().dispatchCommand(cloudChatBukkitCommandSender.get(cmd), dispatchScmdMessage.getCommand());
    }

    @PacketHandler
    public void onSetNickMessage(SetNickMessage setNickMessage) {
        setNickMessage.getSender().getBukkitPlayer().setDisplayName(setNickMessage.getNick());
    }

    @PacketHandler
    public void onAFKStateMessage(AFKMessage afkMessage) {
        if(!afkMessage.isAfk())
            plugin.getManagers().getAfkManager().reset(afkMessage.getSender().getBukkitPlayer());
        else
            plugin.getManagers().getAfkManager().setAFK(afkMessage.getSender().getBukkitPlayer());
    }
}
