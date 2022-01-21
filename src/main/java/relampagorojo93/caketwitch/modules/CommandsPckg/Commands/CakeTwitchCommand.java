package relampagorojo93.caketwitch.modules.CommandsPckg.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.modules.CommandsPckg.Commands.AdminSubCommands.AdminSubCommand;
import relampagorojo93.caketwitch.modules.CommandsPckg.Commands.SQLSubCommands.SQLCommand;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

public class CakeTwitchCommand extends Command {
	public CakeTwitchCommand() {
		super("caketwitch", SettingString.CAKETWITCH_NAME.toString(), SettingString.CAKETWITCH_PERMISSION.toString(),
				SettingString.CAKETWITCH_DESCRIPTION.toString(), SettingString.CAKETWITCH_PARAMETERS.toString(),
				SettingList.CAKETWITCH_ALIASES.toList());
		addCommand(new PackSubCommand(this));
		addCommand(new ReloadSubCommand(this));
		addCommand(new TestSubCommand(this));
		addCommand(new RegisterSubCommand(this));
		addCommand(new UnregisterSubCommand(this));
		addCommand(new StreamersSubCommand(this));
		sortCommands();
		addCommand(new AdminSubCommand(this), 0);
		addCommand(new SQLCommand(this), 0);
		addCommand(new HelpSubCommand(this), 0);
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean userids) {
		Player pl = sender instanceof Player ? (Player) sender : null;
		Streamer user = pl != null ? CakeTwitchAPI.getStreamers().getStreamer(pl.getUniqueId()) : null;
		if (pl == null || user == null || args.length > 0) return super.execute(cmd, sender, args, userids);
		else new StreamerInventory(pl, user).openInventory(CakeTwitchAPI.getPlugin());
		return true;
	}
}