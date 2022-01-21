package relampagorojo93.caketwitch.twitchbot;

import relampagorojo93.LibsCollection.IRCBot.IRCBot;

import javax.net.ssl.SSLSocketFactory;
import java.util.Arrays;

public class TwitchIRCBot extends IRCBot {
	private String botname;
	private String oauth;

	public TwitchIRCBot(String oauth, String botname, TwitchIRCBotListener listener) {
		super("irc.chat.twitch.tv", 6697, SSLSocketFactory.getDefault(), listener);
		this.botname = botname == null || botname.isEmpty() ? "justinfan12345" : botname.toLowerCase();
		this.oauth = oauth;
		setAutoReconnect(3);
		if (oauth != null && !oauth.isEmpty())
			setStartupCommands(Arrays.asList("PASS oauth:" + this.oauth, "NICK " + this.botname,
					"CAP REQ :twitch.tv/membership twitch.tv/tags twitch.tv/commands"));
		else
			setStartupCommands(Arrays.asList("NICK " + this.botname,
					"CAP REQ :twitch.tv/membership twitch.tv/tags twitch.tv/commands"));
	}

	public TwitchIRCBot(String oauth, String botname) {
		this(oauth, botname, new DefaultTwitchIRCBotListener());
	}

	public TwitchIRCBot() {
		this("", "justinfan12345");
	}

	public String getBotName() {
		return botname;
	}

	public String getOAuth() {
		return oauth;
	}

	public void joinChannel(String channel) {
		sendCommands("JOIN #" + channel);
	}

	public void leaveChannel(String channel) {
		sendCommands("PART #" + channel);
	}

	public void sendMessage(String channel, String message) {
		sendCommands("PRIVMSG #" + channel + " :" + message);
	}

	public TwitchIRCBotListener getTwitchListener() {
		return (TwitchIRCBotListener) getListener();
	}
}
