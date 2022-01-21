package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.joins;

import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Column;

public class LeftJoin extends Join {
    public LeftJoin(Column append, Column origin) {
        super(append, origin);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("LEFT OUTER ").append(super.toString()).toString();
    }
}
