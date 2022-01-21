package relampagorojo93.caketwitch.ezinvopener.commandspckg.commands;

import relampagorojo93.caketwitch.spigotcommands.objects.Command;

import java.util.Arrays;

public class EIOCommand extends Command {
    public EIOCommand() {
        super("caketwitch", "ezinvopener", "EzInvOpener.Staff",
            "Get all the EzInvOpener commands", "[help|command]",
            Arrays.asList("eio"));
        addCommand(new AttachSubCommand(this));
        addCommand(new UnattachSubCommand(this));
        sortCommands();
        addCommand(new HelpSubCommand(this), 0);
    }
}