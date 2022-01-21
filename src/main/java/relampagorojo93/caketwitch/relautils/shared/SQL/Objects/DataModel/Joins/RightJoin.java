package relampagorojo93.caketwitch.relautils.shared.SQL.Objects.DataModel.Joins;

import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Column;

public class RightJoin extends Join {
	public RightJoin(Column append, Column origin) { super(append, origin); }
	@Override
	public String toString() { return new StringBuilder().append("RIGHT OUTER ").append(super.toString()).toString(); }
}
