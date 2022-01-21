package relampagorojo93.caketwitch.modules.commandspckg.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory;
import relampagorojo93.caketwitch.modules.commandspckg.commands.adminsubcommands.AdminSubCommand;
import relampagorojo93.caketwitch.modules.commandspckg.commands.sqlsubcommands.SQLCommand;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;

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
        if (pl == null || user == null || args.length > 0) {
            return super.execute(cmd, sender, args, userids);
        }
        else {
            new StreamerInventory(pl, user).openInventory(CakeTwitchAPI.getPlugin());
        }
        return true;
    }
}