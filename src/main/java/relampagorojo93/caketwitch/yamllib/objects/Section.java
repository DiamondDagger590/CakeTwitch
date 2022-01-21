package relampagorojo93.caketwitch.yamllib.objects;

import java.util.ArrayList;
import java.util.List;

public class Section extends Data {
	private int level = 0;
	private Section parent = null;
	private String name = "";
	private List<Data> childs = new ArrayList<>();
	public Section() { this(null, null, null); }
	public Section(Object data) { this(null, null, data); }
	public Section(Section parent, String name) { this(parent, name, null); }
	public Section(Section parent, String name, Object data) { super(Type.SECTION, null); this.parent = parent; this.name = name; if (parent != null) level = parent.level + 1; setData(data); }
	public void addChild(Data child) { childs.add(child); }
	public void removeChild(Data child) { childs.remove(child); }
	public int getLevel() { return level; }
	public Section getParent() { return parent; }
	public List<Data> getChilds() { return childs; }
	public String getName() { return name; }
	public String getPath() {
		String path = name;
		for (Section parent = this.parent; parent != null && parent.getLevel() != 0; parent = parent.getParent()) path = parent.getName() + "." + path;
		return path;
	}
	public Section getParent(int level) {
		Section parent = this;
		for (int i = this.level; i > level; i--) if ((parent = parent.getParent()) == null) return null;
		return parent;
	}
	public Section getNonNullChild(String name) { return getChild(name, new Section()); }
	public Section getChild(String name) { return getChild(name, (Section) null); }
	public Section getChild(String name, Object def) {
		return def instanceof Section ? getChild(name, (Section) def) : getChild(name, new Section(def));
	}
	public Section getChild(String name, Section def) {
		for (Data data:childs) if (data instanceof Section && ((Section) data).getName().equals(name)) return (Section) data;
		return def;
	}
	@Override
	public boolean isSection() { return true; }
	@Override
	public Section asSection() { return this; }
}
