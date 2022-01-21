package relampagorojo93.caketwitch.bukkit.inventories.twitch.contents.statistics;

import org.bukkit.ChatColor;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticsEventsContent {

    private static ItemStack title, bits, reward, message, raid;

    static {

        ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");

        ItemMeta im = null;

        // Title item

        title = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY0YzIxZDE3YWQ2MzYzODdlYTNjNzM2YmZmNmFkZTg5NzMxN2UxMzc0Y2Q1ZDliMWMxNWU2ZTg5NTM0MzIifX19");
        im = title.getItemMeta();
        im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lEvents").toString());
        title.setItemMeta(im);

        // Not found head item

        bits = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmMwZTZkOWUyNDI3MzU0ODE5MThjNWZkMTQ0OThiZDc2MGJiOWY0ZmY2NDMwYWQ0Njk2YjM4ZThhODgzZGE5NyJ9fX0=");

        reward = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2NkM2Q5OWFkMmVlNjNmYmU5ZjEzZGIzYWM2NDdiMjI4NWIyMTdhYjJkZGMzMWY2NGNhYjQ1ZmJiZjdhNCJ9fX0=");

        message = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIyODE1Yjk5YzEzYmZjNTViNGM1YzI5NTlkMTU3YTYyMzNhYjA2MTg2NDU5MjMzYmMxZTRkNGY3ODc5MmM2OSJ9fX0=");

        raid = ItemStacksUtils.setSkin(defaulthead.clone(),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2QyMGJmNTJlYzM5MGEwNzk5Mjk5MTg0ZmM2NzhiZjg0Y2Y3MzJiYjFiZDc4ZmQxYzRiNDQxODU4ZjAyMzVhOCJ9fX0=");
    }

    public static void setContent(StreamerInventory inv) {

        inv.setSlot(4, new Item(title));

        DebugController debug = inv.getStreamer().getStatistics().getEvents();

        int page = inv.getPage(StatisticsEventsContent.class);
        int max = (int) (((double) debug.getRegisteredAmount() / 28D) + 0.99D);

        if (page < 1) {
            page = 1;
            inv.setPage(StatisticsEventsContent.class, 1);
        }

        if (page > max) {
            page = max;
            inv.setPage(StatisticsEventsContent.class, max);
        }

        ItemStack it = null;
        ItemMeta im = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        for (int i = 0; i < 28; i++) {
            DebugData data = debug.getDebugData(i);
			if (data != null) {
				String[] split = data.getMessage().split("\\|");
				switch (split[0]) {
					case "bits":
						it = bits.clone();
						im = it.getItemMeta();
						im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eBits").toString());
						im.setLore(MessagesUtils.getMessageBuilder().createMessage(
							"&a" + split[2] + " &f" + split[3] + (split[3].equals("1") ? " bit" : " bits"),
							"&0",
							"&7" + format.format(new Date(Long.parseLong(split[1])))
						).getStrings());
						it.setItemMeta(im);
						inv.setSlot(10 + i + ((i / 7) * 2), new Item(it));
						break;
					case "msghl":
						it = message.clone();
						im = it.getItemMeta();
						im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eHighlighted Message").toString());
						List<String> lore = new ArrayList<>();
						lore.add(MessagesUtils.getMessageBuilder().createMessage("&a" + split[2]).toString());
						for (String str : MessagesUtils.splitByLines(split[3], 48)) {
							lore.add(MessagesUtils.getMessageBuilder().createMessage("&7" + str).toString());
						}
						lore.add(ChatColor.BLACK.toString());
						lore.add(MessagesUtils.getMessageBuilder().createMessage("&7" + format.format(new Date(Long.parseLong(split[1])))).toString());
						im.setLore(lore);
						it.setItemMeta(im);
						inv.setSlot(10 + i + ((i / 7) * 2), new Item(it));
						break;
					case "raid":
						it = raid.clone();
						im = it.getItemMeta();
						im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eRaid").toString());
						im.setLore(MessagesUtils.getMessageBuilder().createMessage(
							"&a" + split[2] + " &f" + split[3] + (split[3].equals("1") ? " viewer" : " viewers"),
							"&0",
							"&7" + format.format(new Date(Long.parseLong(split[1])))
						).getStrings());
						it.setItemMeta(im);
						inv.setSlot(10 + i + ((i / 7) * 2), new Item(it));
						break;
					case "reward":
						it = reward.clone();
						im = it.getItemMeta();
						im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eChannel Reward").toString());
						im.setLore(MessagesUtils.getMessageBuilder().createMessage(
							"&a" + split[2],
							"&0",
							"&7" + format.format(new Date(Long.parseLong(split[1])))
						).getStrings());
						it.setItemMeta(im);
						inv.setSlot(10 + i + ((i / 7) * 2), new Item(it));
						break;
					default:
						inv.setSlot(10 + i + ((i / 7) * 2), AbstractInventory.NULL);
						break;
				}
			}
			else {
				inv.setSlot(10 + i + ((i / 7) * 2), AbstractInventory.NULL);
			}
        }

		if (page > 1) {
			inv.setSlot(inv.getSize() - 8, new Button(BaseInventory.getLeftArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					inv.setPage(StatisticsEventsContent.class,
						inv.getPage(StatisticsEventsContent.class) - 1);
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
					inv.setPage(StatisticsEventsContent.class,
						inv.getPage(StatisticsEventsContent.class) + 1);
					inv.updateInventory();
				}
			});
		}
		else {
			inv.removeSlot(inv.getSize() - 2);
		}

    }
}
