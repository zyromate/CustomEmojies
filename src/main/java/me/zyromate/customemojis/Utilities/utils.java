package me.zyromate.customemojis.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class utils {
    private final JavaPlugin plugin;
    private String pluginVersion;

    public utils(JavaPlugin plugin) {
        this.plugin = plugin;
        loadVersion();
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendInitialization(String feature) {
        plugin.getLogger().info("---------------------------");
        plugin.getLogger().info("Feature: " + feature);
        plugin.getLogger().info("Successfully loaded");
        plugin.getLogger().info("---------------------------");
    }

    public void onReload() {
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "-------------------------");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + " Successfully Reloaded");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + " CustomEmojies");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "-------------------------");
    }

    public void sendMessageToStaff(String message) {
        String formattedMessage = ChatColor.translateAlternateColorCodes('&', message);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("zyrostaffutils.staff.alerts")) {
                onlinePlayer.sendMessage(formattedMessage);
            }
        }
    }

    public void initialization(String message) {
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "-------------------------");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "  Custom Emojis");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "  Status: " + ChatColor.DARK_RED + message);
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "  Version: " + ChatColor.DARK_RED + pluginVersion);
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "  Author: " + ChatColor.DARK_RED + "Zyromate");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "");
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "-------------------------");
    }

    private void loadVersion() {
        try {
            pluginVersion = plugin.getDescription().getVersion();
            if (pluginVersion == null || pluginVersion.isEmpty()) {
                pluginVersion = "Unknown";
            }

        } catch (Exception ex) {
            Bukkit.getLogger().severe("Error loading plugin version from plugin.yml: " + ex.getMessage());
            pluginVersion = "Unknown";
        }
    }
}
