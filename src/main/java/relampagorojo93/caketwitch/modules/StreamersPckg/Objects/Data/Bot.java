package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data;

import relampagorojo93.LibsCollection.IRCBot.Status;
import relampagorojo93.LibsCollection.SpigotDebug.DebugController;
import relampagorojo93.LibsCollection.TwitchIRCBot.TwitchIRCBot;
import relampagorojo93.LibsCollection.TwitchIRCBot.TwitchIRCBotListener;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.BotListeners.AuthListener;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.BotListeners.UserTwitchListener;

public class Bot {
	
	private Streamer streamer;
	private DebugController debugcontroller;
	private TwitchIRCBot bot;
	private TwitchIRCBotListener userlistener;
	private TwitchIRCBotListener authlistener;

	public Bot(Streamer streamer) {
		this.debugcontroller = new DebugController(256);
		this.streamer = streamer;
		this.userlistener = (TwitchIRCBotListener) new UserTwitchListener(streamer);
		this.authlistener = (TwitchIRCBotListener) new AuthListener(streamer);
	}

	public DebugController getDebugController() {
		return this.debugcontroller;
	}

	public Status getStatus() {
		return (this.bot != null) ? this.bot.getStatus() : Status.STOPPED;
	}

	public void startBot() {
		stopBot();
		if (this.bot == null) {
			this.bot = (TwitchIRCBot) CakeTwitchAPI.getThreadManager()
					.registerThread((Thread) new TwitchIRCBot(null, null,
							this.streamer.getRegisterChannel() != null
								? this.authlistener
								: this.userlistener));
			this.bot.setAutoReconnect(3);
		}
		if (!this.bot.isRunning())
			this.bot.startSecure();
	}

	public void stopBot() {
		if (this.bot == null)
			return;
		if (this.bot.isRunning())
			this.bot.stopSecure();
		CakeTwitchAPI.getCommandsQueue().clearCommands(this.streamer.getUniqueID());
		this.bot = null;
	}
}
