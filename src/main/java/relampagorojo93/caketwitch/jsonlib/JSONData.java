package relampagorojo93.caketwitch.jsonlib;

public class JSONData extends JSONElement {
    private Object value;
    private JSONDataType type;

    public JSONData(String value) {
        if (value == null) {
            this.value = null;
            this.type = JSONDataType.NULL;
        }
        else if (value.length() == 0) {
            this.value = value;
            this.type = JSONDataType.STRING;
        }
        else if (value.charAt(0) == '"') {
            this.value = removeLimits(value).replace("\\\"", "\"");
            this.type = JSONDataType.STRING;
        }
        else {
            switch (value.toLowerCase()) {
                case "null":
                    this.value = null;
                    this.type = JSONDataType.NULL;
                    break;
                case "false":
                    this.value = false;
                case "true":
					if (this.value == null) {
						this.value = false;
					}
                    this.type = JSONDataType.BOOLEAN;
                    break;
                default:
                    if (value.matches("[0-9]{0,}\\.{0,1}[0-9]{0,}")) {
                        if (value.contains(".")) {
                            try {
                                this.value = Double.parseDouble(value);
                                this.type = JSONDataType.DOUBLE;
                                break;
                            }
                            catch (NumberFormatException e) {
                            }
                        }
                        else {
                            try {
                                this.value = Long.parseLong(value);
                                this.type = JSONDataType.LONG;
                                break;
                            }
                            catch (NumberFormatException e) {
                            }
                        }

                    }
                    this.value = removeLimits(value);
                    this.type = JSONDataType.STRING;
                    break;
            }
        }
    }

    public JSONData(float value) {
        this.value = value;
        this.type = JSONDataType.FLOAT;
    }

    public JSONData(double value) {
        this.value = value;
        this.type = JSONDataType.DOUBLE;
    }

    public JSONData(int value) {
        this.value = value;
        this.type = JSONDataType.INTEGER;
    }

    public JSONData(long value) {
        this.value = value;
        this.type = JSONDataType.LONG;
    }

    public JSONData(boolean value) {
        this.value = value;
        this.type = JSONDataType.BOOLEAN;
    }

    public JSONData() {
        this.value = null;
        this.type = JSONDataType.NULL;
    }

    @Override
    public boolean isData() {
        return true;
    }

    @Override
    public JSONData asData() {
        return this;
    }

    public boolean dataIsNull() {
        return type == JSONDataType.NULL;
    }

    public boolean dataIsInteger() {
        return type == JSONDataType.INTEGER || type == JSONDataType.LONG;
    }

    public boolean dataIsLong() {
        return type == JSONDataType.LONG;
    }

    public boolean dataIsFloat() {
        return type == JSONDataType.FLOAT || type == JSONDataType.DOUBLE;
    }

    public boolean dataIsDouble() {
        return type == JSONDataType.DOUBLE;
    }

    public boolean dataIsBoolean() {
        return type == JSONDataType.BOOLEAN;
    }

    public boolean dataIsString() {
        return type == JSONDataType.STRING;
    }

    public JSONDataType getDataType() {
        return type;
    }

    public Object get() {
        return value;
    }

    public String getAsString() throws Exception {
		if (dataIsNull()) {
			throw new NullPointerException();
		}
        return value instanceof String ? (String) value : String.valueOf(value);
    }

    public int getAsInteger() throws Exception {
		if (dataIsNull()) {
			throw new NullPointerException();
		}
		if (!dataIsInteger() && !dataIsFloat()) {
			throw new Exception("Getting data with invalid type!");
		}
        return (int) value;
    }

    public long getAsLong() throws Exception {
		if (dataIsNull()) {
			throw new NullPointerException();
		}
		if (!dataIsLong()) {
			throw new Exception("Getting data with invalid type!");
		}
        return (long) value;
    }

    public float getAsFloat() throws Exception {
		if (dataIsNull()) {
			throw new NullPointerException();
		}
		if (!dataIsFloat()) {
			throw new Exception("Getting data with invalid type!");
		}
        return (float) value;
    }

    public double getAsDouble() throws Exception {
		if (dataIsNull()) {
			throw new NullPointerException();
		}
		if (!dataIsDouble()) {
			throw new Exception("Getting data with invalid type!");
		}
        return (double) value;
    }

    public boolean getAsBoolean() throws Exception {
		if (dataIsNull()) {
			throw new NullPointerException();
		}
		if (!dataIsBoolean()) {
			throw new Exception("Getting data with invalid type!");
		}
        return (boolean) value;
    }

    private String removeLimits(String text) {
        if (text.length() > 1) {
            char c = text.charAt(0);
			if (c == '"' && c == text.charAt(text.length() - 1)) {
				return text.substring(1, text.length() - 1);
			}
        }
        return text;
    }
}
