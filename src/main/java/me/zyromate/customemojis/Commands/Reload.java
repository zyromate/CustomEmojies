package me.zyromate.customemojis.Commands;

import me.zyromate.customemojis.Listeners.Chat;
import me.zyromate.customemojis.Utilities.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@SuppressWarnings("unchecked")
public class Reload implements CommandExecutor {

    private final JavaPlugin plugin;
    private final Chat chatListener;
    private final utils utility;

    public Reload(JavaPlugin plugin, Chat chatListener) {
        this.plugin = plugin;
        this.chatListener = chatListener;
        this.utility = new utils(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        if ("reload".equalsIgnoreCase(args[0])) {
            plugin.reloadConfig();
            chatListener.loadEmojis();

            Object reloadSuccessMessage = plugin.getConfig().get("CustomEmojis.reload-success");

            if (reloadSuccessMessage instanceof List<?>) {
                for (String line : (List<String>) reloadSuccessMessage) {
                    utility.sendMessage(sender, line);
                }
            } else if (reloadSuccessMessage instanceof String) {
                utility.sendMessage(sender, (String) reloadSuccessMessage);
            }
            utility.onReload();

            return true;
        }

        sendUsage(sender);
        return true;
    }

    private void sendUsage(CommandSender sender) {
        List<String> usageMessage = plugin.getConfig().getStringList("CustomEmojis.command-usage");
        if (usageMessage == null || usageMessage.isEmpty()) {
            utility.sendMessage(sender, "&cUsage: /customemojis reload");
            return;
        }
        for (String line : usageMessage) {
            utility.sendMessage(sender, line);
        }
    }
}
