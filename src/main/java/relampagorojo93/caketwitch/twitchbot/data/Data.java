package relampagorojo93.caketwitch.twitchbot.data;

import java.util.HashMap;
import java.util.Map;

public abstract class Data {
	private Map<String,String> tags;
	private String channel, executor;
	private Action action;
	public Data(Action action, Map<String,String> tags, String channel, String executor) {
		this.action = action;
		this.tags = tags;
		if (tags == null) tags = new HashMap<>();
		this.channel = channel;
		this.executor = executor;
	}
	public Action getAction() {
		return action;
	}
	public Map<String,String> getTags() {
		return tags;
	}
	public String getChannel() {
		return channel;
	}
	public String getExecutor() {
		return executor;
	}
	public static enum Action {
		MESSAGE,
		BITS,
		CHANNEL_REWARD,
		SUBSCRIPTION,
		RESUBSCRIPTION,
		SUBSCRIPTION_GIFT,
		RAID,
		RITUAL,
		SUBSCRIPTION_MYSTERY_GIFT,
		REWARD_GIFT,
		COMMUNITY_PAYFORWARD,
		JOIN,
		LEAVE
	}
}
