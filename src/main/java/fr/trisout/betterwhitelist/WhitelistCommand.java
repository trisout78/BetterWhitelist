package fr.trisout.betterwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WhitelistCommand implements CommandExecutor {

    private final Main plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public WhitelistCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("wl").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("whitelist.add")) {
            player.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§cUsage: /wl <player>");
            return true;
        }

        if (cooldowns.containsKey(player.getUniqueId())) {
            long secondsLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + plugin.getConfig().getInt("cooldown")) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                player.sendMessage("§cYou cannot use this command for another " + secondsLeft + " seconds!");
                return true;
            }
        }

        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());

        String targetPlayer = args[0];
        FileConfiguration config = plugin.getConfig();
        config.getStringList("whitelist").add(targetPlayer);
        plugin.saveConfig();

        Bukkit.broadcastMessage("§b" + targetPlayer + " has been added to the whitelist by " + player.getName());

        return true;
    }
}