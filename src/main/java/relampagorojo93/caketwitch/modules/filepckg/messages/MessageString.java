package relampagorojo93.caketwitch.modules.filepckg.messages;

public enum MessageString {
    PREFIX("Prefix", "&6&lCake&e&lTwitch &a&l>>&r&7"),
    RELOAD("Message.Reload", "The plugin has been reloaded successfully."),
    CANCELLED("Message.Cancelled", "Your action has been cancelled."),
    CONSOLEDENIED("Message.Console-denied", "You can't use this command in console."),
    MYSQLCONNECTIONERROR("Message.MySQL-connection-error", "There was an error trying to connect to the MySQL database (Check your MySQL configuration)."),
    MYSQLDISABLEDERROR("Message.MySQL-disabled-error", "You can't parse MySQL information to SQLite while you aren't using MySQL."),
    MYSQLENABLEDERROR("Message.MySQL-enabled-error", "You can't parse SQLite information to MySQL while you're using MySQL."),
    NAMEMAXLENGTHREACHED("Message.Name-max-length-reached", "The name of your channel exceeds the max of 25 characters."),
    NOTONLINE("Message.Not-online", "This player is not online."),
    ONLYNUMBERS("Message.Only-numbers", "You must specify a number."),
    PARSEDDATA("Message.Parsed-data", "Data has been parsed successfully."),
    PARSINGERROR("Message.Parsing-error", "There was an error trying to parse the database information (Contact with the developer)."),
    SQLITECONNECTIONERROR("Message.SQLite-connection-error", "There was an error trying to connect to the SQLite database (Contact with the developer)."),
    STREAMANNOUNCEMENT("Message.Stream-announcement", "&8[&5Twitch&8] &7%ign% is actually streaming! Go check his stream!"),

    COMMONGUI_LEFTARROWNAME("Common-GUI.Left-arrow-name", "&7&l← &7Previous"),
    COMMONGUI_RIGHTARROWNAME("Common-GUI.Right-arrow-name", "&7Next &7&l→"),
    COMMONGUI_UPARROWNAME("Common-GUI.Up-arrow-name", "&7&l↑ &7Previous"),
    COMMONGUI_DOWNARROWNAME("Common-GUI.Down-arrow-name", "&7Next &7&l↓"),
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

    public static String applyPrefix(MessageString message) {
        return applyPrefix(message.toString());
    }

    public static String applyPrefix(String message) {
        return PREFIX.toString() + " " + message;
    }
}
