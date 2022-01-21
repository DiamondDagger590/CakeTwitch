package relampagorojo93.caketwitch.twitchbot.data;

import java.util.Map;

public class JoinData extends Data {
    public JoinData(Map<String, String> tags, String channel, String executor) {
        super(Action.JOIN, tags, channel, executor);
    }
}
