package relampagorojo93.caketwitch.modules.WebQueryPckg;

import relampagorojo93.LibsCollection.JSONLib.JSONData;
import relampagorojo93.LibsCollection.JSONLib.JSONElement;
import relampagorojo93.LibsCollection.JSONLib.JSONObject;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.Utils.Shared.WebQueries.WebMethod;
import relampagorojo93.LibsCollection.Utils.Shared.WebQueries.WebQuery;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.FilePckg.Messages.MessageString;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingInt;
import relampagorojo93.caketwitch.modules.FilePckg.Settings.SettingString;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Pages.AuthorizationPage;
import relampagorojo93.caketwitch.modules.WebQueryPckg.Objects.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class WebQueryModule extends PluginModule {

	public boolean load() {
		if (SettingInt.TWITCH_UPDATER_UPDATETIMERINMINUTES.toInt() < 1
				|| SettingString.TWITCH_API_CLIENTID.toString().isEmpty()
				|| SettingString.TWITCH_API_CLIENTSECRET.toString().isEmpty()) {
			System.out.println(MessageString.applyPrefix("(TwitchWebQuery) Missing Twitch API configuration!"));
			return false;
		}
		String accessToken = "";
		String refreshToken = "";
		try {
			BufferedReader fr = new BufferedReader(new FileReader(CakeTwitchAPI.getFile().ACCESSTOKEN_FILE));
			if (fr.ready())
				accessToken = fr.readLine();
			if (fr.ready())
				refreshToken = fr.readLine();
			fr.close();
		} catch (Exception exception) {
		}
		if (accessToken != null && !accessToken.isEmpty())
			this.token = new Token(accessToken, refreshToken != null && !refreshToken.isEmpty() ? refreshToken : null,
					SettingString.TWITCH_API_CLIENTID.toString(), SettingString.TWITCH_API_CLIENTSECRET.toString());
		else
			this.token = new Token(null, null, SettingString.TWITCH_API_CLIENTID.toString(),
					SettingString.TWITCH_API_CLIENTSECRET.toString());
		if (this.token == null || this.token.getAccessToken() == null || !isValid(this.token)) {
			renewDefaultToken(true);
			if (this.token == null || this.token.getAccessToken() == null || !isValid(this.token))
				return false;
		}
		this.headers.clear();
		this.headers.put("Client-Id", SettingString.TWITCH_API_CLIENTID.toString());
		this.headers.put("Authorization", "Bearer " + this.token.getAccessToken());
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

	private Token token = null;
	private HashMap<String, String> headers = new HashMap<>();

	public Token getDefaultToken() {
		return this.token != null ? this.token
				: new Token(null, null, SettingString.TWITCH_API_CLIENTID.toString(),
						SettingString.TWITCH_API_CLIENTSECRET.toString());
	}

	public HashMap<String, String> getHeaders() {
		return this.headers;
	}
	
	public String getChannelId(String channel) {
		HashMap<String, String> map = new HashMap<>();
		map.put("Client-Id", this.token.getClientId());
		map.put("Authorization", "Bearer " + this.token.getAccessToken());
		try {
			return WebQuery.queryToJSON("https://api.twitch.tv/helix/users?login=" + channel, WebMethod.GET, map).asObject().getArray("data").getObject(0).getData("id").getAsString();
		} catch (Exception e) {}
		return null;
	}

	public boolean isValid(Token token) {
		if (token == null || token.getAccessToken() == null)
			return false;
		HashMap<String, String> map = new HashMap<>();
		map.put("Authorization", "Bearer " + token.getAccessToken());
		return (WebQuery.queryToResponse("https://id.twitch.tv/oauth2/validate", WebMethod.GET, map) < 400);
	}

	public JSONElement getChannelInfo(Token token) {
		if (token == null || token.getAccessToken() == null)
			return null;
		HashMap<String, String> map = new HashMap<>();
		map.put("Client-Id", token.getClientId());
		map.put("Authorization", "Bearer " + token.getAccessToken());
		try {
			return WebQuery.queryToJSON("https://api.twitch.tv/helix/users", WebMethod.GET, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	public void renewDefaultToken(boolean force) {
		this.token = renewToken(this.token, force);
		try {
			FileWriter fw = new FileWriter(CakeTwitchAPI.getFile().ACCESSTOKEN_FILE);
			if (this.token.getAccessToken() != null) {
				fw.write(this.token.getAccessToken());
				if (this.token.getRefreshToken() != null)
					fw.write(System.lineSeparator() + this.token.getRefreshToken());
			}
			fw.flush();
			fw.close();
		} catch (Exception e) {
		}
	}

	public Token renewToken(Token token, boolean force) {
		if (token == null)
			return null;
		if (!force) {
			if (isValid(token)) {
				return token;
			} else if (token.getRefreshToken() != null) {
				try {
					JSONObject json = WebQuery
							.queryToJSON(
									"https://id.twitch.tv/oauth2/token?grant_type=refresh_token&refresh_token="
											+ this.token + "&client_id=" + SettingString.TWITCH_API_CLIENTID.toString()
											+ "&client_secret=" + SettingString.TWITCH_API_CLIENTSECRET.toString(),
									WebMethod.POST)
							.asObject();
					if (json != null) {
						JSONData accessToken = json.getDataOrDefault("access_token", null);
						JSONData refreshToken = json.getDataOrDefault("refresh_token", null);
						if (accessToken != null)
							return new Token(accessToken.getAsString(),
									refreshToken != null ? refreshToken.getAsString() : null, token.getClientId(),
									token.getClientSecret(), token.getCode());
					}
				} catch (Exception exception) {
				}
			} else
				return null;
		}
		try {
			JSONObject json = WebQuery.queryToJSON("https://id.twitch.tv/oauth2/token?grant_type="
					+ (token.getCode() != null ? "authorization_code" : "client_credentials") + "&client_id="
					+ token.getClientId() + "&client_secret=" + token.getClientSecret()
					+ (token.getCode() != null
							? "&code=" + token.getCode() + "&redirect_uri=" + AuthorizationPage.getURL()
							: ""),
					WebMethod.POST).asObject();
			if (json != null) {
				JSONData accessToken = json.getDataOrDefault("access_token", null);
				JSONData refreshToken = json.getDataOrDefault("refresh_token", null);
				if (accessToken != null)
					return new Token(accessToken.getAsString(),
							refreshToken != null ? refreshToken.getAsString() : null, token.getClientId(),
							token.getClientSecret(), token.getCode());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

}
