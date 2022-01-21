package relampagorojo93.caketwitch.yamllib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class YAMLUtils {
    public static void createYml(File f) {
        try {
            f.createNewFile();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createYml(File f, InputStream s) {
        createYml(f);
        try {
            Writer w = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);
            Reader r = new InputStreamReader(s, StandardCharsets.UTF_8);
            int i = 0;
			while ((i = r.read()) != -1) {
				w.write(i);
			}
            w.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
