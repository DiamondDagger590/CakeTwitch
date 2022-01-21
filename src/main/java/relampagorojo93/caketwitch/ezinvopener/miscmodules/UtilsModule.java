package relampagorojo93.caketwitch.ezinvopener.miscmodules;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.ezinvopener.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

public class UtilsModule extends PluginModule {

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public LoadOn loadOn() {
        return LoadOn.BEFORE_LOAD;
    }

    @Override
    public boolean optional() {
        return false;
    }

    @Override
    public boolean allowReload() {
        return false;
    }

    public final String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",")
        .split(",")[3];

    public String applyPrefix(MessageString msg) {
        return MessageString.PREFIX + " " + msg;
    }

    public String applyPrefix(String msg) {
        return MessageString.PREFIX + " " + MessagesUtils.color(msg);
    }

}
