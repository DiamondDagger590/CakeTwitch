package relampagorojo93.caketwitch.modules.FilePckg.Settings;

public enum SettingInt {
	PORT("MySQL.Port", 3306),
	TWITCH_UPDATER_UPDATETIMERINMINUTES("Twitch.Updater.Update-timer-in-minutes", 1),
	TWITCH_EVENTSUB_PORT("Twitch.EventSub.Port", 80);

	// Methods
	String oldpath, path;
	Integer content, defaultcontent;

	SettingInt(String path, Integer defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingInt(String path, String oldpath, Integer defaultcontent) {
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

	public int getDefaultContent() {
		return defaultcontent;
	}

	public int toInt() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(Integer content) {
		this.content = content;
	}
}