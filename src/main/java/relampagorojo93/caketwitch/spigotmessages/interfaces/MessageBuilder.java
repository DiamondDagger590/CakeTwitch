package relampagorojo93.caketwitch.spigotmessages.interfaces;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.LibsCollection.SpigotMessages.Enums.ChatMessageType;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextReplacement;
import relampagorojo93.LibsCollection.SpigotMessages.Instances.TextResult;

public abstract interface MessageBuilder {
	public abstract void sendTitle(Player player, boolean color, String title, String subtitle, int fadein, int stay, int fadeout);
	public abstract void sendMessage(CommandSender[] senders, ChatMessageType type, TextResult result);
	public abstract TextResult createMessage(String... messages);
	public abstract TextResult createMessage(boolean color, String... messages);
	public abstract TextResult createMessage(TextReplacement[] replacements, boolean color, String... messages);
}
