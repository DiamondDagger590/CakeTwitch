package relampagorojo93.caketwitch.bukkit.Inventories.ResourcePacks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Abstracts.PluginChestInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.BaseInventory;
import relampagorojo93.caketwitch.modules.ResourcePacksPckg.Objects.ResourcePack;

import java.util.ArrayList;
import java.util.List;

public class ResourcePacksInventory extends PluginChestInventory {
	
	private int page = 1;
	private List<ResourcePack> packs = new ArrayList<>();
	
	public ResourcePacksInventory(Player player) {
		super(player);
		setName("Resource Packs");
		setSize(36);
		setBackground(BaseInventory.getBase());
		for (ResourcePack pack:CakeTwitchAPI.getResourcePacks().getResourcePacks()) if (player.hasPermission(pack.getPermission())) packs.add(pack);
	}
	
	@Override
	public void updateContent() {
		int m = (int) ((((double) packs.size()) / 14D) + 0.99D);
		if (m != 0 && page > m) page = m;
		ItemStack b = new ItemStack(Material.CHEST);
		ItemMeta bm = (ItemMeta) b.getItemMeta();
		for (int i = 0; i < 14; i++) {
			int slot = 10 + i + ((i / 7) * 2);
			int hl = (14 * (page - 1)) + i;
			if (hl >= 0 && hl < packs.size()) {
				ResourcePack pack = packs.get(hl);
				bm.setDisplayName(pack.getName());
				b.setItemMeta(bm);
				this.setSlot(slot, new Button(b) {
					private final ResourcePack pck = pack;
					@Override
					public void onClick(InventoryClickEvent e) {
						getPlayer().setResourcePack(pck.getURL());
					}
				});
			} else this.setSlot(slot, new Item(null));
		}
		if (page > 1)
			this.setSlot(this.getSize() - 6, new Button(BaseInventory.getLeftArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {page-=1;updateInventory();}
			});
		else removeSlot(getSize() - 6);
		if (page < m)
			this.setSlot(this.getSize() - 4, new Button(BaseInventory.getRightArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {page+=1;updateInventory();}
			});
		else removeSlot(getSize() - 4);
	}
}
