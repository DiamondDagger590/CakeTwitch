package relampagorojo93.caketwitch.ezinvopener.api;

import relampagorojo93.caketwitch.ezinvopener.commandspckg.commands.EIOCommand;
import relampagorojo93.caketwitch.ezinvopener.commandspckg.CommandsModule;

public class CommandsAPI {
    private CommandsModule commands;

    public CommandsAPI(CommandsModule commands) {
        this.commands = commands;
    }

    public EIOCommand getEIOCommand() {
        return commands.getEIOCommand();
    }
}
