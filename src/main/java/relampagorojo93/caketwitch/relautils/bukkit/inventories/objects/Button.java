package relampagorojo93.caketwitch.relautils.bukkit.inventories.objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Button extends Item {
    public Button(ItemStack item) {
        super(item);
    }

    public abstract void onClick(InventoryClickEvent e);
}
