package relampagorojo93.caketwitch.ircbot;

public interface IRCBotListener {
	public abstract void onStart(IRCBot ircbot);
	public abstract void onFinish(IRCBot ircbot);
	public abstract void onError(IRCBot ircbot, Exception exception);
	public abstract void onConnect(IRCBot ircbot);
	public abstract void onDisconnect(IRCBot ircBot);
	public abstract void onInputMessage(IRCBot ircbot, String input);
	public abstract void log(String log);
}
