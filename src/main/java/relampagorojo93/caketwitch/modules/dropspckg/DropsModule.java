package relampagorojo93.caketwitch.modules.dropspckg;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.dropspckg.objects.DropsCommandList;
import relampagorojo93.caketwitch.modules.dropspckg.objects.DropsRunnable;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;
import relampagorojo93.caketwitch.spigotthreads.objects.Thread;
import relampagorojo93.caketwitch.yamllib.YAMLFile;
import relampagorojo93.caketwitch.yamllib.objects.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DropsModule extends PluginModule {

    public boolean load() {
        try {
            YAMLFile yaml = new YAMLFile(CakeTwitchAPI.getFile().DROPS_FILE);
            if (!yaml.getSection("Settings.Enabled", false).getBoolean()) {
                return true;
            }
            for (String streamer : yaml.getSection("Settings.Twitch-channels", new ArrayList<>()).getStringList()) {
                streamers.add(streamer.toLowerCase());
            }
            delay = yaml.getNonNullSection("Settings.Delay").getInteger(300);
            onlineplayers = yaml.getNonNullSection("Settings.Only-online-players").getBoolean(true);
            includestreamer = yaml.getNonNullSection("Settings.Include-streamer").getBoolean(false);
            List<DropsCommandList> list = new ArrayList<>();
            for (Data child : yaml.getNonNullSection("Prize-list").getChilds()) {
                if (child.isSection()) {
                    list.clear();
                    for (Data prize : child.asSection().getChilds()) {
                        list.add(new DropsCommandList(
                            prize.asSection().getChild("Commands", new ArrayList<>()).getStringList(),
                            prize.asSection().getNonNullChild("Chance").getInteger(0)));
                    }
                    String name = child.asSection().getName();
                    switch (name) {
                        case "Default":
                            defaultlist = new ArrayList<>(list);
                            break;
                        default:
                            streamerslist.put(name, new ArrayList<>(list));
                            break;
                    }
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        for (String streamer : streamers) {
            Thread thread = CakeTwitchAPI.getThreadManager()
                .registerThread(new DropsRunnable(streamer));
            streamersthreads.put(streamer, thread);
            thread.startSecure();
        }
        return true;
    }

    @Override
    public boolean unload() {
        for (Thread thread : streamersthreads.values()) {
            CakeTwitchAPI.getThreadManager().unregisterThread(thread);
        }
        streamers.clear();
        defaultlist.clear();
        streamerslist.clear();
        streamersthreads.clear();
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

    private int delay = 3600;
    private boolean onlineplayers = true, includestreamer = false;
    private List<String> streamers = new ArrayList<>();
    private List<DropsCommandList> defaultlist = new ArrayList<>();
    private HashMap<String, List<DropsCommandList>> streamerslist = new HashMap<>();
    private HashMap<String, Thread> streamersthreads = new HashMap<>();

    public int getDelay() {
        return delay;
    }

    public boolean onlyOnlinePlayers() {
        return onlineplayers;
    }

    public boolean includeStreamer() {
        return includestreamer;
    }

    public List<String> getStreamers() {
        return streamers;
    }

    public List<DropsCommandList> getCommandList(String name) {
        return streamerslist.getOrDefault(name, defaultlist);
    }

    public Thread getThread(String name) {
        return streamersthreads.get(name);
    }
}
