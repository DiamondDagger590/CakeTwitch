package relampagorojo93.caketwitch.bukkit.inventories.twitch.contents.statistics;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.caketwitch.bukkit.inventories.BaseInventory;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory;
import relampagorojo93.caketwitch.relautils.bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.AbstractInventory;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Button;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Item;
import relampagorojo93.caketwitch.spigotdebug.DebugController;
import relampagorojo93.caketwitch.spigotdebug.DebugData;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StatisticsSubscriptionsContent {

    private static ItemStack title, notfoundhead;

    static {

        ItemMeta im = null;

        // Title item

        title = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD").clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRjMDAxNjQyMzhkOGFkOTlhZmFkNzkwYzMwNTBmZjllZmY0MTBlMmQ0YTNmZTg1NjAwMzg5ZGNiODE4YTg0OSJ9fX0=");
        im = title.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lSubscriptions").toString());
        title.setItemMeta(im);

        // Not found head item

        notfoundhead = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTM1OWQ5MTI3NzI0MmZjMDFjMzA5YWNjYjg3YjUzM2YxOTI5YmUxNzZlY2JhMmNkZTYzYmY2MzVlMDVlNjk5YiJ9fX0=");

    }

    public static void setContent(StreamerInventory inv) {

        inv.setSlot(4, new Item(title));

        DebugController debug = inv.getStreamer().getStatistics().getSubscriptions();

        int page = inv.getPage(StatisticsSubscriptionsContent.class);
        int max = (int) (((double) debug.getRegisteredAmount() / 28D) + 0.99D);

        if (page < 1) {
            page = 1;
            inv.setPage(StatisticsSubscriptionsContent.class, 1);
        }

        if (page > max) {
            page = max;
            inv.setPage(StatisticsSubscriptionsContent.class, max);
        }

        ItemStack it = null;
        ItemMeta im = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        for (int i = 0; i < 28; i++) {
            DebugData data = debug.getDebugData(i);
			if (data != null) {
				String[] split = data.getMessage().split("\\|");
				UUID uuid = split[split.length - 1].equals("-") ? null : UUID.fromString(split[split.length - 1]);
				OfflinePlayer pl = uuid != null ? Bukkit.getOfflinePlayer(uuid) : null;

				it = pl != null ? ItemStacksUtils.getPlayerHead(pl) : notfoundhead.clone();
				im = it.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&a" + split[split.length - 3]).toString());
				im.setLore(MessagesUtils.getMessageBuilder().createMessage(
					(split[0].equals("subgift") ? "&dFrom " + split[2] + " " : "") + "&f" + split[split.length - 2] + (split[split.length - 2].equals("1") ? " month" : " months"),
					"&0",
					"&7" + format.format(new Date(Long.parseLong(split[1])))
				).getStrings());
				it.setItemMeta(im);

				inv.setSlot(10 + i + ((i / 7) * 2), new Item(it));
			}
			else {
				inv.setSlot(10 + i + ((i / 7) * 2), AbstractInventory.NULL);
			}
        }

		if (page > 1) {
			inv.setSlot(inv.getSize() - 8, new Button(BaseInventory.getLeftArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					inv.setPage(StatisticsSubscriptionsContent.class,
						inv.getPage(StatisticsSubscriptionsContent.class) - 1);
					inv.updateInventory();
				}
			});
		}
		else {
			inv.removeSlot(inv.getSize() - 8);
		}

		if (page < max) {
			inv.setSlot(inv.getSize() - 2, new Button(BaseInventory.getRightArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					inv.setPage(StatisticsSubscriptionsContent.class,
						inv.getPage(StatisticsSubscriptionsContent.class) + 1);
					inv.updateInventory();
				}
			});
		}
		else {
			inv.removeSlot(inv.getSize() - 2);
		}

    }
}