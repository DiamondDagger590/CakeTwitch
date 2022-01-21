package relampagorojo93.caketwitch.modules.CommandsPckg.Commands.SQLSubCommands;

import org.bukkit.command.CommandSender;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.SQLiteConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.SQLiteDatabase;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.SQLObject;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;

import java.util.ArrayList;
import java.util.List;

public class ParseFromMySQLSubCommand extends SubCommand {
	public ParseFromMySQLSubCommand(Command command) {
		super(command, "parsefrommysql", SettingString.CAKETWITCHSQL_PARSEFROMMYSQL_NAME.toString(),
				SettingString.CAKETWITCHSQL_PARSEFROMMYSQL_PERMISSION.toString(),
				SettingString.CAKETWITCHSQL_PARSEFROMMYSQL_DESCRIPTION.toString(), "",
				SettingList.CAKETWITCHSQL_PARSEFROMMYSQL_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (CakeTwitchAPI.getSQL().getType() == SQLType.MYSQL) {
			TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
				SQLObject dest = new SQLObject();
				if (dest.request(
						new SQLiteConnectionData(CakeTwitchAPI.getFile().PLUGIN_FOLDER.getPath() + "/DB.sqlite"))) {
					if (CakeTwitchAPI.getSQL().parseData(dest, new SQLiteDatabase()))
						MessagesUtils.getMessageBuilder()
								.createMessage(MessageString.applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
					else
						MessagesUtils.getMessageBuilder()
								.createMessage(MessageString.applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder()
							.createMessage(MessageString.applyPrefix(MessageString.MYSQLCONNECTIONERROR))
							.sendMessage(sender);
			});
		} else
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(MessageString.MYSQLDISABLEDERROR))
					.sendMessage(sender);
		return true;
	}
}
