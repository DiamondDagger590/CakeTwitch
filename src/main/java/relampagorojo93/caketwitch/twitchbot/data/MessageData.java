package relampagorojo93.caketwitch.twitchbot.data;

import java.util.Map;

public class MessageData extends Data {
    private String message;

    public MessageData(Map<String, String> tags, String channel, String executor, String message) {
        this(Action.MESSAGE, tags, channel, executor, message);
    }

    public MessageData(Action action, Map<String, String> tags, String channel, String executor, String message) {
        super(action, tags, channel, executor);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
