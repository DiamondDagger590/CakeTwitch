package relampagorojo93.caketwitch.bukkit.inventories.twitch;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.inventories.BaseInventory;
import relampagorojo93.caketwitch.bukkit.inventories.abstracts.PluginChestInventory;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.relautils.bukkit.inventories.objects.Button;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.util.ArrayList;
import java.util.List;

public class StreamersInventory extends PluginChestInventory {

    private int page = 1;
    private List<Streamer> streamers = new ArrayList<>();

    public StreamersInventory(Player player) {

        super(player);
        setName(MessagesUtils.getMessageBuilder().createMessage("&eOnline Streamers").toString());
        setSize(45);
        setBackground(BaseInventory.getBase());

		for (Streamer streamer : CakeTwitchAPI.getStreamers().getStreamers()) {
			if (streamer.isRegistered()) {
				streamers.add(streamer);
			}
		}

    }

    @Override
    public void updateContent() {

        int m = (int) ((((double) streamers.size()) / 21D) + 0.99D);
		if (m != 0 && page > m) {
			page = m;
		}
        for (int j = 0; j < 21; j++) {
            int slot = 10 + j + ((j / 7) * 2);
            int hl = (21 * (page - 1)) + j;
			if (hl >= 0 && hl < streamers.size()) {
				Streamer streamer = streamers.get(hl);
				ItemStack i = streamer.getPlayerHead();
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e" + Bukkit.getOfflinePlayer(streamer.getUniqueID()).getName()).toString());
				i.setItemMeta(im);
				this.setSlot(slot, new Button(i) {

					@Override
					public void onClick(InventoryClickEvent e) {
						new StreamerInventory(getPlayer(), streamer).setPreviousHolder(getHolder()).openInventory(CakeTwitchAPI.getPlugin());
					}

				});
			}
			else {
				this.setSlot(slot, NULL);
			}
        }
		if (page > 1) {
			this.setSlot(this.getSize() - 6, new Button(BaseInventory.getLeftArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page -= 1;
					updateInventory();
				}
			});
		}
		else {
			removeSlot(getSize() - 6);
		}
		if (page < m) {
			this.setSlot(this.getSize() - 4, new Button(BaseInventory.getRightArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page += 1;
					updateInventory();
				}
			});
		}
		else {
			removeSlot(getSize() - 4);
		}

        //Back item

		if (getPreviousHolder() != null) {
			setSlot(getSize() - 9, new Button(BaseInventory.getReturnItem()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					goToPreviousHolder(CakeTwitchAPI.getPlugin());
				}
			});
		}
		else {
			removeSlot(getSize() - 9);
		}
    }

    public static Inventory getEzInventory(Player player) {
        return new StreamersInventory(player).getInventory();
    }

}
