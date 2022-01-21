package relampagorojo93.caketwitch.spigotmessages.enums;

import java.lang.reflect.Method;

public enum ChatColor {
	BLACK('0', net.md_5.bungee.api.ChatColor.BLACK), DARK_BLUE('1', net.md_5.bungee.api.ChatColor.DARK_BLUE),
	DARK_GREEN('2', net.md_5.bungee.api.ChatColor.DARK_GREEN), DARK_AQUA('3', net.md_5.bungee.api.ChatColor.DARK_AQUA),
	DARK_RED('4', net.md_5.bungee.api.ChatColor.DARK_RED), DARK_PURPLE('5', net.md_5.bungee.api.ChatColor.DARK_PURPLE),
	GOLD('6', net.md_5.bungee.api.ChatColor.GOLD), GRAY('7', net.md_5.bungee.api.ChatColor.GRAY),
	DARK_GRAY('8', net.md_5.bungee.api.ChatColor.DARK_GRAY), BLUE('9', net.md_5.bungee.api.ChatColor.BLUE),
	GREEN('a', net.md_5.bungee.api.ChatColor.GREEN), AQUA('b', net.md_5.bungee.api.ChatColor.AQUA),
	RED('c', net.md_5.bungee.api.ChatColor.RED), LIGHT_PURPLE('d', net.md_5.bungee.api.ChatColor.LIGHT_PURPLE),
	YELLOW('e', net.md_5.bungee.api.ChatColor.YELLOW), WHITE('f', net.md_5.bungee.api.ChatColor.WHITE),
	MAGIC('k', net.md_5.bungee.api.ChatColor.MAGIC), BOLD('l', net.md_5.bungee.api.ChatColor.BOLD),
	STRIKETHROUGH('m', net.md_5.bungee.api.ChatColor.STRIKETHROUGH),
	UNDERLINE('n', net.md_5.bungee.api.ChatColor.UNDERLINE), ITALIC('o', net.md_5.bungee.api.ChatColor.ITALIC),
	RESET('r', net.md_5.bungee.api.ChatColor.RESET);

	private char c;
	private net.md_5.bungee.api.ChatColor color;

	ChatColor(char c, net.md_5.bungee.api.ChatColor color) {
		this.c = c;
		this.color = color;
	}

	public char getChar() {
		return c;
	}

	public net.md_5.bungee.api.ChatColor getColor() {
		return color;
	}

	public static ChatColor getByChar(char c) {
		for (ChatColor color : values())
			if (color.getChar() == c)
				return color;
		return ChatColor.RESET;
	}

	public static ChatColor getByName(String name) {
		for (ChatColor color : values())
			if (color.name().equalsIgnoreCase(name))
				return color;
		return ChatColor.RESET;
	}

	private static Boolean rgb = null;
	private static Method of = null;
	
	public static boolean rgbIsAvailable() {
		if (rgb == null)
			try {
				of = net.md_5.bungee.api.ChatColor.class.getMethod("of", String.class);
				rgb = true;
			} catch (Exception e) {
				rgb = false;
			}
		return rgb.booleanValue();
	}

	public static net.md_5.bungee.api.ChatColor rgbToColor(String color) {
		color.toUpperCase();
		if (rgbIsAvailable())
			try {
				return (net.md_5.bungee.api.ChatColor) of.invoke(null, color);
			} catch (Exception e) {}
		int v = 0;
		for (char c : color.toCharArray())
			v += c > 47 && c < 58 ? c - 48 : c > 64 && c < 71 ? c - 55 : 0;
		v /= color.length();
		if (v < 10)
			v += 48;
		else
			v += 87;
		return getByChar((char) v).getColor();
	}
}
