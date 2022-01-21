package relampagorojo93.caketwitch.bukkit.inventories.abstracts;

import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;

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
