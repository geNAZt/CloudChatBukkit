package net.cubespace.CloudChatBukkit.Manager;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TownyManager {
    private Towny towny;

    public TownyManager(CloudChatBukkitPlugin plugin) {
        towny = (Towny) plugin.getServer().getPluginManager().getPlugin("Towny");
    }

    public String getTown(Player player) {
        try {
            return towny.getTownyUniverse().getResident(player.getName().toLowerCase()).getTown().getName();
        } catch (NotRegisteredException e) {
            return "";
        }
    }

    public String getNation(Player player) {
        try {
            Town town = towny.getTownyUniverse().getResident(player.getName().toLowerCase()).getTown();
            if(!town.hasNation()) return "";

            return town.getNation().getName();
        } catch (NotRegisteredException e) {
            return "";
        }
    }
}
