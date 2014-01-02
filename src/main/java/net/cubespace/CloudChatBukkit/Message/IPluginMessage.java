package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.StandardPacket;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.12.13 13:45
 */
public interface IPluginMessage {
    public StandardPacket getPacket();
    public Player getPlayer();
}
