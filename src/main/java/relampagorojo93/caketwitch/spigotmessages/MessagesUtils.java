package relampagorojo93.caketwitch.spigotmessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.spigotmessages.interfaces.MessageBuilder;
import relampagorojo93.caketwitch.spigotmessages.nms.Base.NMSMessageBuilder;
import relampagorojo93.caketwitch.spigotmessages.objects.RGBColor;

import java.util.ArrayList;
import java.util.List;

public class MessagesUtils {

    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

    private static final MessageBuilder MESSAGE_BUILDER;

    static {
        MessageBuilder nms = new NMSMessageBuilder();
        MESSAGE_BUILDER = nms;
    }

    public static MessageBuilder getMessageBuilder() {
        return MESSAGE_BUILDER;
    }

    public static String strip(String msg) {
        return ChatColor.stripColor(color(msg));
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String round(double number, int decimals) {
        String s = String.format("%." + decimals + "f", new Object[]{Double.valueOf(number)}).replace(',', '.');
		if (s.startsWith("-0") && s.endsWith("00")) {
			s = s.replace("-", "");
		}
        return s;
    }

    public static void broadcast(String msg) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(msg);
		}
    }

    public static String generateBar(double d, int length, ChatColor chatcolor) {
        String text = "\u00A78[" + chatcolor.toString();
        int b = (int) (d * ((double) length));
        boolean color = true;
        for (int i = 0; i < length; i++) {
            if (color && i >= b) {
                text = String.valueOf(text) + "\u00A77";
                color = false;
            }
            text = String.valueOf(text) + "|";
        }
        text = String.valueOf(text) + "\u00A78]";
        return text;
    }

    public static String applyPalette(String message, RGBColor... colors) {
        StringBuilder sb = new StringBuilder();
        int lastcolor = -1;
        char[] array = message.toCharArray();
        for (int i = 0; i < array.length; i++) {
            int curcolor = (int) ((double) i / (double) array.length * colors.length);
			if (curcolor > lastcolor) {
				sb.append(colors[lastcolor = curcolor].toSpigotColor());
			}
            sb.append(array[i]);
        }
        return sb.toString();
    }

    public static String applyGradient(String message, RGBColor color1, RGBColor color2) {
        StringBuilder sb = new StringBuilder();
        char[] array = message.toCharArray();
        for (int i = 0; i < array.length; i++) {
            double percent = (double) i / (double) array.length;
            sb.append("&#");
            sb.append(Integer.toHexString(color1.getRed() + (int) ((color2.getRed() - color1.getRed()) * percent)));
            sb.append(Integer.toHexString(color1.getGreen() + (int) ((color2.getGreen() - color1.getGreen()) * percent)));
            sb.append(Integer.toHexString(color1.getBlue() + (int) ((color2.getBlue() - color1.getBlue()) * percent)));
            sb.append(array[i]);
        }
        return sb.toString();
    }

    public static String[] splitByLines(String str, int maxperline) {
        List<String> list = new ArrayList<>();
        str = str.trim();
        while (str.length() > 0) {
            if (str.length() < maxperline + 1) {
                list.add(str.substring(0, str.length()));
                break;
            }
            int lastspace = str.lastIndexOf(' ', maxperline);
            int nextspace = str.indexOf(' ', maxperline);
			if (lastspace == -1 || nextspace == maxperline) {
				list.add(str.substring(0, maxperline));
			}
			else {
				list.add(str.substring(0, lastspace));
			}
            str = str.substring(lastspace == -1 || nextspace == maxperline ? maxperline : lastspace + 1).trim();
        }
        return list.toArray(new String[list.size()]);
    }
}
