package relampagorojo93.caketwitch.ezinvopener.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import relampagorojo93.caketwitch.ezinvopener.api.objects.EzInventory;

public class PlayerOpenEzInventory extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private Player player;
    private EzInventory ezinventory;

    public PlayerOpenEzInventory(Player player, EzInventory ezinventory) {
        this.player = player;
        this.ezinventory = ezinventory;
    }

    public Player getPlayer() {
        return player;
    }

    public EzInventory getEzInventory() {
        return ezinventory;
    }

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
