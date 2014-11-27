package net.cubespace.CloudChatBukkit.Manager;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class FactionManager {
    private String defaultFactionMode;
    private HashMap<String, String> factionModes = new HashMap<String, String>();

    public FactionManager(CloudChatBukkitPlugin plugin) {
        //Read the config
        defaultFactionMode = plugin.getMainConfig().DefaultFactionMode;
        factionModes = plugin.getMainConfig().FactionModes;
    }

    public List<String> getFactionPlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();

        MPlayer mPlayer = MPlayer.get(player);
        for(Player ps : mPlayer.getFaction().getOnlinePlayers()) {
            factionPlayers.add(ps.getName());
        }

        return factionPlayers;
    }

    public List<String> getFactionAllyPlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        MPlayer mPlayer = MPlayer.get(player);
        Map<Rel, List<String>> rels = mPlayer.getFaction().getRelationNames( mPlayer.getFaction(), new HashSet<Rel>(){{
            add( Rel.ALLY );
        }}, false);

        for(Player ps : mPlayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(String data : rels.get(Rel.ALLY)) {
            Faction f = FactionColl.get().getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        return factionPlayers;
    }

    public List<String> getFactionTrucePlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        MPlayer mPlayer = MPlayer.get(player);
        Map<Rel, List<String>> rels = mPlayer.getFaction().getRelationNames( mPlayer.getFaction(), new HashSet<Rel>(){{
            add( Rel.TRUCE );
        }}, false);

        for(Player ps : mPlayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(String data : rels.get(Rel.TRUCE)) {
            Faction f = FactionColl.get().getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        return factionPlayers;
    }

    public List<String> getFactionEnemyPlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        MPlayer mPlayer = MPlayer.get(player);
        Map<Rel, List<String>> rels = mPlayer.getFaction().getRelationNames( mPlayer.getFaction(), new HashSet<Rel>() {{
            add( Rel.ENEMY );
        }}, false );


        for(Player ps : mPlayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(String data : rels.get(Rel.ENEMY)) {
            Faction f = FactionColl.get().getByName(ChatColor.stripColor(data));
            for(Player ps : f.getOnlinePlayers()) {
                if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
            }
        }

        return factionPlayers;
    }

    public List<String> getFactionAllyAndTrucePlayers(Player player) {
        List<String> factionPlayers = new ArrayList<String>();
        MPlayer mPlayer = MPlayer.get(player);
        Map<Rel, List<String>> rels = mPlayer.getFaction().getRelationNames( mPlayer.getFaction(), new HashSet<Rel>() {{
            add( Rel.ALLY );
            add( Rel.TRUCE );
        }}, false );

        for(Player ps : mPlayer.getFaction().getOnlinePlayers()) {
            if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
        }

        for(List<String> data : rels.values()) {
            for ( String fac : data ) {
                Faction f = FactionColl.get().getByName(ChatColor.stripColor(fac));
                for(Player ps : f.getOnlinePlayers()) {
                    if(!factionPlayers.contains(ps.getName())) factionPlayers.add(ps.getName());
                }
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
        plugin.getMainConfig().FactionModes = factionModes;
        try {
            plugin.getMainConfig().save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setFactionMode(Player player, String factionMode) {
        factionModes.put(player.getName(), factionMode);
    }

    public String getFaction(Player player) {
        return MPlayer.get(player).getFaction().getName();
    }
}
