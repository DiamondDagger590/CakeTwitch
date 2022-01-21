package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.joins;

import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Column;

public class RightJoin extends Join {
    public RightJoin(Column append, Column origin) {
        super(append, origin);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("RIGHT OUTER ").append(super.toString()).toString();
    }
}
