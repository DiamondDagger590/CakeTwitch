package relampagorojo93.caketwitch.modules.commandspckg.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.inventories.resourcepacks.ResourcePacksInventory;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;

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
        if (pl != null) {
            new ResourcePacksInventory(pl).openInventory(CakeTwitchAPI.getPlugin());
        }
        return true;
    }
}
