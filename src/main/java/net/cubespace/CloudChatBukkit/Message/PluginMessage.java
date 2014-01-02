package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.StandardPacket;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 02.01.14 04:02
 */
public class PluginMessage implements IPluginMessage {
    private Player player;
    private StandardPacket packet;

    public PluginMessage(Player player, StandardPacket packet) {
        this.player = player;
        this.packet = packet;
    }

    @Override
    public StandardPacket getPacket() {
        return packet;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
