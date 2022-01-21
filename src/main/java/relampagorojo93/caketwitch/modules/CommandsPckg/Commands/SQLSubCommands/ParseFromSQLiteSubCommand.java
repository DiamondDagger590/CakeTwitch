package relampagorojo93.caketwitch.modules.CommandsPckg.Commands.SQLSubCommands;

import org.bukkit.command.CommandSender;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.MySQLConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.MySQLDatabase;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.SQLObject;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingInt;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;

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
					if (CakeTwitchAPI.getSQL().parseData(dest, new MySQLDatabase(SettingString.TABLEPREFIX.toString())))
						MessagesUtils.getMessageBuilder()
								.createMessage(MessageString.applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
					else
						MessagesUtils.getMessageBuilder()
								.createMessage(MessageString.applyPrefix(MessageString.PARSINGERROR))
								.sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder()
							.createMessage(MessageString.applyPrefix(MessageString.SQLITECONNECTIONERROR))
							.sendMessage(sender);
			});
		} else
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(MessageString.MYSQLENABLEDERROR))
					.sendMessage(sender);
		return true;
	}
}
