package fr.trisout.betterwhitelist;

import fr.trisout.betterwhitelist.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class HostnameWhitelist implements Listener {

    private Set<String> validHostNames;
    private String warning;
    private boolean ignoreCase;
    private boolean blockLegacy;
    private boolean HostnameEnabled;

    public HostnameWhitelist(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public HostnameWhitelist(Configuration config) {
        reloadConfigHostname(config);
    }

    public void reloadConfigHostname(Configuration config) {
        warning = ChatColor.translateAlternateColorCodes('&', config.getString("warning"));
        ignoreCase = config.getBoolean("ignore-case", true);
        validHostNames = Util.getHostNames(config.getStringList("allowed-host-names"), ignoreCase);
        blockLegacy = config.getBoolean("block-legacy", true);
        HostnameEnabled = config.getBoolean("hostname-enabled", true);
    }

    public boolean isHostnameEnabled() {
        return HostnameEnabled;
    }

    public boolean isBlocked(String host) {
        if (host == null) {
            return blockLegacy;
        } else {
            if (ignoreCase) {
                host = host.toLowerCase();
            }
            return !validHostNames.contains(host);
        }
    }

    public String getWarning() {
        return warning;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (!HostnameEnabled) {
            return;
        }
        String hostname = event.getHostname();
        int port = hostname.indexOf(":");
        if (port != -1) {
            hostname = hostname.substring(0, port);
        }
        if (isBlocked(hostname)) {
            event.disallow(Result.KICK_OTHER, warning);
        }
    }
}