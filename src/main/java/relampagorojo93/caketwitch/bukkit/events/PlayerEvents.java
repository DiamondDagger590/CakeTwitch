package relampagorojo93.caketwitch.bukkit.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;

public class PlayerEvents implements Listener {
    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent e) {
        if (e.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            Streamer streamer = CakeTwitchAPI.getStreamers().getStreamerOrNull(e.getUniqueId());
			if (streamer != null) {
				streamer.reloadData();
			}
			else {
				streamer = CakeTwitchAPI.getStreamers().getStreamer(e.getUniqueId());
			}
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Streamer user = CakeTwitchAPI.getStreamers().getStreamer(e.getPlayer().getUniqueId());
        if (user.isRegistered()) {
            CakeTwitchAPI.getPendingCommands().execPendingPlayerCommands(user.getChannelLogin());
//			if (user.getSettings().isAuthorizedStreamer() && SettingBoolean.TWITCH_STARTBOTSONUSERLOAD.toBoolean()
//					&& !user.isStreaming())
//				user.setIsStreaming(true);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
//		Streamer user = CakeTwitchAPI.getStreamers().getStreamer(e.getPlayer().getUniqueId());
//		if (user.isRegistered() && !SettingList.TWITCH_STARTSTREAMINGONLOAD.toList().contains(user.getChannelLogin())
//				&& user.isStreaming())
//			user.setIsStreaming(false);
    }

    @EventHandler
    public void onPlayerSwitchWorld(PlayerChangedWorldEvent e) {
        Streamer user = CakeTwitchAPI.getStreamers().getStreamer(e.getPlayer().getUniqueId());
		if (user.isRegistered()) {
			CakeTwitchAPI.getPendingCommands().execPendingPlayerCommands(user.getChannelLogin());
		}
		if (user.isStreaming()) {
			CakeTwitchAPI.getCommandsQueue().processCommands(e.getPlayer().getUniqueId());
		}
    }
}
