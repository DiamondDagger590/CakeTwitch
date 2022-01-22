package relampagorojo93.caketwitch.ezinvopener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import relampagorojo93.caketwitch.ezinvopener.api.EIOAPI;
import relampagorojo93.caketwitch.ezinvopener.commandspckg.CommandsModule;
import relampagorojo93.caketwitch.ezinvopener.entitiespckg.EntitiesModule;
import relampagorojo93.caketwitch.ezinvopener.events.PlayerEvents;
import relampagorojo93.caketwitch.ezinvopener.filepckg.FileModule;
import relampagorojo93.caketwitch.ezinvopener.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.ezinvopener.invpckg.InvModule;
import relampagorojo93.caketwitch.ezinvopener.miscmodules.UtilsModule;
import relampagorojo93.caketwitch.spigotdebug.DebugController;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;
import relampagorojo93.caketwitch.spigotthreads.ThreadManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EIOpener {

    private static final Boolean TRUE = Boolean.valueOf(true);
    private static final Boolean FALSE = Boolean.valueOf(false);
    protected Map<Class<?>, PluginModule> modules;
    protected HashMap<Class<?>, Boolean> enabled = new HashMap<>();
    protected boolean ploaded = false, penabled = false, firsttime = true;
    protected DebugController controller = new DebugController();
    protected ThreadManager manager = new ThreadManager();
    private JavaPlugin plugin;

    //---------------------------------------------------------------//
    //MainClass methods
    //---------------------------------------------------------------//

    public EIOpener(JavaPlugin plugin) {

        this.plugin = plugin;

        List<PluginModule> modules = Arrays.asList(
            new UtilsModule(),
            new FileModule(),
            new InvModule(),
            new EntitiesModule(),
            new CommandsModule());

        LinkedHashMap<Class<?>, PluginModule> map = new LinkedHashMap<>();
        for (PluginModule module : modules) {
            map.put(module.getClass(), module);
        }
        this.modules = map;

        execute(true, false, false);
        execute(false, true, false);

        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), plugin);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public PluginModule getModule(Class<?> clazz) {
        return enabled.getOrDefault(clazz, false) ? modules.get(clazz) : null;
    }

    public String getPrefix() {
        return MessageString.PREFIX.toString();
    }

    public boolean canLoad() {
        return true;
    }

    public boolean canEnable() {
        return true;
    }

    public boolean load() {
        return true;
    }

    public boolean enable() {
        Bukkit.getLogger().info(getPrefix() + "");
        Bukkit.getLogger().info(getPrefix() + "                                     __                    ");
        Bukkit.getLogger().info(getPrefix() + "                       _            |  |                   ");
        Bukkit.getLogger().info(getPrefix() + "                     _| |___ ___ ___|  |                   ");
        Bukkit.getLogger().info(getPrefix() + "                    | . | . |   | -_|__|                   ");
        Bukkit.getLogger().info(getPrefix() + "                    |___|___|_|_|___|__|                   ");
        Bukkit.getLogger().info(getPrefix() + "");
        return true;
    }

    public boolean disable() {
        Bukkit.getLogger().info(getPrefix() + "");
        Bukkit.getLogger().info(getPrefix() + "                                            __             ");
        Bukkit.getLogger().info(getPrefix() + "                      _           _       _|  |            ");
        Bukkit.getLogger().info(getPrefix() + "              _ _ ___| |___ ___ _| |___ _| |  |            ");
        Bukkit.getLogger().info(getPrefix() + "             | | |   | | . | .'| . | -_| . |__|            ");
        Bukkit.getLogger().info(getPrefix() + "             |___|_|_|_|___|__,|___|___|___|__|            ");
        Bukkit.getLogger().info(getPrefix() + "");
        return true;
    }

    public boolean beforeLoad() {
        new EIOAPI(this);
        return true;
    }

    public boolean beforeEnable() {
        Bukkit.getLogger().info(getPrefix() + "");
        Bukkit.getLogger().info(getPrefix() + "      _____     _____         _____                         ");
        Bukkit.getLogger().info(getPrefix() + "     |   __|___|     |___ _ _|     |___ ___ ___ ___ ___     ");
        Bukkit.getLogger().info(getPrefix() + "     |   __|- _|-   -|   | | |  |  | . | -_|   | -_|  _|    ");
        Bukkit.getLogger().info(getPrefix() + "     |_____|___|_____|_|_|\\_/|_____|  _|___|_|_|___|_|      ");
        Bukkit.getLogger().info(getPrefix() + "                                   |_|                      ");
        Bukkit.getLogger().info(getPrefix() + "                                                            ");
        Bukkit.getLogger().info(getPrefix() + "               By relampagorojo93/DarkPanda73               ");
        Bukkit.getLogger().info(getPrefix() + "                                                            ");
        Bukkit.getLogger().info(getPrefix() + "                 _           _ _                            ");
        Bukkit.getLogger().info(getPrefix() + "                | |___ ___ _| |_|___ ___                    ");
        Bukkit.getLogger().info(getPrefix() + "                | | . | .'| . | |   | . |_ _ _              ");
        Bukkit.getLogger().info(getPrefix() + "                |_|___|__,|___|_|_|_|_  |_|_|_|             ");
        Bukkit.getLogger().info(getPrefix() + "                                    |___|                   ");
        Bukkit.getLogger().info(getPrefix() + "");
        return true;
    }

    public boolean beforeDisable() {
        return true;
    }

    //---------------------------------------------------------------//
    //Modules
    //---------------------------------------------------------------//

    public FileModule getFileModule() {
        return (FileModule) getModule(FileModule.class);
    }

    public UtilsModule getUtilsModule() {
        return (UtilsModule) getModule(UtilsModule.class);
    }

    public InvModule getInvModule() {
        return (InvModule) getModule(InvModule.class);
    }

    public EntitiesModule getEntitiesModule() {
        return (EntitiesModule) getModule(EntitiesModule.class);
    }

    public CommandsModule getCommandsModule() {
        return (CommandsModule) getModule(CommandsModule.class);
    }

    protected boolean loadModule(PluginModule module) {
        if (enabled.getOrDefault(module.getClass(), false).booleanValue()) {
            return true;
        }
        try {
            long start = System.currentTimeMillis();
            if (module.load()) {
                MessagesUtils.getMessageBuilder()
                    .createMessage(getPrefix() + " Loaded module " + module.getClass().getSimpleName()
                                       + " successfully! (" + (System.currentTimeMillis() - start) + " ms)")
                    .sendMessage(Bukkit.getConsoleSender());
                enabled.put(module.getClass(), TRUE);
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        MessagesUtils
            .getMessageBuilder().createMessage(getPrefix() + " Error trying to load module "
                                                   + module.getClass().getSimpleName() + "!" + (module.optional() ? " (optional)" : ""))
            .sendMessage(Bukkit.getConsoleSender());
        enabled.put(module.getClass(), FALSE);
        return !module.optional() ? false : true;
    }

    protected void execute(boolean load, boolean enable, boolean disable) {
        if (disable) {
            beforeDisable();
            List<PluginModule> moduleslist = new ArrayList<>(modules.values());
            Collections.reverse(moduleslist);
            for (PluginModule module : moduleslist) {
                if ((module.allowReload() || (load == false && enable == false)) && !unloadModule(module)) {
                    return;
                }
            }
            if (load == false && enable == false) {
                manager.unregisterThreads();
            }
            disable();
            ploaded = penabled = false;
        }
        if (load) {
            beforeLoad();
            for (PluginModule module : modules.values()) {
                if (module.loadOn() == LoadOn.BEFORE_LOAD && !loadModule(module)) {
                    ploaded = false;
                    return;
                }
            }
            if (canLoad()) {
                for (PluginModule module : modules.values()) {
                    if (module.loadOn() == LoadOn.LOAD && !loadModule(module)) {
                        ploaded = false;
                        return;
                    }
                }
            }
            else {
                ploaded = false;
                return;
            }
            load();
            ploaded = true;
        }
        if (enable && ploaded) {
            beforeEnable();
            for (PluginModule module : modules.values()) {
                if (module.loadOn() == LoadOn.BEFORE_ENABLE && !loadModule(module)) {
                    penabled = false;
                    return;
                }
            }
            if (canEnable()) {
                for (PluginModule module : modules.values()) {
                    if (module.loadOn() == LoadOn.ENABLE && !loadModule(module)) {
                        penabled = false;
                        return;
                    }
                }
            }
            else {
                penabled = false;
                return;
            }
            enable();
            penabled = true;
            if (firsttime) {
                firsttime = false;
            }
        }
        else {
        }
    }

    protected boolean unloadModule(PluginModule module) {
        if (!enabled.getOrDefault(module.getClass(), FALSE).booleanValue()) {
            return true;
        }
        try {
            long start = System.currentTimeMillis();
            if (module.unload()) {
                MessagesUtils.getMessageBuilder()
                    .createMessage(getPrefix() + " Unloaded module " + module.getClass().getSimpleName()
                                       + " successfully! (" + (System.currentTimeMillis() - start) + " ms)")
                    .sendMessage(Bukkit.getConsoleSender());
                enabled.put(module.getClass(), FALSE);
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        MessagesUtils
            .getMessageBuilder().createMessage(getPrefix() + " Error trying to unload module "
                                                   + module.getClass().getSimpleName() + "!" + (module.optional() ? " (optional)" : ""))
            .sendMessage(Bukkit.getConsoleSender());
        enabled.put(module.getClass(), FALSE);
        return !module.optional() ? false : true;
    }
}
