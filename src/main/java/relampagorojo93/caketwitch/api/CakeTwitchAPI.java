package relampagorojo93.caketwitch.api;

import org.bukkit.plugin.java.JavaPlugin;
import relampagorojo93.caketwitch.CakeTwitch;
import relampagorojo93.caketwitch.modules.commandspckg.CommandsModule;
import relampagorojo93.caketwitch.modules.commandsqueuepckg.CommandsQueueModule;
import relampagorojo93.caketwitch.modules.configpckg.ConfigModule;
import relampagorojo93.caketwitch.modules.dropspckg.DropsModule;
import relampagorojo93.caketwitch.modules.emojispckg.EmojisModule;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule;
import relampagorojo93.caketwitch.modules.filepckg.FileModule;
import relampagorojo93.caketwitch.modules.httppckg.HTTPModule;
import relampagorojo93.caketwitch.modules.pendingcommandspckg.PendingCommandsModule;
import relampagorojo93.caketwitch.modules.resourcepackspckg.ResourcePacksModule;
import relampagorojo93.caketwitch.modules.sqlpckg.SQLModule;
import relampagorojo93.caketwitch.modules.streamerspckg.StreamersModule;
import relampagorojo93.caketwitch.modules.webquerypckg.BasicWebQueryModule;
import relampagorojo93.caketwitch.modules.webquerypckg.WebQueryModule;
import relampagorojo93.caketwitch.spigotthreads.ThreadManager;

public class CakeTwitchAPI {
    private static CakeTwitch plugin;

    public static void setPlugin(CakeTwitch plugin) {
        CakeTwitchAPI.plugin = plugin;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static ThreadManager getThreadManager() {
        return plugin.getThreadManager();
    }

    public static SQLModule getSQL() {
        return (SQLModule) plugin.getModule(SQLModule.class);
    }

    public static FileModule getFile() {
        return new FileModule();
    }

    public static StreamersModule getStreamers() {
        return (StreamersModule) plugin.getModule(StreamersModule.class);
    }

    public static ConfigModule getConfig() {
        return (ConfigModule) plugin.getModule(ConfigModule.class);
    }

    public static BasicWebQueryModule getBasicWebQuery() {
        return (BasicWebQueryModule) plugin.getModule(BasicWebQueryModule.class);
    }

    public static WebQueryModule getWebQuery() {
        return (WebQueryModule) plugin.getModule(WebQueryModule.class);
    }

    public static EventSubModule getEventSub() {
        return (EventSubModule) plugin.getModule(EventSubModule.class);
    }

    public static EmojisModule getEmojis() {
        return (EmojisModule) plugin.getModule(EmojisModule.class);
    }

    public static HTTPModule getHTTP() {
        return (HTTPModule) plugin.getModule(HTTPModule.class);
    }

    public static ResourcePacksModule getResourcePacks() {
        return (ResourcePacksModule) plugin.getModule(ResourcePacksModule.class);
    }

    public static CommandsQueueModule getCommandsQueue() {
        return (CommandsQueueModule) plugin.getModule(CommandsQueueModule.class);
    }

    public static PendingCommandsModule getPendingCommands() {
        return (PendingCommandsModule) plugin.getModule(PendingCommandsModule.class);
    }

    public static CommandsModule getCommands() {
        return (CommandsModule) plugin.getModule(CommandsModule.class);
    }

    public static DropsModule getDrops() {
        return (DropsModule) plugin.getModule(DropsModule.class);
    }

    public static void reloadPlugin() {
        plugin.reloadPlugin();
    }

    public static int getSQLVersion() {
        return 7;
    }

}