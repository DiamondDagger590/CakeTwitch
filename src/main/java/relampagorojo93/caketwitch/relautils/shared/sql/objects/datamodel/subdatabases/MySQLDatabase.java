package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.subdatabases;

import relampagorojo93.caketwitch.relautils.shared.sql.SQLObject;
import relampagorojo93.caketwitch.relautils.shared.sql.abstracts.SQLParser;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.Data;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.conditions.Condition;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Column;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Database;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Table;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.constraints.ForeignConstraint;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.constraints.UniqueConstraint;
import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.joins.Join;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySQLDatabase extends Database {
    public MySQLDatabase() {
        super();
    }

    public MySQLDatabase(String prefix) {
        super(prefix);
    }

    @Override
    public List<String> selectTables(SQLObject sql) {
        List<String> tables = new ArrayList<>();
        try {
            ResultSet set = sql.query("SHOW TABLES;");
            while (set.next()) {
                tables.add(set.getString(1));
            }
        }
        catch (Exception e) {
        }
        return tables;
    }

    @Override
    public boolean updateTables(String version, SQLObject sql, SQLParser... parsers) {
        boolean update = false, result = update;
        if (!sql.execute("CREATE TABLE IF NOT EXISTS " + getPrefix() + "information(param VARCHAR(16),value VARCHAR(16),CONSTRAINT " + getPrefix() + "PK_information PRIMARY KEY (param));")) {
            return false;
        }
        List<String> tlist = new ArrayList<>();
        try {
            sql.execute("SET FOREIGN_KEY_CHECKS=0;");
            ResultSet set = sql.query("SELECT value FROM " + getPrefix() + "information WHERE `param`='version';");
            String v = "";
            if (set.next()) {
                v = set.getString(1);
            }
            if (!v.equals(version)) {
                update = true;
                for (String table : selectTables(sql)) {
                    if (!table.equals(getPrefix() + "information") && table.startsWith(getPrefix())) {
                        if (!sql.execute("CREATE TABLE " + table + "_old AS SELECT * FROM " + table + ";")) {
                            throw new Exception("Error while parsing tables to old tables");
                        }
                        tlist.add(table);
                    }
                }
                String remove = "";
                for (String table : tlist) {
                    remove = String.valueOf(remove) + (remove.isEmpty() ? "" : ",") + table;
                }
                if (!remove.isEmpty()) {
                    sql.execute("DROP TABLE IF EXISTS " + remove + ";");
                }
                for (Table table : this.getTables()) {
                    if (!createTable(sql, table)) {
                        throw new Exception("Error while creating tables");
                    }
                }
                byte b;
                int i;
                SQLParser[] arrayOfSQLParser;
                for (i = (arrayOfSQLParser = parsers).length, b = 0; b < i; ) {
                    SQLParser parser = arrayOfSQLParser[b];
                    if (!parser.parse(sql, v, this.getPrefix())) {
                        throw new Exception("Error while parsing");
                    }
                    b++;
                }
                for (String table : tlist) {
                    List<String> oldt = new ArrayList<>();
                    ResultSet cfields = sql.query("DESC " + table + ";", new Data[0]);
                    for (; cfields.next(); oldt.add(cfields.getString("Field")))
                        ;
                    List<String> transfer = new ArrayList<>();
                    ResultSet tfields = sql.query("DESC " + table + "_old;", new Data[0]);
                    while (tfields.next()) {
                        String column = tfields.getString("Field");
                        if (oldt.contains(column)) {
                            transfer.add(column);
                        }
                    }
                    if (!transfer.isEmpty()) {
                        String fields = "";
                        for (String field : transfer) {
                            fields = String.valueOf(fields) + (fields.isEmpty() ? "" : ",") + field;
                        }
                        if (fields.isEmpty() || !sql.execute("INSERT INTO " + table + "(" + fields + ") SELECT "
                                                                 + fields + " FROM " + table + "_old;"))
                            ;
                    }
                }
            }
            if (!v.isEmpty()) {
                sql.execute("UPDATE " + getPrefix() + "information SET `value`=? WHERE `param`=?;",
                    new Data[]{new Data(12, version),
                        new Data(12, "version")});
            }
            else {
                sql.execute("INSERT INTO " + getPrefix() + "information VALUES(?,?);", new Data[]{new Data(12, "version"),
                    new Data(12, version)});
            }
            result = true;
        }
        catch (Exception e) {
            System.out.println("SQLUtils >> An error has occurred while updating the database. Time to use your backup!");
            e.printStackTrace();
        }
        finally {
            if (update) {
                String remove = "";
                for (String table : selectTables(sql)) {
                    if (table.endsWith("_old")) {
                        remove = String.valueOf(remove) + (remove.isEmpty() ? "" : ",") + table;
                    }
                }
                if (!remove.isEmpty()) {
                    sql.execute("DROP TABLE IF EXISTS " + remove + ";");
                }
            }
            sql.execute("SET FOREIGN_KEY_CHECKS=1;");
        }
        return result;
    }

    @Override
    public boolean createTable(SQLObject sql, Table table) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(table.getName()).append("(");
        StringBuilder esb = new StringBuilder();
        for (Column c : table.getColumns()) {
            if (esb.length() != 0) {
                esb.append(", ");
            }
            esb.append(c.getName());
            Column column = c.getReference() != null ? c.getReference() : c;
            esb.append(" ").append(column.getType());
            if (column.getUnsigned()) {
                esb.append(" UNSIGNED");
            }
            if (column.getAutoIncrement()) {
                esb.append(" AUTO_INCREMENT");
            }
            if (column.getNotNull()) {
                esb.append(" NOT NULL");
            }
            if (column.getDefault() != null && !column.getDefault().isEmpty()) {
                esb.append(" DEFAULT ").append(column.getDefault());
            }
        }
        sb.append(esb);
        if (table.getPrimaryColumns().size() != 0) {
            sb.append(", CONSTRAINT PK_").append(table.getName()).append(" PRIMARY KEY(");
            esb = new StringBuilder();
            for (Column key : table.getPrimaryColumns()) {
                if (esb.length() != 0) {
                    esb.append(", ");
                }
                esb.append(key.getName());
            }
            sb.append(esb).append(")");
        }
        for (ForeignConstraint foreignc : table.getForeignConstraints()) {
            sb.append(", CONSTRAINT FK_").append(foreignc.getOrigins().get(0).getTable().getName()).append("_").append(foreignc.getReferences().get(0).getTable().getName()).append(" FOREIGN KEY (");
            esb = new StringBuilder();
            for (Column c : foreignc.getOrigins()) {
                if (esb.length() != 0) {
                    esb.append(", ");
                }
                esb.append(c.getName());
            }
            sb.append(esb).append(") REFERENCES ").append(foreignc.getReferences().get(0).getTable().getName()).append("(");
            esb = new StringBuilder();
            for (Column c : foreignc.getReferences()) {
                if (esb.length() != 0) {
                    esb.append(", ");
                }
                esb.append(c.getName());
            }
            sb.append(esb).append(") ON DELETE CASCADE");
        }
        for (UniqueConstraint uniquec : table.getUniqueConstraints()) {
            sb.append(", CONSTRAINT UK_").append(table.getName());
            for (Column column : uniquec.getUniques()) {
                sb.append("_").append(column.getName());
            }
            sb.append(" UNIQUE (");
            esb = new StringBuilder();
            for (Column column : uniquec.getUniques()) {
                if (esb.length() != 0) {
                    esb.append(", ");
                }
                esb.append(column.getName());
            }
            sb.append(esb).append(")");
        }
        if (!sql.execute(sb.append(");").toString())) {
            return false;
        }
        return true;
    }

    public boolean truncateTable(SQLObject sql, Table table) {
        return sql.execute("TRUNCATE TABLE " + table.getName() + ";");
    }

    @Override
    public boolean dropTable(SQLObject sql, Table table) {
        return sql.execute("DROP TABLE IF EXISTS " + table.getName() + ";");
    }

    @Override
    public boolean setForeignKeyCheck(SQLObject sql, boolean enabled) {
        return sql.execute("SET FOREIGN_KEY_CHECKS = " + (enabled ? '1' : '0') + ";");
    }

    @Override
    public ResultSet selectDistinct(SQLObject sql, boolean distinct, List<Table> allcolumns, List<Column> columns, List<Join> joins, Condition... conditions) {
        List<Data> array = new ArrayList<>();
        List<Table> tables = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT ");
        if (distinct) {
            sb.append("DISTINCT ");
        }
        StringBuilder eb = new StringBuilder();
        for (Table table : allcolumns) {
            if (eb.length() != 0) {
                eb.append(",");
            }
            eb.append(table.getName()).append(".*");
            if (!tables.contains(table)) {
                tables.add(table);
            }
        }
        for (Column column : columns) {
            if (eb.length() != 0) {
                eb.append(",");
            }
            eb.append(column.getTable().getName()).append(".").append(column.getName());
            if (!tables.contains(column.getTable())) {
                tables.add(column.getTable());
            }
        }
        sb.append(eb).append(" FROM ");
        eb = new StringBuilder();
        for (Table table : tables) {
            Column found = null;
            for (Join join : joins) {
                if (join.getAppend().getTable().getName().equals(table.getName())) {
                    found = join.getAppend();
                    break;
                }
            }
            if (found != null) {
                continue;
            }
            if (eb.length() != 0) {
                eb.append(",");
            }
            eb.append(table.getName());
        }
        sb.append(eb);
        for (Join join : joins) {
            sb.append(" ").append(join.toString());
        }
        if (conditions.length != 0) {
            sb.append(" WHERE");
            for (Condition condition : conditions) {
                sb.append(" ").append(condition.toString());
                array.addAll(condition.getData());
            }
        }
        return sql.query(sb.append(";").toString(), array.toArray(new Data[array.size()]));
    }

    @Override
    public boolean insert(SQLObject sql, Table table, Map<Column, Data> values) {
        Data[] array = values.values().toArray(new Data[values.values().size()]);
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(table.getName()).append("(");
        StringBuilder esb = new StringBuilder();
        for (Column column : values.keySet()) {
            if (esb.length() != 0) {
                esb.append(",");
            }
            esb.append(column.getName());
        }
        sb.append(esb).append(") VALUES (");
        esb = new StringBuilder();
        for (int i = 0; i < values.values().size(); i++) {
            esb.append(esb.length() != 0 ? ",?" : "?");
        }
        return sql.execute(sb.append(esb).append(");").toString(), array);
    }

    @Override
    public int insertWithID(SQLObject sql, Table table, Map<Column, Data> values) {
        Data[] array = values.values().toArray(new Data[values.values().size()]);
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(table.getName()).append("(");
        StringBuilder esb = new StringBuilder();
        for (Column column : values.keySet()) {
            if (esb.length() != 0) {
                esb.append(",");
            }
            esb.append(column.getName());
        }
        sb.append(esb).append(") VALUES (");
        esb = new StringBuilder();
        for (int i = 0; i < values.values().size(); i++) {
            esb.append(esb.length() != 0 ? ",?" : "?");
        }
        return sql.executeWithId(sb.append(esb).append(");").toString(), array);
    }

    @Override
    public boolean update(SQLObject sql, Table table, Map<Column, Data> values, Condition... conditions) {
        List<Data> array = new ArrayList<>(values.values());
        StringBuilder sb = new StringBuilder("UPDATE ").append(table.getName()).append(" SET ");
        StringBuilder esb = new StringBuilder();
        for (Column column : values.keySet()) {
            if (esb.length() != 0) {
                esb.append(",");
            }
            esb.append(column.getName()).append("=?");
        }
        sb.append(esb);
        if (conditions.length != 0) {
            sb.append(" WHERE");
            for (Condition condition : conditions) {
                sb.append(" ").append(condition.toString());
                array.addAll(condition.getData());
            }
        }
        return sql.execute(sb.append(";").toString(), array.toArray(new Data[array.size()]));
    }

    @Override
    public boolean insertOrUpdate(SQLObject sql, Table table, Map<Column, Data> values, Condition... conditions) {
        Data[] array = values.values().toArray(new Data[values.values().size()]);
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(table.getName()).append("(");
        StringBuilder esb = new StringBuilder();
        for (Column column : values.keySet()) {
            if (esb.length() != 0) {
                esb.append(",");
            }
            esb.append(column.getName());
        }
        sb.append(esb).append(") VALUES (");
        esb = new StringBuilder();
        for (int i = 0; i < values.values().size(); i++) {
            esb.append(esb.length() != 0 ? ",?" : "?");
        }
        sb.append(esb).append(") ON DUPLICATE KEY UPDATE ");
        esb = new StringBuilder();
        for (Column column : values.keySet()) {
            if (esb.length() != 0) {
                esb.append(",");
            }
            esb.append(column.getName()).append(" = VALUES(").append(column.getName()).append(")");
        }
        return sql.execute(sb.append(esb).append(";").toString(), array);
    }

    @Override
    public boolean delete(SQLObject sql, Table table, Condition... conditions) {
        List<Data> array = new ArrayList<>();
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(table.getName());
        if (conditions.length != 0) {
            sb.append(" WHERE");
            for (Condition condition : conditions) {
                sb.append(" ").append(condition.toString());
                array.addAll(condition.getData());
            }
        }
        return sql.execute(sb.append(";").toString(), array.toArray(new Data[array.size()]));
    }
}
