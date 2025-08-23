package me.zyromate.customemojis.Commands;

import me.zyromate.customemojis.GUI.EmojiGUI;
import me.zyromate.customemojis.Listeners.Chat;
import me.zyromate.customemojis.Utilities.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Emojis implements CommandExecutor {

    private final EmojiGUI emojiGUI;
    private final utils Chatutils;
    private final FileConfiguration config;

    public Emojis(EmojiGUI emojiGUI, Chat chatListener, utils utility, FileConfiguration config) {
        this.emojiGUI = emojiGUI;
        this.Chatutils = utility;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("emojis")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (Chat.emojiList.isEmpty()) {
                    Chatutils.sendMessage(player, "§cNo emojis available!");
                    return false;
                }

                emojiGUI.openEmojiGui(player, 0);

                String openMessage = config.getString("Gui.open-message");
                Chatutils.sendMessage(player, openMessage);
                return true;
            } else {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
        }
        return false;
    }
}
