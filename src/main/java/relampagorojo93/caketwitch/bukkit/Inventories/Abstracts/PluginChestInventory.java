package relampagorojo93.caketwitch.bukkit.Inventories.Abstracts;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.ChestInventory;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingBoolean;

public abstract class PluginChestInventory extends ChestInventory {
	public PluginChestInventory(Player player) {
		super(player);
	}
	public PluginChestInventory(Player player, ItemStack[] background) {
		super(player);
		setBackground(background);
	}
	public PluginChestInventory(Player player, String name, int size) {
		super(player);
		setName(name);
		setSize(size);
	}
	public PluginChestInventory(Player player, String name, int size, ItemStack[] background) {
		this(player, name, size);
		setBackground(background);
	}
	public void updateInventory() {
		this.updateInventory(CakeTwitchAPI.getPlugin());
	}
	
	@Override
	public void onClose(InventoryCloseEvent e) {
		if (SettingBoolean.INVENTORIES_OPENPREVIOUSINVENTORYONCLOSE.toBoolean()) goToPreviousHolder(CakeTwitchAPI.getPlugin());
	}
	
}
