package relampagorojo93.caketwitch.bukkit.Events.PluginEvents;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.enums.EventType;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.HashMap;
import java.util.UUID;

public class CakeTwitchFollowerEvent extends CakeTwitchEvent {
	private int bits;
	public CakeTwitchFollowerEvent(UUID id, Streamer streamer, String user) {
		super(id, streamer, user, new HashMap<>());
		this.eventtype = EventType.FOLLOWER;
	}
	public int getBits() { return bits; }
	@Override
	public String processCommand(String command, String ign, UUID uuid) {
		return command.replace("%user%", getUser().replace("\s", ""))
				.replace("%user-uuid%", uuid != null ? uuid.toString() : "?").replace("%user-ign%", ign).replace("%ign%",
						Bukkit.getOfflinePlayer(getStreamer().getUniqueID()).getName());
	}
}
