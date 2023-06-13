package ast.terms;



public class Beta {
    private final String fieldId;
    private final String objectId;

    public Beta(String fieldId, String objectId) {
        this.fieldId = fieldId;
        this.objectId = objectId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"@"+this.hashCode();
    }
}
