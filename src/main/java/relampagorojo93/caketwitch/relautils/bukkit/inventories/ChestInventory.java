package relampagorojo93.caketwitch.relautils.bukkit.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Modifiable;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Slot;

import java.util.ArrayList;
import java.util.HashMap;

public class ChestInventory extends AbstractInventory {
	private String name;
	private int size;
	private ItemStack[] background = new ItemStack[0];
	private HashMap<Integer, Slot> slots = new HashMap<>();
	
	public ChestInventory(Player player) {
		super(player);
	}
	public ChestInventory(Player player, ItemStack[] background) {
		super(player);
		setBackground(background);
	}
	public ChestInventory(Player player, String name, int size) {
		super(player);
		setName(name);
		setSize(size);
	}
	public ChestInventory(Player player, String name, int size, ItemStack[] background) {
		this(player, name, size);
		setBackground(background);
	}
	
	@Override
	public void updateContent() {
		
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setSize(int size) {
		if (size % 9 != 0) size += 9 - (size % 9);
		this.size = size;
	}
	
	@Override
	public int getSize() {
		return this.size;
	}
	
	@Override
	public void setBackground(ItemStack[] background) {
		this.background = background;
	}
	
	@Override
	public ItemStack[] getBackground() {
		return background;
	}
	
	@Override
	public Slot getSlot(int num) {
		return this.slots.get((Integer) num);
	}
	
	@Override
	public void setSlot(int num, Slot slot) {
		this.slots.put((Integer) num, slot);
	}
	
	@Override
	public void removeSlot(int num) {
		this.slots.remove((Integer) num);
	}
	
	@Override
	public void clearSlots() {
		this.slots.clear();
	}
	
	@Override
	public Inventory getInventory() {
		if (size > 0) {
			Inventory inventory = Bukkit.createInventory(this, size, name);
			updateContent();
			if (background != null) for (int i = 0; i < size && i < background.length; i++) if (background[i] != null && getSlot(i) == null) setSlot(i, new Item(background[i]));
			for (Integer i:new ArrayList<>(slots.keySet())) if (i < size) {
				Slot slot = slots.get(i);
				if (slot != null) inventory.setItem(i, slot.getItemStack());
			}
			return inventory;
		}
		return null;
	}
	
	@Override
	public void onClose(InventoryCloseEvent e) {}
	
	@Override
	public void onMoveItem(InventoryMoveItemEvent e) {}
	
	@Override
	public void onDrag(InventoryDragEvent e) {
		for (int slot: e.getRawSlots()) if (!(getSlot(slot) instanceof Modifiable)) {
			e.setCancelled(true); break;
		}
	}
}