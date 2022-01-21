package relampagorojo93.caketwitch.yamllib.objects;

import java.util.List;

public abstract class Data {
    protected Type type;
    protected Object data;

    protected Data(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Data setData(Object data) {
        this.data = data;
		if (getString() == null) {
			type = Type.SECTION;
		}
		else if (getStringList() != null) {
			type = Type.LIST;
		}
		else {
			if (getString().matches("^[0-9]{1,}\\.[0-9]{1,}(E(-|\\+)[0-9]{1,}){0,1}$")) {
				type = Type.DECIMAL;
			}
			else if (getString().matches("^[0-9]{1,}$")) {
				type = Type.INTEGER;
			}
			else {
				type = Type.STRING;
			}
		}
        return this;
    }

    public Type getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public String getString() {
        return this.getString(null);
    }

    public String getString(String def) {
        return data != null ? String.valueOf(data) : def;
    }

    public Float getFloat() {
        return this.getFloat(null);
    }

    public Float getFloat(Float def) {
        try {
            return Float.parseFloat(getString());
        }
        catch (Exception e) {
            return def;
        }
    }

    public Integer getInteger() {
        return this.getInteger(null);
    }

    public Integer getInteger(Integer def) {
        try {
            return Integer.parseInt(getString().split("\\.")[0]);
        }
        catch (Exception e) {
            return def;
        }
    }

    public Double getDouble() {
        return this.getDouble(null);
    }

    public Double getDouble(Double def) {
        try {
            return Double.parseDouble(getString());
        }
        catch (Exception e) {
            return def;
        }
    }

    public Long getLong() {
        return this.getLong(null);
    }

    public Long getLong(Long def) {
        try {
            return Long.parseLong(getString().split("\\.")[0]);
        }
        catch (Exception e) {
            return def;
        }
    }

    public Boolean getBoolean() {
        return this.getBoolean(false);
    }

    public Boolean getBoolean(Boolean def) {
        try {
            return Boolean.parseBoolean(getString());
        }
        catch (Exception e) {
            return def;
        }
    }

    public List<String> getStringList() {
        return this.getStringList(null);
    }

    @SuppressWarnings ("unchecked")
    public List<String> getStringList(List<String> def) {
        try {
            return (List<String>) this.data;
        }
        catch (Exception e) {
            return def;
        }
    }

    public boolean isSection() {
        return false;
    }

    public Section asSection() {
        return null;
    }
}
