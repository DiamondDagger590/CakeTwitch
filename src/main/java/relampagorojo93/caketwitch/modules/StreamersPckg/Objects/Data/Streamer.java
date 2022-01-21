package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import relampagorojo93.LibsCollection.JSONLib.JSONElement;
import relampagorojo93.LibsCollection.JSONLib.JSONObject;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.Inventories.Twitch.StreamerInventory.Section;
import relampagorojo93.caketwitch.modules.WebQueryPckg.Objects.Token;

import java.util.UUID;

public class Streamer {

	// ---------------------------------------------------------------//
	// Data initializers
	// ---------------------------------------------------------------//

	public Streamer(UUID uuid) {
		this.uuid = uuid;
		TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> Streamer.this.playerhead = ItemStacksUtils
				.getPlayerHead(Bukkit.getOfflinePlayer(Streamer.this.uuid)));
	}

	public Streamer(UUID uuid, String channelLogin, String channelId) {
		this(uuid); setData(channelLogin, channelId, null);
	}

	public Streamer(UUID uuid, String channelLogin, String channelId, Token token) {
		this(uuid); setData(channelLogin, channelId, token);
	}
	
	public void setData(String channelLogin, String channelId, Token token) {
		this.channelLogin = channelLogin;
		this.channelId = channelId;
		this.token = token;
		if (this.channelLogin != null && this.channelId == null)
			setChannelId();
	}

	// ---------------------------------------------------------------//
	// UUID
	// ---------------------------------------------------------------//

	private UUID uuid = null;

	public UUID getUniqueID() {
		return uuid;
	}

	// ---------------------------------------------------------------//
	// Streaming status
	// ---------------------------------------------------------------//
	
	private boolean isStreaming = false;

	public void setIsStreaming(boolean isStreaming) {
		if (this.settings.isAuthorizedStreamer()) {
			this.isStreaming = isStreaming;
			if (isStreaming) {
				CakeTwitchAPI.getCommandsQueue().processCommands(uuid);
				this.bot.startBot();
			} else {
				CakeTwitchAPI.getCommandsQueue().stopCommands(uuid);
				this.bot.stopBot();
			}
		}
	}

	public boolean isStreaming() {
		return isStreaming;
	}

	// ---------------------------------------------------------------//
	// Player head
	// ---------------------------------------------------------------//

	private ItemStack playerhead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");

	public ItemStack getPlayerHead() {
		return playerhead.clone();
	}

	// ---------------------------------------------------------------//
	// Objects
	// ---------------------------------------------------------------//

	private Bot bot = new Bot(this);
	private EventSub eventsub = new EventSub(this);
	private Settings settings = new Settings(this);
	private Stats stats = new Stats(this);
	private ViewersController viewerscontroller = new ViewersController();
	private Statistics statistics = new Statistics();

	public Bot getBot() {
		return this.bot;
	}

	public EventSub getEventSub() {
		return this.eventsub;
	}

	public Settings getSettings() {
		return this.settings;
	}

	public Stats getStats() {
		return this.stats;
	}

	public Statistics getStatistics() {
		return this.statistics;
	}

	public ViewersController getViewersController() {
		return this.viewerscontroller;
	}

	// ---------------------------------------------------------------//
	// Register channel
	// ---------------------------------------------------------------//
	
	private String registerChannel;
	
	public void setRegisterChannel(String registerChannel) {
		this.registerChannel = registerChannel != null ? registerChannel.toLowerCase() : null;
		if (this.registerChannel != null && !this.registerChannel.equals(""))
			this.bot.startBot();
		else
			this.bot.stopBot();
	}
	
	public String getRegisterChannel() {
		return this.registerChannel;
	}

	// ---------------------------------------------------------------//
	// Channel Login & ID
	// ---------------------------------------------------------------//

	private String channelLogin, channelId;
	
	public boolean setChannelLogin(String channelLogin) {
		return this.setChannelLogin(channelLogin, true);
	}
	
	public boolean setChannelLogin(String channelLogin, boolean updateId) {
		if (channelLogin == null || channelLogin.isEmpty()) {
			this.channelLogin = null;
			this.channelId = null;
			this.token = null;
			this.settings.importSettings(new Settings(this));
			CakeTwitchAPI.getSQL().unregisterStreamer(this.uuid);
			if (this.isStreaming)
				this.setIsStreaming(false);
		} else {
			this.channelLogin = channelLogin.toLowerCase();
			if (!CakeTwitchAPI.getSQL().registerStreamer(this.uuid, this.channelLogin)) {
				setChannelLogin(null); return false;
			}
			else if (updateId)
				setChannelId();
//			if (!this.isStreaming)
//				this.setIsStreaming(SettingList.TWITCH_STARTSTREAMINGONLOAD.toList().contains(this.channelLogin));
		}
		StreamerInventory.updateInventoryEveryone(this, (Section) null);
		return true;
	}
	
	public void setChannelId() {
		if (this.channelLogin != null && CakeTwitchAPI.getWebQuery() != null)
			this.setChannelId(CakeTwitchAPI.getWebQuery().getChannelId(this.channelLogin)); 
	}
	
	public void setChannelId(String channelId) {
		this.channelId = channelId;
		if (this.channelId != null)
			CakeTwitchAPI.getSQL().setChannelId(this.uuid, this.channelId);
	}
	
	public String getChannelLogin() {
		return channelLogin;
	}
	
	public String getChannelId() {
		return channelId;
	}
	
	// ---------------------------------------------------------------//
	// Token
	// ---------------------------------------------------------------//
	
	private Token token;

	public boolean setToken(Token token) {
		if (token != null) {
			this.token = token;
			if (CakeTwitchAPI.getWebQuery().isValid(this.token)) {
				JSONElement json = CakeTwitchAPI.getWebQuery().getChannelInfo(this.token);
				if (json != null) {
					try {
						JSONObject data = json.asObject().getArray("data").getObject(0);
						if (this.setChannelLogin(data.getData("login").getAsString())) {
							this.setChannelId(data.getData("id").getAsString());
							CakeTwitchAPI.getSQL().setToken(uuid, this.token); return true;
						}
					} catch (Exception e ) {}
				}
			}
		}
		return (this.token = token) == null ? true : false;
	}

	public Token getToken() {
		if (this.token != null && !CakeTwitchAPI.getWebQuery().isValid(this.token))
			this.setToken(CakeTwitchAPI.getWebQuery().renewToken(this.token, false));
		return this.token;
	}
	
	// ---------------------------------------------------------------//
	// Methods
	// ---------------------------------------------------------------//
	
	public void reloadData() {
		boolean isStreaming = this.isStreaming;
		if (isStreaming)
			this.setIsStreaming(false);
		Streamer newdata = CakeTwitchAPI.getSQL().getStreamer(uuid);
		this.settings.importSettings(newdata.getSettings());
		if (newdata.isRegistered()) {
			this.channelLogin = newdata.channelLogin;
			this.setData(newdata.channelLogin, newdata.channelId, newdata.token);
			this.setIsStreaming(isStreaming);
		} else if (this.isRegistered())
			this.setChannelLogin(null);
	}
	
	public boolean isRegistered() {
		return this.channelLogin != null;
	}

}