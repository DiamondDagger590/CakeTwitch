package relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.constraints;

import relampagorojo93.caketwitch.relautils.shared.sql.objects.datamodel.Column;

import java.util.List;

public class ForeignConstraint {
    private List<Column> origin;
    private List<Column> reference;

    public ForeignConstraint(List<Column> origin, List<Column> reference) {
        this.origin = origin;
        this.reference = reference;
    }

    public ForeignConstraint addOrigin(Column column) {
        origin.add(column);
        return this;
    }

    public ForeignConstraint addOrigins(Column... columns) {
        for (Column column : columns) {
            addOrigin(column);
        }
        return this;
    }

    public ForeignConstraint addReference(Column column) {
        reference.add(column);
        return this;
    }

    public ForeignConstraint addReferences(Column... columns) {
        for (Column column : columns) {
            addReference(column);
        }
        return this;
    }

    public List<Column> getOrigins() {
        return origin;
    }

    public List<Column> getReferences() {
        return reference;
    }
}
