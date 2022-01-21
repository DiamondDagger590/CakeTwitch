package relampagorojo93.caketwitch.modules.commandspckg.commands.sqlsubcommands;

import relampagorojo93.caketwitch.modules.commandspckg.commands.HelpSubCommand;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;

public class SQLCommand extends Command {
    public SQLCommand(Command command) {
        super(command, "sql", SettingString.CAKETWITCHSQL_NAME.toString(), SettingString.CAKETWITCHSQL_PERMISSION.toString(),
            SettingString.CAKETWITCHSQL_DESCRIPTION.toString(), SettingString.CAKETWITCHSQL_PARAMETERS.toString(),
            SettingList.CAKETWITCHSQL_ALIASES.toList());
        addCommand(new ParseFromSQLiteSubCommand(this));
        addCommand(new ParseFromMySQLSubCommand(this));
        sortCommands();
        addCommand(new HelpSubCommand(this), 0);
    }
}