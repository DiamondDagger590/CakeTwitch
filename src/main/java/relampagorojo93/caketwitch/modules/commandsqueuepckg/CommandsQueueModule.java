package relampagorojo93.caketwitch.modules.commandsqueuepckg;

import relampagorojo93.caketwitch.modules.configpckg.configurations.commands.ExecutableCommand;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CommandsQueueModule extends PluginModule {
    public boolean load() {
        return true;
    }

    public boolean unload() {
        return true;
    }

    public LoadOn loadOn() {
        return LoadOn.ENABLE;
    }

    public boolean optional() {
        return false;
    }

    public boolean allowReload() {
        return false;
    }

    private HashMap<UUID, CommandsQueue> queues = new HashMap<>();

    public void addCommands(List<ExecutableCommand> commands) {
		for (ExecutableCommand command : commands) {
			addCommand(command);
		}
    }

    public void addCommand(ExecutableCommand command) {
		if (!queues.containsKey(command.getTwitchEvent().getStreamer().getUniqueID())) {
			queues.put(command.getTwitchEvent().getStreamer().getUniqueID(), new CommandsQueue());
		}
        queues.get(command.getTwitchEvent().getStreamer().getUniqueID()).addCommand(command);
    }

    public void processCommands(UUID uuid) {
        CommandsQueue queue = queues.get(uuid);
		if (queue != null) {
			queue.processCommands();
		}
    }

    public void stopCommands(UUID uuid) {
        CommandsQueue queue = queues.get(uuid);
		if (queue != null) {
			queue.stopCommands();
		}
    }

    public void clearCommands(UUID uuid) {
        CommandsQueue queue = queues.get(uuid);
		if (queue != null) {
			queue.clearCommands();
		}
    }

}
