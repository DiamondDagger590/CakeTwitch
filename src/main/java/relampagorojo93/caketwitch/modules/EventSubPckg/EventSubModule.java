package relampagorojo93.caketwitch.modules.EventSubPckg;

import relampagorojo93.LibsCollection.JSONLib.JSONElement;
import relampagorojo93.LibsCollection.JSONLib.JSONObject;
import relampagorojo93.LibsCollection.JSONLib.JSONParser;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.Utils.Shared.Map.SimpleEntry;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.EventSubPckg.Pages.EventRetrievePage;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class EventSubModule extends PluginModule {

	public boolean load() {
		if (CakeTwitchAPI.getHTTP() == null) {
			System.out.println(MessageString.applyPrefix("(TwitchEventSub) To use EventSub, the HTTP module must be working!")); return false;
		}
		int length = SettingString.TWITCH_EVENTSUB_SECRET.toString().length();
		if (length < 10 || length > 100) {
	        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-?/,".toCharArray();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 25; i++) sb.append(chars[(int) (chars.length * Math.random())]);
			SettingString.TWITCH_EVENTSUB_SECRET.setContent(sb.toString());
			System.out.println(MessageString.applyPrefix("(TwitchEventSub) Secret must be between 10 and 100 characters. Changing secret to " + SettingString.TWITCH_EVENTSUB_SECRET.toString() + ", but you can't use EventSub until you change the secret!"));
			return false;
		}
		return true;
	}

	public boolean unload() {
		return true;
	}

	public LoadOn loadOn() {
		return LoadOn.ENABLE;
	}

	public boolean optional() {
		return true;
	}

	public boolean allowReload() {
		return true;
	}

	public Information getInformation() throws Exception {
		HashMap<String, List<Entry<Event, UUID>>> subs = new HashMap<>();
		URL urlobject = new URL("https://api.twitch.tv/helix/eventsub/subscriptions");
		HttpURLConnection http = (HttpURLConnection) urlobject.openConnection();
		HashMap<String, String> map = new HashMap<>();
		map.put("Client-Id", SettingString.TWITCH_API_CLIENTID.toString());
		map.put("Authorization", "Bearer " + CakeTwitchAPI.getWebQuery().getDefaultToken());
		map.put("Content-Type", "application/json");
		for (Entry<String, String> entry : map.entrySet())
			http.setRequestProperty(entry.getKey(), entry.getValue());
		http.setRequestMethod("GET");
		http.setDoInput(true);
		BufferedReader inreader = null;
		StringBuilder sb = null;
		String input = null;
		try {
			inreader = new BufferedReader(new InputStreamReader(http.getInputStream()));
			sb = new StringBuilder();
			input = null;
			try {
				while ((input = inreader.readLine()) != null)
					sb.append(input);
			} catch (SocketTimeoutException socketTimeoutException) {
			}
		} catch (Exception exception) {
		}
		inreader.close();
		http.disconnect();
		JSONElement json = JSONParser.parseJson(sb.toString());
		for (JSONElement element : json.asObject().getArray("data").getObjects()) {
			JSONObject obj = element.asObject().getObject("condition");
			String channel = null;
			if (obj.hasKey("broadcaster_user_id")) {
				channel = obj.getData("broadcaster_user_id").getAsString();
			} else if (obj.hasKey("user_id")) {
				channel = obj.getData("user_id").getAsString();
			} else if (obj.hasKey("to_broadcaster_user_id")) {
				channel = obj.getData("to_broadcaster_user_id").getAsString();
			}
			if (!subs.containsKey(channel))
				subs.put(channel, new ArrayList<>());
			subs.get(channel)
					.add(new SimpleEntry<>(Event.fromEventName(element.asObject().getData("type").getAsString()),
							UUID.fromString(element.asObject().getData("id").getAsString())));
		}
		return new Information(subs, json.asObject().getData("total_cost").getAsInteger(),
				json.asObject().getData("max_total_cost").getAsInteger());
	}

	public SubscriptionResponse sendSubscription(Event type, Streamer streamer) {
		try {
			String channel_id = streamer.getChannelId();
			if (channel_id == null)
				return SubscriptionResponse.NONE;
			if (streamer.getToken() == null)
				return SubscriptionResponse.NONE;
			URL urlobject = new URL("https://api.twitch.tv/helix/eventsub/subscriptions");
			HttpURLConnection http = (HttpURLConnection) urlobject.openConnection();
			HashMap<String, String> map = new HashMap<>();
			map.put("Client-Id", SettingString.TWITCH_API_CLIENTID.toString());
			map.put("Authorization", "Bearer " + CakeTwitchAPI.getWebQuery().getDefaultToken().getAccessToken());
			map.put("Content-Type", "application/json");
			for (Entry<String, String> entry : map.entrySet())
				http.setRequestProperty(entry.getKey(), entry.getValue());
			http.setRequestMethod("POST");
			http.setDoInput(true);
			http.setDoOutput(true);
			BufferedWriter outwriter = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
			JSONObject json = new JSONObject();
			json.addObject("type", type.getEventName()).addObject("version", "\"1\"")
					.addObject("condition",
							(JSONElement) (new JSONObject()).addObject("broadcaster_user_id", "\"" + channel_id + "\"")
									.addObject("user_id", "\"" + channel_id + "\"")
									.addObject("to_broadcaster_user_id", "\"" + channel_id + "\""))
					.addObject("transport",
							(JSONElement) (new JSONObject()).addObject("method", "webhook")
									.addObject("callback", EventRetrievePage.getURL())
									.addObject("secret", SettingString.TWITCH_EVENTSUB_SECRET.toString()));
			outwriter.append(json.toString());
			outwriter.flush();
			int code = http.getResponseCode();
			http.disconnect();
			switch (code) {
			case 202:
				return SubscriptionResponse.REQUESTING;
			case 409:
				return SubscriptionResponse.DUPLICATED;
			case 403:
				return SubscriptionResponse.REJECTED;
			default:
				System.out.println(code);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SubscriptionResponse.NONE;
	}

	public void removeSubscriptions() throws Exception {
		Information information;
		while (!(information = getInformation()).getSubscriptions().isEmpty())
			for (List<Entry<Event, UUID>> map : information.getSubscriptions().values())
				for (Entry<Event, UUID> entry : map)
					removeSubscription(entry.getValue());
		for (Streamer streamer : CakeTwitchAPI.getStreamers().getStreamers())
			for (Event event : Event.values()) {
				SubscriptionResponse response = streamer.getEventSub().getResponse(event);
				if (response == SubscriptionResponse.ACCEPTED || response == SubscriptionResponse.DUPLICATED)
					streamer.getEventSub().setResponse(event, SubscriptionResponse.REMOVED);
			}
	}

	private void removeSubscription(UUID uuid) throws Exception {
		URL urlobject = new URL("https://api.twitch.tv/helix/eventsub/subscriptions?id=" + uuid);
		HttpURLConnection http = (HttpURLConnection) urlobject.openConnection();
		HashMap<String, String> map = new HashMap<>();
		map.put("Client-Id", SettingString.TWITCH_API_CLIENTID.toString());
		map.put("Authorization", "Bearer " + CakeTwitchAPI.getWebQuery().getDefaultToken());
		map.put("Content-Type", "application/json");
		for (Entry<String, String> entry : map.entrySet())
			http.setRequestProperty(entry.getKey(), entry.getValue());
		http.setRequestMethod("DELETE");
		http.setDoInput(true);
		BufferedReader inreader = null;
		StringBuilder sb = null;
		String input = null;
		try {
			inreader = new BufferedReader(new InputStreamReader(http.getInputStream()));
			sb = new StringBuilder();
			input = null;
			try {
				while ((input = inreader.readLine()) != null)
					sb.append(input);
			} catch (SocketTimeoutException socketTimeoutException) {
			}
		} catch (Exception exception) {
		}
		inreader.close();
		http.disconnect();
	}

	public static abstract class Method {
		public void run() {
		}

		public String getString() {
			return null;
		}
	}

	public static class Information {
		private HashMap<String, List<Entry<Event, UUID>>> subscriptions;

		private int total_cost;

		private int max_total_cost;

		public Information(HashMap<String, List<Entry<Event, UUID>>> subscriptions, int total_cost,
                           int max_total_cost) {
			this.subscriptions = subscriptions;
			this.total_cost = total_cost;
			this.max_total_cost = max_total_cost;
		}

		public HashMap<String, List<Entry<Event, UUID>>> getSubscriptions() {
			return this.subscriptions;
		}

		public int getTotalCost() {
			return this.total_cost;
		}

		public int getMaxTotalCost() {
			return this.max_total_cost;
		}
	}

	public enum Event {
		CHANNEL_FOLLOW("channel.follow", "Follow"),
		CHANNEL_CHANNELPOINTSCUSTOMREWARDREDEMPTION_ADD("channel.channel_points_custom_reward_redemption.add", "Channel Points Reward Redemption"),
		/*
		 * CHANNEL_HYPETRAIN_BEGIN("channel.hype_train.begin"),
		 * CHANNEL_HYPETRAIN_PROGRESS("channel.hype_train.progress"),
		 * CHANNEL_HYPETRAIN_END("channel.hype_train.end"),
		 */
		STREAM_ONLINE("stream.online", "Stream Online"), STREAM_OFFLINE("stream.offline", "Stream Offline");

		private String eventname;

		private String eventpublicname;

		public String toString() {
			return this.eventpublicname;
		}

		Event(String eventname, String eventpublicname) {
			this.eventname = eventname;
			this.eventpublicname = eventpublicname;
		}

		public String getEventName() {
			return this.eventname;
		}

		public static Event fromEventName(String eventname) {
			eventname = eventname.toLowerCase();
			byte b;
			int i;
			Event[] arrayOfEvent;
			for (i = (arrayOfEvent = values()).length, b = 0; b < i;) {
				Event event = arrayOfEvent[b];
				if (event.getEventName().equals(eventname))
					return event;
				b++;
			}
			return null;
		}
	}

	public enum SubscriptionResponse {

		ACCEPTED, REJECTED, DUPLICATED, NONE, REQUESTING, REMOVED;

		@Override
		public String toString() {
			return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
		}

	}
}
