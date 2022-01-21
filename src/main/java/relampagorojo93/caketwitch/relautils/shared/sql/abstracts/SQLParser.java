package relampagorojo93.caketwitch.relautils.shared.sql.abstracts;

import relampagorojo93.caketwitch.relautils.shared.sql.SQLObject;

public abstract class SQLParser {
    public abstract boolean parse(SQLObject api, String version, String prefix);
}
