package net.cubespace.CloudChatBukkit.Command;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class AFK implements CommandExecutor {
    private CloudChatBukkitPlugin plugin;

    public AFK(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String alias, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().Command_NotPlayer));
            return true;
        }

        Player player = (Player) commandSender;

        if (plugin.getManagers().getAfkManager().hasCooldown(player)) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().Command_AFK_Cooldown.replace("%seconds", "" + Math.round(plugin.getManagers().getAfkManager().getCooldown(player) - System.currentTimeMillis() / 1000D))));
            return true;
        }

        if (plugin.getManagers().getAfkManager().isAFK(player)) {
            plugin.getManagers().getAfkManager().reset(player);
        } else {
            plugin.getManagers().getAfkManager().setAFK(player);
        }

        plugin.getManagers().getAfkManager().addCooldown(player);

        return true;
    }
}
