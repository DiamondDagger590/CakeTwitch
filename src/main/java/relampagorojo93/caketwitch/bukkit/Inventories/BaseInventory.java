package relampagorojo93.caketwitch.bukkit.Inventories;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;

public class BaseInventory {
	
	private static ItemStack[] base;
	
	public static ItemStack[] getBase() { return base; }

	public static ItemStack getReturnItem() {
		ItemStack item = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1,
				"ORANGE_STAINED_GLASS_PANE");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RETURNNAME.toString());
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeftArrow() {
		ItemStack item = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_LEFTARROWNAME.toString());
		im.setLore(null);
		item.setItemMeta(im);
		return ItemStacksUtils.setSkin(item,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJmMDQyNWQ2NGZkYzg5OTI5MjhkNjA4MTA5ODEwYzEyNTFmZTI0M2Q2MGQxNzViZWQ0MjdjNjUxY2JlIn19fQ==");
	}
	
	public static ItemStack getRightArrow() {
		ItemStack item = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_RIGHTARROWNAME.toString());
		im.setLore(null);
		item.setItemMeta(im);
		return ItemStacksUtils.setSkin(item,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ4NjVhYWUyNzQ2YTliOGU5YTRmZTYyOWZiMDhkMThkMGE5MjUxZTVjY2JlNWZhNzA1MWY1M2VhYjliOTQifX19");
	}
	
	public static ItemStack getUpArrow() {
		ItemStack item = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_UPARROWNAME.toString());
		im.setLore(null);
		item.setItemMeta(im);
		return ItemStacksUtils.setSkin(item,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUxNDlkZGRhZGVkMjBkMjQ0ZTBiYjYyYTJkOWZhMGRjNmM2YTc4NjI1NTkzMjhhOTRmNzc3MjVmNTNjMzU4In19fQ==");
	}
	
	public static ItemStack getDownArrow() {
		ItemStack item = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MessageString.COMMONGUI_DOWNARROWNAME.toString());
		im.setLore(null);
		item.setItemMeta(im);
		return ItemStacksUtils.setSkin(item,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ3MmM5ZDYyOGJiMzIyMWVmMzZiNGNiZDBiOWYxNWVkZDU4ZTU4NjgxODUxNGQ3ZTgyM2Q1NWM0OGMifX19");
	}
	
	static {
		ItemStack gpane = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 7,
				"GRAY_STAINED_GLASS_PANE");
		ItemMeta im = gpane.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		gpane.setItemMeta(im);
		base = new ItemStack[54];
		for (int i = 0; i < base.length; i++) base[i] = gpane;
	}
}
