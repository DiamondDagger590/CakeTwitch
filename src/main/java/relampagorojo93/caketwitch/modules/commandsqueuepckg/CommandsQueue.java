package relampagorojo93.caketwitch.modules.commandsqueuepckg;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.enums.CommandStatus;
import relampagorojo93.caketwitch.modules.configpckg.configurations.commands.ExecutableCommand;
import relampagorojo93.caketwitch.spigotthreads.objects.Thread;

import java.util.ArrayList;
import java.util.List;

public class CommandsQueue {

    private List<ExecutableCommand> commands = new ArrayList<>();

    private boolean process = false;

    private Thread thread;

    public void processCommands() {
        if (!this.process) {
            this.process = true;
            this.thread = CakeTwitchAPI.getThreadManager()
                .registerThread(new Thread(new Thread.Runnable() {
                    public void run() {
                        try {
                            while (CommandsQueue.this.process && !CommandsQueue.this.commands.isEmpty()) {
                                ExecutableCommand cmd = CommandsQueue.this.commands.get(0);
								if (cmd.checkConditions() != CommandStatus.QUEUE) {
									break;
								}
                                cmd.execute();
                                CommandsQueue.this.commands.remove(0);
                            }
                            CommandsQueue.this.process = false;
                        }
                        catch (Exception exception) {
                        }
                    }

                    public void output(Object output) {
                    }
                }));
            this.thread.startSecure();
        }
    }

    public void addCommands(List<ExecutableCommand> commands) {
        if (!commands.isEmpty()) {
            this.commands.addAll(commands);
            processCommands();
        }
    }

    public void addCommand(ExecutableCommand command) {
        this.commands.add(command);
        processCommands();
    }

    public void stopCommands() {
        this.process = false;
		if (this.thread != null) {
			this.thread.stopSecure();
		}
    }

    public void clearCommands() {
        stopCommands();
        this.commands.clear();
    }

    public int getCommandAmount() {
        return this.commands.size();
    }

}
