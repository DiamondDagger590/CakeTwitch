package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.DataModel;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Constraints.ForeignConstraint;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Constraints.UniqueConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
	private Database database = null;
	private String name = "default";
	private List<Column> columns = new ArrayList<>();
	private List<Column> pcolumns = new ArrayList<>();
	private List<ForeignConstraint> foreigncs = new ArrayList<>();
	private List<UniqueConstraint> uniquecs = new ArrayList<>();
	public Table(Database database, String name) { this.database = database; this.name = name; }
	public String getName() { return database.getPrefix() + name; }
	public Table addColumn(Column column) { 
		columns.add(column);
		if (column.getPrimary()) pcolumns.add(column);
		if (column.getReference() != null) foreigncs.add(new ForeignConstraint(Arrays.asList(column), Arrays.asList(column.getReference())));
		if (column.getUnique()) uniquecs.add(new UniqueConstraint(column));
		return this;
	}
	public Table addColumns(Column...columns) { for (Column column:columns) addColumn(column); return this; }
	public Table addUniqueConstraint(UniqueConstraint uniquec) { uniquecs.add(uniquec); return this; }
	public Table addUniqueConstraints(UniqueConstraint...uniquecs) { for (UniqueConstraint uniquec:uniquecs) addUniqueConstraint(uniquec); return this; }
	public Table addForeignConstraint(ForeignConstraint foreignc) { foreigncs.add(foreignc); return this; }
	public Table addForeignConstraints(ForeignConstraint...foreigncs) { for (ForeignConstraint foreignc:foreigncs) addForeignConstraint(foreignc); return this; }
	public List<Column> getColumns() { return columns; }
	public List<Column> getPrimaryColumns() { return pcolumns; }
	public List<ForeignConstraint> getForeignConstraints() { return foreigncs; }
	public List<UniqueConstraint> getUniqueConstraints() { return uniquecs; }
	public Column getColumn(String name) { for (Column column:columns) if (column.getName().equals(name)) return column; return null; }
}
