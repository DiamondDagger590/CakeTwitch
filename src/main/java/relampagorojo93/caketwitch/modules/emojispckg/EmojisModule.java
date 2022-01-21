package relampagorojo93.caketwitch.modules.emojispckg;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotmessages.instances.TextReplacement;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;
import relampagorojo93.caketwitch.yamllib.YAMLFile;
import relampagorojo93.caketwitch.yamllib.objects.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class EmojisModule extends PluginModule {

    public boolean load() {
        try {
            for (Data data : new YAMLFile(CakeTwitchAPI.getFile().EMOJIS_FILE).getSection("").getChilds()) {
                if (data.isSection()) {
                    emojis.put(data.asSection().getName(), data.asSection().getString());
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean unload() {
        emojis.clear();
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

    private HashMap<String, String> emojis = new HashMap<>();

    public String applyEmojis(String message) {
        return applyEmojis(message, false);
    }

    public String applyEmojis(String message, boolean cheers) {
        List<TextReplacement> replacements = new ArrayList<>();
        for (Entry<String, String> entry : emojis.entrySet()) {
            if (cheers || !entry.getKey().startsWith("Cheer")) {
                replacements.add(new TextReplacement(entry.getKey(), () -> entry.getValue()));
            }
        }
        return MessagesUtils.getMessageBuilder().createMessage(replacements.toArray(new TextReplacement[replacements.size()]), true, message).toString();
    }
}
