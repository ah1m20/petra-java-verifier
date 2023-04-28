package ast.terms;

public class Beta {
    private final String variableName;
    private final String objectIdentifier;

    public Beta(String variableName, String objectIdentifier) {
        this.variableName = variableName;
        this.objectIdentifier = objectIdentifier;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }
}
