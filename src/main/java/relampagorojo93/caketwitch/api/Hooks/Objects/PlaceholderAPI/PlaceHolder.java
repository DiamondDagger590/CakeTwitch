package relampagorojo93.caketwitch.api.Hooks.Objects.PlaceholderAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.IRCBot.Status;
import relampagorojo93.LibsCollection.SpigotDebug.DebugData;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

public class PlaceHolder extends PlaceholderExpansion {
	@Override
	public String getAuthor() {
		return "RelampagoRojo93";
	}

	@Override
	public String getIdentifier() {
		return "caketwitch";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		if (player != null && CakeTwitchAPI.getStreamers() != null) {
			Streamer user = CakeTwitchAPI.getStreamers().getStreamer(player.getUniqueId());
			if (user != null) {
				switch (identifier.toLowerCase()) {
					case "stream_game":
						return user.getStats().getStreamGame();
					case "stream_title":
						return user.getStats().getStreamTitle();
					case "stream_is_mature":
						return user.getStats().getStreamIsMature() ? MessagesUtils.color("&aYes") : MessagesUtils.color("&cNo");
					case "streaming":
						return user.getStats().isStreaming() ? MessagesUtils.color("&aYes") : MessagesUtils.color("&cNo");
					case "bot_status":
						if (!user.getSettings().isAuthorizedStreamer()) return "";
						Status status = user.getBot().getStatus();
						return (status == Status.RUNNING ? "&a" : "&c") + status.name();
					case "bot_log":
						if (!user.getSettings().isAuthorizedStreamer()) return "";
						DebugData data = user.getBot().getDebugController().getDebugData(0);
						return data != null ? data.getMessage() : "";
					case "channel":
						return user.getChannelLogin() != null ? user.getChannelLogin() : "";
					default:
						break;
				}
			}
		}
		return "";
	}
}
