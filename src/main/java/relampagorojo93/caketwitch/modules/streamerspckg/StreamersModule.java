package relampagorojo93.caketwitch.modules.streamerspckg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StreamersModule extends PluginModule {

    public boolean load() {
        for (Streamer streamer : CakeTwitchAPI.getSQL().getStreamers()) {
            this.streamers.put(streamer.getUniqueID(), streamer);
        }
        for (Player pl : Bukkit.getOnlinePlayers()) {
            getStreamer(pl.getUniqueId());
        }
        for (Streamer streamer : this.streamers.values()) {
            if (SettingList.TWITCH_STARTSTREAMINGONLOAD.toList().contains(streamer.getChannelLogin())) {
                streamer.setIsStreaming(true);
            }
        }
        return true;
    }

    public boolean unload() {
        for (Streamer streamer : this.streamers.values()) {
            streamer.setIsStreaming(false);
        }
        this.streamers.clear();
        return true;
    }

    public LoadOn loadOn() {
        return LoadOn.ENABLE;
    }

    public boolean optional() {
        return false;
    }

    public boolean allowReload() {
        return false;
    }

    private HashMap<UUID, Streamer> streamers = new HashMap<>();

    public Streamer getStreamer(UUID uuid) {
        Streamer streamer = getStreamerOrNull(uuid);
        if (streamer == null) {
            this.streamers.put(uuid, (streamer = CakeTwitchAPI.getSQL().getStreamer(uuid)));
        }
        return streamer;
    }

    public Streamer getStreamerOrNull(UUID uuid) {
        return this.streamers.get(uuid);
    }

    public Streamer getStreamerByLogin(String channel) {
        if (channel == null || channel.isEmpty()) {
            return null;
        }
        for (Streamer streamer : this.streamers.values()) {
            if (channel.equalsIgnoreCase(streamer.getChannelLogin())) {
                return streamer;
            }
        }
        return null;
    }

    public Streamer getStreamerById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        for (Streamer streamer : this.streamers.values()) {
            if (id.equalsIgnoreCase(streamer.getChannelId())) {
                return streamer;
            }
        }
        return null;
    }

    public List<Streamer> getStreamers() {
        return new ArrayList<>(this.streamers.values());
    }
}
