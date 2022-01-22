package relampagorojo93.caketwitch.jsonlib;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public abstract class JSONElement {
    public boolean isArray() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isData() {
        return false;
    }

    public JSONArray asArray() {
        return new JSONArray();
    }

    public JSONObject asObject() {
        return null;
    }

    public JSONData asData() {
        return null;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int level) {
        if (isData()) {
            try {
                switch (asData().getDataType()) {
                    case BOOLEAN:
                        return addSpaces(level, String.valueOf(asData().getAsBoolean()));
                    case FLOAT:
                        return addSpaces(level, String.valueOf(asData().getAsFloat()));
                    case DOUBLE:
                        return addSpaces(level, String.valueOf(asData().getAsDouble()));
                    case INTEGER:
                        return addSpaces(level, String.valueOf(asData().getAsInteger()));
                    case LONG:
                        return addSpaces(level, String.valueOf(asData().getAsLong()));
                    case NULL:
                        return addSpaces(level, "null");
                    case STRING:
                        return addSpaces(level, "\"" + asData().getAsString().replace("\"", "\\\"") + "\"");
                    default:
                        return "\"\"";
                }
            }
            catch (Exception e) {
                return "\"\"";
            }
        }
        StringBuilder sb = new StringBuilder();
        if (isObject()) {
            sb.append(addSpaces(level, "{" + System.lineSeparator()));
            List<String> keys = asObject().getKeys();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                JSONElement value = asObject().get(key);
                sb.append(addSpaces(level + 2, "\"" + key + "\"") + ": " + (value.isData() ? value.toString() : System.lineSeparator() + value.toString(level + 2)));
				if (i < keys.size() - 1) {
					sb.append(",");
				}
                sb.append(System.lineSeparator());
            }
            sb.append(addSpaces(level, "}"));
        }
        else if (isArray()) {
            sb.append(addSpaces(level, "[" + System.lineSeparator()));
            List<JSONElement> elements = asArray().getObjects();
            for (int i = 0; i < elements.size(); i++) {
                sb.append(elements.get(i).toString(level + 2));
				if (i < elements.size() - 1) {
					sb.append(",");
				}
                sb.append(System.lineSeparator());
            }
            sb.append(addSpaces(level, "]"));
        }
        return sb.toString();
    }

    protected String addSpaces(int spaces, String text) {
        StringBuilder sb = new StringBuilder();
		for (int i = 0; i < spaces; i++) {
			sb.append(" ");
		}
        sb.append(text);
        return sb.toString();
    }

    public void save(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        writer.write(toString());
        writer.flush();
        writer.close();
    }
}