package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.PacketPlayer;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.12.13 13:18
 */
public class PluginMessageTask implements Runnable {
    private final CloudChatBukkitPlugin plugin;
    private final PluginMessageManager pluginMessageManager;
    private final String channel;

    public PluginMessageTask(PluginMessageManager pluginMessageManager, CloudChatBukkitPlugin plugin, String channel) {
        this.plugin = plugin;
        this.pluginMessageManager = pluginMessageManager;
        this.channel = channel;
    }

    public void run() {
        try {
            while(true) {
                IPluginMessage pluginMessage = pluginMessageManager.getQueue().take();
                if(pluginMessage.getPlayer() == null) continue;
                StandardPacket message = pluginMessage.getPacket();
                pluginMessageManager.getPacketManager().sendPacket(new PacketPlayer(pluginMessage.getPlayer()), message);
            }
        } catch(Exception e) {
            throw new RuntimeException("PluginMessageTask was interrupted", e);
        }
    }

}
