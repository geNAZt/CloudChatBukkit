package net.cubespace.CloudChatBukkit.Command;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 03.01.14 14:12
 */
public class FactionChat implements CommandExecutor {
    private CloudChatBukkitPlugin plugin;

    public FactionChat(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
        //Check the commandSender
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You only can chat as Player");
            return true;
        }

        Player player = (Player) commandSender;

        //Get current mode
        String mode = plugin.getManagers().getFactionManager().getFactionMode(player);

        //First check the Arguments
        if(args.length >= 1) {
            if(args[0].equals("f")) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "faction");
                player.sendMessage("You now talk to your Faction");
                return true;
            }

            if(args[0].equals("a")) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "ally");
                player.sendMessage("You now talk to your Allies");
                return true;
            }

            if(args[0].equals("at")) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "allyandtruce");
                player.sendMessage("You now talk to your Allies and Truces");
                return true;
            }

            if(args[0].equals("t")) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "truce");
                player.sendMessage("You now talk to your Truces");
                return true;
            }

            if(args[0].equals("e")) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "enemy");
                player.sendMessage("You now talk to your Enemies");
                return true;
            }

            if(args[0].equals("g")) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "global");
                player.sendMessage("You now talk to the Global Chat");
                return true;
            }
        } else {
            //Normal rotation
            if(mode.equals("global") || (args.length > 1 && args[0].equals("f"))) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "faction");
                player.sendMessage("You now talk to your Faction");
                return true;
            }

            if(mode.equals("faction") || (args.length > 1 && args[0].equals("a"))) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "ally");
                player.sendMessage("You now talk to your Allies");
                return true;
            }

            if(mode.equals("ally") || (args.length > 1 && args[0].equals("at"))) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "allyandtruce");
                player.sendMessage("You now talk to your Allies and Truces");
                return true;
            }

            if(mode.equals("allyandtruce") || (args.length > 1 && args[0].equals("t"))) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "truce");
                player.sendMessage("You now talk to your Truces");
                return true;
            }

            if(mode.equals("truce") || (args.length > 1 && args[0].equals("e"))) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "enemy");
                player.sendMessage("You now talk to your Enemies");
                return true;
            }

            if(mode.equals("enemy") || (args.length > 1 && args[0].equals("g"))) {
                plugin.getManagers().getFactionManager().setFactionMode(player, "global");
                player.sendMessage("You now talk to the Global Chat");
                return true;
            }
        }

        return true;
    }
}
