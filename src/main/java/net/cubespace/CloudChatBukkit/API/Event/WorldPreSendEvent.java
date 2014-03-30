package net.cubespace.CloudChatBukkit.API.Event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 *
 * This event gets fired before the Informations gets send over to CloudChat. So you can modify or cancel the update
 */
public class WorldPreSendEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private String world;
    private String alias;

    public WorldPreSendEvent(String world, String alias) {
        this.world = world;
        this.alias = alias;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
