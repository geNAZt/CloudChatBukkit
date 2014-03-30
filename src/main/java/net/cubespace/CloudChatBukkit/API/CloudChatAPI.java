package net.cubespace.CloudChatBukkit.API;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.PluginMessages.CustomFormatMessage;
import net.cubespace.PluginMessages.IgnoreMessage;
import net.cubespace.PluginMessages.OutputMessage;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class CloudChatAPI {
    /**
     * This method should be used to set a custom format Value inside CloudChat. This can be used in all
     * Messages which go through the MessageFormat parser (nearly all). The format you give in here can be used
     * directly in the Formats as %[your format]. The value must be a String. This can be updated for one Player at
     * time. If you send a Format key twice it overwrites the current Value. So to change a format just send it again
     * with another Value. To "remove" a format just send null or an empty String.
     *
     * HINT: If you rapidly push updates there is a small Chance to loose the order of Updates. This only happens if you
     * update the Format every <50ms. So please be sure to not update that often :)
     *
     * @param player The Player for which this format should be set
     * @param format The format Key which should be used to set the Format
     * @param value The value to set for this Format, can be null to empty it
     */
    public static void setCustomFormat(Player player, String format, String value) {
        Validate.notNull(CloudChatBukkitPlugin.getInstance(), "CloudChatBukkit is not loaded. Be sure you depend on it correctly");
        Validate.notNull(player, "Player can't be null");
        Validate.notNull(format, "The format key can't be null");

        CustomFormatMessage customFormatMessage = new CustomFormatMessage(format, value);
        CloudChatBukkitPlugin.getInstance().getPluginMessageManager("CloudChat").sendPluginMessage(player, customFormatMessage);
    }

    /**
     * Checks if the Player is currently marked as AFK by CloudChat
     *
     * @param player The Player which you want to check
     * @return true when afk, false if not
     */
    public static boolean isAfk(Player player) {
        Validate.notNull(CloudChatBukkitPlugin.getInstance(), "CloudChatBukkit is not loaded. Be sure you depend on it correctly");
        Validate.notNull(player, "Player can't be null");

        return CloudChatBukkitPlugin.getInstance().getManagers().getAfkManager().isAFK(player);
    }

    /**
     * Set the AFK state of a Player.
     *
     * @param player The Player for which you want to set the AFK status
     * @param afk false for not afk, true for afk
     */
    public static void setAfkState(Player player, boolean afk) {
        Validate.notNull(CloudChatBukkitPlugin.getInstance(), "CloudChatBukkit is not loaded. Be sure you depend on it correctly");
        Validate.notNull(player, "Player can't be null");

        if (afk) {
            CloudChatBukkitPlugin.getInstance().getManagers().getAfkManager().setAFK(player);
        } else {
            CloudChatBukkitPlugin.getInstance().getManagers().getAfkManager().reset(player);
        }
    }

    /**
     * Set the Players output mode. When you set this to false all Messages send to this Players get stored into a Buffer
     * so the Player does not get any Chat messages. This should be used if you want to send important Messages to the Player
     * so you can be sure it does not get flooded away. To clear out and display the Buffer set this mode to true again.
     *
     * HINT: This gets saved over multiple logins away. To be sure that the Player has a clear state on join set this to
     * true once. CloudChat on it self does not change the Output flag so only APIs use it.
     *
     * @param player The Player which output should be controlled
     * @param output false to enable the Buffer, true to clear out the Buffer and let the Player get the Messages directly again
     */
    public static void setOutputMode(Player player, boolean output) {
        Validate.notNull(CloudChatBukkitPlugin.getInstance(), "CloudChatBukkit is not loaded. Be sure you depend on it correctly");
        Validate.notNull(player, "Player can't be null");

        OutputMessage outputMessage = new OutputMessage(output);
        CloudChatBukkitPlugin.getInstance().getPluginMessageManager("CloudChat").sendPluginMessage(player, outputMessage);
    }

    /**
     * Either or not to ignore the Player Chat messages. This can be used to silent a Player.
     *
     * HINT: The Player gets unignored when he leaves a Server
     *
     * @param player The Player which may be ignored or not
     * @param ignore true to ignore, false to unignore
     */
    public static void setIgnore(Player player, boolean ignore) {
        Validate.notNull(CloudChatBukkitPlugin.getInstance(), "CloudChatBukkit is not loaded. Be sure you depend on it correctly");
        Validate.notNull(player, "Player can't be null");

        IgnoreMessage ignoreMessage = new IgnoreMessage(ignore);
        CloudChatBukkitPlugin.getInstance().getPluginMessageManager("CloudChat").sendPluginMessage(player, ignoreMessage);
    }
}
