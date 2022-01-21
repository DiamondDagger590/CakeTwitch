package relampagorojo93.caketwitch.api.hooks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import relampagorojo93.caketwitch.api.hooks.objects.papi.PlaceHolder;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingBoolean;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

public class PlaceholderAPI {
    private static boolean hooked = false;

    public PlaceholderAPI() {
        hooked = false;
		if (SettingBoolean.HOOKS_PLACEHOLDERAPI.toBoolean()) {
			MessagesUtils.getMessageBuilder()
				.createMessage(MessageString
					.applyPrefix("<PlaceholderAPI> Hook is enabled. Finding PlaceholderAPI."))
				.sendMessage(Bukkit.getConsoleSender());
			if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString
						.applyPrefix("<PlaceholderAPI> PlaceholderAPI found. Creating pladeholders."))
					.sendMessage(Bukkit.getConsoleSender());
				new PlaceHolder().register();
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.applyPrefix("<PlaceholderAPI> Done!"))
					.sendMessage(Bukkit.getConsoleSender());
				hooked = true;
			}
			else {
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString
						.applyPrefix("<PlaceholderAPI> PlaceholderAPI not found. Ignoring its implementation."))
					.sendMessage(Bukkit.getConsoleSender());
			}
		}
		else {
			MessagesUtils.getMessageBuilder()
				.createMessage(MessageString
					.applyPrefix("<PlaceholderAPI> Hook is disabled. Ignoring its implementation."))
				.sendMessage(Bukkit.getConsoleSender());
		}
    }

    public static boolean isHooked() {
        return hooked;
    }

    public static String processPlaceholders(OfflinePlayer player, String string) {
        return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, string);
    }
}
