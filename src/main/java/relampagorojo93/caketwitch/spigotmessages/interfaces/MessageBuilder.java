package relampagorojo93.caketwitch.spigotmessages.interfaces;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.spigotmessages.enums.ChatMessageType;
import relampagorojo93.caketwitch.spigotmessages.instances.TextReplacement;
import relampagorojo93.caketwitch.spigotmessages.instances.TextResult;

public interface MessageBuilder {
    void sendTitle(Player player, boolean color, String title, String subtitle, int fadein, int stay, int fadeout);

    void sendMessage(CommandSender[] senders, ChatMessageType type, TextResult result);

    TextResult createMessage(String... messages);

    TextResult createMessage(boolean color, String... messages);

    TextResult createMessage(TextReplacement[] replacements, boolean color, String... messages);
}
