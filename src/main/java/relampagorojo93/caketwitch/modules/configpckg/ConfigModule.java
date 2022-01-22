package relampagorojo93.caketwitch.modules.configpckg;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.modules.configpckg.configurations.Configuration;
import relampagorojo93.caketwitch.modules.filepckg.FileModule;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigModule extends PluginModule {

    public boolean load() {

        //Having to be extra safe....
        if (!FileModule.PLUGIN_FOLDER.exists()) {
            FileModule.PLUGIN_FOLDER.mkdir();
        }

        if (!FileModule.CONFIGS_FOLDER.exists()) {
            FileModule.CONFIGS_FOLDER.mkdir();
        }

        for (File f : FileModule.CONFIGS_FOLDER.listFiles()) {
            try {
                configs.put(f.getName().replace(".yml", ""), new Configuration(f));
            }
            catch (Exception e) {
                MessagesUtils.getMessageBuilder()
                    .createMessage(MessageString.applyPrefix("Configuration " + f.getName() + " is not valid!"))
                    .sendMessage(Bukkit.getConsoleSender());
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        configs.clear();
        return true;
    }

    @Override
    public LoadOn loadOn() {
        return LoadOn.ENABLE;
    }

    @Override
    public boolean optional() {
        return false;
    }

    @Override
    public boolean allowReload() {
        return true;
    }

    private HashMap<String, Configuration> configs = new HashMap<>();

    public Configuration getConfig(String config) {
        return configs.get(config);
    }

    public List<Configuration> getConfigs() {
        return new ArrayList<>(configs.values());
    }

}
