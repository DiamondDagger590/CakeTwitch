package relampagorojo93.caketwitch.bukkit.Inventories.Debug;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import relampagorojo93.LibsCollection.SpigotDebug.DebugController;
import relampagorojo93.LibsCollection.SpigotDebug.DebugData;
import relampagorojo93.LibsCollection.SpigotDebug.DebugType;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.HoverEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextResult;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Abstracts.PluginChestInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.BaseInventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebugInventory extends PluginChestInventory {

	private int page = 1;
	private DebugController controller;

	public DebugInventory(String invname, Player pl, DebugController controller) {
		super(pl);
		this.controller = controller;
		setName(invname);
		setSize(36);
		setBackground(BaseInventory.getBase());
	}

	@Override
	public void updateContent() {
		int length = 0;
		for (; length <= controller.getDebugLength(); length++) {
			if (length == controller.getDebugLength()) {
				length = 0;
				break;
			}
			if (controller.getDebugData(length) == null)
				break;
		}
		int m = (int) ((((double) length) / 14D) + 0.99D);
		if (m != 0 && page > m)
			page = m;
		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		for (int i = 0; i < 14; i++) {
			int slot = 10 + i + ((i / 7) * 2);
			int indx = (14 * (page - 1)) + i;
			if (indx >= 0 && indx < length) {
				DebugData data = controller.getDebugData(indx);
				if (data != null) {
					String skin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQxZDEzYTE4NTVhNzVkNmFlMWFlMmIyODM3ZDc2ZDkyMGM1Zjc3MzllNjQ0Njk4NDE2NThmOTE3ZmMwIn19fQ==";
					switch (data.getDebugType()) {
						case ERROR: skin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmY0ZjI1MTc1MmM3MzE3YmIyZmI2MjhlNGMzN2M4MmM2MDcxOWQ5MDk5ODUxNDZiMjYxODMyMTUyYTMwYWRhMiJ9fX0="; break;
						case ALERT: skin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTdlZjI4ZGU4ZTJhY2NkY2IyM2IwOTQyZjU2MjRmNDE0OWE1YWE0OTBlYzE5ZjdkYzQ5OTFhODUwYjY1ZjQwIn19fQ=="; break;
						default: break;
					}
					ItemStack l = ItemStacksUtils.setSkin(defaulthead.clone(), skin);
					SkullMeta lm = (SkullMeta) l.getItemMeta();
					lm.setDisplayName("§8[§7" + new Date(data.getLogTime()) + "§8] " + (data.getDebugType() == DebugType.ERROR ? "§c" : data.getDebugType() == DebugType.ALERT ? "§e" : "§a") + data.getDebugType().name());
					List<String> lore = new ArrayList<>();
					lore.add("§0");
					StringBuilder sb = new StringBuilder();
					for (String split:data.getMessage().split(" ")) {
						sb.append((sb.length() == 0 ? "" : " ") + split);
						if (sb.length() > 48) {
							lore.add("§f" + sb.toString());
							sb.setLength(0);
						}
					}
					if (sb.length() != 0)
						lore.add("§f" + sb.toString());
					lore.add("§0");
					lore.add("  §eLeft click to print the");
					lore.add("  §emessage in chat and copy it");
					lm.setLore(lore);
					l.setItemMeta(lm);
					this.setSlot(slot, new Button(l) {
						
						private TextResult result = MessagesUtils.getMessageBuilder().createMessage(new TextReplacement[] {
								new TextReplacement("{msg}", () -> data.getMessage(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, data.getMessage()), new HoverEvent(HoverEvent.Action.SHOW_TEXT, "Click me!"))
						}, false, "{msg}");

						@Override
						public void onClick(InventoryClickEvent e) {
							result.sendMessage(e.getWhoClicked());
						}
						
					});
				} else
					this.setSlot(slot, new Item(null));
			} else
				this.setSlot(slot, new Item(null));
		}
		if (page > 1)
			this.setSlot(this.getSize() - 6, new Button(BaseInventory.getLeftArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page -= 1;
					updateInventory();
				}
			});
		else
			removeSlot(getSize() - 6);
		if (page < m)
			this.setSlot(this.getSize() - 4, new Button(BaseInventory.getRightArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page += 1;
					updateInventory();
				}
			});
		else
			removeSlot(getSize() - 4);

		// Back item

		if (getPreviousHolder() != null)
			setSlot(27, new Button(BaseInventory.getReturnItem()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					goToPreviousHolder(CakeTwitchAPI.getPlugin());
				}
			});
		else
			removeSlot(27);
	}
	
}
