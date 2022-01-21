package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.Conditions;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.ConditionType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Data;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Column;

import java.util.ArrayList;
import java.util.List;

public class Condition {
	private Object first, second;
	private ConditionType type;
	private Condition (Object first, Object second, ConditionType type) {
		this.first = first;
		this.second = second;
		this.type = type;
	}
	public Condition(Condition...conditions) { this((Object) conditions, (Object) null, ConditionType.MULTI_CONDITION); }
	public Condition(Column first, List<?> second, ConditionType type) { this((Object) first, (Object) second, type); }
	public Condition(Column first, Column second, ConditionType type) { this((Object) first, (Object) second, type); }
	public Condition(Column first, Data second, ConditionType type) { this((Object) first, (Object) second, type); }
	public Condition(Data first, Column second, ConditionType type) { this((Object) first, (Object) second, type); }
	public Condition(Data first, Data second, ConditionType type) { this((Object) first, (Object) second, type); }
	public Condition (Column first, ConditionType type) { this((Object) first, (Object) null, type); }
	public Condition (Data first, ConditionType type) { this((Object) first, (Object) null, type); }
	public Condition(ConditionType type) { this((Object) null, (Object) null, type); }
	public List<Data> getData() {
		List<Data> data = new ArrayList<>();
		if (type == ConditionType.MULTI_CONDITION) {
			for (Condition condition:(Condition[]) first) data.addAll(condition.getData());
		}
		else {
			if (first instanceof Data) data.add((Data) first);
			if (second instanceof Data) data.add((Data) second);
		}
		return data;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		switch (type) {
			case MULTI_CONDITION:
				sb.append("(");
				Condition[] conditions = (Condition[]) first;
				for (int i = 0; i < conditions.length; i++) sb.append(" ").append(conditions[i].toString());
				sb.append(" )");
				break;
			default:
				if (first == null) sb.append(type.name());
				else {
					if (first instanceof Data) sb.append("?");
					else if (first instanceof Column) {
						Column col = (Column) first;
						sb.append(col.getTable().getName()).append(".").append(col.getName());

					}
					sb.append(" ");
					if (second != null) {
						switch (type) {
							case EQUAL: sb.append("="); break;
							case GREATER: sb.append(">"); break;
							case GREATER_OR_EQUAL: sb.append(">="); break;
							case LESS: sb.append("<"); break;
							case LESS_OR_EQUAL: sb.append("<="); break;
							case NOT_EQUAL: sb.append("<>"); break;
							case IN: sb.append("IN"); break;
							default: break;
						}
						sb.append(" ");
						if (second instanceof Data) sb.append("?");
						else if (second instanceof Column) {
							Column col = (Column) second;
							sb.append(col.getTable().getName()).append(".").append(col.getName()).append(" ");
						}
						else if (second instanceof List<?>) {
							List<?> list = (List<?>) second;
							sb.append("(");
							for (int i = 0; i < list.size(); i++) {
								if (i > 0) sb.append(",");
								Object o = list.get(i);
								if (o instanceof String) sb.append("'" + (String) o + "'");
								else sb.append(o);
							}
							sb.append(")");
						}
					}
					else {
						switch (type) {
							case IS_NULL: sb.append("IS NULL"); break;
							case IS_NOT_NULL: sb.append("IS NOT NULL"); break;
							default: break;
						}
					}
				}
				break;
		}
		return sb.toString();
	}
}
