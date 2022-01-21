package relampagorojo93.caketwitch.spigotmessages.nms.v1_11_R1;

import org.bukkit.entity.Player;

public class NMSMessageBuilder extends relampagorojo93.LibsCollection.SpigotMessages.NMS.v1_10_R1.NMSMessageBuilder {
	
	@Override
	public void sendTitle(Player player, boolean color, String title, String subtitle, int fadein, int stay, int fadeout) {
		player.sendTitle(createMessage(color, title).getStrings().get(0), createMessage(color, subtitle).getStrings().get(0), fadein, stay, fadeout);
	}
	
}
