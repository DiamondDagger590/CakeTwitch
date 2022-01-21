package relampagorojo93.caketwitch.ezinvopener.commandspckg.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.ezinvopener.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.ezinvopener.api.EIOAPI;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.SubCommand;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UnattachSubCommand extends SubCommand {

    public UnattachSubCommand(Command parent) {
        super(parent, "unattach", "unattach", "",
            "Unattach an inventory from an entity", "[UUID]",
            Arrays.asList("u"));
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        for (UUID uuid : EIOAPI.getEntitiesAPI().getEntities()) {
            list.add(uuid.toString());
        }
        return list;
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
        Player pl = sender instanceof Player ? (Player) sender : null;
        if (pl != null) {
            if (args.length > 1) {
                try {
                    UUID uuid = UUID.fromString(args[1]);
                    EIOAPI.getEntitiesAPI().unregisterEntity(uuid);
                    MessagesUtils.getMessageBuilder().createMessage(EIOAPI.getUtils().applyPrefix(MessageString.ENTITYUNATTACHED)).sendMessage(sender);
                }
                catch (IllegalArgumentException e) {
                    MessagesUtils.getMessageBuilder().createMessage(EIOAPI.getUtils().applyPrefix(MessageString.ONLYUUIDS)).sendMessage(sender);
                }
            }
        }
        else {
            MessagesUtils.getMessageBuilder().createMessage(EIOAPI.getUtils().applyPrefix(MessageString.CONSOLEDENIED)).sendMessage(sender);
        }
        return true;
    }
}
