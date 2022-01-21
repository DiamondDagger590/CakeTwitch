package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel;

public class Column {
    private Table table;
    private String name, type, def;
    private int typeid;
    private boolean primary, notnull, unique, binary, unsigned, autoincrement, generated;
    private Column reference;

    private Column(Table table, String name) {
        this.table = table;
        this.name = name;
    }

    public Column(Table table, String name, Column reference) {
        this(table, name);
        this.reference = reference;
    }

    public Column(Table table, String name, String type, int typeid) {
        this(table, name);
        this.type = type;
        this.typeid = typeid;
    }

    public Column(Table table, String name, String type, int typeid, String def) {
        this(table, name, type, typeid);
        this.def = def;
    }

    public Column(Table table, String name, String type, int typeid, String def, boolean primary, boolean notnull, boolean unique, boolean binary, boolean unsigned, boolean autoincrement, boolean generated) {
        this(table, name, type, typeid, def);
        this.primary = primary;
        this.notnull = notnull;
        this.unique = unique;
        this.binary = binary;
        this.unsigned = unsigned;
        this.autoincrement = autoincrement;
        this.generated = generated;
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getTypeId() {
        return typeid;
    }

    public String getDefault() {
        return def;
    }

    public Column getReference() {
        return reference;
    }

    public Column setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public boolean getPrimary() {
        return primary;
    }

    public Column setNotNull(boolean notnull) {
        this.notnull = notnull;
        return this;
    }

    public boolean getNotNull() {
        return notnull;
    }

    public Column setUnique(boolean unique) {
        this.unique = unique;
        return this;
    }

    public boolean getUnique() {
        return unique;
    }

    public Column setBinary(boolean binary) {
        this.binary = binary;
        return this;
    }

    public boolean getBinary() {
        return binary;
    }

    public Column setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
        return this;
    }

    public boolean getUnsigned() {
        return unsigned;
    }

    public Column setAutoIncrement(boolean autoincrement) {
        this.autoincrement = autoincrement;
        return this;
    }

    public boolean getAutoIncrement() {
        return autoincrement;
    }

    public Column setGenerated(boolean generated) {
        this.generated = generated;
        return this;
    }

    public boolean getGenerated() {
        return generated;
    }
}