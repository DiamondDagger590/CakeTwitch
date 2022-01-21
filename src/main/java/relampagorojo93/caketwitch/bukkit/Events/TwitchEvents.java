package relampagorojo93.caketwitch.bukkit.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import relampagorojo93.LibsCollection.SpigotDebug.Data.DebugLogData;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchBitsDonationEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchChannelRaidEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchFollowerEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchMessageEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchRewardEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchSubscriptionEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchSubscriptionGiftEvent;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory.SubSection;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands.CommandList;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Configuration;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Bits.BitOption;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Raids.RaidOption;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Subscriptions.SubscriptionOption;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

public class TwitchEvents implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void event(CakeTwitchEvent event) {
		switch (event.getEventType()) {
		case BITS: eventBitsDonation((CakeTwitchBitsDonationEvent) event); break;
		case MESSAGE: eventMessage((CakeTwitchMessageEvent) event); break;
		case SUBSCRIPTION: eventSubscription((CakeTwitchSubscriptionEvent) event); break;
		case SUBSCRIPTIONGIFT: eventSubscriptionGift((CakeTwitchSubscriptionGiftEvent) event); break;
		case RAID: eventChannelRaid((CakeTwitchChannelRaidEvent) event); break;
		case REWARD: eventReward((CakeTwitchRewardEvent) event); break;
		case FOLLOWER: eventFollower((CakeTwitchFollowerEvent) event); break;
		default: break;
		}
	}

	public void eventBitsDonation(CakeTwitchBitsDonationEvent event) {
		if (!event.isCancelled() && event.getStreamer().isStreaming()) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(event.getStreamer().getSettings().getConfiguration());
			if (config != null)
				TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
					for (BitOption bitoption : config.getBitOptions())
						if (bitoption.matchConditions(event.getBits()))
							CakeTwitchAPI.getCommandsQueue()
									.addCommands(CommandList.execute(event, bitoption.getCommandList()));
				});
			event.getStreamer().getStatistics().getEvents().addDebugData(new DebugLogData("bits|" + System.currentTimeMillis() + "|" + event.getUser() + "|" + event.getBits()));
			StreamerInventory.updateInventoryEveryone(event.getStreamer(), SubSection.STATISTICS_EVENTS);
		}
	}

	public void eventMessage(CakeTwitchMessageEvent event) {
		if (!event.isCancelled() && event.getStreamer().isStreaming()) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(event.getStreamer().getSettings().getConfiguration());
			if (config != null)
				TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
					CakeTwitchAPI.getCommandsQueue()
							.addCommands(config.getMessageCommandList().execute(event));
				});
			if (((String) event.getTags().getOrDefault("msg-id", "")).equals("highlighted-message")) {
				event.getStreamer().getStatistics().getEvents().addDebugData(new DebugLogData("msghl|" + System.currentTimeMillis() + "|" + event.getUser() + "|" + event.getMessage()));
				StreamerInventory.updateInventoryEveryone(event.getStreamer(), SubSection.STATISTICS_EVENTS);
			}
		}
	}

	public void eventSubscription(CakeTwitchSubscriptionEvent event) {
		if (!event.isCancelled() && event.getStreamer().isStreaming()) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(event.getStreamer().getSettings().getConfiguration());
			if (config != null)
				TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
					for (SubscriptionOption subscriptionoption : config.getSubscriptionOptions())
						if (subscriptionoption.matchConditions(event.getMonths()))
							CakeTwitchAPI.getCommandsQueue()
									.addCommands(CommandList.execute(event, subscriptionoption.getCommands(false)));
				});
			TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
				Streamer streamer = CakeTwitchAPI.getStreamers().getStreamerByLogin(event.getUser().toLowerCase());
				event.getStreamer().getStatistics().getSubscriptions().addDebugData(new DebugLogData("sub|" + System.currentTimeMillis() + "|" + event.getUser() + "|" + event.getMonths() + "|" + (streamer != null ? streamer.getUniqueID().toString() : "-")));
				StreamerInventory.updateInventoryEveryone(event.getStreamer(), SubSection.STATISTICS_SUBSCRIPTIONS);
			});
		}
	}

	public void eventSubscriptionGift(CakeTwitchSubscriptionGiftEvent event) {
		if (!event.isCancelled() && event.getStreamer().isStreaming()) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(event.getStreamer().getSettings().getConfiguration());
			if (config != null)
				TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
					for (SubscriptionOption subscriptionoption : config.getSubscriptionOptions())
						if (subscriptionoption.matchConditions(event.getMonths()))
							CakeTwitchAPI.getCommandsQueue()
									.addCommands(CommandList.execute(event, subscriptionoption.getCommands(true)));
				});
			TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
				Streamer streamer = CakeTwitchAPI.getStreamers().getStreamerByLogin(event.getDestination().toLowerCase());
				event.getStreamer().getStatistics().getSubscriptions().addDebugData(new DebugLogData("subgift|" + System.currentTimeMillis() + "|" + event.getUser() + "|" + event.getDestination() + "|" + event.getMonths() + "|" + (streamer != null ? streamer.getUniqueID().toString() : "-")));
				StreamerInventory.updateInventoryEveryone(event.getStreamer(), SubSection.STATISTICS_SUBSCRIPTIONS);
			});
		}
	}

	public void eventChannelRaid(CakeTwitchChannelRaidEvent event) {
		if (!event.isCancelled() && event.getStreamer().isStreaming()) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(event.getStreamer().getSettings().getConfiguration());
			if (config != null)
				TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
					for (RaidOption raidoption : config.getRaidOptions())
						if (raidoption.matchConditions(event.getViewers()))
							CakeTwitchAPI.getCommandsQueue()
									.addCommands(CommandList.execute(event, raidoption.getCommandList()));
				});
			event.getStreamer().getStatistics().getEvents().addDebugData(new DebugLogData("raid|" + System.currentTimeMillis() + "|" + event.getUser() + "|" + event.getViewers()));
			StreamerInventory.updateInventoryEveryone(event.getStreamer(), SubSection.STATISTICS_EVENTS);
		}
	}

	public void eventReward(CakeTwitchRewardEvent event) {
		if (!event.isCancelled() && event.getStreamer().isStreaming()) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(event.getStreamer().getSettings().getConfiguration());
			if (config != null) {
				CommandList commands = config.getRewardCommands(event.getReward());
				if (commands != null)
					TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
						CakeTwitchAPI.getCommandsQueue()
								.addCommands(CommandList.execute(event, commands));
					});
				else
					event.getStreamer().getBot().getDebugController()
							.addDebugData(new DebugLogData("Unknown Reward >> " + event.getReward().toString()));
			}
			event.getStreamer().getStatistics().getEvents().addDebugData(new DebugLogData("reward|" + System.currentTimeMillis() + "|" + event.getUser() + "|" + event.getReward().toString()));
			StreamerInventory.updateInventoryEveryone(event.getStreamer(), SubSection.STATISTICS_EVENTS);
		}
	}

	public void eventFollower(CakeTwitchFollowerEvent event) {
		if (!event.isCancelled() && event.getStreamer().isStreaming()) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(event.getStreamer().getSettings().getConfiguration());
			if (config != null)
				TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
					CakeTwitchAPI.getCommandsQueue()
							.addCommands(CommandList.execute(event, config.getFollowersCommands()));
				});
			TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
				Streamer streamer = CakeTwitchAPI.getStreamers().getStreamerByLogin(event.getUser().toLowerCase());
				event.getStreamer().getStatistics().getFollowers().addDebugData(new DebugLogData(System.currentTimeMillis() + "|" + event.getUser() + "|" + (streamer != null ? streamer.getUniqueID().toString() : "-")));
				StreamerInventory.updateInventoryEveryone(event.getStreamer(), SubSection.STATISTICS_FOLLOWERS);
			});
		}
	}
}
