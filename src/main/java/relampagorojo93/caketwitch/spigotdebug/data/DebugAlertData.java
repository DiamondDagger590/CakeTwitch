package relampagorojo93.caketwitch.spigotdebug.data;

import relampagorojo93.caketwitch.spigotdebug.DebugData;
import relampagorojo93.caketwitch.spigotdebug.DebugType;

public class DebugAlertData extends DebugData {

    public DebugAlertData(String message) {
        super(DebugType.ALERT, message);
    }

}
