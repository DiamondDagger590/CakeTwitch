package relampagorojo93.caketwitch;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.api.hooks.EIOpenerAPI;
import relampagorojo93.caketwitch.api.hooks.PlaceholderAPI;
import relampagorojo93.caketwitch.bukkit.events.ChatEvents;
import relampagorojo93.caketwitch.bukkit.events.InventoryEvents;
import relampagorojo93.caketwitch.bukkit.events.PlayerEvents;
import relampagorojo93.caketwitch.bukkit.events.TwitchEvents;
import relampagorojo93.caketwitch.ezinvopener.EIOpener;
import relampagorojo93.caketwitch.modules.commandspckg.CommandsModule;
import relampagorojo93.caketwitch.modules.commandsqueuepckg.CommandsQueueModule;
import relampagorojo93.caketwitch.modules.configpckg.ConfigModule;
import relampagorojo93.caketwitch.modules.dropspckg.DropsModule;
import relampagorojo93.caketwitch.modules.emojispckg.EmojisModule;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule;
import relampagorojo93.caketwitch.modules.filepckg.FileModule;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingBoolean;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingInt;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingString;
import relampagorojo93.caketwitch.modules.httppckg.HTTPModule;
import relampagorojo93.caketwitch.modules.pendingcommandspckg.PendingCommandsModule;
import relampagorojo93.caketwitch.modules.resourcepackspckg.ResourcePacksModule;
import relampagorojo93.caketwitch.modules.sqlpckg.SQLModule;
import relampagorojo93.caketwitch.modules.streamerspckg.StreamersModule;
import relampagorojo93.caketwitch.modules.webquerypckg.BasicWebQueryModule;
import relampagorojo93.caketwitch.modules.webquerypckg.WebQueryModule;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata.MySQLConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata.SQLiteConnectionData;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotplugin.MainClass;
import relampagorojo93.caketwitch.yamllib.YAMLUtils;

import java.io.File;

public class CakeTwitch extends MainClass {

    // ---------------------------------------------------------------//
    // MainClass methods
    // ---------------------------------------------------------------//

    public CakeTwitch() {
        super(new FileModule(),
            new SQLModule(), new CommandsModule(), new ConfigModule(),
            new CommandsQueueModule(), new PendingCommandsModule(), new HTTPModule(), new BasicWebQueryModule(),
            new WebQueryModule(), new EventSubModule(), new ResourcePacksModule(),
            new EmojisModule(), new StreamersModule(), new DropsModule());
    }

    @Override
    public String getPrefix() {
        return MessageString.PREFIX.toString();
    }

    @Override
    public boolean canLoad() {
        if (CakeTwitchAPI.getSQL().isConnected() || CakeTwitchAPI.getSQL().connect(SettingBoolean.SQL.toBoolean()
                                                                                       ? new MySQLConnectionData(SettingString.PROTOCOL.toString(), SettingString.HOST.toString(),
            SettingInt.PORT.toInt(), SettingString.DATABASE.toString(), SettingString.USERNAME.toString(),
            SettingString.PASSWORD.toString(), SettingString.PARAMETERS.toString().split("&"))
                                                                                       : new SQLiteConnectionData(
            FileModule.PLUGIN_FOLDER.getPath() + "/DB.sqlite"))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canEnable() {
        return true;
    }

    @Override
    public boolean load() {
        new PlaceholderAPI();
        return true;
    }

    @Override
    public boolean enable() {
        if (isFirstTime()) {
            new EIOpener(this); //Because we can't download the dep and have to hard code it in lmfao
            new EIOpenerAPI();
            Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
            Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
            Bukkit.getPluginManager().registerEvents(new TwitchEvents(), this);
            Bukkit.getPluginManager().registerEvents(new ChatEvents(), this);

            if (!FileModule.PLUGIN_FOLDER.exists()) {
                FileModule.PLUGIN_FOLDER.mkdir();
            }

            if (!FileModule.CONFIGS_FOLDER.exists()) {
                FileModule.CONFIGS_FOLDER.mkdir();
            }

            if (!new File("Configs/Default.yml").exists()) {
                saveResource("Configs/Default.yml", false);
            }
            if (!FileModule.DROPS_FILE.exists()) {
                saveResource("Drops.yml", false);
            }
            if (!FileModule.EMOJIS_FILE.exists()) {
                saveResource("Emojis.yml", false);
            }
            if (!FileModule.LANG_FILE.exists()) {
                saveResource("Lang.yml", false);
            }
            if (!FileModule.SETTINGS_FILE.exists()) {
                saveResource("Settings.yml", false);
            }
            if (!FileModule.PENDINGCOMMANDS_FILE.exists()) {
                saveResource("PendingCommands.json", false);
            }

            if (!FileModule.ACCESSTOKEN_FILE.exists()) {
                YAMLUtils.createYml(FileModule.ACCESSTOKEN_FILE);
            }

            new MetricsLite(this, 10083);
        }
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                     __                    ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                       _            |  |                   ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                     _| |___ ___ ___|  |                   ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                    | . | . |   | -_|__|                   ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                    |___|___|_|_|___|__|                   ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
        return true;
    }

    @Override
    public boolean disable() {
        CakeTwitchAPI.setPlugin(null);
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                            __             ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                      _           _       _|  |            ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "              _ _ ___| |___ ___ _| |___ _| |  |            ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "             | | |   | | . | .'| . | -_| . |__|            ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "             |___|_|_|_|___|__,|___|___|___|__|            ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
        return true;
    }

    @Override
    public boolean beforeLoad() {
        CakeTwitchAPI.setPlugin(this);
        return true;
    }

    @Override
    public boolean beforeEnable() {
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                  _       _         _ _       _             ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "          ___ ___| |_ ___| |_ _ _ _|_| |_ ___| |_           ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "         |  _| .'| '_| -_|  _| | | | |  _|  _|   |          ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "         |___|__,|_,_|___|_| |_____|_|_| |___|_|_|          ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                                            ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "               By relampagorojo93/DarkPanda73               ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                                            ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                 _           _ _                            ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                | |___ ___ _| |_|___ ___                    ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                | | . | .'| . | |   | . |_ _ _              ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                |_|___|__,|___|_|_|_|_  |_|_|_|             ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                    |___|                   ").sendMessage(Bukkit.getConsoleSender());
        MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
        return true;
    }

    @Override
    public boolean beforeDisable() {
        return true;
    }

}
