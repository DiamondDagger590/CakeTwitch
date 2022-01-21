package relampagorojo93.caketwitch.spigotmessages.nms.v1_8_R2;

import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_8_R2.ChatMessage;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotMessages.Enums.ChatMessageType;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextResult;

public class NMSMessageBuilder extends relampagorojo93.LibsCollection.SpigotMessages.NMS.Base.NMSMessageBuilder {
	
	@Override
	public void sendTitle(Player player, boolean color, String title, String subtitle, int fadein, int stay, int fadeout) {
		PlayerConnection pc = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutTitle ptimes = new PacketPlayOutTitle(fadein, stay, fadeout),
				ptitle = title != null ? new PacketPlayOutTitle(EnumTitleAction.TITLE, new ChatMessage(createMessage(color, title).getStrings().get(0))) : null,
				psubtitle = title != null ? new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, new ChatMessage(createMessage(color, subtitle).getStrings().get(0))) : null;
		if (ptitle != null || psubtitle != null) {
			pc.sendPacket(ptimes);
			if (ptitle != null) pc.sendPacket(ptitle);
			if (psubtitle != null) pc.sendPacket(psubtitle);
		}
	}
	
	@Override
	public void sendMessage(CommandSender[] senders, ChatMessageType type, TextResult result) {
		for (CommandSender sender:senders) {
			Player pl = sender instanceof Player ? (Player) sender : null;
			if (pl != null) {
				PacketPlayOutChat message = new PacketPlayOutChat(null, (byte) type.ordinal());
				for (BaseComponent[] components:result.getComponents()) {
					message.components = components;
					((CraftPlayer) pl).getHandle().playerConnection.sendPacket(message);
				}
			}
			else for (String line:result.getStrings()) sender.sendMessage(line);
		}
	}
	
}
