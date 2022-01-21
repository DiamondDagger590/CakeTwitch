package relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.IRCBot.Status;
import relampagorojo93.LibsCollection.SpigotDebug.DebugData;
import relampagorojo93.LibsCollection.SpigotDebug.DebugType;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Debug.DebugInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;

public class BotContent {

	private static ItemStack bot, registry;

	static {
		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		
		ItemMeta im = null;
		
		// Bot item

		bot = defaulthead.clone();
		im = bot.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lTwitch Bot &8&l[&r&a%param%&8&l]").toString());
		bot.setItemMeta(im);

		// Registry item

		registry = new ItemStack(Material.BOOK);
		im = registry.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lRegistry").toString());
		registry.setItemMeta(im);

	}

	public static void setContent(StreamerInventory inv) {
		Status status = inv.getStreamer().getBot().getStatus();
		DebugData lastdata = inv.getStreamer().getBot().getDebugController().getDebugData(0);

		ItemStack i = null;
		ItemMeta im = null;
		
		// Bot button

		i = ItemStacksUtils.setSkin(bot.clone(), status == Status.RUNNING
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE3MmZkMzNjZmJiMzVkZWVlZDNhZTU4ZjNlM2VkYjIzNWFhMmFmZjE1NzdiYWE4Mzk4ODlmZmU5MWQ2OWYyOSJ9fX0="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyMTZkMWY4MzA4NzJiOTgxNjUzMGFkYzFkNjZjNzA1ZTY4MGU3ZDZmMGRmMmE2ZDVjMmUzMGMwY2I0MTE5MSJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(im.getDisplayName().replace("%param%", MessagesUtils.getMessageBuilder().createMessage((status == Status.RUNNING ? "&a" : "&c") + status.name()).toString()));
		i.setItemMeta(im);
		inv.setSlot(20, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				if (status == Status.RUNNING)
					TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> inv.getStreamer().getBot().stopBot());
				else
					TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> inv.getStreamer().getBot().startBot());
			}
		});

		// Debug button

		i = registry.clone();
		im = i.getItemMeta();
		im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0",
				"&8  > &7Last log: " + (lastdata != null ? (lastdata.getDebugType() == DebugType.LOG
						? "&a"
						: lastdata.getDebugType() == DebugType.ALERT ? "&e" : "&a")
						+ (lastdata.getMessage().length() < 33 ? lastdata.getMessage()
								: lastdata.getMessage().substring(0, 32) + "...")
						: ""))
				.getStrings());
		i.setItemMeta(im);
		inv.setSlot(24, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new DebugInventory(
						MessagesUtils.getMessageBuilder().createMessage("&r&aBot &8&l> &r&cDebug").toString(),
						inv.getPlayer(), inv.getStreamer().getBot().getDebugController()).setPreviousHolder(inv.getHolder())
								.openInventory(CakeTwitchAPI.getPlugin());
			}
		});
		
	}
}
