package relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Options;

import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands.CommandList;
import relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Condition;

import java.util.List;

public class Option {
	private List<Condition> conditions;
	private CommandList commands;
	public Option(List<Condition> conditions, CommandList commands) {
		this.conditions = conditions;
		this.commands = commands;
	}
	public boolean matchConditions(int value) {
		for (Condition condition:conditions) if (!condition.matchCondition(value)) return false;
		return true;
	}
	public List<Condition> getConditions() {
		return conditions;
	}
	public CommandList getCommandList() {
		return commands;
	}
}
