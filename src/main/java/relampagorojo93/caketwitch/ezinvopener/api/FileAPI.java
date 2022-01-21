package relampagorojo93.caketwitch.ezinvopener.api;

import relampagorojo93.caketwitch.ezinvopener.filepckg.FileModule;

import java.io.File;

public class FileAPI {
    private FileModule file;

    public FileAPI(FileModule file) {
        this.file = file;
    }

    public File getEntitiesFile() {
        return file.ENTITIES_FILE;
    }

    public File getPluginFolder() {
        return file.PLUGIN_FOLDER;
    }

    public File getMMOHorsesNPCsFile() {
        return file.MMOHORSES_NPCS_FILE;
    }

    public File getMMOHorsesFolder() {
        return file.MMOHORSES_PLUGIN_FOLDER;
    }
}
