package relampagorojo93.caketwitch.spigotdebug.data;

import relampagorojo93.caketwitch.spigotdebug.DebugData;
import relampagorojo93.caketwitch.spigotdebug.DebugType;

public class DebugLogData extends DebugData {

    public DebugLogData(String message) {
        super(DebugType.LOG, message);
    }

}
