package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.DataModel.Joins;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Column;

public class FullJoin extends Join {
	public FullJoin(Column append, Column origin) { super(append, origin); }
	@Override
	public String toString() { return new StringBuilder().append("FULL OUTER ").append(super.toString()).toString(); }
}
