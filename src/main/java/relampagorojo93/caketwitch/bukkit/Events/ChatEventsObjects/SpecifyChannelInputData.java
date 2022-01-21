package relampagorojo93.caketwitch.bukkit.Events.ChatEventsObjects;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.HoverEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Events.ChatEvents;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

public class SpecifyChannelInputData extends InputData {
	public SpecifyChannelInputData(Player player) {
		super(player, (data, result) -> {
			Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(data.getPlayer().getUniqueId());
			if (streamer == null)
				MessagesUtils.getMessageBuilder()
						.createMessage(new String[] { MessageString.applyPrefix(MessageString.CANCELLED) })
						.sendMessage(new CommandSender[] { (CommandSender) data.getPlayer() });
			else
				switch (result.toLowerCase()) {
				case "cancel":
					if (streamer.getRegisterChannel() != null)
						streamer.setRegisterChannel(null);
					MessagesUtils.getMessageBuilder()
							.createMessage(new String[] { MessageString.applyPrefix(MessageString.CANCELLED) })
							.sendMessage(new CommandSender[] { (CommandSender) data.getPlayer() });
					break;
				default:
					if (streamer.getRegisterChannel() != null) {
						MessagesUtils.getMessageBuilder()
								.createMessage(new String[] { MessageString
										.applyPrefix("You must finish the registration or type 'cancel'!") })
								.sendMessage(new CommandSender[] { (CommandSender) data.getPlayer() });
						ChatEvents.register(data);
					}
					if (result.isEmpty()) {
						MessagesUtils.getMessageBuilder().createMessage(
								new String[] { MessageString.applyPrefix("You have to specify your channel name!") })
								.sendMessage(new CommandSender[] { (CommandSender) data.getPlayer() });
						ChatEvents.register(data);
					} else {
						if (result.length() <= 25) {
							if (!SettingList.TWITCH_BLACKLISTEDCHANNELS.toList().contains(result.toLowerCase())) {
								if (!CakeTwitchAPI.getSQL().isRegistered(result)) {
									if (SettingBoolean.TWITCH_REQUIRESUUIDVERIFICATION.toBoolean()) {
										streamer.setRegisterChannel(result);
										MessagesUtils.getMessageBuilder().sendTitle(data.getPlayer(), true,
												"VALIDATION",
												"Type on your channel chat the UUID of your account\nType 'cancel' to stop the validation",
												20, 100, 20);
										MessagesUtils.getMessageBuilder()
												.createMessage(new TextReplacement[] { new TextReplacement("%uuid%",
														() -> streamer.getUniqueID().toString(),
														new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
																data.getPlayer().getUniqueId().toString()),
														new HoverEvent(HoverEvent.Action.SHOW_TEXT,
																MessagesUtils.getMessageBuilder().createMessage(
																		"Click me to write your UUID on your text box")
																		.toString())) },
														true, "%uuid%")
												.sendMessage(data.getPlayer());
										ChatEvents.register(data);
									} else {
										streamer.setChannelLogin(result);
										MessagesUtils.getMessageBuilder().sendTitle(data.getPlayer(), true,
												"Registered successfully!", "", 20, 40, 20);
									}
								} else {
									MessagesUtils.getMessageBuilder()
											.createMessage(new String[] {
													MessageString.applyPrefix("This channel is already registered!") })
											.sendMessage(new CommandSender[] { (CommandSender) data.getPlayer() });
									ChatEvents.register(data);
								}
							} else {
								MessagesUtils.getMessageBuilder().createMessage(
										new String[] { MessageString.applyPrefix("This channel is blacklisted!") })
										.sendMessage(new CommandSender[] { (CommandSender) data.getPlayer() });
								ChatEvents.register(data);
							}
						} else {
							MessagesUtils.getMessageBuilder().createMessage(
									new String[] { MessageString.applyPrefix(MessageString.NAMEMAXLENGTHREACHED) })
									.sendMessage(new CommandSender[] { (CommandSender) data.getPlayer() });
							ChatEvents.register(data);
						}
					}
				}
		});
	}
}
