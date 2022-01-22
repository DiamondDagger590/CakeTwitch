package relampagorojo93.caketwitch.spigotplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import relampagorojo93.caketwitch.spigotdebug.DebugController;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotthreads.ThreadManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MainClass extends JavaPlugin {
    private static final Boolean TRUE = Boolean.valueOf(true);
    private static final Boolean FALSE = Boolean.valueOf(false);
    private Map<Class<?>, PluginModule> modules;
    private HashMap<Class<?>, Boolean> enabled = new HashMap<>();
    private boolean ploaded = false, penabled = false, firsttime = true;
    private DebugController controller = new DebugController();
    private ThreadManager manager = new ThreadManager();

    public DebugController getDebugController() {
        return controller;
    }

    public ThreadManager getThreadManager() {
        return manager;
    }

    public boolean pluginLoaded() {
        return ploaded;
    }

    public boolean pluginEnabled() {
        return penabled;
    }

    public MainClass(PluginModule... modules) {
        LinkedHashMap<Class<?>, PluginModule> map = new LinkedHashMap<>();
        for (PluginModule module : modules) {
            map.put(module.getClass(), module);
        }
        this.modules = map;
    }

    public PluginModule getModule(Class<?> clazz) {
        return enabled.getOrDefault(clazz, FALSE).booleanValue() ? modules.get(clazz) : null;
    }

    public abstract String getPrefix();

    public abstract boolean canLoad();

    public abstract boolean canEnable();

    public abstract boolean load();

    public abstract boolean enable();

    public abstract boolean disable();

    public abstract boolean beforeLoad();

    public abstract boolean beforeEnable();

    public abstract boolean beforeDisable();

    @Override
    public void onLoad() {
        execute(true, false, false);
    }

    @Override
    public void onEnable() {
        execute(false, true, false);
    }

    public void reloadPlugin() {
        execute(true, true, true);
    }

    @Override
    public void onDisable() {
        execute(false, false, true);
    }

    public boolean isFirstTime() {
        return firsttime;
    }

    protected boolean loadModule(PluginModule module) {
        if (enabled.getOrDefault(module.getClass(), FALSE).booleanValue()) {
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
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
            }
            if (canEnable()) {
                for (PluginModule module : modules.values()) {
                    if (module.loadOn() == LoadOn.ENABLE && !loadModule(module)) {
                        penabled = false;
                        Bukkit.getPluginManager().disablePlugin(this);
                        return;
                    }
                }
            }
            else {
                penabled = false;
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            enable();
            penabled = true;
            if (firsttime) {
                firsttime = false;
            }
        }
        else {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
