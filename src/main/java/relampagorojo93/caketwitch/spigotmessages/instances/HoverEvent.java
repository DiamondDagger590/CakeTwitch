package relampagorojo93.caketwitch.spigotmessages.instances;

public class HoverEvent {

    private Action action;
    private String value;

    public HoverEvent(Action action, String value) {
        this.action = action;
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public String getValue() {
        return value;
    }

    public enum Action {
        SHOW_TEXT,
        SHOW_ACHIEVEMENT,
        SHOW_ITEM,
        SHOW_ENTITY
    }

}
