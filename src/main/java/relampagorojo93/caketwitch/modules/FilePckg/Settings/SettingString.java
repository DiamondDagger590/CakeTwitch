package relampagorojo93.caketwitch.modules.FilePckg.Settings;

public enum SettingString {
	HOST("MySQL.Hostname", "localhost"), DATABASE("MySQL.Database", "server"), USERNAME("MySQL.Username", "admin"),
	PROTOCOL("MySQL.Protocol", "jdbc:mysql:"), PASSWORD("MySQL.Password", "admin"),
	TABLEPREFIX("MySQL.Table_prefix", ""),
	PARAMETERS("MySQL.Parameters", "sslMode=DISABLED&connectionTimeout=3000&socketTimeout=3000"),
	// ---------------------------------------------------------------------//
	// Twitch API settings
	// ---------------------------------------------------------------------//

	TWITCH_API_CLIENTID("Twitch.API.Client-id", "Twitch.Updater.Updater-client-id", ""),
	TWITCH_API_CLIENTSECRET("Twitch.API.Client-secret", "Twitch.Updater.Updater-client-secret", ""),
	TWITCH_EVENTSUB_CALLBACKURL("Twitch.EventSub.Callback-URL", ""),
	TWITCH_EVENTSUB_SECRET("Twitch.EventSub.Secret", "S3cr7t1234."),
	TWITCH_EVENTSUB_SSL_PRIVATEKEY("Twitch.EventSub.SSL.Private-key", ""),
	TWITCH_EVENTSUB_SSL_FULLCHAIN("Twitch.EventSub.SSL.Full-chain", ""),

	// ---------------------------------------------------------------------//
	// CakeTwitch commands
	// ---------------------------------------------------------------------//

	CAKETWITCH_NAME("Commands.CakeTwitch.Name", "CakeTwitch"),
	CAKETWITCH_PERMISSION("Commands.CakeTwitch.Permission", ""),
	CAKETWITCH_DESCRIPTION("Commands.CakeTwitch.Description", "Get all CakeTwitch commands"),
	CAKETWITCH_PARAMETERS("Commands.CakeTwitch.Parameters", "[command/help]"),

	CAKETWITCH_HELP_NAME("Commands.CakeTwitch.Subcommands.Help.Name", "help"),
	CAKETWITCH_HELP_PERMISSION("Commands.CakeTwitch.Subcommands.Help.Permission", ""),
	CAKETWITCH_HELP_DESCRIPTION("Commands.CakeTwitch.Subcommands.Help.Description", "Get all the commands"),
	CAKETWITCH_HELP_PARAMETERS("Commands.CakeTwitch.Subcommands.Help.Parameters", "[page]"),

	CAKETWITCH_PACK_NAME("Commands.CakeTwitch.Subcommands.Pack.Name", "pack"),
	CAKETWITCH_PACK_PERMISSION("Commands.CakeTwitch.Subcommands.Pack.Permission", "CakeTwitch.Pack"),
	CAKETWITCH_PACK_DESCRIPTION("Commands.CakeTwitch.Subcommands.Pack.Description",
			"Open an inventory to get all the available Resource Packs"),

	CAKETWITCH_STREAMERS_NAME("Commands.CakeTwitch.Subcommands.Streamers.Name", "streamers"),
	CAKETWITCH_STREAMERS_PERMISSION("Commands.CakeTwitch.Subcommands.Streamers.Permission", "CakeTwitch.Streamers"),
	CAKETWITCH_STREAMERS_DESCRIPTION("Commands.CakeTwitch.Subcommands.Streamers.Description",
			"Open an inventory to get all the online streamers"),

	CAKETWITCH_RELOAD_NAME("Commands.CakeTwitch.Subcommands.Reload.Name", "reload"),
	CAKETWITCH_RELOAD_PERMISSION("Commands.CakeTwitch.Subcommands.Reload.Permission", "CakeTwitch.Reload"),
	CAKETWITCH_RELOAD_DESCRIPTION("Commands.CakeTwitch.Subcommands.Reload.Description", "Reload the plugin"),

