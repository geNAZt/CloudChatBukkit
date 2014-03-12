package net.cubespace.CloudChatBukkit;

import net.cubespace.CloudChatBukkit.Command.AFK;
import net.cubespace.CloudChatBukkit.Command.FactionChat;
import net.cubespace.CloudChatBukkit.Command.Log;
import net.cubespace.CloudChatBukkit.Command.NationChat;
import net.cubespace.CloudChatBukkit.Command.TownyChat;
import net.cubespace.CloudChatBukkit.Config.Main;
import net.cubespace.CloudChatBukkit.Config.Messages;
import net.cubespace.CloudChatBukkit.Listener.ChatListener;
import net.cubespace.CloudChatBukkit.Listener.EntityDamage;
import net.cubespace.CloudChatBukkit.Listener.PlayerJoin;
import net.cubespace.CloudChatBukkit.Listener.PlayerMove;
import net.cubespace.CloudChatBukkit.Listener.PlayerQuit;
import net.cubespace.CloudChatBukkit.Listener.WorldChange;
import net.cubespace.CloudChatBukkit.Manager.Managers;
import net.cubespace.CloudChatBukkit.Message.LibraryPluginMessageListener;
import net.cubespace.CloudChatBukkit.Message.LocalPlayersRequestListener;
import net.cubespace.CloudChatBukkit.Message.PluginMessageListener;
import net.cubespace.CloudChatBukkit.Message.PluginMessageManager;
import net.cubespace.PluginMessages.AFKMessage;
import net.cubespace.PluginMessages.AffixMessage;
import net.cubespace.PluginMessages.ChatMessage;
import net.cubespace.PluginMessages.DispatchCmdMessage;
import net.cubespace.PluginMessages.DispatchScmdMessage;
import net.cubespace.PluginMessages.FactionChatMessage;
import net.cubespace.PluginMessages.IgnoreMessage;
import net.cubespace.PluginMessages.LocalPlayersRequest;
import net.cubespace.PluginMessages.LocalPlayersResponse;
import net.cubespace.PluginMessages.PermissionRequest;
import net.cubespace.PluginMessages.PermissionResponse;
import net.cubespace.PluginMessages.RespondScmdMessage;
import net.cubespace.PluginMessages.TownyChatMessage;
import net.cubespace.PluginMessages.WorldMessage;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class CloudChatBukkitPlugin extends JavaPlugin {
    private Managers managers = null;

    private boolean factions;
    private boolean towny;
    private boolean log = false;

    private HashMap<String, PluginMessageManager> pluginMessageManagers = new HashMap<String, PluginMessageManager>();

    private Main config;
    private Messages messages;

    @Override
    public void onEnable() {
        //Init config
        config = new Main(this);
        messages = new Messages(this);

        try {
            config.init();
            messages.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            setEnabled(false);
            return;
        }

        //Check if server has Factions
        factions = getServer().getPluginManager().isPluginEnabled("Factions");
        towny = getServer().getPluginManager().isPluginEnabled("Towny");

        //Startup the Managers
        managers = new Managers(this);

        //Startup the PluginMessage Framework
        pluginMessageManagers.put("CloudChat", new PluginMessageManager(this, "CloudChat"));
        pluginMessageManagers.put("CubespaceLibrary", new PluginMessageManager(this, "CubespaceLibrary"));

        pluginMessageManagers.get("CubespaceLibrary").addPacketToRegister(PermissionResponse.class);
        pluginMessageManagers.get("CubespaceLibrary").addPacketToRegister(PermissionRequest.class);
        pluginMessageManagers.get("CubespaceLibrary").addListenerToRegister(new LibraryPluginMessageListener(this));

        pluginMessageManagers.get("CloudChat").addPacketToRegister(AffixMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(AFKMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(DispatchCmdMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(FactionChatMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(WorldMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(DispatchScmdMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(RespondScmdMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(IgnoreMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(ChatMessage.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(TownyChatMessage.class);
        //JR start
        pluginMessageManagers.get("CloudChat").addPacketToRegister(LocalPlayersRequest.class);
        pluginMessageManagers.get("CloudChat").addPacketToRegister(LocalPlayersResponse.class);
        //JR end
        pluginMessageManagers.get("CloudChat").addListenerToRegister(new PluginMessageListener(this));
        
        //JR start
        pluginMessageManagers.get("CloudChat").addListenerToRegister(new LocalPlayersRequestListener(this));
        //JR end
        
        //Register the Listener
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldChange(this), this);

        if(getConfig().getBoolean("HandleAFK", false)) {
            getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
            getServer().getPluginManager().registerEvents(new EntityDamage(this), this);
        }

        if(isFactions()) {
            ArrayList<String> aliasesFactions = new ArrayList<String>();
            aliasesFactions.add("fc");

            getCommand("fchat").setAliases(aliasesFactions);
            getCommand("fchat").setExecutor(new FactionChat(this));
        }

        if(isTowny()) {
            ArrayList<String> aliasesTowny = new ArrayList<String>();
            aliasesTowny.add("tc");
            getCommand("tchat").setAliases(aliasesTowny);
            getCommand("tchat").setExecutor(new TownyChat(this));

            ArrayList<String> aliasesNation = new ArrayList<String>();
            aliasesNation.add("nc");
            getCommand("nchat").setAliases(aliasesNation);
            getCommand("nchat").setExecutor(new NationChat(this));
        }

        if(config.HandleAFK) {
            getCommand("afk").setExecutor(new AFK(this));
        }

        try {
            Class.forName("org.apache.logging.log4j.core.Logger");

            getCommand("log").setExecutor(new Log(this));
            log = true;
        } catch (ClassNotFoundException e) {}

        pluginMessageManagers.get("CloudChat").finish();
        pluginMessageManagers.get("CubespaceLibrary").finish();
    }

    public Managers getManagers() {
        return managers;
    }

    public boolean isFactions() {
        return factions;
    }

    public boolean isTowny() {
        return towny;
    }

    public PluginMessageManager getPluginMessageManager(String channel) {
        return pluginMessageManagers.get(channel);
    }

    public Main getMainConfig() {
        return config;
    }

    public Messages getMessages() {
        return messages;
    }

    public void onDisable() {
        if(factions) {
            managers.getFactionManager().save(this);
        }
    }

    public boolean isLog() {
        return log;
    }
}
