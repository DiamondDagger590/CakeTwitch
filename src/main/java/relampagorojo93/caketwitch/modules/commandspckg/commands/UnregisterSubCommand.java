package relampagorojo93.caketwitch.modules.commandspckg.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebMethod;
import relampagorojo93.caketwitch.relautils.shared.webqueries.WebQuery;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnregisterSubCommand extends SubCommand {

    public UnregisterSubCommand(Command parent) {
        super(parent, "unregister", SettingString.CAKETWITCH_UNREGISTER_NAME.toString(),
            SettingString.CAKETWITCH_UNREGISTER_PERMISSION.toString(),
            SettingString.CAKETWITCH_UNREGISTER_DESCRIPTION.toString(),
            SettingString.CAKETWITCH_UNREGISTER_PARAMETERS.toString(),
            SettingList.CAKETWITCH_UNREGISTER_ALIASES.toList());
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        Player pl = sender instanceof Player ? (Player) sender : null;
		if (pl != null) {
			Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(pl.getUniqueId());
			if (streamer.isRegistered()) {
				if (streamer.getToken() != null) {
					WebQuery.queryToResponse(
						"https://id.twitch.tv/oauth2/revoke?client_id="
							+ streamer.getToken().getClientId() + "&token="
							+ streamer.getToken().getAccessToken(),
						WebMethod.POST, new HashMap<>());
				}
				streamer.setChannelLogin(null);
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.applyPrefix("Account unlinked successfully!"))
					.sendMessage(pl);
			}
			else {
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.applyPrefix("You don't have a Twitch account!"))
					.sendMessage(sender);
			}
		}
		else {
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(MessageString.CONSOLEDENIED))
				.sendMessage(sender);
		}
        return true;
    }
}
