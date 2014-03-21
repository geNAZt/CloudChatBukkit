package net.cubespace.CloudChatBukkit.Message;

import com.iKeirNez.PluginMessageApiPlus.PacketHandler;
import com.iKeirNez.PluginMessageApiPlus.PacketListener;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.PermissionRequest;
import net.cubespace.PluginMessages.PermissionResponse;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class LibraryPluginMessageListener implements PacketListener {
    private CloudChatBukkitPlugin plugin;

    public LibraryPluginMessageListener(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @PacketHandler
    public void onPermissionRequest(PermissionRequest permissionRequest){
        Player player = permissionRequest.getSender().getBukkitPlayer();

        plugin.getPluginMessageManager("CL-CloudChatPlug").sendPluginMessage(player, new PermissionResponse("", 0));

        for(PermissionAttachmentInfo permissionAttachment : player.getEffectivePermissions()) {
            String permission = permissionAttachment.getPermission();

            if (!permissionAttachment.getValue()) {
                permission = "-" + permission;
            }

            plugin.getPluginMessageManager("CL-CloudChatPlug").sendPluginMessage(player, new PermissionResponse(permission, 1));
        }

        plugin.getPluginMessageManager("CL-CloudChatPlug").sendPluginMessage(player, new PermissionResponse("", 2));
    }
}
