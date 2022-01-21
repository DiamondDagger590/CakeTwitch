package relampagorojo93.caketwitch.bukkit.Events.ChatEventsObjects;

import org.bukkit.entity.Player;

public abstract class InputData {
	private Player player;

	private Task task;

	public InputData(Player player, Task task) {
		this.player = player;
		this.task = task;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void input(String result) {
		this.task.method(this, result);
	}

	public void remove() {
	}

	public static interface Task {
		void method(InputData param1InputData, String param1String);
	}
}
