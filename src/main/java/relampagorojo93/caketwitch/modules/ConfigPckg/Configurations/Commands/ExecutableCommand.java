package relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands;

import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchEvent;
import relampagorojo93.caketwitch.enums.CommandStatus;

public class ExecutableCommand {
	private Command command;

	private CakeTwitchEvent event;

	public ExecutableCommand(Command command, CakeTwitchEvent event) {
		this.command = command;
		this.event = event;
	}

	public Command getCommand() {
		return this.command;
	}

	public CakeTwitchEvent getTwitchEvent() {
		return this.event;
	}

	public CommandStatus checkConditions() {
		return Command.checkConditions(this.command, this.event);
	}

	public void execute() {
		Command.execute(this.command, this.event);
	}
}