	CAKETWITCH_TEST_NAME("Commands.CakeTwitch.Subcommands.Test.Name", "test"),
	CAKETWITCH_TEST_PERMISSION("Commands.CakeTwitch.Subcommands.Test.Permission", "CakeTwitch.Twitch.Test"),
	CAKETWITCH_TEST_DESCRIPTION("Commands.CakeTwitch.Subcommands.Test.Description",
			"Test your configuration by simulating events"),
	CAKETWITCH_TEST_PARAMETERS("Commands.CakeTwitch.Subcommands.Test.Parameters",
			"<channelpoints|subscriptions/subscriptionsgift/bits/raids> <UUID|numerical value> [player]"),

	CAKETWITCH_REGISTER_NAME("Commands.CakeTwitch.Subcommands.Register.Name", "register"),
	CAKETWITCH_REGISTER_PERMISSION("Commands.CakeTwitch.Subcommands.Register.Permission", "CakeTwitch.Twitch.Register"),
	CAKETWITCH_REGISTER_DESCRIPTION("Commands.CakeTwitch.Subcommands.Register.Description",
			"Register a Twitch account into your Minecraft account"),

	CAKETWITCH_UNREGISTER_NAME("Commands.CakeTwitch.Subcommands.Unregister.Name", "unregister"),
	CAKETWITCH_UNREGISTER_PERMISSION("Commands.CakeTwitch.Subcommands.Unregister.Permission",
			"CakeTwitch.Twitch.Unregister"),
	CAKETWITCH_UNREGISTER_DESCRIPTION("Commands.CakeTwitch.Subcommands.Unregister.Description",
			"Unregister a Twitch account from your Minecraft account"),
	CAKETWITCH_UNREGISTER_PARAMETERS("Commands.CakeTwitch.Subcommands.Unregister.Parameters", "[player]"),

	// ---------------------------------------------------------------------//
	// CakeTwitch Admin commands
	// ---------------------------------------------------------------------//

	CAKETWITCHTWITCH_ADMIN_NAME("Commands.CakeTwitch.Subcommands.Admin.Name", "admin"),
	CAKETWITCHTWITCH_ADMIN_PERMISSION("Commands.CakeTwitch.Subcommands.Admin.Permission",
			"CakeTwitch.Twitch.Admin"),
	CAKETWITCHTWITCH_ADMIN_DESCRIPTION("Commands.CakeTwitch.Subcommands.Admin.Description",
			"Get all CakeTwitch commands for the Twitch Admin management"),
	CAKETWITCHTWITCH_ADMIN_PARAMETERS("Commands.CakeTwitch.Subcommands.Admin.Parameters",
			"[command/help]"),

	CAKETWITCH_ADMIN_REGISTER_NAME("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Register.Name", "register"),
	CAKETWITCH_ADMIN_REGISTER_PERMISSION("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Register.Permission", ""),
	CAKETWITCH_ADMIN_REGISTER_DESCRIPTION("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Register.Description",
			"Link a Twitch account with a Minecraft account"),
	CAKETWITCH_ADMIN_REGISTER_PARAMETERS("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Register.Parameters",
			"<channel> <player>"),

	CAKETWITCH_ADMIN_UNREGISTER_NAME("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Unregister.Name", "unregister"),
	CAKETWITCH_ADMIN_UNREGISTER_PERMISSION("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Unregister.Permission", ""),
	CAKETWITCH_ADMIN_UNREGISTER_DESCRIPTION("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Unregister.Description",
			"Unlink a Twitch account from a Minecraft account"),
	CAKETWITCH_ADMIN_UNREGISTER_PARAMETERS("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Unregister.Parameters",
			"<player>"),

	CAKETWITCH_ADMIN_CLEARQUEUE_NAME("Commands.CakeTwitch.Subcommands.Admin.Subcommands.ClearQueue.Name", "clearqueue"),
	CAKETWITCH_ADMIN_CLEARQUEUE_PERMISSION("Commands.CakeTwitch.Subcommands.Admin.Subcommands.ClearQueue.Permission", ""),
	CAKETWITCH_ADMIN_CLEARQUEUE_DESCRIPTION("Commands.CakeTwitch.Subcommands.Admin.Subcommands.ClearQueue.Description",
			"Stops and clears the queue of commands for a Streamer"),
	CAKETWITCH_ADMIN_CLEARQUEUE_PARAMETERS("Commands.CakeTwitch.Subcommands.Admin.Subcommands.ClearQueue.Parameters",
			"<player>"),

	// ---------------------------------------------------------------------//
	// CakeTwitch EventSub commands
	// ---------------------------------------------------------------------//

