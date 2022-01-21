package relampagorojo93.caketwitch.modules.commandspckg.commands.sqlsubcommands;

import org.bukkit.command.CommandSender;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.relautils.bukkit.TasksUtils;
import relampagorojo93.caketwitch.relautils.shared.sql.SQLObject;
import relampagorojo93.caketwitch.relautils.shared.sql.enums.SQLType;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata.SQLiteConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.subdatabases.SQLiteDatabase;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

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
                    if (CakeTwitchAPI.getSQL().parseData(dest, new SQLiteDatabase())) {
                        MessagesUtils.getMessageBuilder()
                            .createMessage(MessageString.applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
                    }
                    else {
                        MessagesUtils.getMessageBuilder()
                            .createMessage(MessageString.applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
                    }
                }
                else {
                    MessagesUtils.getMessageBuilder()
                        .createMessage(MessageString.applyPrefix(MessageString.MYSQLCONNECTIONERROR))
                        .sendMessage(sender);
                }
            });
        }
        else {
            MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(MessageString.MYSQLDISABLEDERROR))
                .sendMessage(sender);
        }
        return true;
    }
}
