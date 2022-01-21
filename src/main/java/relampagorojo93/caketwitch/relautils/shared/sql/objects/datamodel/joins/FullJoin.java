package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.joins;

import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Column;

public class FullJoin extends Join {
    public FullJoin(Column append, Column origin) {
        super(append, origin);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("FULL OUTER ").append(super.toString()).toString();
    }
}
