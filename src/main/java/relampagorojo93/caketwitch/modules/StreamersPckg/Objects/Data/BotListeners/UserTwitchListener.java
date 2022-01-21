package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.BotListeners;

import org.bukkit.Bukkit;
import relampagorojo93.LibsCollection.IRCBot.IRCBot;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugErrorData;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.BitsData;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.ChannelRewardData;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.Data;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.MessageData;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.RaidData;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.SubscriptionData;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.SubscriptionGiftData;
import relampagorojo93.LibsCollection.TwitchIRCBot.TwitchIRCBot;
import relampagorojo93.LibsCollection.TwitchIRCBot.TwitchIRCBotListener;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchBitsDonationEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchChannelRaidEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchMessageEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchRewardEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchSubscriptionEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchSubscriptionGiftEvent;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory.Section;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.Map;
import java.util.UUID;

public class UserTwitchListener extends TwitchIRCBotListener {

	// ---------------------------------------------------------------------//
	// TwitchIRCBot methods
	// ---------------------------------------------------------------------//

	private Streamer streamer;

	public UserTwitchListener(Streamer streamer) {
		this.streamer = streamer;
	}

	@Override
	public void onAction(Data data) {
		switch (data.getAction()) {
		case BITS:
			BitsData bitsdata = (BitsData) data;
			this.eventBitsDonation(bitsdata.getChannel(), bitsdata.getExecutor(), bitsdata.getMessage(),
					bitsdata.getBits(), bitsdata.getTags());
			break;
		case SUBSCRIPTION:
			SubscriptionData subdata = (SubscriptionData) data;
			this.eventSubscription(subdata.getChannel(), subdata.getExecutor(), subdata.getMessage(),
					subdata.getMonths(), subdata.getTags());
			break;
		case SUBSCRIPTION_GIFT:
			SubscriptionGiftData subgiftdata = (SubscriptionGiftData) data;
			this.eventSubscriptionGift(subgiftdata.getChannel(), subgiftdata.getExecutor(), subgiftdata.getMessage(),
					subgiftdata.getMonths(), subgiftdata.getGiftedTo(), subgiftdata.getTags());
			break;
		case RAID:
			RaidData raiddata = (RaidData) data;
			this.eventChannelRaid(raiddata.getChannel(), raiddata.getExecutor(), raiddata.getViewers(),
					raiddata.getTags());
			break;
		case CHANNEL_REWARD:
			ChannelRewardData rewarddata = (ChannelRewardData) data;
			this.eventReward(rewarddata.getChannel(), rewarddata.getExecutor(), rewarddata.getMessage(),
					rewarddata.getUUID(), rewarddata.getTags());
			break;
		case MESSAGE:
			MessageData messagedata = (MessageData) data;
			this.eventMessage(messagedata.getChannel(), messagedata.getExecutor(), messagedata.getMessage(),
					messagedata.getTags());
			break;
		default:
			break;
		}
	}

	@Override
	public void onConnect(TwitchIRCBot twitchbot) {
		if (streamer.getChannelLogin() != null)
			twitchbot.joinChannel(streamer.getChannelLogin().toLowerCase());
	}

	@Override
	public void onDisconnect(TwitchIRCBot twitchbot) {
		streamer.getBot().getDebugController().addDebugData(new DebugLogData("Bot disconnected successfully!"));
	}

	@Override
	public void onError(TwitchIRCBot twitchbot, Exception exception) {
		streamer.getBot().getDebugController().addDebugData(new DebugErrorData("Error! " + exception.getMessage()));
	}

	@Override
	public void onStart(IRCBot ircbot) {
		streamer.getBot().getDebugController().addDebugData(new DebugLogData("Bot started successfully!"));
	}

	@Override
	public void onFinish(IRCBot ircbot) {
		streamer.getBot().getDebugController().addDebugData(new DebugLogData("Bot stopped successfully!"));
	}

	// ---------------------------------------------------------------------//
	// Event methods
	// ---------------------------------------------------------------------//

	public void eventBitsDonation(String channel, String user, String message, int bits, Map<String, String> tags) {
		if (SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(user.toLowerCase()))
			return;
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			if (streamer.getSettings().detectBits())
				Bukkit.getPluginManager().callEvent(new CakeTwitchBitsDonationEvent(UUID.fromString(tags.get("id")),
						streamer, user, message, bits, tags));
		});
	}

	public void eventMessage(String channel, String user, String message, Map<String, String> tags) {
		if (SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(user.toLowerCase()))
			return;
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> Bukkit.getPluginManager()
				.callEvent(new CakeTwitchMessageEvent(UUID.fromString(tags.get("id")), streamer, user, message, tags)));
	}

	public void eventSubscription(String channel, String user, String message, int months, Map<String, String> tags) {
		if (SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(user.toLowerCase()))
			return;
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			if (streamer.getSettings().detectSubscriptions())
				Bukkit.getPluginManager().callEvent(new CakeTwitchSubscriptionEvent(UUID.fromString(tags.get("id")),
						streamer, user, message, months, tags));
		});
	}

	public void eventSubscriptionGift(String channel, String user, String message, int months, String dest,
			Map<String, String> tags) {
		if (SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(user.toLowerCase()))
			return;
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			if (streamer.getSettings().detectSubscriptions())
				Bukkit.getPluginManager().callEvent(new CakeTwitchSubscriptionGiftEvent(UUID.fromString(tags.get("id")),
						streamer, user, message, months, dest, tags));
		});
	}

	public void eventChannelRaid(String channel, String user, int viewers, Map<String, String> tags) {
		if (SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(user.toLowerCase()))
			return;
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			if (streamer.getSettings().detectRaids())
				Bukkit.getPluginManager().callEvent(
						new CakeTwitchChannelRaidEvent(UUID.fromString(tags.get("id")), streamer, user, viewers, tags));
		});
	}

	public void eventReward(String channel, String user, String message, UUID reward, Map<String, String> tags) {
		if (SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(user.toLowerCase()))
			return;
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			if (streamer.getSettings().detectChannelPoints())
				Bukkit.getPluginManager().callEvent(new CakeTwitchRewardEvent(UUID.fromString(tags.get("id")), streamer,
						user, message, reward, tags));
		});
	}

	@Override
	public void log(String log) {
		streamer.getBot().getDebugController().addDebugData(new DebugLogData(log));
		StreamerInventory.updateInventoryEveryone(streamer, Section.BOT);
	}

}
