package relampagorojo93.caketwitch.jsonlib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class JSONObject extends JSONElement {
    private HashMap<String, JSONElement> map = new LinkedHashMap<>();

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public JSONObject asObject() {
        return this;
    }

    public List<String> getKeys() {
        return new ArrayList<>(map.keySet());
    }

    public List<JSONElement> getValues() {
        return new ArrayList<>(map.values());
    }

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public JSONElement get(String key) {
        return map.get(key);
    }

    public JSONElement getOrDefault(String key, JSONElement element) {
        return map.getOrDefault(key, element);
    }

    public JSONObject getObject(String key) {
        return (JSONObject) map.get(key);
    }

    public JSONArray getArray(String key) {
        return (JSONArray) map.get(key);
    }

    public JSONData getData(String key) {
        return (JSONData) map.get(key);
    }

    public JSONObject getObjectNonNull(String key) {
        return (JSONObject) getOrDefault(key, new JSONObject());
    }

    public JSONArray getArrayNonNull(String key) {
        return (JSONArray) getOrDefault(key, new JSONObject());
    }

    public JSONData getDataNonNull(String key) {
        return (JSONData) getOrDefault(key, new JSONObject());
    }

    public JSONObject getObjectOrDefault(String key, JSONObject object) {
        return (JSONObject) getOrDefault(key, object);
    }

    public JSONArray getArrayOrDefault(String key, JSONArray array) {
        return (JSONArray) getOrDefault(key, array);
    }

    public JSONData getDataOrDefault(String key, JSONData data) {
        return (JSONData) getOrDefault(key, data);
    }

    public JSONObject addObject(String key, String data) {
        map.put(key, new JSONData(data));
        return this;
    }

    public JSONObject addObject(String key, double data) {
        map.put(key, new JSONData(data));
        return this;
    }

    public JSONObject addObject(String key, long data) {
        map.put(key, new JSONData(data));
        return this;
    }

    public JSONObject addObject(String key, boolean data) {
        map.put(key, new JSONData(data));
        return this;
    }

    public JSONObject addObject(String key, JSONElement element) {
        map.put(key, element);
        return this;
    }

    public JSONObject addNullObject(String key) {
        map.put(key, new JSONData());
        return this;
    }

    public JSONObject removeObject(String key) {
        map.remove(key);
        return this;
    }
}
