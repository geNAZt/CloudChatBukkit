package net.cubespace.CloudChatBukkit.Command;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Log implements CommandExecutor {
    private static ArrayList<CommandSender> senders = new ArrayList<CommandSender>();
    private static HashMap<CommandSender, BukkitTask> killTasks = new HashMap<CommandSender, BukkitTask>();
    private CloudChatBukkitPlugin plugin;

    private class ConsoleStubAppender extends AbstractAppender {
        public ConsoleStubAppender() {
            super("CloudChatBukkit", null, PatternLayout.createLayout("[%d{HH:mm:ss} %level]: %msg", null, null, null, null), false);
        }

        @Override
        public boolean isStarted() {
            return true;
        }

        @Override
        public void append(LogEvent e) {
            for(CommandSender commandSender : senders) {
                commandSender.sendMessage(getLayout().toSerializable(e).toString());
            }
        }
    }

    public Log(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;

        Logger l = (Logger) LogManager.getRootLogger();
        l.addAppender(new ConsoleStubAppender());
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String alias, String[] args) {
        if(!senders.contains(commandSender)) {
            senders.add(commandSender);

            killTasks.put(commandSender, plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    remove(commandSender);
                }
            }, 12000));
        } else {
            remove(commandSender);
        }

        return true;
    }

    public static void remove(CommandSender commandSender) {
        if(senders.contains(commandSender)) {
            killTasks.get(commandSender).cancel();
            senders.remove(commandSender);
        }
    }
}
