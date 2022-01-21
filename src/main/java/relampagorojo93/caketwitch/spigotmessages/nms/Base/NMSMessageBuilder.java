package relampagorojo93.caketwitch.spigotmessages.nms.Base;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import relampagorojo93.caketwitch.spigotmessages.enums.ChatColor;
import relampagorojo93.caketwitch.spigotmessages.enums.ChatMessageType;
import relampagorojo93.caketwitch.spigotmessages.instances.TextReplacement;
import relampagorojo93.caketwitch.spigotmessages.instances.TextResult;
import relampagorojo93.caketwitch.spigotmessages.interfaces.MessageBuilder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class NMSMessageBuilder implements MessageBuilder {

    public TextResult createMessage(String... messages) {
        return createMessage(true, messages);
    }

    @Override
    public TextResult createMessage(boolean color, String... messages) {
        return createMessage(new TextReplacement[]{}, color, messages);
    }

    @Override
    public TextResult createMessage(TextReplacement[] replacements, boolean color, String... messages) {
        long start = System.currentTimeMillis();
        List<BaseComponent[]> components = new ArrayList<>();
        for (String message : messages) {
            List<Entry<Entry<Integer, Integer>, TextReplacement>> split = new ArrayList<>();
            Arrays.sort(replacements, (replacement1,
                                       replacement2) -> replacement1.getTextToReplace().compareTo(replacement2.getTextToReplace()) * -1);
            int lastindex = 0;
            for (TextReplacement replacement : replacements) {
                while ((lastindex = message.indexOf(replacement.getTextToReplace(), lastindex)) != -1) {
                    int startindex = lastindex;
                    lastindex = startindex + replacement.getTextToReplace().length() - 1;
                    boolean dup = false;
					for (Entry<Entry<Integer, Integer>, TextReplacement> part : split) {
						if (lastindex >= part.getKey().getKey() && startindex <= part.getKey().getValue()) {
							dup = true;
							break;
						}
					}
                    lastindex++;
					if (dup) {
						continue;
					}
                    split.add(new AbstractMap.SimpleEntry<>(
						new AbstractMap.SimpleEntry<>(startindex, lastindex - 1), replacement));
                }
                lastindex = 0;
            }
            split.sort(Comparator.comparingInt((Entry<Entry<Integer, Integer>, TextReplacement> entry) -> entry.getKey().getKey()).thenComparingInt(entry -> entry.getKey().getValue()));
            int pos = 0;
            lastindex = 0;
            for (Entry<Entry<Integer, Integer>, TextReplacement> entry : new ArrayList<>(split)) {
				if (lastindex < entry.getKey().getKey()) {
					String part = message.substring(lastindex, entry.getKey().getKey());
					split.add(pos, new AbstractMap.SimpleEntry<>(
						new AbstractMap.SimpleEntry<>(lastindex, entry.getKey().getKey() - 1),
						new TextReplacement(null, () -> part)));
					pos += 2;
				}
				else {
					pos++;
				}
                lastindex = entry.getKey().getValue() + 1;
                continue;
            }
            if (lastindex < message.length()) {
                String part = message.substring(lastindex);
                split.add(new AbstractMap.SimpleEntry<Entry<Integer, Integer>, TextReplacement>(
                    new AbstractMap.SimpleEntry<Integer, Integer>(lastindex, message.length() - 1),
                    new TextReplacement(null, () -> part)));
            }
            List<BaseComponent> scomponents = new ArrayList<>();
            BaseComponent last = null;
            for (Entry<Entry<Integer, Integer>, TextReplacement> entry : split) {
                TextComponent bc = createComponent(color, entry.getValue());
                if (!bc.getExtra().isEmpty()) {
                    if (last != null) {
                        for (BaseComponent component : bc.getExtra()) {
							if (component.getColorRaw() == null && last.getColorRaw() != null) {
								component.setColor(last.getColorRaw());
							}
							if (component.isStrikethroughRaw() == null && last.isStrikethroughRaw() != null) {
								component.setStrikethrough(last.isStrikethroughRaw());
							}
							if (component.isItalicRaw() == null && last.isItalicRaw() != null) {
								component.setItalic(last.isItalicRaw());
							}
							if (component.isBoldRaw() == null && last.isBoldRaw() != null) {
								component.setBold(last.isBoldRaw());
							}
							if (component.isUnderlinedRaw() == null && last.isUnderlinedRaw() != null) {
								component.setUnderlined(last.isUnderlinedRaw());
							}
							if (component.isObfuscatedRaw() == null && last.isObfuscatedRaw() != null) {
								component.setObfuscated(last.isObfuscatedRaw());
							}
                        }
                    }
                    scomponents.add(bc);
                    last = bc.getExtra().get(bc.getExtra().size() - 1);
                }
            }
            components.add(scomponents.toArray(new BaseComponent[scomponents.size()]));
        }
        TextResult result = new TextResult(components);
        result.setTimeRequired(System.currentTimeMillis() - start);
        return result;
    }

    protected TextComponent createComponent(boolean color, TextReplacement textreplacement) {
        HoverEvent hoverevent = textreplacement.getHoverEvent() != null ? new HoverEvent(
            HoverEvent.Action.values()[textreplacement.getHoverEvent().getAction().ordinal()],
            new BaseComponent[]{createComponent(color, new TextReplacement(null, () -> textreplacement.getHoverEvent().getValue()))})
                                    : null;
        ClickEvent clickevent = textreplacement.getClickEvent() != null
                                    ? new ClickEvent(ClickEvent.Action.values()[textreplacement.getClickEvent().getAction().ordinal()],
            textreplacement.getClickEvent().getValue())
                                    : null;
        TextComponent tc = new TextComponent();
        tc.setHoverEvent(hoverevent);
        tc.setClickEvent(clickevent);
        String message = textreplacement.getText();
        message = message.replace("&", "§");
        net.md_5.bungee.api.ChatColor c = null;
        Boolean k, l, m, n, o;
        k = l = m = n = o = null;
        String[] msplit = message.split("§");
        for (int i = 0; i < msplit.length; i++) {
            String s = msplit[i];
            if (i != 0) {
                if (s.replace("\n", " ").matches("^[klmnorKLMNOR0-9a-fA-F].*")) {
                    if (color) {
                        switch (s.charAt(0)) {
                            case 'k':
                                k = true;
                                break;
                            case 'l':
                                l = true;
                                break;
                            case 'm':
                                m = true;
                                break;
                            case 'n':
                                n = true;
                                break;
                            case 'o':
                                o = true;
                                break;
                            case 'r':
                                k = l = m = n = o = false;
                                c = net.md_5.bungee.api.ChatColor.WHITE;
                                break;
                            default:
                                c = ChatColor.getByChar(s.charAt(0)).getColor();
                                break;
                        }
                    }
                    s = s.substring(1);
                }
                else if (s.replace("\n", " ").matches("^#[0-9a-fA-F]{6}.*")) {
					if (color) {
						c = ChatColor.rgbToColor(s.substring(0, 7));
					}
                    s = s.substring(7);
                }
                else if (s.replace("\n", " ").matches("^\\{.*\\}.*")) {
                    String settings = s.substring(1, s.indexOf('}'));
                    s = s.substring(settings.length() + 2);
                    if (color) {
                        for (String setting : settings.split(";")) {
                            String[] data = setting.split("=");
                            if (data.length > 0) {
                                switch (data[0].toLowerCase()) {
                                    case "color":
                                        if (data.length > 1) {
                                            data[1] = data[1].toUpperCase();
											if (data[1].length() == 1) {
												c = ChatColor.getByChar(data[1].charAt(0)).getColor();
											}
											else {
												try {
													if (data[1].startsWith("#")) {
														c = ChatColor.rgbToColor(data[1]);
													}
													else {
														c = ChatColor.getByName(data[1]).getColor();
													}
												}
												catch (Exception e) {
													c = null;
												}
											}
                                        }
                                        break;
                                    case "strikethrough":
                                        m = true;
                                        break;
                                    case "italic":
                                        o = true;
                                        break;
                                    case "bold":
                                        l = true;
                                        break;
                                    case "underlined":
                                        n = true;
                                        break;
                                    case "obfuscated":
                                        k = true;
                                        break;
                                    case "reset":
                                        k = l = m = n = o = Boolean.FALSE;
                                        c = ChatColor.WHITE.getColor();
                                        break;
                                }
                            }
                        }
                    }
                }
            }
            BaseComponent bc = new TextComponent(s);
            if (color) {
				if (c != null) {
					bc.setColor(c);
				}
				if (m != null) {
					bc.setStrikethrough(m);
				}
				if (o != null) {
					bc.setItalic(o);
				}
				if (l != null) {
					bc.setBold(l);
				}
				if (n != null) {
					bc.setUnderlined(n);
				}
				if (k != null) {
					bc.setObfuscated(k);
				}
            }
            tc.addExtra(bc);
        }
        return tc;
    }

    @Override
    public void sendTitle(Player player, boolean color, String title, String subtitle, int fadein, int stay, int fadeout) {
        player.sendTitle(createMessage(color, title).getStrings().get(0), createMessage(color, subtitle).getStrings().get(0), fadein, stay, fadeout);
    }

    @Override
    public void sendMessage(CommandSender[] senders, ChatMessageType type, TextResult result) {
        for (CommandSender sender:senders) {
            Player pl = sender instanceof Player ? (Player) sender : null;
            if (pl != null) {
                for (BaseComponent[] components:result.getComponents()) pl.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.values()[type.ordinal()], components);
            }
            else for (String line:result.getStrings()) sender.sendMessage(line);
        }
    }
}
