package net.cubespace.CloudChatBukkit.Manager;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.UPlayer;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 03.01.14 12:38
 */
public class FactionManager {
    private String defaultFactionMode;
    private HashMap<String, String> factionModes = new HashMap<String, String>();

    public FactionManager(CloudChatBukkitPlugin plugin) {
        //Read the config
        defaultFactionMode = plugin.getConfig().getString("DefaultFactionMode");
        ConfigurationSection factionModes = plugin.getConfig().getConfigurationSection("FactionModes");

        for(String key : factionModes.getKeys(false)) {
            this.factionModes.put(key, factionModes.getString(key));
        }
    }

    public List<String> getFactionPlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();

        UPlayer uplayer = UPlayer.get(player);
        for(Player ps : uplayer.getFaction().getOnlinePlayers()) {
            factionPlayers.add(ps.getName());
        }

        return factionPlayers;
    }

    public List<String> getFactionAllyPlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        UPlayer uplayer = UPlayer.get(player);
        Map<Rel, List<String>> rels = uplayer.getFaction().getFactionNamesPerRelation(uplayer.getFaction());

        for(Player ps : uplayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(String data : rels.get(Rel.ALLY)) {
            Faction f = FactionColls.get().getForUniverse(uplayer.getFaction().getUniverse()).getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        return factionPlayers;
    }

    public List<String> getFactionTrucePlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        UPlayer uplayer = UPlayer.get(player);
        Map<Rel, List<String>> rels = uplayer.getFaction().getFactionNamesPerRelation(uplayer.getFaction());

        for(Player ps : uplayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(String data : rels.get(Rel.TRUCE)) {
            Faction f = FactionColls.get().getForUniverse(uplayer.getFaction().getUniverse()).getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        return factionPlayers;
    }

    public List<String> getFactionEnemyPlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        UPlayer uplayer = UPlayer.get(player);
        Map<Rel, List<String>> rels = uplayer.getFaction().getFactionNamesPerRelation(uplayer.getFaction());

        for(Player ps : uplayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(String data : rels.get(Rel.ENEMY)) {
            Faction f = FactionColls.get().getForUniverse(uplayer.getFaction().getUniverse()).getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        return factionPlayers;
    }

    public List<String> getFactionAllyAndTrucePlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        UPlayer uplayer = UPlayer.get(player);
        Map<Rel, List<String>> rels = uplayer.getFaction().getFactionNamesPerRelation(uplayer.getFaction());

        for(Player ps : uplayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(String data : rels.get(Rel.ALLY)) {
            Faction f = FactionColls.get().getForUniverse(uplayer.getFaction().getUniverse()).getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        for(String data : rels.get(Rel.TRUCE)) {
            Faction f = FactionColls.get().getForUniverse(uplayer.getFaction().getUniverse()).getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        return factionPlayers;
    }

    public String getFactionMode(Player player) {
        return factionModes.get(player.getName());
    }

    public void checkFactionMode(Player player) {
        if(!factionModes.containsKey(player.getName())) {
            factionModes.put(player.getName(), defaultFactionMode);
        }
    }

    public void save(CloudChatBukkitPlugin plugin) {
        ConfigurationSection factionModesCS = plugin.getConfig().getConfigurationSection("FactionModes");

        for(Map.Entry<String, String> key : factionModes.entrySet()) {
            factionModesCS.set(key.getKey(), key.getValue());
        }

        plugin.saveConfig();
    }

    public void setFactionMode(Player player, String factionMode) {
        factionModes.put(player.getName(), factionMode);
    }

    public String getFaction(Player player) {
        return UPlayer.get(player).getFaction().getName();
    }
}
