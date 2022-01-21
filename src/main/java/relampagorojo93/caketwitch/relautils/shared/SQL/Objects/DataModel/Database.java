package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.DataModel;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Abstracts.SQLParser;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Conditions.Condition;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Data;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Joins.Join;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.SQLObject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Database {
	private String prefix = "";
	private List<Table> tables = new ArrayList<>();
	public Database() {};
	public Database(String prefix) { this.prefix = prefix; }
	public String getPrefix() { return prefix; }
	public void addTable(Table table) { tables.add(table); }
	public List<Table> getTables() { return tables; }
	public Table getTable(String name) { for (Table table:tables) if (table.getName().equals(name) || table.getName().equals(prefix + name)) return table; return null; }
	public List<String> selectTables(SQLObject sql) { return new ArrayList<>(); }
	public boolean updateTables(String version, SQLObject sql, SQLParser...parsers) { return false; }
	public boolean createTable(SQLObject sql, Table table) { return false; }
	public boolean truncateTable(SQLObject sql, Table table) { return false; }
	public boolean dropTable(SQLObject sql, Table table) { return false; }
	public boolean setForeignKeyCheck(SQLObject sql, boolean enabled) { return false; }
	public ResultSet select(SQLObject sql, List<Table> allcolumns, Condition...conditions) { return select(sql, allcolumns, new ArrayList<>(), conditions); }
	public ResultSet select(SQLObject sql, List<Table> allcolumns, List<Column> columns, Condition...conditions) { return select(sql, allcolumns, columns, new ArrayList<>(), conditions); }
	public ResultSet select(SQLObject sql, List<Table> allcolumns, List<Column> columns, List<Join> joins, Condition...conditions) { return selectDistinct(sql, false, allcolumns, columns, joins, conditions); }
	public ResultSet selectDistinct(SQLObject sql, List<Table> allcolumns, Condition...conditions) { return selectDistinct(sql, allcolumns, new ArrayList<>(), conditions); }
	public ResultSet selectDistinct(SQLObject sql, List<Table> allcolumns, List<Column> columns, Condition...conditions) { return selectDistinct(sql, true, allcolumns, columns, new ArrayList<>(), conditions); }	
	public ResultSet selectDistinct(SQLObject sql, boolean distinct, List<Table> allcolumns, List<Column> columns, List<Join> joins, Condition...conditions) { return null; }
	public boolean insert(SQLObject sql, Table table, Map<Column, Data> values) { return false; }
	public int insertWithID(SQLObject sql, Table table, Map<Column, Data> values) { return -1; }
	public boolean update(SQLObject sql, Table table, Map<Column, Data> values, Condition...conditions) { return false; }
	public boolean insertOrUpdate(SQLObject sql, Table table, Map<Column, Data> values, Condition...conditions) { return false; }
	public boolean delete(SQLObject sql, Table table, Condition...conditions) { return false; }
}
