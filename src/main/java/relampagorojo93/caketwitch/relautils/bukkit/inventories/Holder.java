package relampagorojo93.caketwitch.relautils.bukkit.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import relampagorojo93.caketwitch.relautils.bukkit.TasksUtils;

public abstract class Holder implements InventoryHolder {
    private Player pl = null;
    private InventoryHolder prevholder = null;

    public Holder(InventoryHolder prevholder, Player pl) {
        this.prevholder = prevholder;
        this.pl = pl;
    }

    public Holder(Player pl) {
        this.pl = pl;
    }

    public Player getPlayer() {
        return pl;
    }

    public void openInventory(Plugin plugin) {
        Inventory result = getInventory();
		if (result != null) {
			TasksUtils.execute(plugin, () -> pl.openInventory(getInventory()));
		}
		else {
			closeInventory(plugin);
		}
    }

    public boolean updateInventory(Plugin plugin) {
        Inventory result = getInventory();
		if (result != null) {
			Inventory inv = pl.getOpenInventory().getTopInventory();
			if (inv == null || inv.getHolder() != this) {
				TasksUtils.execute(plugin, () -> pl.openInventory(getInventory()));
			}
			else {
				inv.setContents(result.getContents());
			}
			TasksUtils.execute(plugin, () -> pl.updateInventory());
			return true;
		}
		else {
			closeInventory(plugin);
		}
        return false;
    }

    public void closeInventory(Plugin plugin) {
        TasksUtils.execute(plugin, () -> {
            pl.closeInventory();
            pl.updateInventory();
        });
    }

    public void goToPreviousHolder(Plugin plugin) {
        InventoryHolder holder = getPreviousHolder();
        setPreviousHolder(null);
		if (holder != null) {
			if (holder instanceof Holder) {
				((Holder) holder).updateInventory(plugin);
			}
			else {
				TasksUtils.execute(plugin, () -> pl.openInventory(holder.getInventory()));
			}
		}
		else {
			closeInventory(plugin);
		}
    }

    public Holder getHolder() {
        return this;
    }

    public InventoryHolder getPreviousHolder() {
        return prevholder;
    }

    public Holder setPreviousHolder(InventoryHolder prevholder) {
        this.prevholder = prevholder;
        return this;
    }
}
