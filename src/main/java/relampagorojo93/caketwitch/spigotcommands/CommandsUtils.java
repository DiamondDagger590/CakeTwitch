package relampagorojo93.caketwitch.spigotcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command.CommandTabCompleter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CommandsUtils {
	public static Command registerCommand(JavaPlugin plugin, relampagorojo93.LibsCollection.SpigotCommands.Objects.Command cmd) {
		if (plugin.getCommand(cmd.getCommand()) == null) {
			PluginCommand command = getCommand(cmd.getCommand(), plugin);
			command.setExecutor(cmd);
			command.setDescription(cmd.getDescription());
			command.setAliases(cmd.getAliases());
			command.setPermission(cmd.getPermission());
			command.setUsage(cmd.getUsage());
			command.setTabCompleter(new CommandTabCompleter(cmd));
			try {
				getCommandMap().register(plugin.getDescription().getName(), (Command) command);
				return (Command) command;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean unregisterCommand(JavaPlugin plugin, Command cmd) {
		try {
			SimpleCommandMap commandMap = getCommandMap();
			Object map = getPrivateField(SimpleCommandMap.class, commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			List<String> names = new ArrayList<>(knownCommands.keySet());
			names.sort((s1, s2) -> s1.compareTo(s2));
			for (String alias : names) {
				if (alias.startsWith(plugin.getDescription().getName().toLowerCase())) {
					knownCommands.remove(alias);
					continue;
				}
				if (alias.equals(cmd.getName().toLowerCase(Locale.ENGLISH))) {
					knownCommands.remove(alias);
					continue;
				}
				for (String calias : cmd.getAliases()) {
					if (alias.equals(calias.toLowerCase(Locale.ENGLISH))
							&& ((Command) knownCommands.get(alias)).getName().equals(cmd.getName())) {
						knownCommands.remove(alias);
						break;
					}
				}
			}
			return true;
		} catch (Exception e) {
			return cmd.unregister((CommandMap) getCommandMap());
		}
	}

	private static PluginCommand getCommand(String name, Plugin plugin) {
		PluginCommand command = null;
		try {
			Constructor<PluginCommand> c = PluginCommand.class
					.getDeclaredConstructor(new Class[] { String.class, Plugin.class });
			c.setAccessible(true);
			command = c.newInstance(new Object[] { name, plugin });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return command;
	}

	private static SimpleCommandMap getCommandMap() {
		try {
			return (SimpleCommandMap) getPrivateField(SimplePluginManager.class, Bukkit.getPluginManager(),
					"commandMap");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static Object getPrivateField(Class<?> clazz, Object object, String field)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field objectField = clazz.getDeclaredField(field);
		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(false);
		return result;
	}
}
