package relampagorojo93.caketwitch.twitchbot.data;

import java.util.Map;
import java.util.UUID;

public class ChannelRewardData extends MessageData {
    private UUID uuid;

    public ChannelRewardData(Map<String, String> tags, String channel, String executor, String message, UUID uuid) {
        super(Action.CHANNEL_REWARD, tags, channel, executor, message);
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

}
