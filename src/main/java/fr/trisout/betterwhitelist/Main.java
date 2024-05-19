package fr.trisout.betterwhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private HostnameWhitelist hostnameWhitelist;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        hostnameWhitelist = new HostnameWhitelist(getConfig());
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();
        if (hostnameWhitelist != null) {
            pm.registerEvents(hostnameWhitelist, this);
            reloadConfig();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        hostnameWhitelist.reloadConfigHostname(getConfig());
        reloadConfig();
        if (!getConfig().getStringList("whitelist").contains(event.getPlayer().getName())) {
            event.getPlayer().kickPlayer("§cYou are not whitelisted on this server.");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        reloadConfig();
        if (!getConfig().getStringList("whitelist").contains(event.getPlayer().getName())) {
            event.setQuitMessage(null);
        }
    }

    public void reloadConfig() {
        super.reloadConfig();
        hostnameWhitelist.reloadConfigHostname(getConfig());
    }
}