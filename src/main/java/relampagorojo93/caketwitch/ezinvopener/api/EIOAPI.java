package relampagorojo93.caketwitch.ezinvopener.api;

import org.bukkit.plugin.java.JavaPlugin;
import relampagorojo93.caketwitch.ezinvopener.EIOpener;
import relampagorojo93.caketwitch.ezinvopener.miscmodules.UtilsModule;

public class EIOAPI {
    private static EIOpener plugin;
    private static UtilsModule utils;
    private static FileAPI fileapi;
    private static InvAPI invapi;
    private static EntitiesAPI entitiesapi;
    private static CommandsAPI commands;

    public EIOAPI(EIOpener pl) {
        plugin = pl;
    }

    public static JavaPlugin getPlugin() {
        return plugin.getPlugin();
    }

    public static UtilsModule getUtils() {
        if (plugin.getUtilsModule() == null) {
            return null;
        }
        return utils == null ? utils = plugin.getUtilsModule() : utils;
    }

    public static FileAPI getFileAPI() {
        if (plugin.getFileModule() == null) {
            return null;
        }
        return fileapi == null && plugin.getFileModule() != null ? fileapi = new FileAPI(plugin.getFileModule())
                   : fileapi;
    }

    public static InvAPI getInvAPI() {
        if (plugin.getInvModule() == null) {
            return null;
        }
        return invapi == null && plugin.getInvModule() != null ? invapi = new InvAPI(plugin.getInvModule())
                   : invapi;
    }

    public static EntitiesAPI getEntitiesAPI() {
        if (plugin.getEntitiesModule() == null) {
            return null;
        }
        return entitiesapi == null && plugin.getEntitiesModule() != null ? entitiesapi = new EntitiesAPI(plugin.getEntitiesModule())
                   : entitiesapi;
    }

    public static CommandsAPI getCommandsAPI() {
        if (plugin.getCommandsModule() == null) {
            return null;
        }
        return commands == null && plugin.getCommandsModule() != null ? commands = new CommandsAPI(plugin.getCommandsModule())
                   : commands;
    }
}