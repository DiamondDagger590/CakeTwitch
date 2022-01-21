package relampagorojo93.caketwitch.yamllib;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            YAMLFile file = new YAMLFile(new File("Lang.yml"));
            File save = new File("Lang2.yml");
            save.createNewFile();
            file.saveYAML(save);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
