package me.zyromate.customemojis.Listeners;

import me.zyromate.customemojis.CustomEmojis;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat implements Listener {

    public static List<String> emojiList = new ArrayList<>();
    private final HashMap<String, String> emojiMap = new HashMap<>();
    FileConfiguration config = Bukkit.getPluginManager().getPlugin("CustomEmojis").getConfig();

    public Chat(CustomEmojis customEmojis) {
        loadEmojis();
    }

    public void loadEmojis() {
        List<String> emojiConfigList = config.getStringList("Emojis");
        emojiMap.clear();
        emojiList.clear();

        for (String emojiEntry : emojiConfigList) {
            if (emojiEntry.contains("->")) {
                String[] parts = emojiEntry.split("->", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    emojiMap.put(key, value);
                    emojiList.add(emojiEntry);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        String Permission = config.getString("permission.command");
        if (!p.hasPermission(Permission)) return;
        String message = event.getMessage();

        for (String key : emojiMap.keySet()) {
            if (message.contains(key)) {
                String emoji = emojiMap.get(key).replace("&", "§");
                message = message.replace(key, emoji);
            }
        }
        event.setMessage(message);
    }
}
