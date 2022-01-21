package relampagorojo93.caketwitch.spigotdebug;

public abstract class DebugData {
	
	private long logtime = System.currentTimeMillis();
	private DebugType type;
	private String message;
	
	public DebugData(DebugType type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public long getLogTime() {
		return logtime;
	}
	
	public DebugType getDebugType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}
	
}
