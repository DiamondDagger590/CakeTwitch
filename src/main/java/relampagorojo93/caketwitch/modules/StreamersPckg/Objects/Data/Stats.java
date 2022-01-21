package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory.Section;
import relampagorojo93.caketwitch.enums.ChannelData;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingBoolean;

import java.util.HashMap;

public class Stats {
	
	private Streamer streamer;
	private HashMap<ChannelData, Object> data = new HashMap<>();

	public Stats(Streamer streamer) {
		this.streamer = streamer;
	}

	public void setData(ChannelData type, Object data) {
		switch (type) {
		case IS_STREAMING:
			boolean streaming = (boolean) data;
			boolean curstreaming = isStreaming();
			this.data.put(ChannelData.IS_STREAMING, Boolean.valueOf(streaming));
			if (this.streamer.getSettings().isAuthorizedStreamer() && !MessageString.STREAMANNOUNCEMENT.toString().isEmpty() && !curstreaming
					&& curstreaming != streaming) {
				Player pl = Bukkit.getPlayer(this.streamer.getUniqueID());
				if (pl != null)
					MessagesUtils.broadcast(MessageString.STREAMANNOUNCEMENT.toString().replace("%ign%", pl.getDisplayName()));
			}
			if (!SettingBoolean.TWITCH_STARTBOTSONUSERLOAD.toBoolean()) {
				this.streamer.setIsStreaming(streaming); StreamerInventory.updateInventoryEveryone(this.streamer, (Section) null);
			}
			break;
		default:
			this.data.put(type, data);
			break;
		}
	}

	public Object getData(ChannelData type, Object def) {
		return this.data.getOrDefault(type, def);
	}

	public String getStreamTitle() {
		try {
			return (String) getData(ChannelData.STREAM_TITLE, "");
		} catch (ClassCastException e) {
			return "";
		}
	}

	public String getStreamGame() {
		try {
			return (String) getData(ChannelData.STREAM_GAME, "");
		} catch (ClassCastException e) {
			return "";
		}
	}

	public boolean getStreamIsMature() {
		try {
			return (boolean) getData(ChannelData.STREAM_IS_MATURE, false);
		} catch (ClassCastException e) {
			return false;
		}
	}

	public boolean isStreaming() {
		try {
			return ((Boolean) getData(ChannelData.IS_STREAMING, Boolean.valueOf(false))).booleanValue();
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	public void clearData() {
		this.data.clear();
	}
}