	CAKETWITCHTWITCH_EVENTSUB_NAME("Commands.CakeTwitch.Subcommands.EventSub.Name", "eventsub"),
	CAKETWITCHTWITCH_EVENTSUB_PERMISSION("Commands.CakeTwitch.Subcommands.EventSub.Permission",
			"CakeTwitch.Admin.Twitch.EventSub"),
	CAKETWITCHTWITCH_EVENTSUB_DESCRIPTION("Commands.CakeTwitch.Subcommands.EventSub.Description",
			"Get all CakeTwitch commands for the Twitch EventSub management"),
	CAKETWITCHTWITCH_EVENTSUB_PARAMETERS("Commands.CakeTwitch.Subcommands.EventSub.Parameters",
			"[command/help]"),

	CAKETWITCHTWITCH_EVENTSUB_INFORMATION_NAME(
			"Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Information.Name", "information"),
	CAKETWITCHTWITCH_EVENTSUB_INFORMATION_PERMISSION(
			"Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Information.Permission",
			"CakeTwitch.Admin.Twitch.EventSub.Information"),
	CAKETWITCHTWITCH_EVENTSUB_INFORMATION_DESCRIPTION(
			"Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Information.Description",
			"Get information of the actual status of the EventSub protocol"),

	CAKETWITCHTWITCH_EVENTSUB_RESET_NAME(
			"Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Reset.Name", "reset"),
	CAKETWITCHTWITCH_EVENTSUB_RESET_PERMISSION(
			"Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Reset.Permission",
			"CakeTwitch.Admin.Twitch.EventSub.Reset"),
	CAKETWITCHTWITCH_EVENTSUB_RESET_DESCRIPTION(
			"Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Reset.Description",
			"Remove all the subscriptions (This will make events from this protocol stop working on every server until rejoin)"),

	// ---------------------------------------------------------------------//
	// CakeTwitch SQL commands
	// ---------------------------------------------------------------------//

	CAKETWITCHSQL_NAME("Commands.CakeTwitch.Subcommands.SQL.Name", "sql"),
	CAKETWITCHSQL_PERMISSION("Commands.CakeTwitch.Subcommands.SQL.Permission", "CakeTwitch.Admin.SQL"),
	CAKETWITCHSQL_DESCRIPTION("Commands.CakeTwitch.Subcommands.SQL.Description",
			"Get all CakeTwitch commands for the SQL management"),
	CAKETWITCHSQL_PARAMETERS("Commands.CakeTwitch.Subcommands.SQL.Parameters", "[command/help]"),

	CAKETWITCHSQL_PARSEFROMSQLITE_NAME("Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromSQLite.Name",
			"parsefromsqlite"),
	CAKETWITCHSQL_PARSEFROMSQLITE_PERMISSION(
			"Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromSQLite.Permission",
			"CakeTwitch.Admin.SQL.ParseFromSQLite"),
	CAKETWITCHSQL_PARSEFROMSQLITE_DESCRIPTION(
			"Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromSQLite.Description",
			"Parse all the content from the SQLite file to the configured MySQL database (Only works if MySQL is disabled)"),

	CAKETWITCHSQL_PARSEFROMMYSQL_NAME("Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromMySQL.Name",
			"parsefrommysql"),
	CAKETWITCHSQL_PARSEFROMMYSQL_PERMISSION("Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromMySQL.Permission",
			"CakeTwitch.Admin.SQL.ParseFromMySQL"),
	CAKETWITCHSQL_PARSEFROMMYSQL_DESCRIPTION(
			"Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromMySQL.Description",
			"Parse all the content from the MySQL database to the configured SQLite file (Only works if MySQL is enabled)");

	private String oldpath;

	private String path;

	private String content;

	private String defaultcontent;

	SettingString(String path, String defaultcontent) {
		this.path = path;
		this.oldpath = path;
		this.defaultcontent = defaultcontent;
	}

	SettingString(String path, String oldpath, String defaultcontent) {
		this.path = path;
		this.oldpath = oldpath;
		this.defaultcontent = defaultcontent;
	}

	public String getPath() {
		return this.path;
	}

	public String getOldPath() {
		return this.oldpath;
	}

	public String getDefaultContent() {
		return this.defaultcontent;
	}

	public String toString() {
		return (this.content != null) ? this.content : this.defaultcontent;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
