package relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata;

import relampagorojo93.caketwitch.relautils.shared.sql.abstracts.ConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.enums.SQLType;

public class MongoDBConnectionData extends ConnectionData {
    private String host, database, username, password;
    private int port;

    public MongoDBConnectionData(String host, int port, String database, String username, String password) {
        super(SQLType.MONGODB);
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
