package relampagorojo93.caketwitch.modules.DropsPckg.Objects;

import java.util.List;

public class DropsCommandList {
	private int chance = 0;
	private List<String> commands;

	public DropsCommandList(List<String> commands, int chance) {
		this.commands = commands;
		this.chance = chance;
	}

	public List<String> getCommands() {
		return commands;
	}
	
	public int getChance() {
		return chance;
	}
}
