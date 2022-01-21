package relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.AbstractInventory;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.SettingsConfigInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SettingsContent {

	private static ItemStack configuration, event, chat, channelPoints, subscriptions, raids, bits, followers, normalChat, subscriberChat, highlightedChat, ritualChat;

	static {
		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		
		ItemMeta im = null;

		// Configuration item

		configuration = new ItemStack(Material.BOOK);
		im = configuration.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lConfiguration &8[&a%param%&8]").toString());
		configuration.setItemMeta(im);

		// Event information item

		event = ItemStacksUtils.setSkin(defaulthead.clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjFjNjNkOWI5ZmQ4NzQyZWFlYjA0YzY5MjE3MmNiOWRhNDM3ODE2OThhNTc1Y2RhYmUxYzA0ZGYxMmMzZiJ9fX0=");
		im = event.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lEvent Settings").toString());
		event.setItemMeta(im);

		// Chat information item

		chat = ItemStacksUtils.setSkin(defaulthead.clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjQ4Y2UxY2YxOGFmMDVhNTc2ZDYwODEyMzAwMWI3OTFmZWRiNjIyOTExZWY4ZDM4YTMyMGRhM2JjYmY2ZmQyMCJ9fX0=");
		im = chat.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lChat Settings").toString());
		chat.setItemMeta(im);

		// Channel points event item

		channelPoints = defaulthead.clone();
		im = channelPoints.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eChannel Points").toString());
		channelPoints.setItemMeta(im);

		// Subscriptions event item

		subscriptions = defaulthead.clone();
		im = subscriptions.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eSubscriptions").toString());
		subscriptions.setItemMeta(im);

		// Raids event item

		raids = defaulthead.clone();
		im = raids.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eRaids").toString());
		raids.setItemMeta(im);

		// Bits event item

		bits = defaulthead.clone();
		im = bits.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eBits").toString());
		bits.setItemMeta(im);

		// Followers event item

		followers = defaulthead.clone();
		im = followers.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eFollowers").toString());
		followers.setItemMeta(im);

		// Normal chat message item

		normalChat = defaulthead.clone();
		im = normalChat.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eNormal Messages").toString());
		normalChat.setItemMeta(im);

		// Subscriber chat message item

		subscriberChat = defaulthead.clone();
		im = subscriberChat.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eSubscriber Messages").toString());
		subscriberChat.setItemMeta(im);

		// Highlighted chat message item

		highlightedChat = defaulthead.clone();
		im = highlightedChat.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eHighlighted Messages").toString());
		highlightedChat.setItemMeta(im);

		// Ritual chat message item

		ritualChat = defaulthead.clone();
		im = ritualChat.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eRitual Messages").toString());
		ritualChat.setItemMeta(im);

	}

	public static void setContent(StreamerInventory inv) {
		
		inv.setSlot(30, AbstractInventory.NULL);
		inv.setSlot(33, AbstractInventory.NULL);
		inv.setSlot(34, AbstractInventory.NULL);

		String cs = inv.getStreamer().getSettings().getConfiguration();
		Configuration co = CakeTwitchAPI.getConfig().getConfig(cs);

		boolean dcp = inv.getStreamer().getSettings().detectChannelPoints();
		boolean ds = inv.getStreamer().getSettings().detectSubscriptions();
		boolean dr = inv.getStreamer().getSettings().detectRaids();
		boolean db = inv.getStreamer().getSettings().detectBits();
		boolean df = inv.getStreamer().getSettings().detectFollowers();

		boolean snm = inv.getStreamer().getSettings().showNormalMessages();
		boolean ssm = inv.getStreamer().getSettings().showSubscriberMessages();
		boolean shm = inv.getStreamer().getSettings().showHighlightedMessages();
		boolean srm = inv.getStreamer().getSettings().showRitualMessages();
		
		ItemStack i = null;
		ItemMeta im = null;
		List<String> lore = null;

		// Event settings information

		i = event.clone();
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0",
						"  &8> &7Channel points: " + (dcp ? "&aEnabled" : "&cDisabled"),
						"  &8> &7Subscriptions: " + (ds ? "&aEnabled" : "&cDisabled"),
						"  &8> &7Raids: " + (dr ? "&aEnabled" : "&cDisabled"),
						"  &8> &7Bits: " + (db ? "&aEnabled" : "&cDisabled"),
						"  &8> &7Followers: " + (df ? "&aEnabled" : "&cDisabled"))
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		inv.setSlot(11, new Item(i));

		// Chat settings information

		i = chat.clone();
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0",
						"  &8> &7Normal messages: " + (snm ? "&aEnabled" : "&cDisabled"),
						"  &8> &7Subscriber messages: " + (ssm ? "&aEnabled" : "&cDisabled"),
						"  &8> &7Highlighted messages: " + (shm ? "&aEnabled" : "&cDisabled"),
						"  &8> &7Ritual messages: " + (srm ? "&aEnabled" : "&cDisabled"))
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		inv.setSlot(15, new Item(i));

		// Configuration item

		i = configuration.clone();
		im = i.getItemMeta();
		im.setDisplayName(im.getDisplayName().replace("%param%", MessagesUtils.getMessageBuilder().createMessage(co != null ? "&a" + cs : "&c!" + (cs != null ? cs : "")).toString()));
		i.setItemMeta(im);
		inv.setSlot(49, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				new SettingsConfigInventory(inv.getPlayer(), inv.getStreamer()).setPreviousHolder(inv.getHolder())
						.openInventory(CakeTwitchAPI.getPlugin());
			}
		});

		// Channel points button
		
		inv.setSlot(19, new Button(ItemStacksUtils.setSkin(channelPoints.clone(), dcp
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings()
						.setDetectChannelPoints(!inv.getStreamer().getSettings().detectChannelPoints());
				inv.updateInventory();
			}
		});

		// Subscriptions button
		
		inv.setSlot(20, new Button(ItemStacksUtils.setSkin(subscriptions.clone(), ds
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings()
						.setDetectSubscriptions(!inv.getStreamer().getSettings().detectSubscriptions());
				inv.updateInventory();
			}
		});

		// Raids button
		
		inv.setSlot(21, new Button(ItemStacksUtils.setSkin(raids.clone(), dr
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings().setDetectRaids(!inv.getStreamer().getSettings().detectRaids());
				inv.updateInventory();
			}
		});

		// Bits button
		
		inv.setSlot(28, new Button(ItemStacksUtils.setSkin(bits.clone(), db
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings().setDetectBits(!inv.getStreamer().getSettings().detectBits());
				inv.updateInventory();
			}
		});

		// Followers button
		
		inv.setSlot(29, new Button(ItemStacksUtils.setSkin(followers.clone(), df
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings()
						.setDetectFollowers(!inv.getStreamer().getSettings().detectFollowers());
				inv.updateInventory();
			}
		});

		// Normal messages
		
		inv.setSlot(23, new Button(ItemStacksUtils.setSkin(normalChat.clone(), snm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings()
						.setShowNormalMessages(!inv.getStreamer().getSettings().showNormalMessages());
				inv.updateInventory();
			}
		});

		// Subscriber messages
		
		inv.setSlot(24, new Button(ItemStacksUtils.setSkin(subscriberChat.clone(), ssm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings()
						.setShowSubscriberMessages(!inv.getStreamer().getSettings().showSubscriberMessages());
				inv.updateInventory();
			}
		});

		// Highlighted messages
		
		inv.setSlot(25, new Button(ItemStacksUtils.setSkin(highlightedChat.clone(), shm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings()
						.setShowHighlightedMessages(!inv.getStreamer().getSettings().showHighlightedMessages());
				inv.updateInventory();
			}
		});

		// Ritual messages
		
		inv.setSlot(32, new Button(ItemStacksUtils.setSkin(ritualChat.clone(), srm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=")) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!inv.getStreamer().getSettings().isAuthorizedStreamer())
					return;
				inv.getStreamer().getSettings()
						.setShowRitualMessages(!inv.getStreamer().getSettings().showRitualMessages());
				inv.updateInventory();
			}
		});
		
	}
}
