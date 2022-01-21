package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.constraints;

import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Column;

import java.util.ArrayList;
import java.util.List;

public class UniqueConstraint {
    private List<Column> uniques = new ArrayList<>();

    public UniqueConstraint(Column... columns) {
        for (Column column : columns) {
            uniques.add(column);
        }
    }

    public List<Column> getUniques() {
        return uniques;
    }
}
