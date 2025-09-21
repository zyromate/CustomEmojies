package me.zyromate.customemojis.GUI;

import me.zyromate.customemojis.Utilities.utils;
import me.zyromate.customemojis.Utilities.NBTUtils;
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
    private final utils utils;

    // Configuration cache for better performance
    private final int guiSize;
    private final int maxItemsPerPage;
    private final String guiTitleFormat;
    private final Material borderMaterial;
    private final short borderData;
    private final String borderName;
    private final Material emojiItemMaterial;
    private final String emojiItemName;
    private final List<String> emojiItemLore;
    private final Material navigationMaterial;
    private final String previousPageName;
    private final String nextPageName;
    private final List<String> navigationLore;
    private final Material headerMaterial;
    private final String headerName;
    private final List<String> headerLore;
    private final int headerSlot;
    private final int previousPageSlot;
    private final int nextPageSlot;

    public EmojiGUI(FileConfiguration config, utils utils) {
        this.config = config;
        this.utils = utils;

        // Cache configuration values for better performance
        this.guiSize = config.getInt("Gui.Size");
        this.maxItemsPerPage = config.getInt("Gui.MaxItemsPerPage");
        this.guiTitleFormat = config.getString("Gui.Title");

        // Border configuration
        String borderMaterialName = config.getString("Gui.BorderItem.Material");
        this.borderMaterial = getMaterialSafely(borderMaterialName, Material.STAINED_GLASS_PANE);
        this.borderData = (short) config.getInt("Gui.BorderItem.Data");
        this.borderName = config.getString("Gui.BorderItemName");

        // Emoji item configuration
        String emojiMaterialName = config.getString("Gui.EmojiItem.Material");
        this.emojiItemMaterial = getMaterialSafely(emojiMaterialName, Material.PAPER);
        this.emojiItemName = config.getString("Gui.EmojiItem.Name");
        this.emojiItemLore = config.getStringList("Gui.EmojiItem.Lore");

        // Navigation configuration
        String navigationMaterialName = config.getString("Gui.NavigationButton.Material");
        this.navigationMaterial = getMaterialSafely(navigationMaterialName, Material.ARROW);
        this.previousPageName = config.getString("Gui.PreviousPageButton.Name");
        this.nextPageName = config.getString("Gui.NextPageButton.Name");
        this.navigationLore = config.getStringList("Gui.NavigationButton.Lore");

        // Header configuration
        String headerMaterialName = config.getString("Gui.HeaderItem.Material");
        this.headerMaterial = getMaterialSafely(headerMaterialName, Material.BOOK);
        this.headerName = config.getString("Gui.HeaderItem.Name");
        this.headerLore = config.getStringList("Gui.HeaderItem.Lore");

        // Slot configuration
        this.headerSlot = config.getInt("Gui.HeaderItem.Slot");
        this.previousPageSlot = config.getInt("Gui.PreviousPageButton.Slot");
        this.nextPageSlot = config.getInt("Gui.NextPageButton.Slot");

    }

    public void openEmojiGui(Player player, int page) {

        int totalPages = (int) Math.ceil((double) emojiList.size() / maxItemsPerPage);

        if (page < 0) page = 0;
        if (page >= totalPages) page = totalPages - 1;

        String title = ChatColor.translateAlternateColorCodes('&', guiTitleFormat
                .replace("{page}", String.valueOf(page + 1))
                .replace("{totalPages}", String.valueOf(totalPages)));

        Inventory gui = Bukkit.createInventory(null, guiSize, title);

        addBorder(gui);
        addEmojiItems(gui, page);
        addNavigationButtons(gui, page, totalPages);
        addHeaderItem(gui, page, totalPages);
        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        String title = event.getView().getTitle();

        if (!title.contains("Emojis")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        if (NBTUtils.hasKey(clickedItem, "customemojis")) {
            handleNavigationClick(player, clickedItem, title);
        }

        if (clickedItem.getType() == emojiItemMaterial) {
            handleEmojiClick(player, clickedItem);
        }
    }

    private void addBorder(Inventory gui) {
        ItemStack borderItem = createBorderItem();

        int rows = guiSize / 9;
        for (int i = 0; i < guiSize; i++) {
            int row = i / 9;
            int col = i % 9;

            // Top border, bottom border, left border, right border
            if (row == 0 || row == rows - 1 || col == 0 || col == 8) {
                gui.setItem(i, borderItem);
            }
        }
    }

    private void addEmojiItems(Inventory gui, int page) {
        int startIndex = page * maxItemsPerPage;
        int endIndex = Math.min(startIndex + maxItemsPerPage, emojiList.size());

        int slot = 10;

        for (int i = startIndex; i < endIndex; i++) {
            while (isBorderSlot(slot)) {
                slot++;
            }

            String emojiEntry = emojiList.get(i);
            String[] parts = emojiEntry.split("->", 2);

            if (parts.length == 2) {
                ItemStack emojiItem = createEmojiItem(parts[0].trim(), parts[1].trim());
                gui.setItem(slot, emojiItem);
                slot++;
            }
        }
    }

    private void addNavigationButtons(Inventory gui, int page, int totalPages) {
        // Previous page button
        if (page > 0) {
            ItemStack previousPage = createNavigationButton(true, page, totalPages);
            gui.setItem(previousPageSlot, previousPage);
        }

        // Next page button
        if (page < totalPages - 1) {
            ItemStack nextPage = createNavigationButton(false, page, totalPages);
            gui.setItem(nextPageSlot, nextPage);
        }
    }

    private void addHeaderItem(Inventory gui, int page, int totalPages) {
        ItemStack headerItem = createHeaderItem(page, totalPages);
        gui.setItem(headerSlot, headerItem);
    }

    private ItemStack createBorderItem() {
        ItemStack borderItem = new ItemStack(borderMaterial, 1, borderData);
        ItemMeta borderMeta = borderItem.getItemMeta();
        if (borderMeta != null) {
            borderMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', borderName));
            borderItem.setItemMeta(borderMeta);
        }
        return borderItem;
    }

    private ItemStack createEmojiItem(String before, String after) {
        ItemStack emojiItem = new ItemStack(emojiItemMaterial);
        ItemMeta meta = emojiItem.getItemMeta();
        if (meta != null) {
            String displayName = ChatColor.translateAlternateColorCodes('&',
                    emojiItemName.replace("{emojiName}", before));
            meta.setDisplayName(displayName);

            List<String> lore = new ArrayList<>();
            for (String loreLine : emojiItemLore) {
                String processedLore = ChatColor.translateAlternateColorCodes('&',
                        loreLine.replace("{emojiName}", before)
                                .replace("{emojiReplaced}", ChatColor.translateAlternateColorCodes('&', after)));
                lore.add(processedLore);
            }
            meta.setLore(lore);
            emojiItem.setItemMeta(meta);
        }
        return emojiItem;
    }

    private ItemStack createNavigationButton(boolean isPrevious, int page, int totalPages) {
        ItemStack button = new ItemStack(navigationMaterial);
        ItemMeta meta = button.getItemMeta();
        if (meta != null) {
            String displayName = ChatColor.translateAlternateColorCodes('&',
                    isPrevious ? previousPageName : nextPageName);
            meta.setDisplayName(displayName);

            List<String> lore = new ArrayList<>();
            for (String loreLine : navigationLore) {
                String processedLore = ChatColor.translateAlternateColorCodes('&',
                        loreLine.replace("{page}", String.valueOf(page + 1))
                                .replace("{totalPages}", String.valueOf(totalPages)));
                lore.add(processedLore);
            }
            meta.setLore(lore);
            button.setItemMeta(meta);
        }
        if (isPrevious) {
            button = NBTUtils.setKey(button, "customemojis", "previous_page");
        } else {
            button = NBTUtils.setKey(button, "customemojis", "next_page");
        }

        return button;
    }

    private ItemStack createHeaderItem(int page, int totalPages) {
        ItemStack headerItem = new ItemStack(headerMaterial);
        ItemMeta headerMeta = headerItem.getItemMeta();
        if (headerMeta != null) {
            String displayName = ChatColor.translateAlternateColorCodes('&', headerName);
            headerMeta.setDisplayName(displayName);

            List<String> lore = new ArrayList<>();
            for (String loreLine : headerLore) {
                String processedLore = ChatColor.translateAlternateColorCodes('&',
                        loreLine.replace("{page}", String.valueOf(page + 1))
                                .replace("{totalPages}", String.valueOf(totalPages)));
                lore.add(processedLore);
            }
            headerMeta.setLore(lore);
            headerItem.setItemMeta(headerMeta);
        }
        return headerItem;
    }

    private void handleNavigationClick(Player player, ItemStack clickedItem, String title) {
        String nbtValue = NBTUtils.getKeyStringValue(clickedItem, "customemojis");
        int currentPage = extractPageFromTitle(title) - 1;

        if ("previous_page".equals(nbtValue)) {
            openEmojiGui(player, currentPage - 1);
        } else if ("next_page".equals(nbtValue)) {
            openEmojiGui(player, currentPage + 1);
        }
    }

    private void handleEmojiClick(Player player, ItemStack clickedItem) {
        String displayName = clickedItem.getItemMeta() != null ?
                clickedItem.getItemMeta().getDisplayName() : "";

        String emojiName = extractEmojiFromDisplayName(displayName);
        if (emojiName != null && !emojiName.isEmpty()) {
            String clickMessage = config.getString("Gui.emoji-click-message");
            if (clickMessage != null && !clickMessage.isEmpty()) {
                utils.sendMessage(player, clickMessage.replace("{emojiName}", emojiName));
            }
        }
    }

    private int extractPageFromTitle(String title) {
        try {
            String cleanTitle = ChatColor.stripColor(title);

            String[] parts = cleanTitle.split("\\s+");
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (part.contains("/")) {
                    String[] pageParts = part.split("/");
                    if (pageParts.length >= 1) {
                        String pageNum = pageParts[0].replaceAll("\\D", "");
                        if (!pageNum.isEmpty()) {
                            return Integer.parseInt(pageNum);
                        }
                    }
                }
                if (i < parts.length - 1 && parts[i + 1].startsWith("/")) {
                    String pageNum = part.replaceAll("\\D", "");
                    if (!pageNum.isEmpty()) {
                        return Integer.parseInt(pageNum);
                    }
                }
            }
        } catch (NumberFormatException e) {
            return 1;
        }
        return 1;
    }

    private String extractEmojiFromDisplayName(String displayName) {
        String strippedName = ChatColor.stripColor(displayName);
        String configPattern = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', emojiItemName));

        if (configPattern.contains("{emojiName}")) {
            String[] parts = configPattern.split("\\{emojiName\\}");
            if (parts.length >= 1) {
                String prefix = parts[0];
                String suffix = parts.length > 1 ? parts[1] : "";

                if (strippedName.startsWith(prefix)) {
                    String result = strippedName.substring(prefix.length());
                    if (!suffix.isEmpty() && result.endsWith(suffix)) {
                        result = result.substring(0, result.length() - suffix.length());
                    }
                    return result.trim();
                }
            }
        }

        return strippedName;
    }

    private boolean isBorderSlot(int slot) {
        int row = slot / 9;
        int col = slot % 9;
        int rows = guiSize / 9;

        return row == 0 || row == rows - 1 || col == 0 || col == 8;
    }

    private Material getMaterialSafely(String materialName, Material fallback) {
        try {
            return Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }
}