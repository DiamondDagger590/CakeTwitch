package relampagorojo93.caketwitch.ircbot;

public interface IRCBotListener {
    void onStart(IRCBot ircbot);

    void onFinish(IRCBot ircbot);

    void onError(IRCBot ircbot, Exception exception);

    void onConnect(IRCBot ircbot);

    void onDisconnect(IRCBot ircBot);

    void onInputMessage(IRCBot ircbot, String input);

    void log(String log);
}
