package net.cubespace.CloudChatBukkit;

import net.cubespace.CloudChatBukkit.Command.FactionChat;
import net.cubespace.CloudChatBukkit.Listener.ChatListener;
import net.cubespace.CloudChatBukkit.Listener.EntityDamage;
import net.cubespace.CloudChatBukkit.Listener.PlayerJoin;
import net.cubespace.CloudChatBukkit.Listener.PlayerMove;
import net.cubespace.CloudChatBukkit.Listener.PlayerQuit;
import net.cubespace.CloudChatBukkit.Listener.WorldChange;
import net.cubespace.CloudChatBukkit.Manager.Managers;
import net.cubespace.PluginMessages.AFKMessage;
import net.cubespace.PluginMessages.AffixMessage;
import net.cubespace.PluginMessages.DispatchCmdMessage;
import net.cubespace.PluginMessages.DispatchScmdMessage;
import net.cubespace.PluginMessages.FactionChatMessage;
import net.cubespace.CloudChatBukkit.Message.PluginMessageListener;
import net.cubespace.CloudChatBukkit.Message.PluginMessageManager;
import net.cubespace.PluginMessages.RespondScmdMessage;
import net.cubespace.PluginMessages.WorldMessage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

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

        //Check if server has Factions
        factions = getServer().getPluginManager().isPluginEnabled("Factions");

        //Startup the Managers
        managers = new Managers(this);

        //Startup the PluginMessage Framework
        pluginMessageManager = new PluginMessageManager(this, "CloudChat");
        pluginMessageManager.addPacketToRegister(AffixMessage.class);
        pluginMessageManager.addPacketToRegister(AFKMessage.class);
        pluginMessageManager.addPacketToRegister(DispatchCmdMessage.class);
        pluginMessageManager.addPacketToRegister(FactionChatMessage.class);
        pluginMessageManager.addPacketToRegister(WorldMessage.class);
        pluginMessageManager.addPacketToRegister(DispatchScmdMessage.class);
        pluginMessageManager.addPacketToRegister(RespondScmdMessage.class);
        pluginMessageManager.addListenerToRegister(new PluginMessageListener(this));

        //Register the Listener
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldChange(this), this);

        if(getConfig().getBoolean("HandleAFK", false)) {
            getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
            getServer().getPluginManager().registerEvents(new EntityDamage(this), this);
        }

        ArrayList<String> aliases = new ArrayList<String>();
        aliases.add("fc");
        getCommand("fchat").setAliases(aliases);
        getCommand("fchat").setExecutor(new FactionChat(this));

        pluginMessageManager.finish();
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

    public void onDisable() {
        if(factions) {
            managers.getFactionManager().save(this);
        }
    }
}
