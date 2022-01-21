package relampagorojo93.caketwitch.modules.FilePckg.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MessageList {
	HELP_HEADER("Help-command.Header",
			Arrays.asList(" ", "&c・。・。・。・。・。・。・。%left_arrow%&r %current_page%/%max_page% %right_arrow%&c。・。・。・。・。・。・。・",
					" ")),
	HELP_BODY("Help-command.Body", Arrays.asList("&6%command_usage%", "  &8%command_description%")),
	HELP_FOOT("Help-command.Foot", Arrays.asList(" ", "&c・。・。・。・。・。・。・。・。・。・。・。・。・。・。・。・。・"));

	String path, oldpath;

	List<String> defaultcontent, content;

	MessageList(String path, List<String> defaultcontent) {
		this(path, path, defaultcontent);
	}

	MessageList(String path, String oldpath, List<String> defaultcontent) {
		this.path = path;
		this.oldpath = oldpath;
		this.defaultcontent = defaultcontent;
	}

	public String getPath() {
		return this.path;
	}

	public String getOldPath() {
		return this.oldpath;
	}

	public List<String> getDefaultContent() {
		return this.defaultcontent;
	}

	public List<String> toList() {
		List<String> list = new ArrayList<>();
		for (String l : (this.content != null) ? this.content : this.defaultcontent)
			list.add(l.replace("&", "\u00A7"));
		return list;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
}
