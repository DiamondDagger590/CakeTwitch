package relampagorojo93.caketwitch.modules.CommandsPckg;

import org.bukkit.command.Command;
import relampagorojo93.LibsCollection.SpigotCommands.CommandsUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.CommandsPckg.Commands.CakeTwitchCommand;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingBoolean;

public class CommandsModule extends PluginModule {
	
	public boolean load() {
		if (SettingBoolean.COMMAND_CAKETWITCH_ENABLE.toBoolean())
			caketwitch = CommandsUtils.registerCommand(CakeTwitchAPI.getPlugin(), ccaketwitch = new CakeTwitchCommand());
		return true;
	}

	@Override
	public boolean unload() {
		if (this.caketwitch != null)
			CommandsUtils.unregisterCommand(CakeTwitchAPI.getPlugin(), this.caketwitch);
		caketwitch = null;
		ccaketwitch = null;
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.ENABLE;
	}

	@Override
	public boolean optional() {
		return true;
	}

	@Override
	public boolean allowReload() {
		return true;
	}

	private CakeTwitchCommand ccaketwitch;
	private Command caketwitch;
	
	public CakeTwitchCommand getCakeTwitchCommand() {
		return ccaketwitch;
	}
	
}
