package relampagorojo93.caketwitch.spigotcommands.objects;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Command extends SubCommand implements CommandExecutor {

    public Command(String id, String command, String permission, String description, String usage,
                   List<String> aliases) {
        this(null, id, command, permission, description, usage, aliases);
    }

    public Command(SubCommand parent, String id, String command, String permission, String description, String usage,
                   List<String> aliases) {
        super(parent, id, command, permission, description, usage, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        return this.execute(null, sender, args, false);
    }

    private List<SubCommand> subcommands = new ArrayList<>();

    public List<SubCommand> getCommands() {
        return new ArrayList<>(subcommands);
    }

    public boolean addCommand(SubCommand subcommand) {
        return subcommands.add(subcommand);
    }

    public boolean addCommand(SubCommand subcommand, int position) {
        subcommands.add(position, subcommand);
        return true;
    }

    public void sortCommands() {
        subcommands.sort(Comparator.comparing(SubCommand::getCommand));
    }

    public boolean removeCommand(SubCommand subcommand) {
        return subcommands.remove(subcommand);
    }

    @Override
    public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length != 0 && (getPermission().isEmpty() || sender.hasPermission(getPermission()))) {
            switch (args.length) {
                case 1:
					for (SubCommand command : subcommands) {
						if ((command.getPermission().isEmpty() || sender.hasPermission(command.getPermission()))) {
							list.add(command.getCommand());
						}
					}
                    break;
                default:
                    String scmd = args[0].toLowerCase();
					for (SubCommand command : subcommands) {
						if (scmd.equals(command.getCommand()) || command.getAliases().contains(scmd)) {
							String[] nw = new String[args.length - 1];
							for (int i = 1; i < args.length; i++) {
								nw[i - 1] = args[i];
							}
							return command.tabComplete(this, sender, nw);
						}
					}
                    break;
            }
        }
        return list;
    }

    @Override
    public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (!getPermission().isEmpty() && !sender.hasPermission(getPermission())) {
			return false;
		}
        if (args.length != 0) {
            String scmd = args[0].toLowerCase();
			for (SubCommand command : subcommands) {
				if ((command.getPermission().isEmpty() || sender.hasPermission(command.getPermission()))
						&& ((!useids && (scmd.equals(command.getCommand().toLowerCase()) || command.getAliases().contains(scmd)))
								|| (useids && scmd.equals(command.getId())))) {
					if (command instanceof Command) {
						String[] nw = new String[args.length - 1];
						for (int i = 1; i < args.length; i++) {
							nw[i - 1] = args[i];
						}
						return command.execute(this, sender, nw, useids);
					}
					else {
						return command.execute(this, sender, args, useids);
					}
				}
			}
			if (useids && scmd.equals("help")) {
				return false;
			}
        }
		if (getCommands().size() != 0) {
			return execute(cmd, sender, new String[]{"help"}, true);
		}
        return false;
    }

    public static class CommandTabCompleter implements TabCompleter {
        private Command command;

        public CommandTabCompleter(Command command) {
            this.command = command;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label,
                                          String[] args) {
            return command.tabComplete(null, sender, args);
        }
    }
}
