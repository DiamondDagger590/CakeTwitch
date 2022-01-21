package relampagorojo93.caketwitch.ezinvopener.commandspckg.commands;

import relampagorojo93.caketwitch.ezinvopener.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.spigotcommands.objects.Command;
import relampagorojo93.caketwitch.spigotcommands.objects.HelpCommand;

import java.util.Arrays;

public class HelpSubCommand extends HelpCommand {

    public HelpSubCommand(Command parent) {
        super(parent, "help", "",
            "Get all the EzInvOpener commands", "",
            Arrays.asList("h"));
        this.setHeader(() -> Arrays.asList(" ", "&c・。・。・。・。・。・。・。%left_arrow%&r %current_page%/%max_page% %right_arrow%&c。・。・。・。・。・。・。・",
            " "));
        this.setBody(() -> Arrays.asList("&6%command_usage%", "  &8%command_description%"));
        this.setFooter(() -> Arrays.asList(" ", "&c・。・。・。・。・。・。・。・。・。・。・。・。・。・。・。・。・"));
        this.setAvailableLeftArrow(MessageString.HELP_LEFTARROWAVAILABLE::toString);
        this.setAvailableRightArrow(MessageString.HELP_RIGHTARROWAVAILABLE::toString);
        this.setUnavailableLeftArrow(MessageString.HELP_LEFTARROWUNAVAILABLE::toString);
        this.setUnavailableRightArrow(MessageString.HELP_RIGHTARROWUNAVAILABLE::toString);
        this.setErrorNumbers(MessageString.ONLYNUMBERS::toString);
    }

}
