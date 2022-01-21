package relampagorojo93.caketwitch.bukkit.Inventories.Twitch;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Abstracts.ChestInventoryWithStreamer;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents.BotContent;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents.EventsubContent;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents.HomeContent;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents.SettingsContent;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents.StatisticsContent;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StreamerInventory extends ChestInventoryWithStreamer {

	private static ItemStack[] base;

	public static ItemStack[] getBase() {
		return base;
	}

	static {
		ItemStack it = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 7, "GRAY_STAINED_GLASS_PANE");
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		it.setItemMeta(im);
		base = new ItemStack[54];
		for (int i = 0; i < base.length; i++)
			base[i] = it;

		it = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 15, "BLACK_STAINED_GLASS_PANE");
		im = it.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		it.setItemMeta(im);
		for (int i = 0; i < base.length; i++)
			if (i % 9 == 0 || i % 9 == 8)
				base[i] = it;
	}
	
	public static enum Section {
		HOME((inv) -> HomeContent.setContent(inv)),
		SETTINGS((inv) -> SettingsContent.setContent(inv)),
		BOT((inv) -> BotContent.setContent(inv)),
		EVENTSUB((inv) -> EventsubContent.setContent(inv)),
		STATISTICS((inv) -> StatisticsContent.setContent(inv));
		public Method method;
		
		Section(Method method) {
			this.method = method;
		}
		
		private interface Method {
			public abstract void setContent(StreamerInventory inv);
		}
	}
	
	public static enum SubSection {
		STATISTICS_FOLLOWERS(),
		STATISTICS_SUBSCRIPTIONS(),
		STATISTICS_EVENTS()
	}
	
	private Section currentSection = Section.HOME;
	private SubSection currentSubSection = null;
	private HashMap<Class<?>, Integer> pages = new HashMap<>();
	
	public Section getCurrentSection() {
		return currentSection;
	}
	
	public void setCurrentSection(Section currentSection) {
		this.currentSection = currentSection;
	}
	
	public SubSection getCurrentSubSection() {
		return currentSubSection;
	}
	
	public void setCurrentSubSection(SubSection currentSubSection) {
		this.currentSubSection = currentSubSection;
	}

	public StreamerInventory(Player player, Streamer user) {
		super(player, user);

		setAllowStorageExchange(false);
		setName(ChatColor.BLACK.toString());
		setBackground(getBase());
		setSize(54);
		
		ItemStack i = null;
		ItemMeta im = null;
		
		//Streamer head item

		OfflinePlayer pl = Bukkit.getOfflinePlayer(getStreamer().getUniqueID());

		i = getStreamer().getPlayerHead();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e" + (pl != null ? pl.getName() : "???")).toString());
		i.setItemMeta(im);

		setSlot(0, new Item(i));
		
		//Close item
		
		i = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 1, "ORANGE_STAINED_GLASS_PANE");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&cClose").toString());
		i.setItemMeta(im);
		
		setSlot(8, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) { closeInventory(CakeTwitchAPI.getPlugin()); }
		});

		// Revoke streamer rights item

		i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eRevoke streamer rights").toString());
		i.setItemMeta(im);
		setSlot(55, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				getStreamer().getSettings().setIsAuthorizedStreamer(false, true);
				StreamerInventory.updateInventoryEveryone(getStreamer(), (Section) null);
			}
		});

		// Grant streamer rights item

		i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eGrant streamer rights").toString());
		i.setItemMeta(im);
		setSlot(56, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				getStreamer().getSettings().setIsAuthorizedStreamer(true, true);
				StreamerInventory.updateInventoryEveryone(getStreamer(), (Section) null);
			}
		});

	}

	@Override
	public void updateContent() {
		
		for (int i = 0; i < getSize(); i++)
			if (i != 0 && i != 8)
				removeSlot(i);

		ItemStack i = null;
		ItemMeta im = null;
		
		boolean same = getPlayer().getUniqueId().compareTo(getStreamer().getUniqueID()) == 0;
		boolean staff = getPlayer().hasPermission("CakeTwitch.Staff");
		
		List<Section> availableSections = new ArrayList<>();
		
		availableSections.add(Section.HOME);
		if ((same || staff) && getStreamer().isRegistered()) {
			if (getStreamer().getSettings().isAuthorizedStreamer()) {
				availableSections.add(Section.SETTINGS);
				availableSections.add(Section.BOT);
				if (CakeTwitchAPI.getEventSub() != null) availableSections.add(Section.EVENTSUB);
				if (getStreamer().isStreaming()) availableSections.add(Section.STATISTICS);
				
				//Start|Stop stream head item

				i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"), getStreamer().isStreaming() ?
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTM1MTcwNWM3NjVjNGYzNjRjZDk0ZTJjYTk1MjA0ZjdlZWM4YzNjNDdmNDI1ZDZkOTA1MDkwNDVmOTcxNDc2OSJ9fX0=" :
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmFlM2EzYTRhMWFhNTBkODVkYmNkYWM4ZGE2M2Q3Y2JmZDQ1ZTUyMGRmZWMyZDUwYmVkZjhlOTBlOGIwZTRlYSJ9fX0=");
				im = i.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage(getStreamer().isStreaming() ? "&eStop streaming" : "&eStart streaming").toString());
				i.setItemMeta(im);

				setSlot(getSize() - 1, new Button(i) {
					
					@Override
					public void onClick(InventoryClickEvent e) {
						getStreamer().setIsStreaming(!getStreamer().isStreaming()); updateInventory();
					}
				});
			}
			if (staff) {
				if (getStreamer().getSettings().isAuthorizedStreamer())
					setSlot(44, getSlot(55));
				else
					setSlot(44, getSlot(56));
			}
		}
		
		if (!availableSections.contains(this.currentSection))
			this.currentSection = availableSections.get(0);
		
		int[] slots = { 9, 18, 27, 36, 45, 17, 26, 35 };
		
		for (int j = 0;j < slots.length && j < availableSections.size(); j++) {
			Section section = availableSections.get(j);
			if (section != this.currentSection) {
				i = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 4, "YELLOW_STAINED_GLASS_PANE");
				im = i.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e" + section.name().substring(0, 1) + section.name().substring(1).toLowerCase()).toString());
				i.setItemMeta(im);
				setSlot(slots[j], new Button(i) {
					@Override
					public void onClick(InventoryClickEvent e) {
						currentSection = section; currentSubSection = null; updateInventory();
					}
				});
			}
			else {
				i = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
				im = i.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e" + section.name().substring(0, 1) + section.name().substring(1).toLowerCase()).toString());
				i.setItemMeta(im);
				if (this.currentSubSection != null)
					setSlot(slots[j], new Button(i) {
						@Override
						public void onClick(InventoryClickEvent e) {
							currentSubSection = null; updateInventory();
						}
					});
				else
					setSlot(slots[j], new Item(i));
			}
		
		}
		
		this.currentSection.method.setContent(this);
		
	}
	
	public void setPage(Class<?> clazz, int page) {
		this.pages.put(clazz, page);
	}
	
	public int getPage(Class<?> clazz) {
		return this.pages.getOrDefault(clazz, 1);
	}

	public static Inventory getEzInventory(Player player) {
		return new StreamerInventory(player, CakeTwitchAPI.getStreamers().getStreamer(player.getUniqueId()))
				.getInventory();
	}

	public static void updateInventoryEveryone(Streamer user, Section section) {
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getOpenInventory() != null) {
					Inventory inv = pl.getOpenInventory().getTopInventory();
					switch (inv.getType()) {
					case CHEST:
						if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof StreamerInventory) {
							StreamerInventory ui = (StreamerInventory) inv.getHolder();
							if (ui.getStreamer().equals(user) && (section == null || ui.getCurrentSection() == section))
								TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(),
										() -> ui.updateInventory());
						}
						break;
					default:
						break;
					}
				}
			}
		});
	}

	public static void updateInventoryEveryone(Streamer user, SubSection subsection) {
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getOpenInventory() != null) {
					Inventory inv = pl.getOpenInventory().getTopInventory();
					switch (inv.getType()) {
					case CHEST:
						if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof StreamerInventory) {
							StreamerInventory ui = (StreamerInventory) inv.getHolder();
							if (ui.getStreamer().equals(user) && (subsection == null || ui.getCurrentSubSection() == subsection))
								TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(),
										() -> ui.updateInventory());
						}
						break;
					default:
						break;
					}
				}
			}
		});
	}

}
