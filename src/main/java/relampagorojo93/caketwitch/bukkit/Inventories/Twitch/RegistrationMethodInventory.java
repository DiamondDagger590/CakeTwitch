package relampagorojo93.caketwitch.bukkit.Inventories.Twitch;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Events.ChatEvents;
import relampagorojo93.caketwitch.bukkit.Events.ChatEventsObjects.InputData;
import relampagorojo93.caketwitch.bukkit.Events.ChatEventsObjects.SpecifyChannelInputData;
import relampagorojo93.caketwitch.bukkit.Inventories.Abstracts.PluginChestInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.BaseInventory;

public class RegistrationMethodInventory extends PluginChestInventory {

	private static ItemStack[] base;
	private static ItemStack uuid, oauth;

	public static ItemStack[] getBase() {
		return base;
	}

	static {
		ItemStack it = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 7, "GRAY_STAINED_GLASS_PANE");
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		it.setItemMeta(im);
		base = new ItemStack[9];
		for (int i = 0; i < base.length; i++)
			base[i] = it;

		it = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 15, "BLACK_STAINED_GLASS_PANE");
		im = it.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		it.setItemMeta(im);
		base[0] = it;

		// UUID item

		uuid = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY2OTJmOTljYzZkNzgyNDIzMDQxMTA1NTM1ODk0ODQyOThiMmU0YTAyMzNiNzY3NTNmODg4ZTIwN2VmNSJ9fX0=");
		im = uuid.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eUUID").toString());
		uuid.setItemMeta(im);

		// OAuth item

		oauth = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiZTY1ZjQ0Y2QyMTAxNGM4Y2RkZDAxNThiZjc1MjI3YWRjYjFmZDE3OWY0YzFhY2QxNThjODg4NzFhMTNmIn19fQ==");
		im = oauth.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eOAuth").toString());
		oauth.setItemMeta(im);

	}

	public RegistrationMethodInventory(Player player) {
		super(player);

		setAllowStorageExchange(false);
		setName("Registration Method");
		setBackground(getBase());
		setSize(9);

	}

	@Override
	public void updateContent() {

		setSlot(3, new Button(uuid) {
			@Override
			public void onClick(InventoryClickEvent e) {
				closeInventory(CakeTwitchAPI.getPlugin());
				MessagesUtils.getMessageBuilder().sendTitle(getPlayer(), true, "REGISTRATION",
						"Type the username of your channel\n Type 'cancel' to stop the registration", 20, 100, 20);
				ChatEvents.register((InputData) new SpecifyChannelInputData(getPlayer()));
			}
		});

		setSlot(5, new Button(oauth) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new OAuthPermissionsInventory(getPlayer()).setPreviousHolder(getHolder())
						.openInventory(CakeTwitchAPI.getPlugin());
			}
		});

		if (getPreviousHolder() != null)
			setSlot(0, new Button(BaseInventory.getReturnItem()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					goToPreviousHolder(CakeTwitchAPI.getPlugin());
				}
			});
		else
			removeSlot(0);

	}

	public static Inventory getEzInventory(Player player) {
		return new RegistrationMethodInventory(player).getInventory();
	}
}
