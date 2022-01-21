package relampagorojo93.caketwitch.relautils.shared.sql;

import relampagorojo93.caketwitch.relautils.shared.sql.abstracts.ConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata.MySQLConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata.SQLiteConnectionData;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private String path, user, password;
    private java.sql.Connection con;

    public SQLConnection(ConnectionData data) {
        switch (data.getType()) {
            case MYSQL:
                MySQLConnectionData mysqldata = (MySQLConnectionData) data;
                this.path = mysqldata.getProtocol() + "//" + mysqldata.getHost() + ":" + mysqldata.getPort() + "/" + mysqldata.getDatabase();
                this.user = mysqldata.getUsername();
                this.password = mysqldata.getPassword();
                if (mysqldata.getParameters().length != 0) {
                    this.path += "?" + mysqldata.getParameters()[0];
                    for (int i = 1; i < mysqldata.getParameters().length; i++) {
                        this.path += "&" + mysqldata.getParameters()[i];
                    }
                }
                break;
            case SQLITE:
                SQLiteConnectionData sqlitedata = (SQLiteConnectionData) data;
                this.path = "jdbc:sqlite:" + sqlitedata.getPath();
                File f = new File(sqlitedata.getPath());
                File folder = f.getParentFile();
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public boolean isAutoCommit() {
        try {
            return this.con.getAutoCommit();
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean connect() {
        try {
            con = DriverManager.getConnection(path, user, password);
            return isConnected();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        try {
            return this.con.isValid(1);
        }
        catch (Exception e) {
            return false;
        }
    }

    public java.sql.Connection getConnection() {
        return this.con;
    }
}
