package relampagorojo93.caketwitch.modules.CommandsPckg.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.ResourcePacks.ResourcePacksInventory;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;

import java.util.ArrayList;
import java.util.List;

public class PackSubCommand extends SubCommand {

	public PackSubCommand(Command parent) {
		super(parent, "pack", SettingString.CAKETWITCH_PACK_NAME.toString(),
				SettingString.CAKETWITCH_PACK_PERMISSION.toString(),
				SettingString.CAKETWITCH_PACK_DESCRIPTION.toString(), "", SettingList.CAKETWITCH_PACK_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player pl = sender instanceof Player ? (Player) sender : null;
		if (pl != null)
			new ResourcePacksInventory(pl).openInventory(CakeTwitchAPI.getPlugin());
		return true;
	}
}
