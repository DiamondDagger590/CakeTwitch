package relampagorojo93.caketwitch.spigotmessages.instances;

public class TextReplacement {
	private Text text;
	private ClickEvent clickevent;
	private HoverEvent hoverevent;
	private String texttoreplace, stext;
	public TextReplacement(String texttoreplace, Text text) {
		this(texttoreplace, text, null, null);
	}
	public TextReplacement(String texttoreplace, Text text, ClickEvent clickevent) {
		this(texttoreplace, text, clickevent, null);
	}
	public TextReplacement(String texttoreplace, Text text, HoverEvent hoverevent) {
		this(texttoreplace, text, null, hoverevent);
	}
	public TextReplacement(String texttoreplace, Text text, ClickEvent clickevent, HoverEvent hoverevent) {
		this.texttoreplace = texttoreplace;
		this.text = text;
		this.clickevent = clickevent;
		this.hoverevent = hoverevent;
	}
	public String getTextToReplace() {
		return texttoreplace;
	}
	public String getText() {
		return stext != null ? stext : (stext = text.getText());
	}
	public ClickEvent getClickEvent() {
		return clickevent;
	}
	public HoverEvent getHoverEvent() {
		return hoverevent;
	}
	public static interface Text { String getText(); }
}
