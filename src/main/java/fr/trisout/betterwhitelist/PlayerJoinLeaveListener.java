package fr.trisout.betterwhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveListener implements Listener {

    private final Main plugin;

    public PlayerJoinLeaveListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getBoolean("custom-join-leave-message")) {
            String playerName = event.getPlayer().getName();
            event.setJoinMessage("§7[§l§a+§r§7] " + playerName);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("custom-join-leave-message")) {
            String playerName = event.getPlayer().getName();
            event.setQuitMessage("§7[§l§a-§r§7] " + playerName);
        }
    }
}