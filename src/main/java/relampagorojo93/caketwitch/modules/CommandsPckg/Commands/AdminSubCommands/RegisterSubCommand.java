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

public class RegisterSubCommand extends SubCommand {

	public RegisterSubCommand(Command parent) {
		super(parent, "register", SettingString.CAKETWITCH_ADMIN_REGISTER_NAME.toString(),
				SettingString.CAKETWITCH_ADMIN_REGISTER_PERMISSION.toString(),
				SettingString.CAKETWITCH_ADMIN_REGISTER_DESCRIPTION.toString(),
				SettingString.CAKETWITCH_ADMIN_REGISTER_PARAMETERS.toString(),
				SettingList.CAKETWITCH_ADMIN_REGISTER_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		Player p = sender instanceof Player ? (Player) sender : null;
		List<String> list = new ArrayList<>();
		if (p != null) {
			switch (args.length) {
			case 2:
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
		if (args.length > 2) {
			String channel = args[1].toLowerCase();
			if (CakeTwitchAPI.getStreamers().getStreamerByLogin(channel) == null) {
				Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(Bukkit.getOfflinePlayer(args[2]).getUniqueId());
				if (!streamer.isRegistered()) {
					streamer.setChannelLogin(channel);
					MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.applyPrefix("Registered player successfully!"))
					.sendMessage(sender);
				} else MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.applyPrefix("This player already have a Twitch acount!"))
					.sendMessage(sender);
			} else MessagesUtils.getMessageBuilder()
				.createMessage(MessageString.applyPrefix("There's already a player with this Twitch account!"))
				.sendMessage(sender);
		}
		else MessagesUtils.getMessageBuilder()
			.createMessage(MessageString.applyPrefix(getUsage()))
			.sendMessage(sender);
		return true;
	}
}
