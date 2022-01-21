package relampagorojo93.caketwitch.api.hooks;

import org.bukkit.Bukkit;
import relampagorojo93.caketwitch.api.CakeTwitchAPI;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamerInventory;
import relampagorojo93.caketwitch.bukkit.inventories.twitch.StreamersInventory;
import relampagorojo93.caketwitch.ezinvopener.api.EIOAPI;
import relampagorojo93.caketwitch.ezinvopener.api.objects.EzInventory;
import relampagorojo93.caketwitch.modules.filepckg.messages.MessageString;
import relampagorojo93.caketwitch.modules.filepckg.settings.SettingBoolean;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;

public class EIOpenerAPI {
    private static boolean hooked = false;

    public EIOpenerAPI() {
        hooked = false;
		if (SettingBoolean.HOOKS_EIOPENER.toBoolean()) {
			MessagesUtils.getMessageBuilder()
				.createMessage(
					MessageString.applyPrefix("<EIOpener> Hook is enabled. Finding EzInvOpener."))
				.sendMessage(Bukkit.getConsoleSender());
			if (Bukkit.getPluginManager().getPlugin("EzInvOpener") != null) {
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString
						.applyPrefix("<EIOpener> EzInvOpener found. Creating ez inventories."))
					.sendMessage(Bukkit.getConsoleSender());
				EIOAPI.getInvAPI().registerInventory(new EzInventory(CakeTwitchAPI.getPlugin(), "streamer-menu",
					(player) -> StreamerInventory.getEzInventory(player)));
				EIOAPI.getInvAPI().registerInventory(new EzInventory(CakeTwitchAPI.getPlugin(), "streamers-menu",
					(player) -> StreamersInventory.getEzInventory(player)));
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString.applyPrefix("<EIOpener> Done!"))
					.sendMessage(Bukkit.getConsoleSender());
				hooked = true;
			}
			else {
				MessagesUtils.getMessageBuilder()
					.createMessage(MessageString
						.applyPrefix("<EIOpener> EzInvOpener not found. Ignoring its implementation."))
					.sendMessage(Bukkit.getConsoleSender());
			}
		}
		else {
			MessagesUtils.getMessageBuilder()
				.createMessage(MessageString
					.applyPrefix("<EIOpener> Hook is disabled. Ignoring its implementation."))
				.sendMessage(Bukkit.getConsoleSender());
		}
    }

    public static boolean isHooked() {
        return hooked;
    }
}
