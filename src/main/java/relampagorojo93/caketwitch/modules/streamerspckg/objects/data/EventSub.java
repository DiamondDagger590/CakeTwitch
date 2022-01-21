package relampagorojo93.caketwitch.modules.streamerspckg.objects.data;

import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory.Section;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule.Event;
import relampagorojo93.caketwitch.modules.eventsubpckg.EventSubModule.SubscriptionResponse;

import java.util.HashMap;

public class EventSub {

    private boolean loaded = false;
    private Streamer streamer;
    private HashMap<Event, SubscriptionResponse> responses = new HashMap<>();
    private HashMap<Event, Long> lastrequest = new HashMap<>();
    private HashMap<Event, Long> requiredtime = new HashMap<>();

    public EventSub(Streamer streamer) {
        this.streamer = streamer;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded() {
        this.loaded = true;
    }

    public void requestSubscription(Event event) {
		if (CakeTwitchAPI.getEventSub() == null) {
			return;
		}
        lastrequest.put(event, System.currentTimeMillis());
        setResponse(event, CakeTwitchAPI.getEventSub().sendSubscription(event, streamer));
    }

    public long getLastRequest(Event event) {
        return lastrequest.getOrDefault(event, 0L);
    }

    public long getRequiredTime(Event event) {
        return requiredtime.getOrDefault(event, 0L);
    }

    public SubscriptionResponse getResponse(Event event) {
        return responses.getOrDefault(event, SubscriptionResponse.NONE);
    }

    public void setResponse(Event event, SubscriptionResponse response) {
        this.lastrequest.putIfAbsent(event, 0L);
        this.responses.put(event, response);
		if (response != SubscriptionResponse.REMOVED) {
			this.requiredtime.put(event, System.currentTimeMillis() - this.lastrequest.get(event));
		}
		else {
			this.requiredtime.put(event, 0L);
		}
        StreamerInventory.updateInventoryEveryone(streamer, Section.EVENTSUB);
    }
}
