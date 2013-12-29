package net.cubespace.CloudChatBukkit;

import net.cubespace.CloudChatBukkit.Listener.ChatListener;
import net.cubespace.CloudChatBukkit.Listener.EntityDamage;
import net.cubespace.CloudChatBukkit.Listener.PlayerJoin;
import net.cubespace.CloudChatBukkit.Listener.PlayerMove;
import net.cubespace.CloudChatBukkit.Listener.PlayerQuit;
import net.cubespace.CloudChatBukkit.Listener.PluginMessageListener;
import net.cubespace.CloudChatBukkit.Listener.WorldChange;
import net.cubespace.CloudChatBukkit.Manager.Managers;
import net.cubespace.CloudChatBukkit.Message.AFKMessage;
import net.cubespace.CloudChatBukkit.Message.AffixMessage;
import net.cubespace.CloudChatBukkit.Message.FactionsChatMessage;
import net.cubespace.CloudChatBukkit.Message.WorldMessage;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:29
 */
public class CloudChatBukkitPlugin extends JavaPlugin {
    private Managers managers = null;
    private boolean factions;

    @Override
    public void onEnable() {
        //Init config
        getConfig().options().copyDefaults(true);
        saveConfig();

        //Startup the Managers
        managers = new Managers(this);

        //Register all Output of this Plugin into the CloudChat Plugin Message Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "CloudChat");
        getServer().getMessenger().registerIncomingPluginChannel(this, "CloudChat", new PluginMessageListener(this));

        //Start up the Messages
        AffixMessage.init(this);
        WorldMessage.init(this);
        AFKMessage.init(this);
        FactionsChatMessage.init(this);

        //Register the Listener
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldChange(this), this);

        if(getConfig().getBoolean("HandleAFK", false)) {
            getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
            getServer().getPluginManager().registerEvents(new EntityDamage(this), this);
        }

        //Check if server has Factions
        factions = getServer().getPluginManager().isPluginEnabled("Factions");
    }

    public Managers getManagers() {
        return managers;
    }

    public boolean isFactions() {
        return factions;
    }

    public void onDisable() {
        saveConfig();
    }
}
