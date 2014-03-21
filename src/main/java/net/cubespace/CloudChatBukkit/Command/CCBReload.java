package net.cubespace.CloudChatBukkit.Command;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class CCBReload implements CommandExecutor {
    private CloudChatBukkitPlugin plugin;

    public CCBReload(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String alias, String[] args) {
        try {
            plugin.getMainConfig().reload();
            plugin.getMessages().reload();
            commandSender.sendMessage("Config/Messages Reloaded");
        } catch (InvalidConfigurationException e) {
            commandSender.sendMessage("Error while reloading");
            e.printStackTrace();
        }

        return true;
    }
}
