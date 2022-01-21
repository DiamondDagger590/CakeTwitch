package relampagorojo93.caketwitch.ezinvopener.commandspckg;

import org.bukkit.command.Command;
import relampagorojo93.caketwitch.ezinvopener.api.EIOAPI;
import relampagorojo93.caketwitch.ezinvopener.commandspckg.commands.EIOCommand;
import relampagorojo93.caketwitch.spigotcommands.CommandsUtils;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

public class CommandsModule extends PluginModule {

    public boolean load() {
        eiocommand = CommandsUtils.registerCommand(EIOAPI.getPlugin(), ceiocommand = new EIOCommand());
        return true;
    }

    @Override
    public boolean unload() {
		if (this.eiocommand != null) {
			CommandsUtils.unregisterCommand(EIOAPI.getPlugin(), this.eiocommand);
		}
        eiocommand = null;
        ceiocommand = null;
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

    private EIOCommand ceiocommand;
    private Command eiocommand;

    public EIOCommand getEIOCommand() {
        return ceiocommand;
    }

}
