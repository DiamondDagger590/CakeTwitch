package relampagorojo93.caketwitch.spigotdebug;

import java.util.Date;

public class DebugController {
	
	private DebugData[] data;
	private int currentdata = -1;
	
	public DebugController() {
		this(64);
	}
	
	public DebugController(int maxlog) {
		data = new DebugData[maxlog];
	}
	
	public int getDebugLength() {
		return data.length;
	}
	
	public int getRegisteredAmount() {
		for (int i = 0; i < data.length; i++) if (getDebugData(i) == null) return i;
		return data.length;
	}
	
	public void addDebugData(DebugData data) {
		if (++currentdata >= this.data.length) currentdata = 0;
		this.data[currentdata] = data;
	}
	
	public DebugData getDebugData(int index) {
		if (index < 0 || index >= this.data.length) throw new ArrayIndexOutOfBoundsException("Index: " + index + ", length: " + this.data.length);
		if (index == -1) return null;
		return this.data[(currentdata - index + this.data.length) % this.data.length];
	}
	
	public void changeDebugLength(int size) {
		DebugData[] newdata = new DebugData[size];
		for (int i = 0; i < size && i < getDebugLength(); i++) newdata[i] = getDebugData(i);
	}
	
	public void clearDebugData() {
		for (int i = 0; i < data.length; i++) data[i] = null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (DebugData dd:data) sb.append("[" + new Date(dd.getLogTime()) + "] " + dd.getDebugType().name() + " " + dd.getMessage()).append("\r\n");
		return sb.toString();
	}
}
