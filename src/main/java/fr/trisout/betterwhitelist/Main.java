package fr.trisout.betterwhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        reloadConfig();
            if (!getConfig().getStringList("whitelist").contains(event.getPlayer().getName())) {
                event.getPlayer().kickPlayer("§cYou are not whitelisted on this server.");
            }
        }
    }
