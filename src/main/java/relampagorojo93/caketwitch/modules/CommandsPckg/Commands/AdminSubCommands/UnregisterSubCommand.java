package relampagorojo93.caketwitch.modules.CommandsPckg.Commands.AdminSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.ArrayList;
import java.util.List;

public class UnregisterSubCommand extends SubCommand {

	public UnregisterSubCommand(Command parent) {
		super(parent, "unregister", SettingString.CAKETWITCH_ADMIN_UNREGISTER_NAME.toString(),
				SettingString.CAKETWITCH_ADMIN_UNREGISTER_PERMISSION.toString(),
				SettingString.CAKETWITCH_ADMIN_UNREGISTER_DESCRIPTION.toString(),
				SettingString.CAKETWITCH_ADMIN_UNREGISTER_PARAMETERS.toString(),
				SettingList.CAKETWITCH_ADMIN_UNREGISTER_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		Player p = sender instanceof Player ? (Player) sender : null;
		List<String> list = new ArrayList<>();
		if (p != null) {
			switch (args.length) {
			case 1:
				for (Player pl : Bukkit.getOnlinePlayers())
					list.add(pl.getName());
				break;
			default:
				break;
			}
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (args.length > 1) {
			Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
			if (streamer.isRegistered()) {
				streamer.setChannelLogin(null);
				MessagesUtils.getMessageBuilder()
				.createMessage(MessageString.applyPrefix("Unregistered player successfully!"))
				.sendMessage(sender);
			} else MessagesUtils.getMessageBuilder()
				.createMessage(MessageString.applyPrefix("This player doesn't have a Twitch acount!"))
				.sendMessage(sender);
		}
		else MessagesUtils.getMessageBuilder()
			.createMessage(MessageString.applyPrefix(getUsage()))
			.sendMessage(sender);
		return true;
	}
}
