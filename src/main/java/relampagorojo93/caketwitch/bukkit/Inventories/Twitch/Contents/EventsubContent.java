package relampagorojo93.caketwitch.bukkit.Inventories.Twitch.Contents;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.AbstractInventory;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.modules.EventSubPckg.EventSubModule.Event;
import relampagorojo93.caketwitch.modules.EventSubPckg.EventSubModule.SubscriptionResponse;

import java.util.ArrayList;
import java.util.List;

public class EventsubContent {

	private static ItemStack information;

	static {
		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		
		ItemMeta im = null;

		// Event information item
		
		information = ItemStacksUtils.setSkin(defaulthead.clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=");
		im = information.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lEventSub status").toString());
		information.setItemMeta(im);

	}

	public static void setContent(StreamerInventory inv) {
		
		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		
		ItemStack i = null;
		ItemMeta im = null;
		List<String> lore = null;
		
		// Event status information
		
		i = information.clone();
		im = i.getItemMeta();
		lore = new ArrayList<>();
		lore.add(MessagesUtils.getMessageBuilder().createMessage("&0").toString());
		for (Event event:Event.values()) {
			SubscriptionResponse response = inv.getStreamer().getEventSub().getResponse(event);
			lore.add(MessagesUtils.getMessageBuilder()
				.createMessage(
						"  &8> &7" + event.toString() +": " + (response == SubscriptionResponse.ACCEPTED ? "&a" : "&c") + response.toString()
						)
				.toString());
		}
		im.setLore(lore);
		i.setItemMeta(im);
		inv.setSlot(13, new Item(i));

		// Events status individual information
		
		int[] slots = { 20, 21, 23, 24, 30, 31, 32 };
		
		for (int n = 0; n < slots.length; n++) {
			Event event = n < Event.values().length ? Event.values()[n] : null;
			if (event != null) {
				SubscriptionResponse response = inv.getStreamer().getEventSub().getResponse(event);
				long lastrequest = (System.currentTimeMillis() - inv.getStreamer().getEventSub().getLastRequest(event)) / 1000,
						requiredtime = inv.getStreamer().getEventSub().getRequiredTime(event);
				
				i = ItemStacksUtils.setSkin(defaulthead.clone(), response == SubscriptionResponse.ACCEPTED ?
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ==" :
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
				im = i.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e" + event.toString()).toString());
				im.setLore(MessagesUtils.getMessageBuilder().createMessage(
						"&0",
						"  &8> &7Response: " + (response == SubscriptionResponse.ACCEPTED ? "&a" : "&c") + response.toString(),
						"  &8> &7Required time: &a" + requiredtime + " ms",
						"  &8> &7Last request: &a" + (lastrequest > 60 ? (lastrequest > 3600 ? (int) (lastrequest / 3600) + " hour/s ago" :  (int) (lastrequest / 60) + " minute/s ago") : (int) lastrequest + " second/s ago")
						).getStrings());
				i.setItemMeta(im);
				
				inv.setSlot(slots[n], new Button(i) {
					@Override
					public void onClick(InventoryClickEvent e) {
						if (System.currentTimeMillis() - inv.getStreamer().getEventSub().getLastRequest(event) < 1000*60*5)
							return;
						TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> inv.getStreamer().getEventSub().requestSubscription(event));
					}
				});
			}
			else inv.setSlot(slots[n], AbstractInventory.NULL);
		}
	}
}
