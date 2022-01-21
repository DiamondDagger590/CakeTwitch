package relampagorojo93.caketwitch.modules.CommandsPckg.Commands.EventSubSubCommands;

import org.bukkit.command.CommandSender;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;

import java.util.ArrayList;
import java.util.List;

public class ResetSubCommand extends SubCommand {

	public ResetSubCommand(Command parent) {
		super(parent, "reset", SettingString.CAKETWITCHTWITCH_EVENTSUB_RESET_NAME.toString(),
				SettingString.CAKETWITCHTWITCH_EVENTSUB_RESET_PERMISSION.toString(),
				SettingString.CAKETWITCHTWITCH_EVENTSUB_RESET_DESCRIPTION.toString(), "",
				SettingList.CAKETWITCH_EVENTSUB_RESET_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (CakeTwitchAPI.getEventSub() != null) {
			try {
				CakeTwitchAPI.getEventSub().removeSubscriptions();
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix("Subscriptions reset successfully!"))
						.sendMessage(sender);
			} catch (Exception e) {
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix("Not able to reset subscriptions!"))
						.sendMessage(sender);
			}
		} else
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix("EventSub is not enabled!"))
					.sendMessage(sender);
		return true;
	}
}
