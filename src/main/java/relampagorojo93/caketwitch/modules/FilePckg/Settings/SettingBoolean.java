package relampagorojo93.caketwitch.modules.FilePckg.Settings;

public enum SettingBoolean {
	SQL("MySQL.Enable", false),
	
	HOOKS_PLACEHOLDERAPI("Hooks.PlaceholderAPI", true),
	HOOKS_EIOPENER("Hooks.EIOpener", true),
	
	TWITCH_REQUIRESUUIDVERIFICATION("Twitch.Requires-UUID-verification", true),
	TWITCH_STARTBOTSONUSERLOAD("Twitch.Start-bots-on-user-load", "Bots.Start-on-join", true),
	TWITCH_GRANTSTREAMERPRIVILEGESONREGISTER("Twitch.Grant-streamer-privileges-on-register", false),
	TWITCH_EVENTSUB_SSL_ENABLE("Twitch.EventSub.SSL.Enable", false),
	
	COMMAND_CAKETWITCH_ENABLE("Commands.CakeTwitch.Enable", true),
	
	INVENTORIES_OPENPREVIOUSINVENTORYONCLOSE("Inventories.Open-previous-inventory-on-close", false);

	// Methods
	String oldpath, path;
	Boolean content, defaultcontent;

	SettingBoolean(String path, Boolean defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingBoolean(String path, String oldpath, Boolean defaultcontent) {
		this.path = path;
		this.oldpath = oldpath;
		this.defaultcontent = defaultcontent;
	}

	public String getPath() {
		return path;
	}

	public String getOldPath() {
		return oldpath;
	}

	public boolean getDefaultContent() {
		return defaultcontent;
	}

	public boolean toBoolean() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(Boolean content) {
		this.content = content;
	}
}