package relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Subscriptions;

import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands.CommandList;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Condition;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options.Option;

import java.util.List;

public class SubscriptionOption extends Option {
	private CommandList ifgifted;
	public SubscriptionOption(List<Condition> conditions, CommandList commands, CommandList ifgifted) {
		super(conditions,commands);
		this.ifgifted = ifgifted; 
	}
	public CommandList getCommands(boolean gifted) {
		return gifted ? ifgifted : getCommandList();
	}
}
