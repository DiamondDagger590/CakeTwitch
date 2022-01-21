package relampagorojo93.caketwitch.modules.commandspckg.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamersInventory;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class StreamersSubCommand extends SubCommand {

    public StreamersSubCommand(Command parent) {
        super(parent, "streamers", SettingString.CAKETWITCH_STREAMERS_NAME.toString(),
            SettingString.CAKETWITCH_STREAMERS_PERMISSION.toString(),
            SettingString.CAKETWITCH_STREAMERS_DESCRIPTION.toString(), "",
            SettingList.CAKETWITCH_STREAMERS_ALIASES.toList());
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        Player pl = sender instanceof Player ? (Player) sender : null;
		if (pl != null) {
			new StreamersInventory(pl).openInventory(CakeTwitchAPI.getPlugin());
		}
        return true;
    }
}
