package me.zyromate.customemojis.Utilities;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NBTUtils {
    public static ItemStack setKey(ItemStack itemStack, String key, String value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString(key, value);
        nbtItem.applyNBT(itemStack);
        return nbtItem.getItem();
    }

    public static ItemStack setKey(ItemStack itemStack, String key, int value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger(key, value);
        nbtItem.applyNBT(itemStack);
        return nbtItem.getItem();
    }

    public static boolean hasKey(ItemStack itemStack, String key) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey(key);
    }

    public static boolean hasData(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasNBTData();
    }

    public static ItemStack removeKey(ItemStack itemStack, String key) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.removeKey(key);
        nbtItem.applyNBT(itemStack);
        return nbtItem.getItem();
    }

    public static int getKeyIntValue(ItemStack itemStack, String key) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getInteger(key);
    }

    public static String getKeyStringValue(ItemStack itemStack, String key) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString(key);
    }

    public static ItemStack addGlow(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static boolean hasSameNBT(ItemStack item1, ItemStack item2, String nbtKey) {
        if (item1 == null || item2 == null) return false;

        try {
            NBTItem nbt1 = new NBTItem(item1);
            NBTItem nbt2 = new NBTItem(item2);

            return nbt1.hasKey(nbtKey) &&
                    nbt2.hasKey(nbtKey) &&
                    nbt1.getString(nbtKey).equals(nbt2.getString(nbtKey));
        } catch (Exception e) {
            return false;
        }
    }

    public static ItemStack setUnbreakable(ItemStack itemStack, boolean value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Unbreakable", value);
        nbtItem.applyNBT(itemStack);
        return nbtItem.getItem();
    }
}