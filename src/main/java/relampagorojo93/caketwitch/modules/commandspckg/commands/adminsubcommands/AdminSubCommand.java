package relampagorojo93.caketwitch.modules.commandspckg.commands.adminsubcommands;

import relampagorojo93.caketwitch.modules.commandspckg.commands.HelpSubCommand;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;

public class AdminSubCommand extends Command {
    public AdminSubCommand(Command command) {
        super(command, "admin", SettingString.CAKETWITCHTWITCH_ADMIN_NAME.toString(), SettingString.CAKETWITCHTWITCH_ADMIN_PERMISSION.toString(),
            SettingString.CAKETWITCHTWITCH_ADMIN_DESCRIPTION.toString(), SettingString.CAKETWITCHTWITCH_ADMIN_PARAMETERS.toString(),
            SettingList.CAKETWITCH_ADMIN_ALIASES.toList());
        addCommand(new RegisterSubCommand(this));
        addCommand(new UnregisterSubCommand(this));
        addCommand(new ClearQueueSubCommand(this));
        sortCommands();
        addCommand(new HelpSubCommand(this), 0);
    }
}