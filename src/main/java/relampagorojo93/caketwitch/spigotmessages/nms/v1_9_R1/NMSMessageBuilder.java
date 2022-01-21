package relampagorojo93.caketwitch.spigotmessages.nms.v1_9_R1;

import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotMessages.Enums.ChatMessageType;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextResult;

public class NMSMessageBuilder extends relampagorojo93.LibsCollection.SpigotMessages.NMS.Base.NMSMessageBuilder {
	
	@SuppressWarnings("deprecation")
	@Override
	public void sendTitle(Player player, boolean color, String title, String subtitle, int fadein, int stay, int fadeout) {
		player.sendTitle(createMessage(color, title).getStrings().get(0), createMessage(color, subtitle).getStrings().get(0));
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
