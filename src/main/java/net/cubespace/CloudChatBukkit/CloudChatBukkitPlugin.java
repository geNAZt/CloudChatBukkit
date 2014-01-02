package net.cubespace.CloudChatBukkit;

import net.cubespace.CloudChatBukkit.Listener.ChatListener;
import net.cubespace.CloudChatBukkit.Listener.EntityDamage;
import net.cubespace.CloudChatBukkit.Listener.PlayerJoin;
import net.cubespace.CloudChatBukkit.Listener.PlayerMove;
import net.cubespace.CloudChatBukkit.Listener.PlayerQuit;
import net.cubespace.CloudChatBukkit.Listener.WorldChange;
import net.cubespace.CloudChatBukkit.Manager.Managers;
import net.cubespace.CloudChatBukkit.Message.AFKMessage;
import net.cubespace.CloudChatBukkit.Message.AffixMessage;
import net.cubespace.CloudChatBukkit.Message.DispatchCmdMessage;
import net.cubespace.CloudChatBukkit.Message.FactionChatMessage;
import net.cubespace.CloudChatBukkit.Message.PluginMessageListener;
import net.cubespace.CloudChatBukkit.Message.PluginMessageManager;
import net.cubespace.CloudChatBukkit.Message.WorldMessage;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:29
 */
public class CloudChatBukkitPlugin extends JavaPlugin {
    private Managers managers = null;
    private boolean factions;
    private PluginMessageManager pluginMessageManager;

    @Override
    public void onEnable() {
        //Init config
        getConfig().options().copyDefaults(true);
        saveConfig();

        //Startup the Managers
        managers = new Managers(this);

        //Startup the PluginMessage Framework
        pluginMessageManager = new PluginMessageManager(this, "CloudChat");
        pluginMessageManager.getPacketManager().registerPacket(AffixMessage.class);
        pluginMessageManager.getPacketManager().registerPacket(AFKMessage.class);
        pluginMessageManager.getPacketManager().registerPacket(DispatchCmdMessage.class);
        pluginMessageManager.getPacketManager().registerPacket(FactionChatMessage.class);
        pluginMessageManager.getPacketManager().registerPacket(WorldMessage.class);
        pluginMessageManager.getPacketManager().registerListener(new PluginMessageListener(this));

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

    public PluginMessageManager getPluginMessageManager() {
        return pluginMessageManager;
    }
}
