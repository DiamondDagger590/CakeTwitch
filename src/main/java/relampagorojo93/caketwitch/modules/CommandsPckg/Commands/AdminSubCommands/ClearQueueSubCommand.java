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

import java.util.ArrayList;
import java.util.List;

public class ClearQueueSubCommand extends SubCommand {

	public ClearQueueSubCommand(Command parent) {
		super(parent, "clearqueue", SettingString.CAKETWITCH_ADMIN_CLEARQUEUE_NAME.toString(),
				SettingString.CAKETWITCH_ADMIN_CLEARQUEUE_PERMISSION.toString(),
				SettingString.CAKETWITCH_ADMIN_CLEARQUEUE_DESCRIPTION.toString(),
				SettingString.CAKETWITCH_ADMIN_CLEARQUEUE_PARAMETERS.toString(),
				SettingList.CAKETWITCH_ADMIN_CLEARQUEUE_ALIASES.toList());
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
			CakeTwitchAPI.getCommandsQueue().clearCommands(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix("Queue cleared successfully!")).sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(getUsage())).sendMessage(sender);
		return true;
	}
}
