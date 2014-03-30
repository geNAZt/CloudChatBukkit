package net.cubespace.CloudChatBukkit.API.Event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 *
 * This event gets fired before the Informations gets send over to CloudChat. So you can modify or cancel the update
 */
public class AffixPreSendEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private String prefix;
    private String suffix;
    private String faction;
    private String nation;
    private String town;

    public AffixPreSendEvent(String prefix, String suffix, String faction, String nation, String town) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.faction = faction;
        this.nation = nation;
        this.town = town;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
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
