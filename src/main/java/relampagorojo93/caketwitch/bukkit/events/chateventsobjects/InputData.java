package relampagorojo93.caketwitch.bukkit.events.chateventsobjects;

import org.bukkit.entity.Player;

public abstract class InputData {
    private final Player player;

    private final Task task;

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

    public interface Task {
        void method(InputData param1InputData, String param1String);
    }
}
