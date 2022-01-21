package relampagorojo93.caketwitch.bukkit.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import relampagorojo93.caketwitch.bukkit.Inventories.Abstracts.PluginChestInventory;

public class InventoryEvents implements Listener {
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getInventory().getHolder()).onClick(e);
	}
	@EventHandler
	public void onInvMoveItem(InventoryMoveItemEvent e) {
		if (e.getDestination().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getDestination().getHolder()).onMoveItem(e);
	}
	@EventHandler
	public void onInvDrag(InventoryDragEvent e) {
		if (e.getInventory().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getInventory().getHolder()).onDrag(e);
	}
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (e.getInventory() != null && e.getInventory().getHolder() instanceof PluginChestInventory) ((PluginChestInventory) e.getInventory().getHolder()).onClose(e);
	}
}