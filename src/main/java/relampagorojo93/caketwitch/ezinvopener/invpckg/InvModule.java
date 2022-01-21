package relampagorojo93.caketwitch.ezinvopener.invpckg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import relampagorojo93.caketwitch.ezinvopener.api.EIOAPI;
import relampagorojo93.caketwitch.ezinvopener.api.objects.EzInventory;
import relampagorojo93.caketwitch.spigotplugin.LoadOn;
import relampagorojo93.caketwitch.spigotplugin.PluginModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvModule extends PluginModule {

    @Override
    public boolean load() {
        registerInventory(new EzInventory((Plugin) EIOAPI.getPlugin(), "test", (player) -> {
            Inventory inv = Bukkit.createInventory(null, 9, "Test");
            ItemStack i = new ItemStack(Material.BOOK);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName("Hey :D");
            i.setItemMeta(im);
            inv.setItem(4, i);
            return inv;
        }));
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public LoadOn loadOn() {
        return LoadOn.BEFORE_LOAD;
    }

    @Override
    public boolean optional() {
        return false;
    }

    @Override
    public boolean allowReload() {
        return true;
    }

    private HashMap<String, HashMap<String, EzInventory>> inventories = new HashMap<>();

    public void registerInventory(EzInventory inventory) {
        String pl = inventory.getPlugin().getName().toLowerCase();
        if (!inventories.containsKey(pl)) {
            inventories.put(pl, new HashMap<>());
        }
        inventories.get(pl).put(inventory.getName().toLowerCase(), inventory);
    }

    public void unregisterInventories(Plugin plugin) {
        inventories.remove(plugin.getName().toLowerCase());
    }

    public void unregisterInventory(Plugin plugin, String name) {
        String pl = plugin.getName().toLowerCase();
        if (!inventories.containsKey(pl)) {
            return;
        }
        inventories.get(pl).remove(name.toLowerCase());
    }

    public List<EzInventory> getInventories() {
        List<EzInventory> list = new ArrayList<>();
        for (HashMap<String, EzInventory> col : this.inventories.values()) {
            for (EzInventory inventory : col.values()) {
                list.add(inventory);
            }
        }
        return list;
    }

    public EzInventory getInventory(String path) {
        String[] split = path.split("\\.", 2);
        if (split.length < 2) {
            return null;
        }
        return getInventory(split[0], split[1]);
    }

    public EzInventory getInventory(Plugin plugin, String name) {
        return getInventory(plugin.getName(), name);
    }

    public EzInventory getInventory(String plugin, String name) {
        plugin = plugin.toLowerCase();
        name = name.toLowerCase();
        if (inventories.containsKey(plugin)) {
            return inventories.get(plugin).get(name);
        }
        return null;
    }
}
