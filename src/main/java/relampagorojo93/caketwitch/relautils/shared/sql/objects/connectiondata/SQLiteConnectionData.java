package relampagorojo93.caketwitch.relautils.shared.sql.objects.connectiondata;

import relampagorojo93.caketwitch.relautils.shared.sql.abstracts.ConnectionData;
import relampagorojo93.caketwitch.relautils.shared.sql.enums.SQLType;

public class SQLiteConnectionData extends ConnectionData {
    private String path;

    public SQLiteConnectionData(String path) {
        super(SQLType.SQLITE);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String toString() {
        return path.replace("\\", "/");
    }
}
