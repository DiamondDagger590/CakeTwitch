package relampagorojo93.caketwitch.modules.WebQueryPckg.Objects;

public class Token {
	private String accessToken = null, refreshToken = null, clientId = null, clientSecret = null, code = null;
	public Token(String accessToken, String refreshToken, String clientId, String clientSecret) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	public Token(String accessToken, String refreshToken, String clientId, String clientSecret, String code) {
		this(accessToken, refreshToken, clientId, clientSecret);
		this.code = code;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public String getClientId() {
		return clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public String getCode() {
		return code;
	}
}
