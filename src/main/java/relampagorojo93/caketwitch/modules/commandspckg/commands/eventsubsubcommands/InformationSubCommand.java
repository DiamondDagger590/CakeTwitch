package relampagorojo93.caketwitch.modules.commandspckg.commands.eventsubsubcommands;

import org.bukkit.command.CommandSender;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule.Event;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule.Information;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingList;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class InformationSubCommand extends SubCommand {

    public InformationSubCommand(Command parent) {
        super(parent, "information", SettingString.CAKETWITCHTWITCH_EVENTSUB_INFORMATION_NAME.toString(),
            SettingString.CAKETWITCHTWITCH_EVENTSUB_INFORMATION_PERMISSION.toString(),
            SettingString.CAKETWITCHTWITCH_EVENTSUB_INFORMATION_DESCRIPTION.toString(), "",
            SettingList.CAKETWITCH_EVENTSUB_INFORMATION_ALIASES.toList());
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        if (CakeTwitchAPI.getEventSub() != null) {
            try {
                Information info = CakeTwitchAPI.getEventSub().getInformation();
                MessagesUtils.getMessageBuilder()
                    .createMessage("&8>--------------------------------------------------<").sendMessage(sender);
                MessagesUtils.getMessageBuilder().createMessage("&7Subscriptions:").sendMessage(sender);
                for (Entry<String, List<Entry<Event, UUID>>> channel : info.getSubscriptions().entrySet()) {
                    MessagesUtils.getMessageBuilder().createMessage("&7  " + channel.getKey() + ":")
                        .sendMessage(sender);
                    for (Entry<Event, UUID> subscription : channel.getValue()) {
                        MessagesUtils.getMessageBuilder().createMessage("&8    - &e" + subscription.getKey().toString())
                            .sendMessage(sender);
                    }
                }
                MessagesUtils.getMessageBuilder()
                    .createMessage("&7Cost: &a" + info.getTotalCost() + "&8/&e" + info.getMaxTotalCost())
                    .sendActionBar(sender);
                MessagesUtils.getMessageBuilder()
                    .createMessage("&8>--------------------------------------------------<").sendActionBar(sender);
            }
            catch (Exception e) {
                MessagesUtils.getMessageBuilder()
                    .createMessage(MessageString.applyPrefix("Not able to get information!")).sendMessage(sender);
            }
        }
        else {
            MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix("EventSub is not enabled!"))
                .sendMessage(sender);
        }
        return true;
    }
}
