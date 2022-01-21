package relampagorojo93.caketwitch.modules.commandspckg.commands.sqlsubcommands;

import org.bukkit.command.CommandSender;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingInt;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.relautils.bukkit.TasksUtils;
import relampagorojo93.caketwitch.relautils.shared.sql.SQLObject;
import relampagorojo93.caketwitch.relautils.shared.sql.enums.SQLType;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata.MySQLConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.subdatabases.MySQLDatabase;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.util.ArrayList;
import java.util.List;

public class ParseFromSQLiteSubCommand extends SubCommand {
    public ParseFromSQLiteSubCommand(Command command) {
        super(command, "parsefromsqlite", SettingString.CAKETWITCHSQL_PARSEFROMSQLITE_NAME.toString(),
            SettingString.CAKETWITCHSQL_PARSEFROMSQLITE_PERMISSION.toString(),
            SettingString.CAKETWITCHSQL_PARSEFROMSQLITE_DESCRIPTION.toString(), "",
            SettingList.CAKETWITCHSQL_PARSEFROMSQLITE_ALIASES.toList());
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (CakeTwitchAPI.getSQL().getType() == SQLType.SQLITE) {
			TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
				SQLObject dest = new SQLObject();
				if (dest.request(new MySQLConnectionData(SettingString.PROTOCOL.toString(),
					SettingString.HOST.toString(), SettingInt.PORT.toInt(), SettingString.DATABASE.toString(),
					SettingString.USERNAME.toString(), SettingString.PASSWORD.toString(),
					SettingString.PARAMETERS.toString().split("&")))) {
					if (CakeTwitchAPI.getSQL().parseData(dest, new MySQLDatabase(SettingString.TABLEPREFIX.toString()))) {
						MessagesUtils.getMessageBuilder()
							.createMessage(MessageString.applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
					}
					else {
						MessagesUtils.getMessageBuilder()
							.createMessage(MessageString.applyPrefix(MessageString.PARSINGERROR))
							.sendMessage(sender);
					}
				}
				else {
					MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix(MessageString.SQLITECONNECTIONERROR))
						.sendMessage(sender);
				}
			});
		}
		else {
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(MessageString.MYSQLENABLEDERROR))
				.sendMessage(sender);
		}
        return true;
    }
}
