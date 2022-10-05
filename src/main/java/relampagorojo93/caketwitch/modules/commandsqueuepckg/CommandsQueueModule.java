package relampagorojo93.caketwitch.modules.commandsqueuepckg;

import org.apache.logging.log4j.core.LogEventListener;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.configpckg.configurations.commands.ExecutableCommand;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        Logger logger = CakeTwitchAPI.getPlugin().getLogger();
        logger.log(Level.INFO, "\n\n");
        logger.log(Level.INFO, "Processing queued commands for player with UUID: + " + uuid);
        logger.log(Level.INFO, "\n\n");
        logger.log(Level.INFO, "Displaying all users with commands queued...");

        for(UUID queuedUUID : queues.keySet()) {
            CommandsQueue queue = queues.get(queuedUUID);
            logger.log(Level.INFO, "User " + queuedUUID + " has " + queue.getCommandAmount() + " queued commands.");
        }

        logger.log(Level.INFO, "\n\n");

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
