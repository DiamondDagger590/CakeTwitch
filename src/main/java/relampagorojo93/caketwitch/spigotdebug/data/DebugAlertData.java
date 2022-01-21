package relampagorojo93.caketwitch.spigotdebug.data;

import relampagorojo93.LibsCollection.SpigotDebug.DebugData;
import relampagorojo93.LibsCollection.SpigotDebug.DebugType;

public class DebugAlertData extends DebugData {
	
	public DebugAlertData(String message) {
		super(DebugType.ALERT, message);
	}
	
}
