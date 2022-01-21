package relampagorojo93.caketwitch.relautils.shared.sql.enums;

public enum SQLType {
    NONE,
    MYSQL,
    SQLITE,
    MONGODB;

    public static SQLType getValue(String sqltype) {
        String t = sqltype.toUpperCase();
		for (SQLType type : SQLType.values()) {
			if (type.name().equals(t)) {
				return type;
			}
		}
        return NONE;
    }
}
