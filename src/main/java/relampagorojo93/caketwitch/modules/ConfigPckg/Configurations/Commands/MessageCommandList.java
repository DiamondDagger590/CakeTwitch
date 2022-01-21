package relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.ClickEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.HoverEvent;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextResult;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchMessageEvent;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Configuration;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageCommandList {
	private List<Command> nmcmds;

	private List<Command> hmcmds;

	private List<Command> rmcmds;

	private List<Command> smcmds;

	public MessageCommandList(List<Command> nmcmds, List<Command> hmcmds, List<Command> rmcmds, List<Command> smcmds) {
		this.nmcmds = nmcmds;
		this.hmcmds = hmcmds;
		this.rmcmds = rmcmds;
		this.smcmds = smcmds;
	}

	public List<ExecutableCommand> execute(CakeTwitchMessageEvent e) {
		List<Command> commands = this.nmcmds;
		Player pl = Bukkit.getPlayer(e.getStreamer().getUniqueID());
		if (pl != null) {
			Configuration config = CakeTwitchAPI.getConfig()
					.getConfig(e.getStreamer().getSettings().getConfiguration());
			if (config != null) {
				boolean frst = ((String) e.getTags().getOrDefault("msg-id", "")).equals("ritual");
				boolean sub = ((String) e.getTags().getOrDefault("subscriber", "0")).equals("1");
				boolean hl = ((String) e.getTags().getOrDefault("msg-id", "")).equals("highlighted-message");
				String msg = "";
				String color = e.getTags().getOrDefault("color", "");
				if (frst) {
					commands = this.rmcmds;
					if (e.getStreamer().getSettings().showRitualMessages())
						msg = config.ritualMessageFormat();
				} else if (hl) {
					commands = this.hmcmds;
					if (e.getStreamer().getSettings().showHighlightedMessages())
						msg = config.highlightedMessageFormat();
				} else {
					if (sub)
						commands = this.smcmds;
					if ((!sub && e.getStreamer().getSettings().showNormalMessages())
							|| (sub && e.getStreamer().getSettings().showSubscriberMessages()))
						msg = config.normalMessageFormat();
				}
				if (msg != null && !msg.isEmpty()) {
					Streamer user = CakeTwitchAPI.getStreamers().getStreamerByLogin(e.getUser().toLowerCase());
					final OfflinePlayer opl;
					if (user != null)
						opl = Bukkit.getOfflinePlayer(user.getUniqueID());
					else
						opl = null;
					ClickEvent cevent = new ClickEvent(ClickEvent.Action.OPEN_URL,
							"https://www.twitch.tv/" + e.getUser());
					HoverEvent hevent = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							MessagesUtils.getMessageBuilder()
									.createMessage(
											new String[] { String.valueOf(MessageString.PREFIX.toString()) + "&r&7 "
													+ e.getUser() + "\n\n    &8&l[&r&aClick me&8&l]" })
									.toString());
					List<TextReplacement> replacements = new ArrayList<>();
					replacements.add(new TextReplacement("%sub_icon%",
							() -> sub ? config.subscriberIcon() : ""));
					replacements.add(
							new TextReplacement("%user%", () -> (!color.isEmpty() ? "&" + color : "") + e.getUser(), cevent, hevent));
					replacements.add(new TextReplacement("%user_uuid%",
							() -> (opl != null) ? opl.getUniqueId().toString() : "?",
							cevent, hevent));
					replacements.add(new TextReplacement("%user_ign%",
							() -> (opl != null) ? opl.getName() : "?", cevent, hevent));
					replacements.add(new TextReplacement("%msg%",
							() -> CakeTwitchAPI.getEmojis().applyEmojis(e.getMessage())));
					TextResult result = MessagesUtils.getMessageBuilder().createMessage(
							replacements.<TextReplacement>toArray(new TextReplacement[replacements.size()]), true,
							new String[] { msg });
					result.sendMessage(new CommandSender[] { (CommandSender) pl });
					for (UUID uuid : e.getStreamer().getViewersController().getViewers()) {
						Player viewer = Bukkit.getPlayer(uuid);
						if (viewer != null)
							result.sendMessage(new CommandSender[] { (CommandSender) viewer });
					}
				}
			}
		}
		return CommandList.execute((CakeTwitchEvent) e, commands);
	}
}
