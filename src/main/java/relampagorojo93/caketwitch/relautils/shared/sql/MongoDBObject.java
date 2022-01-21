package relampagorojo93.caketwitch.relautils.shared.sql;

import com.mongodb.DB;
import relampagorojo93.caketwitch.relautils.shared.sql.abstracts.ConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata.MongoDBConnectionData;

public class MongoDBObject {
    private MongoDBConnection con;

    public boolean request(ConnectionData data) {
        switch (data.getType()) {
            case MONGODB:
                MongoDBConnectionData mysqldata = (MongoDBConnectionData) data;
                con = new MongoDBConnection(mysqldata.getHost(), mysqldata.getPort(), mysqldata.getDatabase(), mysqldata.getUsername(), mysqldata.getPassword());
				if (con.isConnected()) {
					return true;
				}
                return false;
            default:
                return false;
        }
    }

    public DB getDB() {
        return this.con.getDB();
    }

    public String getHost() {
        return this.con.getHost();
    }

    public int getPort() {
        return this.con.getPort();
    }

    public String getDatabase() {
        return this.con.getDatabase();
    }

    public String getUsername() {
        return this.con.getUsername();
    }

    public void close() {
        this.con.close();
    }
}
