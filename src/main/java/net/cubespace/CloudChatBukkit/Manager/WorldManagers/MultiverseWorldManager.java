package net.cubespace.CloudChatBukkit.Manager.WorldManagers;

import com.onarandombox.MultiverseCore.MultiverseCore;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Manager.WorldManager;
import org.bukkit.World;

/**
 * Created by Fabian on 02.12.13.
 */
public class MultiverseWorldManager implements WorldManager {
    private MultiverseCore multiverseCore;

    public MultiverseWorldManager(CloudChatBukkitPlugin plugin) {
        multiverseCore = (MultiverseCore) plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
    }

    @Override
    public String getWorldAlias(World world) {
        return multiverseCore.getMVWorldManager().getMVWorld(world).getAlias();
    }

    @Override
    public String getWorldName(World world) {
        return multiverseCore.getMVWorldManager().getMVWorld(world).getName();
    }
}
