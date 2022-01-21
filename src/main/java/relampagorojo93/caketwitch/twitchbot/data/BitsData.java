package relampagorojo93.caketwitch.twitchbot.data;

import java.util.Map;

public class BitsData extends MessageData {
	private int bits;
	public BitsData(Map<String,String> tags, String channel, String executor, String message, int bits) {
		super(Action.BITS, tags, channel, executor, message);
		this.bits = bits;
	}
	public int getBits() {
		return bits;
	}

}
