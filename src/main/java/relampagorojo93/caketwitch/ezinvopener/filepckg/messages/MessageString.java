package relampagorojo93.caketwitch.ezinvopener.filepckg.messages;

public enum MessageString {
    PREFIX("Prefix", "&6&lEzInv&e&lOpener &a&l>>&r&7"),
    RELOAD("Message.Reload", "The plugin has been reloaded successfully."),
    CANCELLED("Message.Cancelled", "Your action has been cancelled."),
    CONSOLEDENIED("Message.Console-denied", "You can't use this command in console."),
    ENTITYATTACHCANCELLED("Message.Entity-attach-cancelled", "Entity attach cancelled successfully."),
    ENTITYATTACHED("Message.Entity-attached", "Entity attached to an inventory successfully!"),
    ENTITYATTACHING("Message.Entity-attaching", "Right click the entity you wish to attach your selected inventory. Type '/ezinvopener attach' to stop attaching"),
    ENTITYUNATTACHED("Message.Entity-unattached", "Entity unattached from an inventory successfully!"),
    ONLYNUMBERS("Message.Only-numbers", "You must specify a number."),
    ONLYUUIDS("Message.Only-UUIDs", "You must specify a valid UUID."),

    COMMONGUI_LEFTARROWNAME("Common-GUI.Left-arrow-name", "&7&l<- &7Previous"),
    COMMONGUI_RIGHTARROWNAME("Common-GUI.Right-arrow-name", "&7Next &7&l->"),
    COMMONGUI_RETURNNAME("Common-GUI.Return-name", "&6Return"),

    HELP_LEFTARROWAVAILABLE("Help-command.Left-arrow-available", "&e«"),
    HELP_LEFTARROWUNAVAILABLE("Help-command.Left-arrow-unavailable", "&r«"),
    HELP_RIGHTARROWAVAILABLE("Help-command.Right-arrow-available", "&e»"),
    HELP_RIGHTARROWUNAVAILABLE("Help-command.Right-arrow-unavailable", "&r»");

    String path, oldpath, defaultcontent, content;

    MessageString(String path, String defaultcontent) {
        this(path, path, defaultcontent);
    }

    MessageString(String path, String oldpath, String defaultcontent) {
        this.path = path;
        this.oldpath = oldpath;
        this.defaultcontent = defaultcontent;
    }

    public String getPath() {
        return this.path;
    }

    public String getOldPath() {
        return this.oldpath;
    }

    public String getDefaultContent() {
        return this.defaultcontent;
    }

    public String toString() {
        return ((this.content != null) ? this.content : this.defaultcontent).replace("&", "\u00A7");
    }

    public void setContent(String content) {
        this.content = content;
    }
}
