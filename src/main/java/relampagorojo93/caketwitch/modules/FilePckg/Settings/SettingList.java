package relampagorojo93.caketwitch.modules.FilePckg.Settings;

import java.util.Arrays;
import java.util.List;

public enum SettingList {
	RESOURCEPACKS("Resource-packs", Arrays.asList("&eMy test Resource Pack:caketwitch.rp.test:https://media.forgecdn.net/files/3246/391/BOS+Core+1.16+21-Mar-2021.zip")),
	
	TWITCH_BLACKLISTEDCHANNELS("Twitch.Blacklisted-channels", Arrays.asList("streamlabs", "nightbot", "streamelements", "moobot", "deepbot", "wizebot", "phantombot", "xanbot")),
	TWITCH_COMMANDSAFTERGRANTINGPRIVILEGES("Twitch.Commands-after-granting-privileges", Arrays.asList("lp user %ign% parent add TwitchStreamer")),
	TWITCH_COMMANDSAFTERREVOKINGPRIVILEGES("Twitch.Commands-after-revoking-privileges", Arrays.asList("lp user %ign% parent remove TwitchStreamer")),
	
	TWITCH_STARTSTREAMINGONLOAD("Twitch.Start-streaming-on-load",
			Arrays.asList("darkpanda_73", "caronte7u7", "joshtified", "verdux", "morelaid")),
	
	CAKETWITCH_ALIASES("Commands.CakeTwitch.Aliases", Arrays.asList("ct")),
	CAKETWITCH_HELP_ALIASES("Commands.CakeTwitch.Subcommands.Help.Aliases", Arrays.asList("h")),
	CAKETWITCH_PACK_ALIASES("Commands.CakeTwitch.Subcommands.Pack.Aliases", Arrays.asList("p")),
	CAKETWITCH_STREAMERS_ALIASES("Commands.CakeTwitch.Subcommands.Pack.Aliases", Arrays.asList("s")),
	CAKETWITCH_RELOAD_ALIASES("Commands.CakeTwitch.Subcommands.Reload.Aliases", Arrays.asList("r")),
	
	// ---------------------------------------------------------------------//
	// Twitch commands
	// ---------------------------------------------------------------------//
	
	CAKETWITCH_TEST_ALIASES("Commands.CakeTwitch.Subcommands.Test.Aliases", "Commands.CakeTwitch.Subcommands.Twitch.Subcommands.Test.Aliases", Arrays.asList("t")),
	CAKETWITCH_REGISTER_ALIASES("Commands.CakeTwitch.Subcommands.Register.Aliases", "Commands.CakeTwitch.Subcommands.Twitch.Subcommands.Register.Aliases", Arrays.asList("r")),
	CAKETWITCH_UNREGISTER_ALIASES("Commands.CakeTwitch.Subcommands.Unregister.Aliases", "Commands.CakeTwitch.Subcommands.Twitch.Subcommands.Unregister.Aliases", Arrays.asList("ur")),
	
	// ---------------------------------------------------------------------//
	// Admin commands
	// ---------------------------------------------------------------------//
	
	CAKETWITCH_ADMIN_ALIASES("Commands.CakeTwitch.Subcommands.Admin.Aliases", Arrays.asList("a")),
	CAKETWITCH_ADMIN_REGISTER_ALIASES("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Register.Aliases", Arrays.asList("r")),
	CAKETWITCH_ADMIN_UNREGISTER_ALIASES("Commands.CakeTwitch.Subcommands.Admin.Subcommands.Unregister.Aliases", Arrays.asList("ur")),
	CAKETWITCH_ADMIN_CLEARQUEUE_ALIASES("Commands.CakeTwitch.Subcommands.Admin.Subcommands.ClearQueue.Aliases", Arrays.asList("cq")),
	
	// ---------------------------------------------------------------------//
	// EventSub commands
	// ---------------------------------------------------------------------//
	
	CAKETWITCH_EVENTSUB_ALIASES("Commands.CakeTwitch.Subcommands.EventSub.Aliases", "Commands.CakeTwitch.Subcommands.Twitch.Subcommands.EventSub.Aliases", Arrays.asList("es")),
	CAKETWITCH_EVENTSUB_INFORMATION_ALIASES("Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Information.Aliases", "Commands.CakeTwitch.Subcommands.Twitch.Subcommands.EventSub.Subcommands.Information.Aliases", Arrays.asList("info", "i")),
	CAKETWITCH_EVENTSUB_RESET_ALIASES("Commands.CakeTwitch.Subcommands.EventSub.Subcommands.Reset.Aliases", "Commands.CakeTwitch.Subcommands.Twitch.Subcommands.EventSub.Subcommands.Reset.Aliases", Arrays.asList("r")),
	
	// ---------------------------------------------------------------------//
	// SQL commands
	// ---------------------------------------------------------------------//
	
	CAKETWITCHSQL_ALIASES("Commands.CakeTwitch.Subcommands.SQL.Aliases", Arrays.asList("")),
	CAKETWITCHSQL_PARSEFROMSQLITE_ALIASES("Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromSQLite.Aliases",
			Arrays.asList("pfs")),
	CAKETWITCHSQL_PARSEFROMMYSQL_ALIASES("Commands.CakeTwitch.Subcommands.SQL.Subcommands.ParseFromMySQL.Aliases",
			Arrays.asList("pfm"));

	// Methods
	String oldpath, path;
	List<String> content, defaultcontent;

	SettingList(String path, List<String> defaultcontent) {
		this(path, path, defaultcontent);
	}

	SettingList(String path, String oldpath, List<String> defaultcontent) {
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

	public List<String> getDefaultContent() {
		return defaultcontent;
	}

	public List<String> toList() {
		return content != null ? content : defaultcontent;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
}