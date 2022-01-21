package relampagorojo93.caketwitch.modules.CommandsPckg.Commands.EventSubSubCommands;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.caketwitch.modules.CommandsPckg.Commands.HelpSubCommand;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;

public class EventSubSubCommand extends Command {
	public EventSubSubCommand(Command command) {
		super(command, "eventsub", SettingString.CAKETWITCHTWITCH_EVENTSUB_NAME.toString(), SettingString.CAKETWITCHTWITCH_EVENTSUB_PERMISSION.toString(),
				SettingString.CAKETWITCHTWITCH_EVENTSUB_DESCRIPTION.toString(), SettingString.CAKETWITCHTWITCH_EVENTSUB_PARAMETERS.toString(),
				SettingList.CAKETWITCH_EVENTSUB_ALIASES.toList());
		addCommand(new InformationSubCommand(this));
		addCommand(new ResetSubCommand(this));
		sortCommands();
		addCommand(new HelpSubCommand(this), 0);
	}
}