package relampagorojo93.caketwitch.bukkit.events.pluginevents;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.enums.EventType;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;

import java.util.Map;
import java.util.UUID;

public class CakeTwitchMessageEvent extends CakeTwitchEvent {
    private String message;

    public CakeTwitchMessageEvent(UUID id, Streamer streamer, String user, String message, Map<String, String> tags) {
        super(id, streamer, user, tags);
        this.eventtype = EventType.MESSAGE;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String processCommand(String command, String ign, UUID uuid) {
        return command.replace("%msg%", CakeTwitchAPI.getEmojis().applyEmojis(getMessage())).replace("%user%", getUser().replace("\s", ""))
            .replace("%user-uuid%", uuid != null ? uuid.toString() : "?").replace("%user-ign%", ign).replace("%ign%",
                Bukkit.getOfflinePlayer(getStreamer().getUniqueID()).getName());
    }
}
