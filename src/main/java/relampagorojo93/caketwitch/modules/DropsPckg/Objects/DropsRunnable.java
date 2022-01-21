package relampagorojo93.caketwitch.modules.DropsPckg.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data.Streamer;

import java.util.List;

public class DropsRunnable implements Thread.Runnable {

	private String streamer;

	public DropsRunnable(String streamer) {
		this.streamer = streamer;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			while (true) {
				Thread.sleep(CakeTwitchAPI.getDrops().getDelay() * 1000);
				List<DropsCommandList> droplist = CakeTwitchAPI.getDrops().getCommandList(streamer);
				List<String> viewers = CakeTwitchAPI.getBasicWebQuery().getTwitchViewers(streamer);
				int maxticket = 0;
				for (DropsCommandList cmdlist : droplist)
					maxticket += cmdlist.getChance();
				for (String viewer:viewers) {
					Streamer user = CakeTwitchAPI.getStreamers().getStreamerByLogin(viewer);
					if (user == null)
						continue;
					Player pl = Bukkit.getPlayer(user.getUniqueID());
					if (pl == null)
						continue;
					int ticket = (int) (Math.random() * maxticket);
					int cur = 0;
					for (DropsCommandList cmdlist : droplist)
						if ((cur += cmdlist.getChance()) > ticket) {
							for (String command : cmdlist.getCommands())
								TasksUtils.execute(CakeTwitchAPI.getPlugin(),
										() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
												command.replace("%user%", viewer)
														.replace("%user-ign%", pl.getName())
														.replace("%user-uuid%", pl.getUniqueId().toString())));
							break;
						}
					
				}
			}
		} catch (InterruptedException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void output(Object output) {
	}

}
