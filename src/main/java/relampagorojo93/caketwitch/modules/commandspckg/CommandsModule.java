package relampagorojo93.caketwitch.modules.commandspckg;

import org.bukkit.command.Command;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.commandspckg.commands.CakeTwitchCommand;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingBoolean;
import relampagorojo93.caketwitch.spigotcommands.CommandsUtils;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

public class CommandsModule extends PluginModule {

    public boolean load() {
		if (SettingBoolean.COMMAND_CAKETWITCH_ENABLE.toBoolean()) {
			caketwitch = CommandsUtils.registerCommand(CakeTwitchAPI.getPlugin(), ccaketwitch = new CakeTwitchCommand());
		}
        return true;
    }

    @Override
    public boolean unload() {
		if (this.caketwitch != null) {
			CommandsUtils.unregisterCommand(CakeTwitchAPI.getPlugin(), this.caketwitch);
		}
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
