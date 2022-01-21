package relampagorojo93.caketwitch.relautils.shared.sql.abstracts;


import relampagorojo93.caketwitch.relautils.shared.sql.enums.SQLType;

public abstract class ConnectionData {
    SQLType type;

    public ConnectionData(SQLType type) {
        this.type = type;
    }

    public SQLType getType() {
        return type;
    }
}
