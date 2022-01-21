package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.joins;


import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Column;

public class InnerJoin extends Join {
    public InnerJoin(Column append, Column origin) {
        super(append, origin);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("INNER ").append(super.toString()).toString();
    }
}
