package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ViewersController {
	private List<UUID> viewers = new ArrayList<>();

	public void addViewer(UUID uuid) {
		if (!isViewing(uuid))
			this.viewers.add(uuid);
	}

	public void removeViewer(UUID uuid) {
		if (isViewing(uuid))
			this.viewers.remove(uuid);
	}

	public boolean isViewing(UUID uuid) {
		return this.viewers.contains(uuid);
	}

	public List<UUID> getViewers() {
		return new ArrayList<>(this.viewers);
	}
}
