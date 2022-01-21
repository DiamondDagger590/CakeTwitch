package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Pages;

import relampagorojo93.LibsCollection.HTTPServer.HTTPServer.Page;
import relampagorojo93.LibsCollection.HTTPServer.HTTPServer.ServerResponse;
import relampagorojo93.LibsCollection.Utils.Shared.WebQueries.WebQuery.ClientResponse;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory.Section;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;
import relampagorojo93.caketwitch.modules.WebQueryPckg.Objects.Token;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.UUID;

public class AuthorizationPage implements Page {

	private HashMap<String, UUID> requests = new HashMap<>();

	@Override
	public ServerResponse getResponse(ClientResponse response) {
		switch (response.getResponseVerb()) {
		case GET:
			int code = 403;
			String html = "<h1>You can't use this like this!</h1>";
			String suuid = response.getParameter("uuid");
			if (suuid != null) {
				boolean rewards = "1".equals(response.getParameter("rewards"));
				boolean hype = "1".equals(response.getParameter("hype"));
				try {
					UUID uuid = UUID.fromString(suuid);
					String hmac = generateHmac256(uuid.toString(),
							SettingString.TWITCH_EVENTSUB_SECRET.toString().getBytes());
					requests.put(hmac, uuid);
					HashMap<String, String> headers = new HashMap<>();
					headers.put("Location",
							"https://id.twitch.tv/oauth2/authorize?force_verify=true&client_id="
									+ SettingString.TWITCH_API_CLIENTID.toString() + "&redirect_uri="
									+ AuthorizationPage.getURL() + "&state=" + hmac
									+ "&response_type=code" + (hype && !rewards ?
											"&scope=channel:read:hype_train" :
											!hype && rewards ?
											"&scope=channel:read:redemptions" :
											hype && rewards ?
											"&scope=channel:read:redemptions%20channel:read:hype_train" :
											""));
					return new ServerResponse("", ServerResponse.ResponseCode.TEMPORARY_REDIRECT, headers);
				} catch (Exception e) {
					return new ServerResponse("", ServerResponse.ResponseCode.BAD_REQUEST);
				}
			} else {
				String authcode = response.getParameter("code");
				if (authcode != null) {
					String state = response.getParameter("state");
					if (state != null) {
						code = 403;
						html = "<h1>The granted code is not related to any of the active authorization requests!</h1>";
						UUID uuid = requests.get(state);
						if (uuid != null) {
							this.requests.remove(state);
							Streamer streamer = CakeTwitchAPI.getStreamers().getStreamerOrNull(uuid);
							if (streamer != null) {
								streamer.setToken(CakeTwitchAPI.getWebQuery().renewToken(
												new Token(null, null, SettingString.TWITCH_API_CLIENTID.toString(),
														SettingString.TWITCH_API_CLIENTSECRET.toString(), authcode),
												true));
								StreamerInventory.updateInventoryEveryone(streamer, Section.HOME);
								code = 200;
								html = "<h1>Thank you for granting access to our bot!</h1>";
							} else {
								code = 403;
								html = "<h1>Authorization request found, but streamer data wasn't found!</h1>";
							}
						}
					}
				}
				return new ServerResponse(html, ServerResponse.ResponseCode.getByCode(code));
			}
		default:
			break;
		}
		return new ServerResponse("", ServerResponse.ResponseCode.BAD_REQUEST);
	}

	private String generateHmac256(String message, byte[] key) throws Exception {
		byte[] bytes = hmac("HmacSHA256", key, message.getBytes());
		return bytesToHex(bytes);
	}

	private byte[] hmac(String algorithm, byte[] key, byte[] message) throws Exception {
		Mac mac = Mac.getInstance(algorithm);
		mac.init(new SecretKeySpec(key, algorithm));
		return mac.doFinal(message);
	}

	private String bytesToHex(byte[] bytes) {
		char[] hexArray = "0123456789abcdef".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0xF];
		}
		return new String(hexChars);
	}

	public static String getURL() {
		return SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString()
				+ (SettingString.TWITCH_EVENTSUB_CALLBACKURL.toString().endsWith("/") ? "" : "/") + "authorization";
	}

}
