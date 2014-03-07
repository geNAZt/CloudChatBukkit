package net.cubespace.CloudChatBukkit.Command;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.TownyChatMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 03.01.14 14:12
 */
public class NationChat implements CommandExecutor {
    private CloudChatBukkitPlugin plugin;
    private Towny towny;

    public NationChat(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
        this.towny = (Towny) plugin.getServer().getPluginManager().getPlugin("Towny");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
        //Check the commandSender
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().Command_NotPlayer));
            return true;
        }

        Player player = (Player) commandSender;
        try {
            Town town = towny.getTownyUniverse().getResident(player.getName()).getTown();
            if(town == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().Towny_NotInTown));
                return true;
            }

            if(!town.hasNation()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().Towny_TownNotInNation));
                return true;
            }

            ArrayList<String> to = new ArrayList<String>();

            for(Town town1 : town.getNation().getTowns()) {
                for(Resident resident : town1.getResidents()) {
                    Player player1 = plugin.getServer().getPlayerExact(resident.getName());
                    if(player1 != null) {
                        if(!to.contains(player1.getName())) {
                            to.add(player1.getName());
                        }
                    }
                }
            }

            plugin.getPluginMessageManager("CloudChat").sendPluginMessage(player, new TownyChatMessage("nation", StringUtils.join(args, " "), to, town.getName(), (town.hasNation()) ? town.getNation().getName() : ""));
        } catch (NotRegisteredException e) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().Towny_CouldNotGetTown));
            e.printStackTrace();
        }

        return true;
    }
}
