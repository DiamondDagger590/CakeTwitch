package relampagorojo93.caketwitch.modules.ConfigPckg.Configurations.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.api.Hooks.PlaceholderAPI;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchBitsDonationEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchChannelRaidEvent;
import relampagorojo93.caketwitch.bukkit.Events.PluginEvents.CakeTwitchEvent;
import relampagorojo93.caketwitch.enums.CommandStatus;
import relampagorojo93.caketwitch.enums.Executor;
import relampagorojo93.caketwitch.enums.PAPIPlayer;
import relampagorojo93.caketwitch.enums.RepeatMode;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Command {
	private Executor executor = Executor.CONSOLE;

	private PAPIPlayer papiplayer = PAPIPlayer.EXECUTOR;

	private RepeatMode repeatmode = RepeatMode.INTEGER;

	private boolean op = false;

	private boolean mustberegistered = false;

	private boolean mustbeconnected = false;

	private int repeat = 1;

	private int delayin = 0;

	private int delayout = 0;

	private String player = "";

	private String originalcommand = "";

	private String command = "";

	private List<String> ignoredworlds = new ArrayList<>();

	private List<String> forcedworlds = new ArrayList<>();

	protected boolean queue = false;

	public Command(String command) {
		this.originalcommand = command;
		int start = command.indexOf('['), end = command.indexOf(']');
		if (start == 0 && end != -1) {
			byte b;
			int i;
			String[] arrayOfString;
			for (i = (arrayOfString = command.substring(start + 1, end).split(",")).length, b = 0; b < i;) {
				String setting = arrayOfString[b];
				String[] data = setting.split("=");
				if (data[0].equalsIgnoreCase("executor")) {
					if (data[1].startsWith("@")) {
						if (data[1].equalsIgnoreCase("@all")) {
							this.executor = Executor.ALL;
						} else if (data[1].equalsIgnoreCase("@console")) {
							this.executor = Executor.CONSOLE;
						} else if (data[1].equalsIgnoreCase("@streamer")) {
							this.executor = Executor.STREAMER;
						} else if (data[1].equalsIgnoreCase("@viewers")) {
							this.executor = Executor.VIEWERS;
						}
					} else {
						this.executor = Executor.PLAYER;
						this.player = data[1];
					}
				} else if (data[0].equalsIgnoreCase("repeat")) {
					if (data[1].startsWith("@")) {
						if (data[1].equalsIgnoreCase("@bits")) {
							this.repeatmode = RepeatMode.BITS;
						} else if (data[1].equalsIgnoreCase("@raid")) {
							this.repeatmode = RepeatMode.RAID_VIEWERS;
						}
					} else {
						this.repeatmode = RepeatMode.INTEGER;
						this.repeat = Integer.parseInt(data[1]);
					}
				} else if (data[0].equalsIgnoreCase("op")) {
					this.op = true;
				} else if (data[0].equalsIgnoreCase("delay-in")) {
					try {
						this.delayin = Integer.parseInt(data[1]);
					} catch (NumberFormatException numberFormatException) {
					}
				} else if (data[0].equalsIgnoreCase("delay-out")) {
					try {
						this.delayout = Integer.parseInt(data[1]);
					} catch (NumberFormatException numberFormatException) {
					}
				} else if (data[0].equalsIgnoreCase("queue")) {
					this.queue = true;
				} else if (data[0].equalsIgnoreCase("must-be-registered")) {
					this.mustberegistered = true;
				} else if (data[0].equalsIgnoreCase("must-be-connected")) {
					this.mustbeconnected = true;
				} else if (data[0].equalsIgnoreCase("papi-player")) {
					if (data[1].equalsIgnoreCase("@user")) {
						this.papiplayer = PAPIPlayer.USER;
					} else if (data[1].equalsIgnoreCase("@streamer")) {
						this.papiplayer = PAPIPlayer.STREAMER;
					} else if (data[1].equalsIgnoreCase("@executor")) {
						this.papiplayer = PAPIPlayer.EXECUTOR;
					}
				} else if (data[0].equalsIgnoreCase("ignored-worlds")) {
					byte b1;
					int j;
					String[] arrayOfString1;
					for (j = (arrayOfString1 = data[1].split(";")).length, b1 = 0; b1 < j;) {
						String world = arrayOfString1[b1];
						this.ignoredworlds.add(world);
						b1++;
					}
				} else if (data[0].equalsIgnoreCase("forced-worlds")) {
					byte b1;
					int j;
					String[] arrayOfString1;
					for (j = (arrayOfString1 = data[1].split(";")).length, b1 = 0; b1 < j;) {
						String world = arrayOfString1[b1];
						this.ignoredworlds.add(world);
						b1++;
					}
				}
				b++;
			}
			command = command.substring(end + 1);
		}
		while (command.indexOf(' ') == 0)
			command = command.substring(1);
		this.command = command;
	}

	public Executor getExecutor() {
		return this.executor;
	}

	public PAPIPlayer getPAPIPlayer() {
		return this.papiplayer;
	}

	public RepeatMode getRepeatMode() {
		return this.repeatmode;
	}

	public boolean asOp() {
		return this.op;
	}

	public boolean mustBeConnected() {
		return this.mustbeconnected;
	}

	public boolean mustBeRegistered() {
		return this.mustberegistered;
	}

	public int repeat() {
		return this.repeat;
	}

	public String getPlayer() {
		return this.player;
	}

	public String getOriginalCommand() {
		return this.originalcommand;
	}

	public String getCommand() {
		return this.command;
	}

	public boolean queueMode() {
		return this.queue;
	}

	public int getDelayIn() {
		return this.delayin;
	}

	public int getDelayOut() {
		return this.delayout;
	}

	public List<String> getIgnoredWorlds() {
		return this.ignoredworlds;
	}

	public List<String> getForcedWorlds() {
		return this.forcedworlds;
	}

	public static CommandStatus checkConditions(Command command, CakeTwitchEvent event) {
		Streamer user = CakeTwitchAPI.getStreamers().getStreamerByLogin(event.getUser().toLowerCase());
		OfflinePlayer opl = null;
		if (user != null)
			opl = Bukkit.getOfflinePlayer(user.getUniqueID());
		if (opl == null && command.mustBeRegistered())
			return CommandStatus.NOTREGISTERED;
		if (command.mustBeConnected() && opl.isOnline())
			return CommandStatus.NOTCONNECTED;
		if (!command.getIgnoredWorlds().isEmpty() || !command.getForcedWorlds().isEmpty()) {
			Player pl = opl != null ? opl.getPlayer() : null;
			if (pl != null)
				if (!command.getForcedWorlds().isEmpty()) {
					if (!command.getForcedWorlds().contains(pl.getWorld().getName()))
						return CommandStatus.INVALIDWORLD;
				} else if (!command.getIgnoredWorlds().isEmpty()
						&& command.getIgnoredWorlds().contains(pl.getWorld().getName())) {
					return CommandStatus.INVALIDWORLD;
				}
		}
		if (command.queueMode())
			return CommandStatus.QUEUE;
		return CommandStatus.EXECUTABLE;
	}

	public static void execute(Command command, CakeTwitchEvent event) {
		Streamer user = CakeTwitchAPI.getStreamers().getStreamerByLogin(event.getUser().toLowerCase());
		OfflinePlayer opl = null;
		if (user != null)
			opl = Bukkit.getOfflinePlayer(user.getUniqueID());
		Executor executor = command.getExecutor();
		List<CommandSender> senders = new ArrayList<>();
		if (executor == Executor.ALL) {
			for (Player pl : Bukkit.getOnlinePlayers())
				senders.add(pl);
		} else if (executor == Executor.CONSOLE) {
			senders.add(Bukkit.getConsoleSender());
		} else if (executor == Executor.PLAYER && !command.getPlayer().isEmpty()) {
			Player pl = Bukkit.getPlayer(command.getPlayer());
			if (pl != null)
				senders.add(pl);
		} else if (executor == Executor.STREAMER) {
			Player pl = Bukkit.getPlayer(event.getStreamer().getUniqueID());
			if (pl != null)
				senders.add(pl);
		} else if (executor == Executor.VIEWERS) {
			Player pl = Bukkit.getPlayer(event.getStreamer().getUniqueID());
			if (pl != null)
				senders.add(pl);
			for (UUID viewer : event.getStreamer().getViewersController().getViewers()) {
				Player p = Bukkit.getPlayer(viewer);
				if (p != null)
					senders.add(p);
			}
		}
		String cmd = event.processCommand(command.getCommand(), opl == null ? "?" : opl.getName(), opl == null ? null : opl.getUniqueId());
		if (PlaceholderAPI.isHooked()) {
			OfflinePlayer streamer;
			switch (command.getPAPIPlayer()) {
			case STREAMER:
				streamer = Bukkit.getOfflinePlayer(event.getStreamer().getUniqueID());
				if (streamer != null)
					cmd = PlaceholderAPI.processPlaceholders(streamer, cmd);
				break;
			case USER:
				if (opl != null)
					cmd = PlaceholderAPI.processPlaceholders(opl, cmd);
				break;
			default:
				break;
			}
		}
		RepeatMode repeatmode = command.getRepeatMode();
		int repeat = (repeatmode == RepeatMode.INTEGER) ? command.repeat()
				: ((repeatmode == RepeatMode.BITS && event instanceof CakeTwitchBitsDonationEvent)
						? ((CakeTwitchBitsDonationEvent) event).getBits()
						: ((repeatmode == RepeatMode.RAID_VIEWERS && event instanceof CakeTwitchChannelRaidEvent)
								? ((CakeTwitchChannelRaidEvent) event).getViewers()
								: 1));
		execute(command, cmd, repeat, senders);
	}

	private static void execute(Command command, String pcommand, int repeat, List<CommandSender> senders) {
		try {
			if (command.getDelayIn() != 0)
				Thread.sleep((command.getDelayIn() * 1000));
			TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
				List<CommandSender> oppedsenders = new ArrayList<>();
				for (CommandSender sender:senders)
					if (command.asOp() && !sender.isOp()) {
						sender.setOp(true); oppedsenders.add(sender);
					}
				for (int i = 0; i < repeat; i++)
					for (CommandSender sender : senders) {
						String fcommand = pcommand.replace("%executor%", sender.getName());
						if (command.getPAPIPlayer() == PAPIPlayer.EXECUTOR && PlaceholderAPI.isHooked()
								&& sender instanceof OfflinePlayer)
							fcommand = PlaceholderAPI.processPlaceholders((OfflinePlayer) sender, fcommand);
						Bukkit.dispatchCommand(sender, fcommand);
					}
				for (CommandSender opped:oppedsenders)
					opped.setOp(false);
			});
			if (command.getDelayOut() != 0)
				Thread.sleep((command.getDelayOut() * 1000));
		} catch (InterruptedException exception) {
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
