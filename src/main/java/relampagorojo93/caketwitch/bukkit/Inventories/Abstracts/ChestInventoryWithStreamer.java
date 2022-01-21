package relampagorojo93.caketwitch.bukkit.Inventories.Abstracts;

import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

public abstract class ChestInventoryWithStreamer extends PluginChestInventory {
	
	private Streamer streamer;
	
	public ChestInventoryWithStreamer(Player player, Streamer streamer) {
		super(player);
		this.streamer = streamer;
	}
	
	public Streamer getStreamer() {
		return streamer;
	}
	
}
