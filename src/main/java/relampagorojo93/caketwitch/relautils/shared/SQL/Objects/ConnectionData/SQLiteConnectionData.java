package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.ConnectionData;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Abstracts.ConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;

public class SQLiteConnectionData extends ConnectionData {
	private String path;
	public SQLiteConnectionData(String path) {
		super(SQLType.SQLITE);
		this.path = path;
	}
	public String getPath() { return path; }
	public String toString() { return path.replace("\\", "/"); }
}
