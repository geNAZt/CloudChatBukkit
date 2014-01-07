package net.cubespace.CloudChatBukkit;

import net.cubespace.PluginMessages.RespondScmdMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 05.01.14 13:52
 */
public class CloudChatBukkitCommandSender implements CommandSender {
    private Integer scmdSessionId;
    private CloudChatBukkitPlugin plugin;

    public CloudChatBukkitCommandSender(CloudChatBukkitPlugin plugin, Integer scmdSessionId) {
        this.plugin = plugin;
        this.scmdSessionId = scmdSessionId;
    }

    @Override
    public void sendMessage(String message) {
        plugin.getPluginMessageManager("CloudChat").sendPluginMessage(plugin.getServer().getOnlinePlayers()[0], new RespondScmdMessage(message, scmdSessionId));
        plugin.getLogger().info("[CommandSender] " + message);
    }

    @Override
    public void sendMessage(String[] messages) {
        for(String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public String getName() {
        return "CloudChatBukkitCommandSender";
    }

    @Override
    public boolean isPermissionSet(String permission) {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return true;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return true;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        return null;
    }

    @Override
    public void removeAttachment(PermissionAttachment permissionAttachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null;
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean b) {

    }
}
