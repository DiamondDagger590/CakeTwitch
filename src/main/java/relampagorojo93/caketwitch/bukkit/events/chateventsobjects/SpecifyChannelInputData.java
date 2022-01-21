package relampagorojo93.caketwitch.bukkit.events.chateventsobjects;

import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.events.ChatEvents;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingBoolean;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotmessages.instances.ClickEvent;
import relampagorojo93.caketwitch.spigotmessages.instances.HoverEvent;
import relampagorojo93.caketwitch.spigotmessages.instances.TextReplacement;

public class SpecifyChannelInputData extends InputData {
    public SpecifyChannelInputData(Player player) {
        super(player, (data, result) -> {
            Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(data.getPlayer().getUniqueId());
			if (streamer == null) {
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.applyPrefix(MessageString.CANCELLED))
					.sendMessage(data.getPlayer());
			}
			else {
				switch (result.toLowerCase()) {
					case "cancel":
						if (streamer.getRegisterChannel() != null) {
							streamer.setRegisterChannel(null);
						}
						MessagesUtils.getMessageBuilder()
							.createMessage(MessageString.applyPrefix(MessageString.CANCELLED))
							.sendMessage(data.getPlayer());
						break;
					default:
						if (streamer.getRegisterChannel() != null) {
							MessagesUtils.getMessageBuilder()
								.createMessage(MessageString
																.applyPrefix("You must finish the registration or type 'cancel'!"))
								.sendMessage(data.getPlayer());
							ChatEvents.register(data);
						}
						if (result.isEmpty()) {
							MessagesUtils.getMessageBuilder().createMessage(
									MessageString.applyPrefix("You have to specify your channel name!"))
								.sendMessage(data.getPlayer());
							ChatEvents.register(data);
						}
						else {
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
												.createMessage(new TextReplacement[]{new TextReplacement("%uuid%",
														() -> streamer.getUniqueID().toString(),
														new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
															data.getPlayer().getUniqueId().toString()),
														new HoverEvent(HoverEvent.Action.SHOW_TEXT,
															MessagesUtils.getMessageBuilder().createMessage(
																	"Click me to write your UUID on your text box")
																.toString()))},
													true, "%uuid%")
												.sendMessage(data.getPlayer());
											ChatEvents.register(data);
										}
										else {
											streamer.setChannelLogin(result);
											MessagesUtils.getMessageBuilder().sendTitle(data.getPlayer(), true,
												"Registered successfully!", "", 20, 40, 20);
										}
									}
									else {
										MessagesUtils.getMessageBuilder()
											.createMessage(MessageString.applyPrefix("This channel is already registered!"))
											.sendMessage(data.getPlayer());
										ChatEvents.register(data);
									}
								}
								else {
									MessagesUtils.getMessageBuilder().createMessage(
											MessageString.applyPrefix("This channel is blacklisted!"))
										.sendMessage(data.getPlayer());
									ChatEvents.register(data);
								}
							}
							else {
								MessagesUtils.getMessageBuilder().createMessage(
										MessageString.applyPrefix(MessageString.NAMEMAXLENGTHREACHED))
									.sendMessage(data.getPlayer());
								ChatEvents.register(data);
							}
						}
				}
			}
        });
    }
}
