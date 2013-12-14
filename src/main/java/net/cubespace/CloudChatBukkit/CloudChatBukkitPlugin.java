package net.cubespace.CloudChatBukkit;

import net.alpenblock.bungeeperms.bukkit.BungeePerms;
import net.cubespace.CloudChatBukkit.Listener.ChatListener;
import net.cubespace.CloudChatBukkit.Listener.PlayerJoin;
import net.cubespace.CloudChatBukkit.Listener.PlayerQuit;
import net.cubespace.CloudChatBukkit.Listener.WorldChange;
import net.cubespace.CloudChatBukkit.Manager.Managers;
import net.cubespace.CloudChatBukkit.Message.AffixMessage;
import net.cubespace.CloudChatBukkit.Message.WorldMessage;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:29
 */
public class CloudChatBukkitPlugin extends JavaPlugin {
    private Managers managers = null;

    @Override
    public void onEnable() {
        //Init config
        getConfig().options().copyDefaults(true);
        saveConfig();

        //Check for BungeePerms
        if(getServer().getPluginManager().isPluginEnabled("BungeePermsBukkit")) {

        }

        managers = new Managers(this);

        //Register all Output of this Plugin into the CloudChat Plugin Message Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "CloudChat");

        //Start up the Messages
        AffixMessage.init(this);
        WorldMessage.init(this);

        //Register the Listener
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new WorldChange(), this);
    }

    public Managers getManagers() {
        return managers;
    }
}
