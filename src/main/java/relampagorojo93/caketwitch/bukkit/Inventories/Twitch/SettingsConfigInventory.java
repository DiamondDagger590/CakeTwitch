package relampagorojo93.caketwitch.bukkit.Inventories.Twitch;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Abstracts.ChestInventoryWithStreamer;
import relampagorojo93.caketwitch.bukkit.Inventories.BaseInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory.Section;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Configuration;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.ArrayList;
import java.util.List;

public class SettingsConfigInventory extends ChestInventoryWithStreamer {
	
	private int page = 1;
	private List<Configuration> configs = new ArrayList<>();
	
	public SettingsConfigInventory(Player player, Streamer user) {
		super(player, user);
		
		setAllowStorageExchange(false);
		setName(MessagesUtils.getMessageBuilder().createMessage("&0").toString());
		setSize(27);
		setBackground(BaseInventory.getBase());
		for (Configuration config:CakeTwitchAPI.getConfig().getConfigs()) if (player.hasPermission(config.getPermission())) configs.add(config);
	}
	
	@Override
	public void updateContent() {
		int m = (int) ((((double) configs.size()) / 7D) + 0.99D);
		if (m != 0 && page > m) page = m;
		ItemStack b = new ItemStack(Material.BOOK);
		ItemMeta bm = (ItemMeta) b.getItemMeta();
		for (int i = 0; i < 7; i++) {
			int slot = 10 + i + ((i / 7) * 2);
			int hl = (7 * (page - 1)) + i;
			if (hl >= 0 && hl < configs.size()) {
				Configuration config = configs.get(hl);
				bm.setDisplayName(MessagesUtils.getMessageBuilder().createMessage(config.getName()).toString());
				b.setItemMeta(bm);
				this.setSlot(slot, new Button(b) {
					private final Configuration cfg = config;
					@Override
					public void onClick(InventoryClickEvent e) {
						if (getStreamer().getSettings().isAuthorizedStreamer()) {
							getStreamer().getSettings().setConfiguration(cfg.getName());
							StreamerInventory.updateInventoryEveryone(getStreamer(), Section.SETTINGS);
							goToPreviousHolder(CakeTwitchAPI.getPlugin());
						} else new StreamerInventory(getPlayer(), getStreamer()).openInventory(CakeTwitchAPI.getPlugin());
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
		
		if (getPreviousHolder() != null) this.setSlot(this.getSize() - 9, new Button(BaseInventory.getReturnItem()) {
			@Override
			public void onClick(InventoryClickEvent e) { goToPreviousHolder(CakeTwitchAPI.getPlugin()); }
		});
		else removeSlot(this.getSize() - 9);
	}
}
