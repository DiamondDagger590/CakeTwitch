package relampagorojo93.caketwitch.modules.commandspckg.commands.eventsubsubcommands;

import org.bukkit.command.CommandSender;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.util.ArrayList;
import java.util.List;

public class ResetSubCommand extends SubCommand {

    public ResetSubCommand(Command parent) {
        super(parent, "reset", SettingString.CAKETWITCHTWITCH_EVENTSUB_RESET_NAME.toString(),
            SettingString.CAKETWITCHTWITCH_EVENTSUB_RESET_PERMISSION.toString(),
            SettingString.CAKETWITCHTWITCH_EVENTSUB_RESET_DESCRIPTION.toString(), "",
            SettingList.CAKETWITCH_EVENTSUB_RESET_ALIASES.toList());
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        if (CakeTwitchAPI.getEventSub() != null) {
            try {
                CakeTwitchAPI.getEventSub().removeSubscriptions();
                MessagesUtils.getMessageBuilder()
                    .createMessage(MessageString.applyPrefix("Subscriptions reset successfully!"))
                    .sendMessage(sender);
            }
            catch (Exception e) {
                MessagesUtils.getMessageBuilder()
                    .createMessage(MessageString.applyPrefix("Not able to reset subscriptions!"))
                    .sendMessage(sender);
            }
        }
        else {
            MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix("EventSub is not enabled!"))
                .sendMessage(sender);
        }
        return true;
    }
}
