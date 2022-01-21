package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.DataModel.SubDatabases;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Abstracts.SQLParser;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Conditions.Condition;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Data;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Column;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Constraints.ForeignConstraint;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Constraints.UniqueConstraint;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Table;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.SQLObject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLiteDatabase extends MySQLDatabase {
	public SQLiteDatabase() { super(); }
	public SQLiteDatabase(String prefix) { super(prefix); }
	@Override
	public List<String> selectTables(SQLObject sql) {
		List<String> tables = new ArrayList<>();
		try {
			ResultSet set = sql.query("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';");
			while (set.next())
				tables.add(set.getString(1));
		} catch (Exception e) {}
		return tables;
	}
	@Override
	public boolean updateTables(String version, SQLObject sql, SQLParser...parsers) {
		boolean update = false, result = update;
		if (!sql.execute("CREATE TABLE IF NOT EXISTS information(param VARCHAR(16),value VARCHAR(16),CONSTRAINT PK_information PRIMARY KEY (param));")) return false;
		List<String> tlist = new ArrayList<>();
		try {
			if (!sql.execute("BEGIN IMMEDIATE TRANSACTION;")) throw new Exception();
			sql.execute("PRAGMA foreign_keys=off;");
			ResultSet set = sql.query("SELECT value FROM information WHERE `param`='version';");
			String v = "";
			if (set.next()) v = set.getString(1);
			set.close();
			if (!v.equals(version)) {
				update = true;
				List<String> check = selectTables(sql);
				for (String name : check) {
					if (!name.equals("information")) {
						if (!sql.execute("CREATE TABLE " + name + "_old AS SELECT * FROM " + name + ";")) throw new Exception();
						if (!sql.execute("DROP TABLE IF EXISTS " + name + ";")) throw new Exception();
						tlist.add(name);
					}
				}
				for (Table table:getTables()) if (!createTable(sql, table)) throw new Exception();
				byte b;
				int i;
				SQLParser[] arrayOfSQLParser;
				for (i = (arrayOfSQLParser = parsers).length, b = 0; b < i;) {
					SQLParser parser = arrayOfSQLParser[b];
					if (!parser.parse(sql, v, "")) throw new Exception("Error while parsing");
					b++;
				}
				for (String table : tlist) {
					ResultSet cfields = sql.query("SELECT name FROM pragma_table_info(\"" + table + "\") WHERE name IN (SELECT name FROM pragma_table_info(\"" + table + "_old\"));");
					if (cfields != null) {
						String fields = "";
						while (cfields.next()) fields+=(fields.isEmpty() ? "" : ",") + cfields.getString("name");
						cfields.close();
						if (fields.isEmpty() || !sql.execute("INSERT INTO " + table + "(" + fields + ") SELECT " + fields + " FROM " + table + "_old;"));
					}
				}
			}
			if (!v.isEmpty()) sql.execute("UPDATE information SET `value`=? WHERE `param`=?;", new Data(12, version), new Data(12, "version"));
			else sql.execute("INSERT INTO information VALUES(?,?);", new Data(12, "version"), new Data(12, version));
			result = true;
			if (!sql.execute("COMMIT TRANSACTION;")) throw new Exception();
		} catch (Exception e) {
			sql.execute("ROLLBACK TRANSACTION;");
			System.out.println("SQLUtils >> An error has occurred while updating the database. Time to use your backup!");
			e.printStackTrace();
		} finally {
			if (update)
				try {
					if (!sql.execute("BEGIN IMMEDIATE TRANSACTION;")) throw new Exception();
					List<String> check = selectTables(sql);
					for (String table : check) if (table.endsWith("_old") && !sql.execute("DROP TABLE IF EXISTS " + table + ";")) throw new Exception();
					if (!sql.execute("COMMIT TRANSACTION;")) throw new Exception();
				} catch (Exception e1) {
					sql.execute("ROLLBACK TRANSACTION;");
				}
			sql.execute("PRAGMA foreign_keys=on;");
		}
		return result;
	}
	public boolean setForeignKeyCheck(SQLObject sql, boolean enabled) {
		return sql.execute("PRAGMA foreign_keys=" + (enabled ? "on" : "off") + ";");
	}
	@Override
	public boolean createTable(SQLObject sql, Table table) {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		sb.append(table.getName()).append("(");
		StringBuilder esb = new StringBuilder();
		for (Column c:table.getColumns()) {
			if (esb.length() != 0) esb.append(", ");
			esb.append(c.getName());
			Column column = c.getReference() != null ? c.getReference() : c;
			if (column.getAutoIncrement()) {
				esb.append(" INTEGER PRIMARY KEY AUTOINCREMENT");
				if (column.getNotNull()) esb.append(" NOT NULL");
			}
			else {
				esb.append(" ").append(column.getType());
				if (column.getUnsigned()) esb.append(" UNSIGNED");
				if (column.getNotNull()) esb.append(" NOT NULL");
				if (column.getDefault() != null && !column.getDefault().isEmpty()) esb.append(" DEFAULT ").append(column.getDefault());
			}
		}
		sb.append(esb);
		List<Column> pkeys = new ArrayList<>();
		for (Column column:table.getPrimaryColumns()) if (!column.getAutoIncrement()) pkeys.add(column);
		if (pkeys.size() != 0) {
			sb.append(", CONSTRAINT PK_").append(table.getName()).append(" PRIMARY KEY(");
			esb = new StringBuilder();
			for (Column key:pkeys) {
				if (esb.length() != 0) esb.append(", ");
				esb.append(key.getName());
			}
			sb.append(esb).append(")");
		}
		for (ForeignConstraint foreignc:table.getForeignConstraints()) {
			sb.append(", CONSTRAINT FK_").append(foreignc.getOrigins().get(0).getTable().getName()).append("_").append(foreignc.getReferences().get(0).getTable().getName()).append(" FOREIGN KEY (");
			esb = new StringBuilder();
			for (Column c:foreignc.getOrigins()) {
				if (esb.length() != 0) esb.append(", ");
				esb.append(c.getName());
			}
			sb.append(esb).append(") REFERENCES ").append(foreignc.getReferences().get(0).getTable().getName()).append("(");
			esb = new StringBuilder();
			for (Column c:foreignc.getReferences()) {
				if (esb.length() != 0) esb.append(", ");
				esb.append(c.getName());
			}
			sb.append(esb).append(") ON DELETE CASCADE");
		}
		for (UniqueConstraint uniquec:table.getUniqueConstraints()) {
			sb.append(", CONSTRAINT UK_").append(table.getName());
			for (Column column:uniquec.getUniques()) sb.append("_").append(column.getName());
			sb.append(" UNIQUE (");
			esb = new StringBuilder();
			for (Column column:uniquec.getUniques()) {
				if (esb.length() != 0) esb.append(", ");
				esb.append(column.getName());
			}
			sb.append(esb).append(")");
		}
		sb.append(");");
		if (!sql.execute(sb.toString())) return false;
		return true;
	}
	@Override
	public boolean insertOrUpdate(SQLObject sql, Table table, Map<Column, Data> values, Condition...conditions) {
		if (insert(sql, table, values)) return true;
		return update(sql, table, values, conditions);
	}
}