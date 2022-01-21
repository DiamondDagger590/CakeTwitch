package relampagorojo93.caketwitch.relautils.bukkit.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Button;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Item;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Modifiable;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Slot;

public abstract class AbstractInventory extends Holder {

    public static final Slot NULL = new Item(null);

    private boolean storageexchange = false;

    public AbstractInventory(Player player) {
        super(player);
    }

    public boolean allowStorageExchange() {
        return storageexchange;
    }

    public void setAllowStorageExchange(boolean storageexchange) {
        this.storageexchange = storageexchange;
    }

    public void onClick(InventoryClickEvent e) {
		if (e.getInventory() == e.getClickedInventory() ||
				(!storageexchange && e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
			e.setCancelled(true);
		}
        Slot slot = this.getSlot(e.getRawSlot());
        if (slot != null) {
            e.setCancelled(true);
			if (slot instanceof Button) {
				((Button) slot).onClick(e);
			}
			else if (slot instanceof Modifiable) {
				e.setCancelled(false);
				((Modifiable) slot).onModify(e);
			}
        }
    }

    public abstract void onMoveItem(InventoryMoveItemEvent e);

    public abstract void onClose(InventoryCloseEvent e);

    public abstract void onDrag(InventoryDragEvent e);

    public abstract void setName(String name);

    public abstract String getName();

    public abstract void updateContent();

    public abstract void setSize(int size);

    public abstract int getSize();

    public abstract void setBackground(ItemStack[] background);

    public abstract ItemStack[] getBackground();

    public abstract Slot getSlot(int num);

    public abstract void setSlot(int num, Slot slot);

    public abstract void removeSlot(int num);

    public abstract void clearSlots();
}