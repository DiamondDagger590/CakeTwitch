package relampagorojo93.caketwitch.bukkit.events.pluginevents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import relampagorojo93.caketwitch.enums.EventType;
import relampagorojo93.caketwitch.modules.streamerspckg.objects.data.Streamer;

import java.util.Map;
import java.util.UUID;

public abstract class CakeTwitchEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    protected EventType eventtype = EventType.NONDEFINED;
    private UUID id;
    private boolean cancelled;
    private Streamer streamer;
    private Map<String, String> tags;
    private String user;

    public CakeTwitchEvent(UUID id, Streamer streamer, String user, Map<String, String> tags) {
        this.id = id;
        this.streamer = streamer;
        this.tags = tags;
        this.user = user;
    }

    public EventType getEventType() {
        return eventtype;
    }

    public UUID getId() {
        return id;
    }

    public Streamer getStreamer() {
        return streamer;
    }

    public String getUser() {
        return user;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public String processCommand(String command, String ign, UUID uuid) {
        return "";
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CakeTwitchEvent && ((CakeTwitchEvent) obj).getId().compareTo(getId()) == 0;
    }
}
