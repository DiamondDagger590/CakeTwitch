package relampagorojo93.caketwitch.modules.commandspckg.commands;

import org.bukkit.command.CommandSender;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand(Command parent) {
        super(parent, "reload", SettingString.CAKETWITCH_RELOAD_NAME.toString(),
            SettingString.CAKETWITCH_RELOAD_PERMISSION.toString(),
            SettingString.CAKETWITCH_RELOAD_DESCRIPTION.toString(), "",
            SettingList.CAKETWITCH_RELOAD_ALIASES.toList());
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        CakeTwitchAPI.reloadPlugin();
        sender.sendMessage(MessageString.applyPrefix(MessageString.RELOAD));
        return true;
    }
}
