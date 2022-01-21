package relampagorojo93.caketwitch.modules.ConfigPckg.Configurations;

import relampagorojo93.caketwitch.enums.NumericalCondition;

public class Condition {
	private NumericalCondition nc = NumericalCondition.EQ;
	private int value = 0;
	public Condition(String condition) {
		String remove = "";
		String value = "";
		for (char c:condition.toCharArray()) {
			if (c > 59 && c < 63) remove+=c;
			else if (c > 47 && c < 58) value+=c;
		}
		if (remove.equals(">")) nc = NumericalCondition.GT;
		else if (remove.equals(">=") || remove.equals("=>")) nc = NumericalCondition.GTEQ;
		else if (remove.equals("=")) nc = NumericalCondition.EQ;
		else if (remove.equals("<")) nc = NumericalCondition.LW;
		else if (remove.equals("<=") || remove.equals("=<")) nc = NumericalCondition.LWEQ;
		if (!remove.isEmpty()) this.value = Integer.parseInt(value);
	}
	public NumericalCondition getNumericalCondition() { return nc; }
	public int getValue() { return value; }
	public boolean matchCondition(int value) {
		switch (nc) {
			case EQ: return value == this.value;
			case GT: return value > this.value;
			case GTEQ: return value >= this.value;
			case LW: return value < this.value;
			case LWEQ: return value <= this.value;
			default: return false;
		}
	}
	public String toString() {
		return "Condition: " + nc.name() + " " + value;
	}
}
