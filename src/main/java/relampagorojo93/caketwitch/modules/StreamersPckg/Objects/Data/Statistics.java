package relampagorojo93.caketwitch.modules.StreamersPckg.Objects.Data;

import relampagorojo93.LibsCollection.SpigotDebug.DebugController;

public class Statistics {
	
	private DebugController followers = new DebugController(256);
	private DebugController subscriptions = new DebugController(256);
	private DebugController events = new DebugController(256);
	
	public DebugController getFollowers() {
		return followers;
	}
	
	public DebugController getSubscriptions() {
		return subscriptions;
	}
	
	public DebugController getEvents() {
		return events;
	}

	public void clear() {
		followers.clearDebugData();
		subscriptions.clearDebugData();
		events.clearDebugData();
	}
}
