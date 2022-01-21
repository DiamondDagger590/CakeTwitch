package relampagorojo93.caketwitch.relautils.bukkit;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TasksUtils {
    //---------------------------------------------------------------------//
    //Async Tasks
    //---------------------------------------------------------------------//
    public static void execute(Plugin plugin, Task task) {
        execute(plugin, task, false);
    }

    public static void executeOnAsync(Plugin plugin, Task task) {
        execute(plugin, task, true);
    }

    public static void execute(Plugin plugin, Task task, boolean async) {
        if (plugin.isEnabled()) {
            BukkitRunnable br = new BukkitRunnable() {
                @Override
                public void run() {
                    task.method();
                }
            };
            if (async) {
                br.runTaskAsynchronously(plugin);
            }
            else {
                br.runTask(plugin);
            }
        }
    }

    public static void executeWithDelay(Plugin plugin, Task task, long startinticks) {
        executeWithDelay(plugin, task, startinticks, false);
    }

    public static void executeOnAsyncWithDelay(Plugin plugin, Task task, long startinticks) {
        executeWithDelay(plugin, task, startinticks, true);
    }

    public static void executeWithDelay(Plugin plugin, Task task, long startinticks, boolean async) {
        if (plugin.isEnabled()) {
            BukkitRunnable br = new BukkitRunnable() {
                @Override
                public void run() {
                    task.method();
                }
            };
            if (async) {
                br.runTaskLaterAsynchronously(plugin, startinticks);
            }
            else {
                br.runTaskLater(plugin, startinticks);
            }
        }
    }

    public static void executeWithTimer(Plugin plugin, Task task, long startinticks, long timerinticks) {
        executeWithTimer(plugin, task, startinticks, timerinticks, false);
    }

    public static void executeOnAsyncWithTimer(Plugin plugin, Task task, long startinticks, long timerinticks) {
        executeWithTimer(plugin, task, startinticks, timerinticks, true);
    }

    public static void executeWithTimer(Plugin plugin, Task task, long startinticks, long timerinticks, boolean async) {
        if (plugin.isEnabled()) {
            BukkitRunnable br = new BukkitRunnable() {
                @Override
                public void run() {
                    task.method();
                }
            };
            if (async) {
                br.runTaskTimerAsynchronously(plugin, startinticks, timerinticks);
            }
            else {
                br.runTaskTimer(plugin, startinticks, timerinticks);
            }
        }
    }

    public static interface Task {
        void method();
    }
}
