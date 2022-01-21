package relampagorojo93.caketwitch.relautils.shared.SQL.Objects;

public class Data {
	private int type;
	private Object value;
	public Data(int type, Object value) {
		this.type = type;
		this.value = value;
	}
	public int getType() { return type; }
	public Object getValue() { return value; }
}
