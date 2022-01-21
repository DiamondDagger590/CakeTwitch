package relampagorojo93.caketwitch.spigotplugin;

public abstract class PluginModule {
	public abstract boolean load();
	public abstract boolean unload();
	public abstract LoadOn loadOn();
	public abstract boolean optional();
	public abstract boolean allowReload();
}
