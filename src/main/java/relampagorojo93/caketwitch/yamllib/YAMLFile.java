package relampagorojo93.caketwitch.yamllib;

import relampagorojo93.LibsCollection.YAMLLib.Objects.Comment;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Data;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Section;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Space;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Type;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class YAMLFile {
	private long start,end;
	private Section root;
	public YAMLFile() { reset(); }
	public YAMLFile(File file) throws IOException { loadYAML(file); }
	public void loadYAML(File file) throws IOException {
		reset();
		start = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		try {
			readSections(reader, root);
			for (int i = root.getChilds().size() - 1; i >= 0; i--) {
				Data data = root.getChilds().get(i);
				if (data instanceof Space) root.removeChild(data);
				else break;
			}
		} catch (IOException e) {
			reset();
			throw e;
		}
		reader.close();
		end = System.currentTimeMillis();
	}
	public void saveYAML(File file) throws IOException {
		start = System.currentTimeMillis();
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
		writer.write(writeSections(new StringBuilder(), root).toString());
		writer.close();
		end = System.currentTimeMillis();
	}
	public Section setSection(String section, Object data) {
		Section actual = root;
		String[] sname = section.split("\\.");
		for (int p = 0; p < sname.length; p++) {
			String path = actual.getPath();
			path+= (path.isEmpty() ? "" : ".") + sname[p];
			Section sec = getSection(path);
			if (sec == null) {
				sec = new Section(actual, sname[p]);
				actual.addChild(sec);
			}
			actual = sec;
		}
		if (data != null) {
			if (data instanceof List) actual.setData(data);
			else actual.setData(data.toString());
			for (Section parent = actual.getParent(); parent != null; parent = parent.getParent()) parent.setData(null);
		}
		return actual;
	}
	public void removeSection(String section) {
		Section sec = getSection(section);
		if (sec != null) for (;sec != null && sec.getChilds().isEmpty();) {
			if (sec.getParent() != null) sec.getParent().removeChild(sec); sec = sec.getParent();
		}
	}
	public Section getNonNullSection(String section) { return getSection(section, new Section()); }
	public Section getSection(String section) { return getSection(section, (Section) null); }
	public Section getSection(String section, Object def) {
		return def instanceof Section ? getSection(section, (Section) def) : getSection(section, new Section(def));
	}
	public Section getSection(String section, Section def) {
		String[] split = section.split("\\.");
		Section s = root;
		for (int i = 0; i < split.length; i++) for (Data data:s.getChilds()) if (data.isSection() && data.asSection().getName().equals(split[i])) {
			s = data.asSection(); break;
		}
		return s.getPath().equals(section) ? s : def;
	}
	public List<Section> getSections() {
		List<Section> childs = new ArrayList<>();
		privateGetSections(childs, root);
		return childs;
	}
	private void privateGetSections(List<Section> sections, Section cursection) {
		if (cursection.getChilds().isEmpty())
			sections.add(cursection);
		else
			for (Data data:cursection.getChilds())
				if (data.isSection())
					privateGetSections(sections, data.asSection());
	}
	public long getRequiredTime() { return end-start; }
	public void reset() {
		end = start = 0L;
		root = new Section(null, "");
	}
	private String getCompleteLine(BufferedReader r, String line) throws IOException {
		char c = line.charAt(0);
		if (c == '"' || c == '\'') {
			int last = -1;
			while (line.length() < 2 || (last = line.lastIndexOf(c)) < 1 || line.charAt(last - 1) == '\\') {
				if (last > 0 && line.charAt(last - 1) == '\\') System.out.println(line.charAt(last - 1) + String.valueOf(line.charAt(last)));
				String next = r.readLine();
				if (next == null) {
					line += (char) c; break;
				}
				line += "\n" + next;
			}
			line = line.substring(1, last);
			String[] parts = line.split(String.valueOf((char) 10));
			line = parts.length > 0 ? parts[0] : line;
			for (int i = 1; i < parts.length; i++) line += " " + parts[i].trim();
			line = line.replaceAll("\\\\" + String.valueOf(c), String.valueOf(c));
		}
		return line;
	}
	private String readSections(BufferedReader r, Section section) throws IOException {
		String lastline = null;
		String line = "";
		while (true) {
			if (lastline == null) {
				line = r.readLine();
				if (line == null) break;
			}
			else {
				line = lastline;
				lastline = null;
			}
			int loc = 0;
			while (line.length() > loc && line.charAt(loc) == ' ') loc++;
			if (loc < (section.getLevel() - 1) * 2) break;
			if (section.getLevel() != 0 && loc == (section.getLevel() - 1) * 2) {
				if (!line.substring(loc).isEmpty() && line.substring(loc).charAt(0) == '-') {
					line = line.substring(loc + 1).trim();
					if (section.getData() == null) section.setData(new ArrayList<String>());
					List<String> list = section.getStringList();
					if (list != null) {
						if (!line.isEmpty()) line = getCompleteLine(r, line);
						list.add(line);
					}
					else throw new IOException("Trying to add multiline strings on a section with non-multiline data:\r\n\r\n" + line + "\r\n\r\n");
				}
				else break;
			}
			else if (loc == section.getLevel() * 2) {
				line = line.substring(loc);
				if (line.isEmpty()) section.addChild(new Space());
				else if (line.charAt(0) == '#') section.addChild(new Comment(line));
				else if (line.charAt(0) == '-') {
					line = line.substring(1).trim();
					if (section.getData() == null) section.setData(new ArrayList<String>());
					List<String> list = section.getStringList();
					if (list != null) {
						if (!line.isEmpty()) line = getCompleteLine(r, line);
						list.add(line);
					}
					else throw new IOException("Trying to add multiline strings on a section with non-multiline data:\r\n\r\n" + (line.length() > 16 ? line.substring(0, 16) + "..." : line) + "\r\n\r\n");
				}
				else {
					int sep = 0;
					if ((sep = line.indexOf(':')) != -1 && sep > 0 && line.charAt(sep - 1) != '\\') {
						if (section.getData() != null) throw new IOException("Trying to add sections on a section of only one data:\r\n\r\n" + (line.length() > 16 ? line.substring(0, 16) + "..." : line) + "\r\n\r\n");
						String[] sec = new String[] { line.substring(0, sep).replace("'", "").replace("\"", ""), line.substring(sep + 1).trim() };
						Object data = null;
						if (!sec[1].isEmpty()) {
							if (sec[1].startsWith("[") && sec[1].endsWith("]")) {
								List<String> list = new ArrayList<>();
								String[] objs = sec[1].substring(1, sec[1].length() - 1).split(",");
								for (String obj:objs) if (!(obj = obj.trim()).isEmpty()) list.add(obj);
								data = list;
							}
							else if (!sec[1].startsWith("#")) data = getCompleteLine(r, sec[1]);
						}
						String path = section.getPath();
						if (!path.isEmpty()) path+=".";
						path+=sec[0];
						lastline = readSections(r, setSection(path, data));
					}
					else if (section.getType() == Type.STRING) section.setData(section.getString() + " " + line);
					else if (section.getType() == Type.LIST) {
						List<String> list = section.getStringList();
						if (list.size() > 0) list.set(list.size() - 1, list.get(list.size() - 1) + " " + line);
						else throw new IOException("Unexpected string list initialize:\r\n\r\n" + (line.length() > 16 ? line.substring(0, 16) + "..." : line) + "\r\n\r\n");
					}
					else if (section.getData() != null) throw new IOException("Trying to set a different kind of data to an already initialized section:\r\n\r\n" + (line.length() > 16 ? line.substring(0, 16) + "..." : line) + "\r\n\r\n");
					else throw new IOException("Trying to initialize a section from a wrong site:\r\n\r\n" + (line.length() > 16 ? line.substring(0, 16) + "..." : line) + "\r\n\r\n");
				}
			}
			else throw new IOException("This line is in an incorrect level (level " + loc + " on '" + section.getPath() + "' with level " + section.getLevel() + "):\r\n\r\n" + (line.length() > 16 ? line.substring(0, 16) + "..." : line) + "\r\n\r\n");
			line = null;
		}
		return line;
	}
	private StringBuilder writeSections(StringBuilder w, Section section) throws IOException {
		String space = "";
		for (int i = 0; i < (section.getLevel()-1)*2; i++) space+=" ";
		if (!section.getName().isEmpty()) {
			if (section.getData() != null || !section.getChilds().isEmpty()) w.append(space + section.getName() + ": ");
			if (section.getData() != null) {
				switch (section.getType()) {
					case DECIMAL: w.append(section.getDouble().toString()); break;
					case INTEGER: w.append(section.getLong().toString()); break;
					case LIST:
						List<String> list = section.getStringList();
						if (list.isEmpty()) w.append("[]");
						else {
							for (String str:list) {
								if (str.isEmpty()) str = "''";
								else if (str.startsWith(" ") || str.endsWith(" ") || str.contains(":")) str = "'" + str + "'";
								int pos = 0;
								while ((pos = str.indexOf(' ', pos + 64)) != -1 && (pos == str.length() - 1 || str.charAt(pos + 1) != ' ')) str = str.substring(0, pos) + "\n  " + space + str.substring(pos + 1);
								w.append("\n" + space + "- " + str);
							}
						}
						break;
					case STRING:
						String text = section.getString();
						if (text.isEmpty()) text = "''";
						else if (text.startsWith(" ") || text.endsWith(" ") || text.contains(":")) text = "'" + text + "'";
						int pos = 0;
						while ((pos = text.indexOf(' ', pos + 64)) != -1 && (pos == text.length() - 1 || text.charAt(pos + 1) != ' ')) text = text.substring(0, pos) + "\n  " + space + text.substring(pos + 1);
						w.append(text);
						break;
					default:
						break;
						
				}
			}
			w.append("\n");
		}
		for (int i = space.length(); i < section.getLevel()*2; i++) space+=" ";
		for (Data data:section.getChilds()) {
			if (data.getType() == Type.COMMENT) w.append(space + (String) ((Comment) data).getData() + "\n");
			else if (data.getType() == Type.SPACE) w.append(space + "\n");
			else writeSections(w, (Section) data);
		}
		return w;
	}
	public String toString() {
		try {
			return writeSections(new StringBuilder(), root).toString();
		} catch (IOException e) {}
		return "";
	}
}