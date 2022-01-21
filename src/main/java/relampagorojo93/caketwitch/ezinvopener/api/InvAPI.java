package relampagorojo93.caketwitch.ezinvopener.api;

import org.bukkit.plugin.Plugin;
import relampagorojo93.caketwitch.ezinvopener.invpckg.InvModule;
import relampagorojo93.caketwitch.ezinvopener.api.objects.EzInventory;

import java.util.List;

public class InvAPI {
    private InvModule inv;

    public InvAPI(InvModule inv) {
        this.inv = inv;
    }

    public void registerInventory(EzInventory inventory) {
        inv.registerInventory(inventory);
    }

    public void unregisterInventories(Plugin plugin) {
        inv.unregisterInventories(plugin);
    }

    public void unregisterInventory(Plugin plugin, String name) {
        inv.unregisterInventory(plugin, name);
    }

    public List<EzInventory> getInventories() {
        return inv.getInventories();
    }

    public EzInventory getInventory(String path) {
        return inv.getInventory(path);
    }

    public EzInventory getInventory(Plugin plugin, String name) {
        return inv.getInventory(plugin, name);
    }

    public EzInventory getInventory(String plugin, String name) {
        return inv.getInventory(plugin, name);
    }
}
