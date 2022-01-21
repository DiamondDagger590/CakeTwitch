package relampagorojo93.caketwitch.twitchbot.data;

import java.util.Map;

public class SubscriptionGiftData extends SubscriptionData {
	private String giftedto;
	public SubscriptionGiftData(Map<String,String> tags, String channel, String executor, String message, int months, String giftedto) {
		super(Action.SUBSCRIPTION_GIFT, tags, channel, executor, message, months);
		this.giftedto = giftedto;
	}
	public String getGiftedTo() {
		return giftedto;
	}

}
