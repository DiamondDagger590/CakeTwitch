package relampagorojo93.caketwitch.ezinvopener.api.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class EzInventory {

    private final Plugin plugin;
    private final String name;
	private final String fullpath;
    private final InventoryMaker inventorymaker;

    public EzInventory(Plugin plugin, String name, InventoryMaker inventorymaker) {
        this.plugin = plugin;
        this.name = name;
        this.inventorymaker = inventorymaker;
        this.fullpath = (plugin.getName() + "." + name).toLowerCase();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public InventoryMaker getInventoryMaker() {
        return inventorymaker;
    }

    public String getFullPath() {
        return fullpath;
    }

    public interface InventoryMaker {
        Inventory getInventory(Player pl);
    }
}
