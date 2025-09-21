package me.zyromate.customemojis;

import me.zyromate.customemojis.Commands.Emojis;
import me.zyromate.customemojis.Commands.Reload;
import me.zyromate.customemojis.GUI.EmojiGUI;
import me.zyromate.customemojis.Listeners.Chat;
import me.zyromate.customemojis.Utilities.utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomEmojis extends JavaPlugin {

    private utils utility;
    private EmojiGUI emojiGUI;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        config = this.getConfig();

        utility = new utils(this);
        utility.initialization("activated");

        emojiGUI = new EmojiGUI(config, utility);

        Registers();
    }

    @Override
    public void onDisable() {
        utility.initialization("deactivated");
    }

    public void Registers() {
        Chat chatListener = new Chat(this);

        getServer().getPluginManager().registerEvents(chatListener, this);
        getServer().getPluginManager().registerEvents(emojiGUI, this);

        getCommand("customemojis").setExecutor(new Reload(this, chatListener));
        getCommand("emojis").setExecutor(new Emojis(emojiGUI, chatListener, utility, config));
    }
}