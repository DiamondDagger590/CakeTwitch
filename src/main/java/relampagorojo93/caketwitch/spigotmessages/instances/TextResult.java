package relampagorojo93.caketwitch.spigotmessages.instances;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import relampagorojo93.caketwitch.spigotmessages.MessagesUtils;
import relampagorojo93.caketwitch.spigotmessages.enums.ChatMessageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextResult {

    private List<BaseComponent[]> components = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private long timerequired = 0L;

    public TextResult(BaseComponent[]... components) {
        this(Arrays.asList(components));
    }

    public TextResult(List<BaseComponent[]> components) {
        this.components = components;
        StringBuilder sb = new StringBuilder();
        for (BaseComponent[] component : components) {
			if (component.length == 0) {
				strings.add("");
			}
			else {
				for (BaseComponent line : component) {
					sb.append(line.toLegacyText());
				}
				String result = sb.toString();
				int i = 0;
				while (i + 1 < result.length() && result.charAt(i) == '§' && result.charAt(i + 1) == 'f') {
					i += 2;
				}
				result.substring(i);
				strings.add(result);
				sb.setLength(0);
			}
        }
    }

    public List<BaseComponent[]> getComponents() {
        return components;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void sendMessage(CommandSender... senders) {
        MessagesUtils.getMessageBuilder().sendMessage(senders, ChatMessageType.CHAT, this);
    }

    public void sendTitle(CommandSender... senders) {
        MessagesUtils.getMessageBuilder().sendMessage(senders, ChatMessageType.TITLE, this);
    }

    public void sendActionBar(CommandSender... senders) {
        MessagesUtils.getMessageBuilder().sendMessage(senders, ChatMessageType.ACTION_BAR, this);
    }

    public void sendSystem(CommandSender... senders) {
        MessagesUtils.getMessageBuilder().sendMessage(senders, ChatMessageType.SYSTEM, this);
    }

    public void setTimeRequired(long timerequired) {
        this.timerequired = timerequired;
    }

    public long getTimeRequired() {
        return timerequired;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : strings) {
			if (sb.length() != 0) {
				sb.append("\r\n");
			}
            sb.append(line);
        }
        return sb.toString();
    }

}
