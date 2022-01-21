package relampagorojo93.caketwitch.ezinvopener.entitiespckg;

import relampagorojo93.caketwitch.ezinvopener.api.EIOAPI;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;
import relampagorojo93.caketwitch.yamllib.YAMLFile;
import relampagorojo93.caketwitch.yamllib.objects.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class EntitiesModule extends PluginModule {

    @Override
    public boolean load() {
        try {
            if (EIOAPI.getFileAPI().getEntitiesFile().exists()) {
                YAMLFile yaml = new YAMLFile(EIOAPI.getFileAPI().getEntitiesFile());
                for (Data data : yaml.getNonNullSection("").getChilds()) {
                    if (data.isSection()) {
                        entities.put(UUID.fromString(data.asSection().getName()), data.getString());
                    }
                }
                yaml.reset();
            }
            if (EIOAPI.getFileAPI().getMMOHorsesNPCsFile().exists()) {
                YAMLFile yaml = new YAMLFile(EIOAPI.getFileAPI().getMMOHorsesNPCsFile());
                for (Data data : yaml.getNonNullSection("").getChilds()) {
                    if (data.isSection()) {
                        String action = data.asSection().getChild("Action", "OPEN_LIST").getString();
                        switch (action) {
                            case "OPEN_LIST":
                                entities.put(UUID.fromString(data.asSection().getName()), "mmohorses.list-menu");
                                break;
                            case "OPEN_SHOP":
                                entities.put(UUID.fromString(data.asSection().getName()), "mmohorses.shop-menu");
                                break;
                            case "OPEN_CRATES":
                                entities.put(UUID.fromString(data.asSection().getName()), "mmohorses.crates-menu");
                                break;
                            default:
                                break;
                        }
                    }
                }
                yaml.reset();
                EIOAPI.getFileAPI().getMMOHorsesNPCsFile().delete();
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unload() {
        boolean state = save();
        entities.clear();
        return state;
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

    private HashMap<UUID, String> entities = new HashMap<>();

    public void registerEntity(UUID uuid, String invpath) {
        entities.put(uuid, invpath);
        save();
    }

    public void unregisterEntity(UUID uuid) {
        entities.remove(uuid);
        save();
    }

    public String getEntityAttach(UUID uuid) {
        return entities.get(uuid);
    }

    public List<UUID> getEntities() {
        return new ArrayList<>(entities.keySet());
    }

    private HashMap<UUID, String> registering = new HashMap<>();

    public void startRegistering(UUID uuid, String invpath) {
        registering.put(uuid, invpath);
    }

    public void stopRegistering(UUID uuid) {
        registering.remove(uuid);
    }

    public String getRegistering(UUID uuid) {
        return registering.get(uuid);
    }

    public boolean save() {
        try {
            if (!EIOAPI.getFileAPI().getEntitiesFile().exists()) {
                EIOAPI.getFileAPI().getEntitiesFile().createNewFile();
            }
            YAMLFile yaml = new YAMLFile();
            for (Entry<UUID, String> entry : entities.entrySet()) {
                yaml.setSection(entry.getKey().toString(), entry.getValue());
            }
            yaml.saveYAML(EIOAPI.getFileAPI().getEntitiesFile());
            yaml.reset();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
