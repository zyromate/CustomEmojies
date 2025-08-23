package me.zyromate.customemojis.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.zyromate.customemojis.Listeners.Chat.emojiList;

public class EmojiGUI implements Listener {

    private final FileConfiguration config;

    public EmojiGUI(FileConfiguration config) {
        this.config = config;
    }

    public void openEmojiGui(Player player, int page) {
        int maxItemsPerPage = 28;
        int totalPages = (int) Math.ceil((double) emojiList.size() / maxItemsPerPage);

        if (page < 0) page = 0;
        if (page >= totalPages) page = totalPages - 1;

        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Emojis (Page " + (page + 1) + "/" + totalPages + ")");

        ItemStack borderItem = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta borderMeta = borderItem.getItemMeta();
        if (borderMeta != null) borderMeta.setDisplayName(" ");
        borderItem.setItemMeta(borderMeta);

        for (int i = 0; i < 54; i++) {
            if (i < 9 || i > 44 || i % 9 == 0 || (i + 1) % 9 == 0) {
                gui.setItem(i, borderItem);
            }
        }

        int startIndex = page * maxItemsPerPage;
        int endIndex = Math.min(startIndex + maxItemsPerPage, emojiList.size());

        for (int i = startIndex, slot = 10; i < endIndex; i++, slot++) {
            if (slot % 9 == 8) slot += 2;

            String emojiEntry = emojiList.get(i);
            String[] parts = emojiEntry.split("->", 2);
            if (parts.length == 2) {
                ItemStack emojiItem = new ItemStack(Material.PAPER);
                ItemMeta meta = emojiItem.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ChatColor.RED + "Emoji: " + ChatColor.WHITE + parts[0].trim());
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "Before: " + ChatColor.WHITE + parts[0].trim());
                    lore.add(ChatColor.GRAY + "After: " + ChatColor.translateAlternateColorCodes('&', parts[1].trim()));
                    meta.setLore(lore);
                    emojiItem.setItemMeta(meta);
                }
                gui.setItem(slot, emojiItem);
            }
        }

        if (page > 0) {
            ItemStack previousPage = new ItemStack(Material.ARROW);
            ItemMeta meta = previousPage.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + "Previous Page");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "Current Page " + (page + 1) + "/" + totalPages);
                meta.setLore(lore);
                previousPage.setItemMeta(meta);
            }
            gui.setItem(45, previousPage);
        }

        if (page < totalPages - 1) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta meta = nextPage.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + "Next Page");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "Current Page " + (page + 1) + "/" + totalPages);
                meta.setLore(lore);
                nextPage.setItemMeta(meta);
            }
            gui.setItem(53, nextPage);
        }

        // Add header item in the center
        ItemStack headerItem = new ItemStack(Material.BOOK);
        ItemMeta headerMeta = headerItem.getItemMeta();
        if (headerMeta != null) {
            headerMeta.setDisplayName(ChatColor.GOLD + "Emoji List");
            List<String> headerLore = new ArrayList<>();
            headerLore.add(ChatColor.GRAY + "Browse through the emoji list!");
            headerLore.add(ChatColor.GRAY + "Page " + (page + 1) + " of " + totalPages);
            headerMeta.setLore(headerLore);
            headerItem.setItemMeta(headerMeta);
        }
        gui.setItem(4, headerItem);

        // Open the GUI
        player.openInventory(gui);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if the clicked inventory is the "Emojis" GUI
        if (event.getView().getTitle().startsWith(ChatColor.GREEN + "Emojis")) {
            event.setCancelled(true);  // Prevent item movement and interaction

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            if (clickedItem.getType() == Material.ARROW) {
                // Get the current page number from the title
                String displayName = clickedItem.getItemMeta() != null ? clickedItem.getItemMeta().getDisplayName() : "";
                int currentPage = Integer.parseInt(event.getView().getTitle().split(" ")[2].split("/")[0]) - 1;

                // Navigate between pages
                if (displayName.contains("Previous")) {
                    openEmojiGui(player, currentPage - 1);  // Open previous page
                } else if (displayName.contains("Next")) {
                    openEmojiGui(player, currentPage + 1);  // Open next page
                }
            }
        }
    }


    private void addBorder(Inventory gui) {
        // Configure the border item based on the config
        ItemStack borderItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta borderMeta = borderItem.getItemMeta();
        if (borderMeta != null) {
            String borderName = config.getString("CustomEmojis.Gui.BorderItemName");
            borderMeta.setDisplayName(borderName != null ? borderName : " ");
        }
        borderItem.setItemMeta(borderMeta);

        for (int i = 0; i < 54; i++) {
            if (i < 9 || i > 44 || i % 9 == 0 || (i + 1) % 9 == 0) {
                gui.setItem(i, borderItem);
            }
        }
    }

    private ItemStack createEmojiItem(String before, String after) {
        ItemStack emojiItem = new ItemStack(Material.PAPER);
        ItemMeta meta = emojiItem.getItemMeta();
        if (meta != null) {
            String itemName = config.getString("CustomEmojis.Gui.EmojiItem.Name")
                    .replace("{emojiName}", before);
            meta.setDisplayName(itemName);

            List<String> lore = new ArrayList<>();
            lore.add(config.getString("CustomEmojis.Gui.EmojiItem.Lore")
                    .replace("{emojiName}", before)
                    .replace("{emojiReplaced}", after));
            meta.setLore(lore);
            emojiItem.setItemMeta(meta);
        }
        return emojiItem;
    }


    private ItemStack createNavigationButton(String name, int page) {
        ItemStack button = new ItemStack(Material.ARROW);
        ItemMeta meta = button.getItemMeta();
        if (meta != null) {
            String buttonName = config.getString("CustomEmojis.Gui." + name + "PageButton.Name");
            buttonName = buttonName != null ? buttonName : (name.equals("Previous") ? ChatColor.YELLOW + "Previous Page" : ChatColor.YELLOW + "Next Page");
            meta.setDisplayName(buttonName);
            button.setItemMeta(meta);
        }
        return button;
    }

    private ItemStack createHeaderItem(int page, int totalPages) {
        ItemStack headerItem = new ItemStack(Material.BOOK);
        ItemMeta headerMeta = headerItem.getItemMeta();
        if (headerMeta != null) {
            String itemName = config.getString("CustomEmojis.Gui.HeaderItem.Name");
            itemName = itemName != null ? itemName : ChatColor.GOLD + "Emoji List";
            headerMeta.setDisplayName(itemName);
            List<String> headerLore = new ArrayList<>();
            headerLore.add(config.getString("CustomEmojis.Gui.HeaderItem.Lore")
                    .replace("{page}", String.valueOf(page + 1))
                    .replace("{totalPages}", String.valueOf(totalPages)));
            headerItem.setItemMeta(headerMeta);
        }
        return headerItem;
    }
}
