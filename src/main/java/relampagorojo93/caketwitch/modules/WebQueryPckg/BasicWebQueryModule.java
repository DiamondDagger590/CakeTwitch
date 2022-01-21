package relampagorojo93.caketwitch.modules.WebQueryPckg;

import relampagorojo93.LibsCollection.JSONLib.JSONElement;
import relampagorojo93.LibsCollection.JSONLib.JSONObject;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.Utils.Shared.WebQueries.WebMethod;
import relampagorojo93.LibsCollection.Utils.Shared.WebQueries.WebQuery;

import java.util.ArrayList;
import java.util.List;

public class BasicWebQueryModule extends PluginModule {

	public boolean load() {
		return true;
	}

	public boolean unload() {
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
		return false;
	}

	public List<String> getTwitchViewers(String channel) {
		List<String> viewers = new ArrayList<>();
		JSONObject json = null;
		try {
			json = WebQuery.queryToJSON("https://tmi.twitch.tv/group/user/" + channel + "/chatters", WebMethod.GET, new JSONObject()).asObject().getObjectNonNull("chatters");
			for (JSONElement element:json.getArrayNonNull("viewers").getObjects()) if (element.isData()) viewers.add(element.asData().getAsString());
			for (JSONElement element:json.getArrayNonNull("global_mods").getObjects()) if (element.isData()) viewers.add(element.asData().getAsString());
			for (JSONElement element:json.getArrayNonNull("admins").getObjects()) if (element.isData()) viewers.add(element.asData().getAsString());
			for (JSONElement element:json.getArrayNonNull("staff").getObjects()) if (element.isData()) viewers.add(element.asData().getAsString());
			for (JSONElement element:json.getArrayNonNull("moderators").getObjects()) if (element.isData()) viewers.add(element.asData().getAsString());
			for (JSONElement element:json.getArrayNonNull("vips").getObjects()) if (element.isData()) viewers.add(element.asData().getAsString());
			for (JSONElement element:json.getArrayNonNull("broadcaster").getObjects()) if (element.isData()) viewers.add(element.asData().getAsString());
		} catch (Exception e) {}
		return viewers;
	}
}
