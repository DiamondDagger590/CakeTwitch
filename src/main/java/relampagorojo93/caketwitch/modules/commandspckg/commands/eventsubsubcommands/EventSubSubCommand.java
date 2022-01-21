package relampagorojo93.caketwitch.modules.commandspckg.commands.eventsubsubcommands;

import relampagorojo93.caketwitch.modules.commandspckg.commands.HelpSubCommand;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;

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