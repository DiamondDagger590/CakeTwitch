package relampagorojo93.caketwitch.modules.configpckg.configurations.options.subscriptions;

import relampagorojo93.caketwitch.modules.configpckg.configurations.Condition;
import relampagorojo93.caketwitch.modules.configpckg.configurations.commands.CommandList;
import relampagorojo93.caketwitch.modules.configpckg.configurations.options.Option;

import java.util.List;

public class SubscriptionOption extends Option {
    private CommandList ifgifted;

    public SubscriptionOption(List<Condition> conditions, CommandList commands, CommandList ifgifted) {
        super(conditions, commands);
        this.ifgifted = ifgifted;
    }

    public CommandList getCommands(boolean gifted) {
        return gifted ? ifgifted : getCommandList();
    }
}
