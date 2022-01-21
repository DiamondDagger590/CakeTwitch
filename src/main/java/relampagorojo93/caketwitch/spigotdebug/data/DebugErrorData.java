package relampagorojo93.caketwitch.spigotdebug.data;

import relampagorojo93.caketwitch.spigotdebug.DebugData;
import relampagorojo93.caketwitch.spigotdebug.DebugType;

public class DebugErrorData extends DebugData {

    public DebugErrorData(String message) {
        super(DebugType.ERROR, message);
    }

}
