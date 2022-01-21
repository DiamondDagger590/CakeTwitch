package relampagorojo93.caketwitch.modules.commandspckg.commands.adminsubcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.util.ArrayList;
import java.util.List;

public class RegisterSubCommand extends SubCommand {

    public RegisterSubCommand(Command parent) {
        super(parent, "register", SettingString.CAKETWITCH_ADMIN_REGISTER_NAME.toString(),
            SettingString.CAKETWITCH_ADMIN_REGISTER_PERMISSION.toString(),
            SettingString.CAKETWITCH_ADMIN_REGISTER_DESCRIPTION.toString(),
            SettingString.CAKETWITCH_ADMIN_REGISTER_PARAMETERS.toString(),
            SettingList.CAKETWITCH_ADMIN_REGISTER_ALIASES.toList());
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        Player p = sender instanceof Player ? (Player) sender : null;
        List<String> list = new ArrayList<>();
        if (p != null) {
            switch (args.length) {
                case 2:
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        list.add(pl.getName());
                    }
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @SuppressWarnings ("deprecation")
    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        if (args.length > 2) {
            String channel = args[1].toLowerCase();
            if (CakeTwitchAPI.getStreamers().getStreamerByLogin(channel) == null) {
                Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                if (!streamer.isRegistered()) {
                    streamer.setChannelLogin(channel);
                    MessagesUtils.getMessageBuilder()
                        .createMessage(MessageString.applyPrefix("Registered player successfully!"))
                        .sendMessage(sender);
                }
                else {
                    MessagesUtils.getMessageBuilder()
                        .createMessage(MessageString.applyPrefix("This player already have a Twitch acount!"))
                        .sendMessage(sender);
                }
            }
            else {
                MessagesUtils.getMessageBuilder()
                    .createMessage(MessageString.applyPrefix("There's already a player with this Twitch account!"))
                    .sendMessage(sender);
            }
        }
        else {
            MessagesUtils.getMessageBuilder()
                .createMessage(MessageString.applyPrefix(getUsage()))
                .sendMessage(sender);
        }
        return true;
    }
}
