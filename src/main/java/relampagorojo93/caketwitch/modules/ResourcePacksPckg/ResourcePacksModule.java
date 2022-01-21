package relampagorojo93.caketwitch.modules.ResourcePacksPckg;

import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingList;
import relampagorojo93.caketwitch.modules.ResourcePacksPckg.Objects.ResourcePack;

import java.util.ArrayList;
import java.util.List;

public class ResourcePacksModule extends PluginModule {
	
	public List<ResourcePack> getResourcePacks() {
		return packs;
	}
	
	public boolean load() {
		for (String pack:SettingList.RESOURCEPACKS.toList()) {
			String[] data = pack.split(":", 3);
			if (data.length == 3) packs.add(new ResourcePack(data[0], data[1], data[2]));
		}
		return true;
	}

	@Override
	public boolean unload() {
		packs.clear();
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
	
	private List<ResourcePack> packs = new ArrayList<>();

}