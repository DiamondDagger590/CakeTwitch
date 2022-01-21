package relampagorojo93.caketwitch.bukkit.events.pluginevents;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.enums.EventType;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;

import java.util.Map;
import java.util.UUID;

public class CakeTwitchBitsDonationEvent extends CakeTwitchMessageEvent {
    private int bits;

    public CakeTwitchBitsDonationEvent(UUID id, Streamer streamer, String user, String message, int bits,
                                       Map<String, String> tags) {
        super(id, streamer, user, message, tags);
        this.eventtype = EventType.BITS;
        this.bits = bits;
    }

    public int getBits() {
        return bits;
    }

    @Override
    public String processCommand(String command, String ign, UUID uuid) {
        return command.replace("%msg%", CakeTwitchAPI.getEmojis().applyEmojis(getMessage(), true))
            .replace("%donor%", getUser().replace("\s", ""))
            .replace("%donor-uuid%", uuid != null ? uuid.toString() : "?").replace("%donor-ign%", ign)
            .replace("%bits%", String.valueOf(getBits()))
            .replace("%ign%", Bukkit.getOfflinePlayer(getStreamer().getUniqueID()).getName());
    }
}
