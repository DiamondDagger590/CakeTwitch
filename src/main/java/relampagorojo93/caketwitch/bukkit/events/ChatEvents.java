package relampagorojo93.caketwitch.bukkit.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import relampagorojo93.caketwitch.bukkit.events.chateventsobjects.InputData;

import java.util.HashMap;
import java.util.UUID;

public class ChatEvents implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
		if (e.getMessage() == null || e.getMessage().isEmpty()) {
			return;
		}
        if (inputs.containsKey(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            inputs.remove(e.getPlayer().getUniqueId()).input(e.getMessage());
        }
    }

    private static final HashMap<UUID, InputData> inputs = new HashMap<>();

    public ChatEvents() {
        inputs.clear();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
		if (inputs.containsKey(e.getPlayer().getUniqueId())) {
			inputs.remove(e.getPlayer().getUniqueId()).remove();
		}
    }

    public static boolean isRegistered(UUID uuid) {
        return inputs.containsKey(uuid);
    }

    public static void register(InputData input) {
        unregister(input.getPlayer().getUniqueId());
        inputs.put(input.getPlayer().getUniqueId(), input);
    }

    public static void unregister(UUID uuid) {
		if (inputs.containsKey(uuid)) {
			inputs.remove(uuid).remove();
		}
    }
}
