package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.BotListeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.IRCBot.IRCBot;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.Data;
import relampagorojo93.LibsCollection.TwitchIRCBot.Data.MessageData;
import relampagorojo93.LibsCollection.TwitchIRCBot.TwitchIRCBot;
import relampagorojo93.LibsCollection.TwitchIRCBot.TwitchIRCBotListener;
import relampagorojo93.caketwitch.bukkit.Events.ChatEvents;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

public class AuthListener extends TwitchIRCBotListener {
	
	private Streamer streamer;

	public AuthListener(Streamer streamer) {
	    this.streamer = streamer;
	  }

	public void onError(TwitchIRCBot twitchbot, Exception exception) {
	}

	public void log(String log) {
	}

	public void onConnect(TwitchIRCBot twitchbot) {
		twitchbot.joinChannel(this.streamer.getRegisterChannel().toLowerCase());
	}

	public void onDisconnect(TwitchIRCBot twitchbot) {
	}

	public void onStart(IRCBot ircbot) {
	}

	public void onFinish(IRCBot ircbot) {
	}

	public void onAction(Data data) {
		if (data.getAction() == Data.Action.MESSAGE) {
			ChatEvents.unregister(this.streamer.getUniqueID());
			MessageData msgdata = (MessageData) data;
			String channel = msgdata.getChannel().toLowerCase(), executor = msgdata.getExecutor().toLowerCase();
			String message = msgdata.getMessage().toLowerCase();
			Player pl = Bukkit.getPlayer(this.streamer.getUniqueID());
			if (channel.equals(executor) && this.streamer.getUniqueID().toString().toLowerCase().equals(message)) {
				this.streamer.setChannelLogin(channel, true);
				if (pl != null)
					MessagesUtils.getMessageBuilder().sendTitle(pl, true, "Registered successfully!", "", 20, 40, 20);
			} else if (pl != null) {
				MessagesUtils.getMessageBuilder().sendTitle(pl, true, "Verification failed!", "", 20, 40, 20);
			}
			this.streamer.getBot().stopBot();
			this.streamer.setRegisterChannel(null);
		}
	}
}
