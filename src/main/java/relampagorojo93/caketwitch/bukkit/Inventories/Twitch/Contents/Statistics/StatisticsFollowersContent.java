package relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.SpigotDebug.DebugController;
import relampagorojo93.LibsCollection.SpigotDebug.DebugData;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.AbstractInventory;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.bukkit.Inventories.BaseInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StatisticsFollowersContent {

	private static ItemStack title, notfoundhead;

	static {

		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");

		ItemMeta im = null;

		// Followers item

		title = ItemStacksUtils.setSkin(defaulthead.clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUwZTYzOGVlYzAwMDg5NDgzNzIwNmFiNzU4NzhlODU0MTVmZTk0ZTViZGU3N2IxYjEyMzdhODlmYjM5Yzc3YyJ9fX0=");
		im = title.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lFollowers").toString());
		title.setItemMeta(im);
		
		// Not found head item
		
		notfoundhead = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTM1OWQ5MTI3NzI0MmZjMDFjMzA5YWNjYjg3YjUzM2YxOTI5YmUxNzZlY2JhMmNkZTYzYmY2MzVlMDVlNjk5YiJ9fX0=");

	}

	public static void setContent(StreamerInventory inv) {
		
		inv.setSlot(4, new Item(title));
		
		DebugController debug = inv.getStreamer().getStatistics().getFollowers();
		
		int page = inv.getPage(StatisticsFollowersContent.class);
		int max = (int) (((double) debug.getRegisteredAmount() / 28D) + 0.99D);
		
		if (page < 1) {
			page = 1; inv.setPage(StatisticsFollowersContent.class, 1);
		}
		
		if (page > max) {
			page = max; inv.setPage(StatisticsFollowersContent.class, max);
		}

		ItemStack it = null;
		ItemMeta im = null;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		for (int i = 0; i < 28; i++) {
			DebugData data = debug.getDebugData(i);
			if (data != null) {
				String[] split = data.getMessage().split("\\|");
				UUID uuid = split[2].equals("-") ? null : UUID.fromString(split[2]);
				OfflinePlayer pl = uuid != null ? Bukkit.getOfflinePlayer(uuid) : null;
				
				it = pl != null ? ItemStacksUtils.getPlayerHead(pl) : notfoundhead.clone();
				im = it.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&a" + split[1]).toString());
				im.setLore(MessagesUtils.getMessageBuilder().createMessage(
						"&0",
						"&7" + format.format(new Date(Long.parseLong(split[0])))
						).getStrings());
				it.setItemMeta(im);
				
				inv.setSlot(10 + i + ((i/7) * 2), new Item(it));
			}
			else inv.setSlot(10 + i + ((i/7) * 2), AbstractInventory.NULL);
		}
		
		if (page > 1)
			inv.setSlot(inv.getSize() - 8, new Button(BaseInventory.getLeftArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					inv.setPage(StatisticsFollowersContent.class, inv.getPage(StatisticsFollowersContent.class) - 1); inv.updateInventory();
				}
			});
		else
			inv.removeSlot(inv.getSize() - 8);
		
		if (page < max)
			inv.setSlot(inv.getSize() - 2, new Button(BaseInventory.getRightArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					inv.setPage(StatisticsFollowersContent.class, inv.getPage(StatisticsFollowersContent.class) + 1); inv.updateInventory();
				}
			});
		else
			inv.removeSlot(inv.getSize() - 2);
		
		
	}
}
