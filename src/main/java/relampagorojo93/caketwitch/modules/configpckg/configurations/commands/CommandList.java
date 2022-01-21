package relampagorojo93.caketwitch.modules.configpckg.configurations.commands;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.events.pluginevents.CakeTwitchEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandList {
    private List<Command> commands;

    public CommandList(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public static List<ExecutableCommand> execute(CakeTwitchEvent e, CommandList command) {
        return execute(e, command.getCommands());
    }

    public static List<ExecutableCommand> execute(CakeTwitchEvent e, List<Command> commands) {
        List<ExecutableCommand> queue = new ArrayList<>();
        List<ExecutableCommand> pending = new ArrayList<>();
        List<ExecutableCommand> worldpending = new ArrayList<>();
        for (Command command : commands) {
            ExecutableCommand pc = new ExecutableCommand(command, e);
            switch (pc.checkConditions()) {
                case INVALIDWORLD:
                    worldpending.add(pc);
                    break;
                case NOTREGISTERED:
                case NOTCONNECTED:
                    pending.add(pc);
                    break;
                case QUEUE:
                    queue.add(pc);
                    break;
                default:
                    pc.execute();
                    break;
            }
        }
		if (!pending.isEmpty()) {
			for (ExecutableCommand pc : pending) {
				CakeTwitchAPI.getPendingCommands().addPendingCommand(pc.getTwitchEvent(), pc.getCommand());
			}
		}
		if (!worldpending.isEmpty()) {
			for (ExecutableCommand pc : worldpending) {
				CakeTwitchAPI.getPendingCommands().addWorldPendingCommand(pc.getTwitchEvent(), pc.getCommand());
			}
		}
        return queue;
    }
}
