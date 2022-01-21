package relampagorojo93.caketwitch.bukkit.Inventories.Twitch;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Abstracts.PluginChestInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.BaseInventory;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;

public class OAuthPermissionsInventory extends PluginChestInventory {

	private static ItemStack[] base;
	private static ItemStack confirmation, channelRewardsWithoutTextItem, hypeTrainItem;

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

		// Confirmation item
		
		confirmation = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
		im = confirmation.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&aConfirm").toString());
		confirmation.setItemMeta(im);

		// Revoke streamer rights item

		channelRewardsWithoutTextItem = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		im = channelRewardsWithoutTextItem.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eChannel Rewards Without Text").toString());
		channelRewardsWithoutTextItem.setItemMeta(im);

		// Grant streamer rights item

		hypeTrainItem = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		im = hypeTrainItem.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eHype Train").toString());
		hypeTrainItem.setItemMeta(im);
	}
	
	public boolean channelRewardsWithoutText = true;
	public boolean hypeTrain = true;

	public OAuthPermissionsInventory(Player player) {
		super(player);

		setAllowStorageExchange(false);
		setName("OAuth Permissions");
		setBackground(getBase());
		setSize(9);
		
		setSlot(8, new Button(confirmation) {
			
			@Override
			public void onClick(InventoryClickEvent e) {
				closeInventory(CakeTwitchAPI.getPlugin());
				String url = SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString()
						+ (SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString().endsWith("/") ? ""
								: "/")
						+ "authorization?uuid=" + getPlayer().getUniqueId().toString() + "&rewards=" + (channelRewardsWithoutText ? 1 : 0) + "&hype=" + (hypeTrain ? 1 : 0);
				MessagesUtils.getMessageBuilder()
						.createMessage(
								new TextReplacement[] { new TextReplacement("%url%", () -> "Click Me",
										new ClickEvent(ClickEvent.Action.OPEN_URL, url)) },
								true, "&8[&a%url%&8]")
						.sendMessage(e.getWhoClicked());
			}
		});

	}

	@Override
	public void updateContent() {
		
		setSlot(3, new Button(ItemStacksUtils.setSkin(channelRewardsWithoutTextItem.clone(), channelRewardsWithoutText ?
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ==" :
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			
			@Override
			public void onClick(InventoryClickEvent e) {
				channelRewardsWithoutText = !channelRewardsWithoutText; updateInventory();
			}
		});
		
		setSlot(5, new Button(ItemStacksUtils.setSkin(hypeTrainItem.clone(), hypeTrain ?
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ==" :
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			
			@Override
			public void onClick(InventoryClickEvent e) {
				hypeTrain = !hypeTrain; updateInventory();
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
		return new OAuthPermissionsInventory(player).getInventory();
	}
	
}
