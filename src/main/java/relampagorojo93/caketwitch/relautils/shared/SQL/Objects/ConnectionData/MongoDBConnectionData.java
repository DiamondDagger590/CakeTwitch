package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.ConnectionData;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Abstracts.ConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;

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
	public String getHost() { return host; }
	public int getPort() { return port; }
	public String getDatabase() { return database; }
	public String getUsername() { return username; }
	public String getPassword() { return password; } 
}
