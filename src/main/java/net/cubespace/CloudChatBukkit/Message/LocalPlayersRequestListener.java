package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.PacketHandler;
import com.iKeirNez.PluginMessageApiPlus.PacketListener;
import java.util.ArrayList;
import java.util.List;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.LocalPlayersRequest;
import net.cubespace.PluginMessages.LocalPlayersResponse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * JR start
 * @author dimensionZ (webmaster@hatventures.net)
 */
public class LocalPlayersRequestListener implements PacketListener
{

    private CloudChatBukkitPlugin plugin;

    public LocalPlayersRequestListener() {}
    
    public LocalPlayersRequestListener(CloudChatBukkitPlugin plugin)
    {
        this.plugin = plugin;
    }

    @PacketHandler
    public void onLocalPlayersRequest(LocalPlayersRequest event)
    {
        //If range is 0, well that's just stupid and we'll ignore it. Maybe throw an exception/debug msg ?
        if (event.getRange() > 0) {
            //Get all Entities nearby
            List<Entity> entities = event.getSender().getBukkitPlayer().getNearbyEntities(event.getRange(), event.getRange(), event.getRange());
            ArrayList<String> sendTo = new ArrayList<String>();
            
            //Add yourself, you fool.
            sendTo.add(event.getSender().getName());
            for (Entity entity : entities) {
                if (entity instanceof Player) {
                    if (!sendTo.contains(((Player) entity).getName())) {
                        // Adding entity to the "local" players.
                        sendTo.add(((Player) entity).getName());
                    }
                }
            }
            // Using the regular SendChatMessage plugin message
            plugin.getPluginMessageManager("CloudChat").sendPluginMessage(event.getSender().getBukkitPlayer(), new LocalPlayersResponse(event.getMessage(), event.getChannel(), sendTo));
        }

    }
}
//JR end