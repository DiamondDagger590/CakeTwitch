package relampagorojo93.caketwitch.spigotcommands.objects;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {
	private SubCommand parent;
	private List<String> aliases;
	private String id, permission, command, description, parameters;
	public SubCommand(SubCommand parent, String id, String command, String permission, String description, String parameters, List<String> aliases) {
		this.parent = parent;
		this.id = id.toLowerCase();
		this.command = command.toLowerCase();
		this.permission = permission;
		this.description = description;
		this.parameters = parameters;
		this.aliases = new ArrayList<>();
		for (String alias:aliases) this.aliases.add(alias.toLowerCase());
	}
	
	public String getId() { return id; }
	public String getPermission() { return permission; }
	public String getCommand() { return command; }
	public String getDescription() { return description; }
	public String getParameters() { return parameters; }
	public String getUsage() { return "/" + getCommandPath() + (!getParameters().isEmpty() ? " " + getParameters() : ""); }
	public List<String> getAliases() { return aliases; }
	
	public String getIdPath() {
		return (parent != null ? parent.getIdPath() + " " : "") + id;
	}
	public String getCommandPath() {
		return (parent != null ? parent.getCommandPath() + " " : "") + command;
	}
	
	public abstract List<String> tabComplete(Command cmd, CommandSender sender, String[] args);
	
	public boolean execute(CommandSender sender, String[] args) { return this.execute(null, sender, args); }
	public boolean execute(Command cmd, CommandSender sender, String[] args) { return this.execute(cmd, sender, args, false); }
	public abstract boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids);
}
