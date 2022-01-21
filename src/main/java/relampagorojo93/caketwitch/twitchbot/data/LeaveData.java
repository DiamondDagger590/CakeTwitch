package relampagorojo93.caketwitch.twitchbot.data;

import java.util.Map;

public class LeaveData extends Data {
	public LeaveData(Map<String,String> tags, String channel, String executor) {
		super(Action.LEAVE, tags, channel, executor);
	}
}
