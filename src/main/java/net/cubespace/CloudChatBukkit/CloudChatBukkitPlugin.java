package net.cubespace.CloudChatBukkit;

import com.onarandombox.MultiverseCore.MultiverseCore;
import net.cubespace.CloudChatBukkit.Listener.ChatListener;
import net.cubespace.CloudChatBukkit.Listener.PlayerJoin;
import net.cubespace.CloudChatBukkit.Manager.Managers;
import net.cubespace.CloudChatBukkit.Message.AffixMessage;
import net.cubespace.CloudChatBukkit.Message.WorldMessage;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:29
 */
public class CloudChatBukkitPlugin extends JavaPlugin {
    private Chat chat = null;
    private Managers managers = null;

    @Override
    public void onEnable() {
        //Check if Vault is up
        if(!setupChat()) {
            getLogger().log(Level.SEVERE, "Could not load Vault Chat Hook");
            setEnabled(false);
            return;
        }
        managers = new Managers(this);

        //Register all Output of this Plugin into the CloudChat Plugin Message Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "CloudChat");

        //Start up the Messages
        AffixMessage.init(this);
        WorldMessage.init(this);

        //Register the Listener
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);

        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    public Managers getManagers() {
        return managers;
    }

    public Chat getChat() {
        return chat;
    }
}
