package relampagorojo93.caketwitch.jsonlib;

import java.util.ArrayList;
import java.util.List;

public class JSONArray extends JSONElement {
    private List<JSONElement> array = new ArrayList<>();

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public JSONArray asArray() {
        return this;
    }

    public List<JSONElement> getObjects() {
        return array;
    }

    public JSONElement get(int index) {
        return array.get(index);
    }

    public JSONElement getOrDefault(int index, JSONElement element) {
        return array.size() > index ? array.get(index) : element;
    }

    public JSONObject getObject(int index) {
        return (JSONObject) array.get(index);
    }

    public JSONArray getArray(int index) {
        return (JSONArray) array.get(index);
    }

    public JSONData getData(int index) {
        return (JSONData) array.get(index);
    }

    public JSONObject getObjectNonNull(int index) {
        return (JSONObject) getOrDefault(index, new JSONObject());
    }

    public JSONArray getArrayNonNull(int index) {
        return (JSONArray) getOrDefault(index, new JSONArray());
    }

    public JSONData getDataNonNull(int index) {
        return (JSONData) getOrDefault(index, new JSONData());
    }

    public JSONObject getObjectOrDefault(int index, JSONObject object) {
        return (JSONObject) getOrDefault(index, new JSONObject());
    }

    public JSONArray getArrayOrDefault(int index, JSONArray array) {
        return (JSONArray) getOrDefault(index, array);
    }

    public JSONData getDataOrDefault(int index, JSONData data) {
        return (JSONData) getOrDefault(index, data);
    }

    public JSONArray addObject(String... data) {
		for (String string : data) {
			array.add(new JSONData(string));
		}
        return this;
    }

    public JSONArray addObject(double... data) {
		for (double doub : data) {
			array.add(new JSONData(doub));
		}
        return this;
    }

    public JSONArray addObject(int... data) {
		for (int in : data) {
			array.add(new JSONData(in));
		}
        return this;
    }

    public JSONArray addObject(boolean... data) {
		for (boolean bool : data) {
			array.add(new JSONData(bool));
		}
        return this;
    }

    public JSONArray addObject(JSONElement... data) {
		for (JSONElement element : data) {
			array.add(element);
		}
        return this;
    }

    public JSONArray addNullObject() {
        array.add(new JSONData());
        return this;
    }

    public JSONArray removeObject(JSONElement element) {
        array.remove(element);
        return this;
    }
}
