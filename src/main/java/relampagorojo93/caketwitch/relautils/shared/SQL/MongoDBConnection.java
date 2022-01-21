package relampagorojo93.caketwitch.relautils.shared.SQL;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Arrays;

public class MongoDBConnection {
	String host, database, username, password;
	int port;
	private DB db;
	private MongoClient client;
	public MongoDBConnection(String host, int port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		try {
			client = new MongoClient(new ServerAddress(host, port), Arrays.asList(MongoCredential.createCredential(username, database, password.toCharArray())));
			db = client.getDB(database);
		} catch (Exception e) {}
	}
	public String getHost() {
		return this.host;
	}
	public int getPort() {
		return this.port;
	}
	public String getDatabase() {
		return this.database;
	}
	public String getUsername() {
		return this.username;
	}
	public boolean isConnected() {
		return this.db != null;
	}
	public DB getDB() {
		return this.db;
	}
	public void close() {
		client.close();
	}
}
