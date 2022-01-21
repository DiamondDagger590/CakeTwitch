package relampagorojo93.caketwitch.jsonlib;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map.Entry;

public class JSONParser {

    private static final char START_OBJECT = '{';
    private static final char END_OBJECT = '}';
    private static final char START_ARRAY = '[';
    private static final char END_ARRAY = ']';
    private static final char STRING_DELIMITER = '"';
    private static final char KEYVALUE_SPLITTER = ':';
    private static final char KEYVALUE_LIMIT = ',';
    private static final char SPECIALKEY_IGNORER = '\\';

    public static JSONElement parseJson(File file) throws Exception {
        return parseJson(file, null);
    }

    public static JSONElement parseJson(String jsonstring) throws Exception {
        return parseJson(jsonstring, null);
    }

    public static JSONElement parseJson(File file, JSONElement default_element) throws Exception {
        JSONElement json = null;
        try (Reader reader = new FileReader(file)) {
            json = new JSONParser().read(reader, default_element);
        }
        catch (Exception e) {
            throw e;
        }
        return json;
    }

    public static JSONElement parseJson(String jsonstring, JSONElement default_element) throws Exception {
        JSONElement json = null;
        try (Reader reader = new StringReader(jsonstring)) {
            json = new JSONParser().read(reader, default_element);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return json;
    }

    private int read_char;

    private JSONElement read(Reader reader, JSONElement default_element) throws Exception {
        read_char = -1;
        while ((read_char = reader.read()) != -1) {
            switch (read_char) {
                case END_ARRAY:
                case END_OBJECT:
                    throw new Exception("Unexpected end of an array/object!");
                case START_ARRAY:
                case START_OBJECT:
                    return read_char == START_ARRAY ? readArray(reader) : readObject(reader);
                default:
                    break;
            }
        }
		if (default_element != null) {
			return default_element;
		}
        throw new Exception("Complete objects not found!");
    }

    private JSONArray readArray(Reader reader) throws Exception {
        JSONArray array = new JSONArray();
        while ((read_char = reader.read()) != -1) {
			if (read_char == ' ' || read_char == '\n' || read_char == '\r') {
				continue;
			}
            boolean check;
            do {
                check = false;
                switch (read_char) {
                    case END_ARRAY:
                        read_char = -1;
                        return array;
                    case START_ARRAY:
                    case START_OBJECT:
                        array.addObject(read_char == START_ARRAY ? readArray(reader) : readObject(reader));
                        break;
                    case KEYVALUE_LIMIT:
                        break;
                    case END_OBJECT:
                        throw new Exception("Unexpected end of an object!");
                    case KEYVALUE_SPLITTER:
                        throw new Exception("Unexpected key value generation!");
                    default:
                        array.addObject(readKeyValue(reader).getValue());
						if (read_char != -1) {
							check = true;
						}
                        break;
                }
            }
            while (check);
        }
        throw new Exception("Unfinished array!");
    }

    private JSONObject readObject(Reader reader) throws Exception {
        JSONObject object = new JSONObject();
        while ((read_char = reader.read()) != -1) {
			if (read_char == ' ' || read_char == '\n' || read_char == '\r') {
				continue;
			}
            boolean check;
            do {
                check = false;
                switch (read_char) {
                    case END_OBJECT:
                        read_char = -1;
                        return object;
                    case KEYVALUE_LIMIT:
                        break;
                    case END_ARRAY:
                        throw new Exception("Unexpected end of an array!");
                    case START_ARRAY:
                        throw new Exception("Unexpected start of an array!");
                    case START_OBJECT:
                        throw new Exception("Unexpected start of an object!");
                    case KEYVALUE_SPLITTER:
                        throw new Exception("Unexpected key value generation!");
                    default:
                        Entry<String, JSONElement> entry = readKeyValue(reader);
                        object.addObject(entry.getKey(), entry.getValue());
						if (read_char != -1) {
							check = true;
						}
                        break;
                }
            }
            while (check);
        }
        throw new Exception("Unfinished object!");
    }

    private Entry<String, JSONElement> readKeyValue(Reader reader) throws Exception {
        String key = null;
        JSONElement value = null;
        StringBuilder sb = new StringBuilder();
		if (read_char != -1) {
			sb.append((char) read_char);
		}
        while ((read_char = reader.read()) != -1) {
            if (sb.length() != 0) {
                if (sb.charAt(0) == STRING_DELIMITER) {
					if (read_char == STRING_DELIMITER && sb.charAt(sb.length() - 1) != SPECIALKEY_IGNORER) {
						value = new JSONData(sb.toString() + (char) read_char);
						sb.setLength(0);
					}
					else {
						sb.append((char) read_char);
					}
                    continue;
                }
                else if (read_char == ' ') {
                    value = new JSONData(sb.toString());
                    sb.setLength(0);
                }
            }
            switch (read_char) {
                case START_ARRAY:
                case START_OBJECT:
					if (key == null || key.isEmpty()) {
						throw new Exception("Trying to add an object/array without a key!");
					}
					if (read_char == START_ARRAY) {
						value = readArray(reader);
					}
					else {
						value = readObject(reader);
					}
                    break;
                case KEYVALUE_SPLITTER:
					if (value == null) {
						throw new Exception("Trying to create a key-value object without key!");
					}
					if (!value.isData()) {
						throw new Exception("Trying to use an object or array as key!");
					}
                    key = value.asData().getAsString();
                    value = null;
                    break;
                case KEYVALUE_LIMIT:
                    read_char = -1;
                case END_ARRAY:
                case END_OBJECT:
                    if (value == null) {
						if (sb.length() == 0) {
							throw new Exception("No value found!");
						}
                        value = new JSONData(sb.toString().trim());
                    }
					if (key != null && key.isEmpty()) {
						throw new Exception("Trying to load a value with an empty key!");
					}
                    return new SimpleEntry<String, JSONElement>(key == null ? "" : key, value);
                default:
					if (read_char == '\n' || read_char == '\r' || (read_char == ' ' && sb.length() == 0)) {
						break;
					}
					if (value == null) {
						sb.append((char) read_char);
					}
                    read_char = -1;
					if (read_char == -1) {
						break;
					}
            }
        }
        throw new Exception("Unfinished object!");
    }

    public class SimpleEntry<K, V> implements Entry<K, V> {

        private K key;
        private V value;

        public SimpleEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return (this.value = value);
        }

    }

}