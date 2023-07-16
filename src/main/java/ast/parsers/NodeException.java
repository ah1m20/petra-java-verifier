package ast.parsers;

import com.github.javaparser.ast.Node;

public final class NodeException extends RuntimeException {
    private final Node node;
    public NodeException(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}
