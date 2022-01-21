package relampagorojo93.caketwitch.modules.streamerspckg.objects.data;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.ircbot.Status;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.botlisteners.AuthListener;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.botlisteners.UserTwitchListener;
import relampagorojo93.caketwitch.spigotdebug.DebugController;
import relampagorojo93.caketwitch.spigotthreads.objects.Thread;
import relampagorojo93.caketwitch.twitchbot.TwitchIRCBot;
import relampagorojo93.caketwitch.twitchbot.TwitchIRCBotListener;

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
		if (!this.bot.isRunning()) {
			this.bot.startSecure();
		}
    }

    public void stopBot() {
		if (this.bot == null) {
			return;
		}
		if (this.bot.isRunning()) {
			this.bot.stopSecure();
		}
        CakeTwitchAPI.getCommandsQueue().clearCommands(this.streamer.getUniqueID());
        this.bot = null;
    }
}
