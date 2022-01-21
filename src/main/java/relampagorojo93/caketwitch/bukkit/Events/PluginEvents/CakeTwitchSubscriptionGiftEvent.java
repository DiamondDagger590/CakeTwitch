package relampagorojo93.caketwitch.bukkit.Events.PluginEvents;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.enums.EventType;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.Map;
import java.util.UUID;

public class CakeTwitchSubscriptionGiftEvent extends CakeTwitchSubscriptionEvent {
	private String dest;
	public CakeTwitchSubscriptionGiftEvent(UUID id, Streamer streamer, String user, String message, int months, String dest, Map<String, String> tags) {
		super(id, streamer, user, message, months, tags);
		this.eventtype = EventType.SUBSCRIPTIONGIFT;
		this.dest = dest;
	}
	public String getDestination() { return dest; }
	@Override
	public String processCommand(String command, String ign, UUID uuid) {
		return command.replace("%msg%", CakeTwitchAPI.getEmojis().applyEmojis(getMessage()))
				.replace("%sub%", getDestination().replace("\s", ""))
				.replace("%donor%", getUser().replace("\s", ""))
				.replace("%donor-uuid%", uuid != null ? uuid.toString() : "?").replace("%donor-ign%", ign)
				.replace("%months%", String.valueOf(getMonths())).replace("%ign%",
						Bukkit.getOfflinePlayer(getStreamer().getUniqueID()).getName());
	}
}
