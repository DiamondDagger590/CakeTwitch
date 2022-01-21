package relampagorojo93.caketwitch.twitchbot;

import relampagorojo93.LibsCollection.IRCBot.IRCBot;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.Data;

public class DefaultTwitchIRCBotListener extends TwitchIRCBotListener {
	@Override
	public void log(String log) {}
	@Override
	public void onError(TwitchIRCBot twitchbot, Exception exception) {}
	@Override
	public void onConnect(TwitchIRCBot twitchbot) {}
	@Override
	public void onDisconnect(TwitchIRCBot twitchbot) {}
	@Override
	public void onStart(IRCBot ircbot) {}
	@Override
	public void onFinish(IRCBot ircbot) {}
	@Override
	public void onAction(Data data) {}
}
