package relampagorojo93.caketwitch.modules.CommandsPckg.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchBitsDonationEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchChannelRaidEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchRewardEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchSubscriptionEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchSubscriptionGiftEvent;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Configuration;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TestSubCommand extends SubCommand {

	public TestSubCommand(Command parent) {
		super(parent, "test", SettingString.CAKETWITCH_TEST_NAME.toString(),
				SettingString.CAKETWITCH_TEST_PERMISSION.toString(),
				SettingString.CAKETWITCH_TEST_DESCRIPTION.toString(),
				SettingString.CAKETWITCH_TEST_PARAMETERS.toString(),
				SettingList.CAKETWITCH_TEST_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		Player p = sender instanceof Player ? (Player) sender : null;
		List<String> list = new ArrayList<>();
		if (p != null) {
			switch (args.length) {
			case 1:
				list.add("Subscriptions");
				list.add("SubscriptionsGift");
				list.add("Bits");
				list.add("Raids");
				list.add("ChannelPoints");
				break;
			case 2:
				if (args[1].toLowerCase().equals("channelpoints")) {
					Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(p.getUniqueId());
					if (streamer != null && streamer.getSettings().isAuthorizedStreamer()) {
						Configuration config = CakeTwitchAPI.getConfig()
								.getConfig(streamer.getSettings().getConfiguration());
						if (config != null)
							for (UUID uuid : config.getRewardsUUIDs())
								list.add(uuid.toString());
					}
				}
				break;
			case 3:
				if (sender.hasPermission("CakeTwitch.Test.Others"))
					for (Player pl : Bukkit.getOnlinePlayers())
						list.add(pl.getName());
				break;
			default:
				break;
			}
		}
		return list;
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player pl = sender instanceof Player ? (Player) sender : null;
		Player other = args.length > 3 && sender.hasPermission("CakeTwitch.Test.Others") ? Bukkit.getPlayer(args[3])
				: pl;
		Streamer pldata = other != null ? CakeTwitchAPI.getStreamers().getStreamer(pl.getUniqueId()) : null;
		Streamer streamer = other != null ? CakeTwitchAPI.getStreamers().getStreamer(other.getUniqueId()) : null;
		if (args.length > 2) {
			if (other != null) {
				if (streamer != null && streamer.getSettings().isAuthorizedStreamer()) {
					String channel = pldata != null && pldata.isRegistered() ? pldata.getChannelLogin() : other.getName();
					String option = args[1].toLowerCase();
					CakeTwitchEvent event = null;
					if (option.equals("channelpoints"))
						try {
							event = new CakeTwitchRewardEvent(UUID.randomUUID(), streamer, other.getName(), ":D",
									UUID.fromString(args[2]), new HashMap<>());
						} catch (Exception e) {
							sender.sendMessage(getUsage());
						}
					else {
						try {
							int value = Integer.parseInt(args[2]);
							switch (option) {
							case "subscriptions":
								event = new CakeTwitchSubscriptionEvent(UUID.randomUUID(), streamer, channel, ":D", value, new HashMap<>());
								break;
							case "subscriptionsgift":
								event = new CakeTwitchSubscriptionGiftEvent(UUID.randomUUID(), streamer, channel, ":D", value, channel, new HashMap<>());
								break;
							case "bits":
								event = new CakeTwitchBitsDonationEvent(UUID.randomUUID(), streamer, channel, ":D", value, new HashMap<>());
								break;
							case "raids":
								event = new CakeTwitchChannelRaidEvent(UUID.randomUUID(), streamer, channel, value, new HashMap<>());
								break;
							default:
								sender.sendMessage(getUsage());
								break;
							}
						} catch (NumberFormatException e) {
							sender.sendMessage(MessageString.applyPrefix(MessageString.ONLYNUMBERS));
						}
					}
					if (event != null)
						Bukkit.getPluginManager().callEvent(event);
				} else if (pl != other)
					sender.sendMessage("This player isn't a streamer!");
				else
					sender.sendMessage("You're not a streamer!");
			} else if (pl == null)
				sender.sendMessage("You can't use this command in console!");
			else
				sender.sendMessage("The player was not found!");
		} else
			sender.sendMessage(getUsage());
		return true;
	}

}
