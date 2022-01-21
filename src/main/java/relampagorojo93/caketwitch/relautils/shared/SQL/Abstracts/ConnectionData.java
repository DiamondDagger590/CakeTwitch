package relampagorojo93.caketwitch.relautils.shared.SQL.Abstracts;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;

public abstract class ConnectionData {
	SQLType type;
	public ConnectionData(SQLType type) {
		this.type = type;
	}
	public SQLType getType() { return type; }
}
