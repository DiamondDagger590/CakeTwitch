package relampagorojo93.caketwitch.twitchbot;

import relampagorojo93.caketwitch.ircbot.IRCBot;
import relampagorojo93.caketwitch.ircbot.IRCBotListener;
import relampagorojo93.caketwitch.twitchbot.data.BitsData;
import relampagorojo93.caketwitch.twitchbot.data.ChannelRewardData;
import relampagorojo93.caketwitch.twitchbot.data.Data;
import relampagorojo93.caketwitch.twitchbot.data.JoinData;
import relampagorojo93.caketwitch.twitchbot.data.LeaveData;
import relampagorojo93.caketwitch.twitchbot.data.MessageData;
import relampagorojo93.caketwitch.twitchbot.data.RaidData;
import relampagorojo93.caketwitch.twitchbot.data.SubscriptionData;
import relampagorojo93.caketwitch.twitchbot.data.SubscriptionGiftData;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class TwitchIRCBotListener implements IRCBotListener {

    @Override
    public void onInputMessage(IRCBot bot, String input) {
        onInputMessage((TwitchIRCBot) bot, input);
    }

    public void onInputMessage(TwitchIRCBot bot, String input) {
		if (input == null || input.isEmpty()) {
			return;
		}
        if (input.equals("PING :tmi.twitch.tv")) {
            bot.sendCommands("PONG :tmi.twitch.tv");
            return;
        }
        if (input.equals(":tmi.twitch.tv CAP * ACK :twitch.tv/membership twitch.tv/tags twitch.tv/commands")) {
            log("Bot connected succesfully!");
            return;
        }
        if (input.startsWith(":")) {
            String[] data = input.split(" ");
            int index = input.indexOf('#') + 1;
            String channel = input.substring(index, ((index = input.indexOf(" ", index)) != -1) ? index : input.length());
            String command = data[1];
            switch (command) {
                case "353":
                    String[] users = input.split(":")[2].split(" ");
					for (String user : users) {
						onAction(new JoinData(new HashMap<>(), channel, user));
					}
                    break;
                case "JOIN":
                case "LEAVE":
                    String user = data[0].contains("!") ? data[0].split("!")[0].substring(1) : "";
					if (user.equals(bot.getBotName())) {
						return;
					}
					if (command.equals("JOIN")) {
						onAction(new JoinData(new HashMap<>(), channel, user));
					}
					else {
						onAction(new LeaveData(new HashMap<>(), channel, user));
					}
                    break;
                default:
                    break;
            }
            return;
        }
        Map<String, String> tags = new HashMap<>();
        if (input.startsWith("@")) {
            for (String tag : input.split(" ")[0].substring(1).split(";")) {
				if (tag.contains("=")) {
					String[] data = tag.split("=");
					tags.put(data[0], data.length > 1 ? data[1] : "");
				}
				else {
					tags.put(tag, "");
				}
            }
        }
        String[] split = input.split(" ", 5);
        String user = tags.getOrDefault("display-name", "unknown");
        String command = split[2];
        String channel = split[3].substring(1);
        String message = new String((split.length > 4 ? split[4].substring(1) : "").getBytes(), StandardCharsets.UTF_8);
        switch (command) {
            case "PRIVMSG":
                if (tags.containsKey("bits")) {
                    onAction(new BitsData(tags, channel, user, message, Integer.parseInt(tags.get("bits"))));
                    log(Integer.parseInt(tags.get("bits")) + " bits from " + user);
                }
                else if (tags.containsKey("custom-reward-id")) {
                    try {
                        onAction(new ChannelRewardData(tags, channel, user, message, UUID.fromString(tags.get("custom-reward-id"))));
                        log(user + " redeemed reward " + tags.get("custom-reward-id"));
                    }
                    catch (IllegalArgumentException e) {
                        log(user + " redeemed invalid reward " + tags.get("custom-reward-id"));
                    }
                }
                else {
                    onAction(new MessageData(tags, channel, user, message));
                    log(user + " >> " + message);
                }
                break;
            case "USERNOTICE":
                if (tags.containsKey("msg-id")) {
                    String id = tags.get("msg-id");
                    switch (id) {
                        case "sub":
                        case "resub":
                            onAction(new SubscriptionData(tags, channel, user, message, Integer.valueOf(tags.getOrDefault("msg-param-cumulative-months", "0"))));
                            log("Resub from " + user + " of " + Integer.valueOf(tags.getOrDefault("msg-param-cumulative-months", "0")) + " month/s");
                            break;
                        case "subgift":
                            onAction(new SubscriptionGiftData(tags, channel, user, message, Integer.valueOf(tags.getOrDefault("msg-param-months", "0")), tags.getOrDefault("msg-param-recipient-user-name", "")));
                            log("Sub gift from " + user + " to " + tags.getOrDefault("msg-param-recipient-user-name", "") + "" + Integer.valueOf(tags.getOrDefault("msg-param-months", "0")) + "");
                            break;
                        case "raid":
                            onAction(new RaidData(tags, channel, user, Integer.valueOf(tags.getOrDefault("msg-param-viewerCount", "0"))));
                            log("Raid from " + user + " with " + Integer.valueOf(tags.getOrDefault("msg-param-viewerCount", "0")) + " viewer/s");
                            break;
                        case "ritual":
                            onAction(new MessageData(tags, channel, user, message));
                            log(user + " !> " + message);
                            break;
                        case "submysterygift":
                        case "rewardgift":
                        case "communitypayforward":
                            break;
                        default:
                            log(id + " -> " + input);
                            break;
                    }
                }
                break;
            case "USERSTATE":
            case "ROOMSTATE":
            case "CLEARCHAT":
            case "CLEARMSG":
                break;
            default:
                log(input);
        }
    }

    @Override
    public void onConnect(IRCBot ircbot) {
        onConnect((TwitchIRCBot) ircbot);
    }

    @Override
    public void onDisconnect(IRCBot ircbot) {
        onDisconnect((TwitchIRCBot) ircbot);
    }

    @Override
    public void onError(IRCBot ircbot, Exception ex) {
        onError((TwitchIRCBot) ircbot, ex);
    }

    public abstract void onConnect(TwitchIRCBot twitchbot);

    public abstract void onDisconnect(TwitchIRCBot twitchbot);

    public abstract void onError(TwitchIRCBot twitchbot, Exception ex);

    public abstract void onAction(Data data);
}
