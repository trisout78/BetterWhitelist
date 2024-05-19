package fr.trisout.betterwhitelist;

import fr.trisout.betterwhitelist.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Set;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class Main extends JavaPlugin implements Listener {

    private Set<String> validHostNames;
    private String warning;
    private boolean ignoreCase;
    private boolean blockLegacy;
    private boolean HostnameEnabled;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(this), this);
        new WhitelistCommand(this);
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        reloadConfig();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        reloadConfigHostname();
        reloadConfig();
            if (!getConfig().getStringList("whitelist").contains(event.getPlayer().getName())) {
                event.getPlayer().kickPlayer("§cYou are not whitelisted on this server.");
            }
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

    private boolean isBlocked(String host) {
        if (host == null) {
            return blockLegacy;
        } else {
            if (ignoreCase) {
                host = host.toLowerCase();
            }
            return !validHostNames.contains(host);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        reloadConfig();
        if (!getConfig().getStringList("whitelist").contains(event.getPlayer().getName())) {
            event.setQuitMessage(null);
        }
    }
    public void reloadConfigHostname() {
        super.reloadConfig();
        Configuration config = getConfig();
        warning = ChatColor.translateAlternateColorCodes('&', config.getString("warning"));
        ignoreCase = config.getBoolean("ignore-case", true);
        validHostNames = Util.getHostNames(config.getStringList("allowed-host-names"), ignoreCase);
        blockLegacy = config.getBoolean("block-legacy", true);
        HostnameEnabled = config.getBoolean("hostname-enabled", true);
    }
}

