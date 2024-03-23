package com.cognitionbox.petra.ast.interp.util.loggers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cognitionbox.petra.ast.interp.util.Ops.mapsto;

public final class DotDiagramLogger {

    public StringBuilder allSubgraphs = new StringBuilder();
    private AtomicInteger vertexId = new AtomicInteger();
    public void startSubgraph(String m) {
        allSubgraphs.append("subgraph cluster_"+m+"{"+"\n");
        allSubgraphs.append("label=\""+m+"\""+"\n");
    }

    public void addMethodTransitions(String method, Set<Map.Entry<String, String>> entries) {
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            // Create key node
            allSubgraphs.append(formatNode(vertexId.getAndIncrement(), key));

            // Create value node
            allSubgraphs.append(formatNode(vertexId.getAndIncrement(), value));

            // Create directed edge from key to value
            allSubgraphs.append(formatEdge(vertexId.get() - 2, vertexId.get() - 1,method));
        }
    }

    private static String formatNode(int vertexId, String label) {
        return String.format("\t%d [label=\"%s\"];", vertexId, label);
    }

    private static String formatEdge(int fromVertexId, int toVertexId, String method) {
        return String.format("\t%d -> %d [label=\"%s\"];", fromVertexId, toVertexId, method);
    }

    public void endSubgraph() {
        allSubgraphs.append("}");
    }

    public void printDigraph(String rootObjectId){
        StringBuilder digraph = new StringBuilder();
        digraph.append("digraph "+rootObjectId+"{"+"\n");
        digraph.append("label=\""+rootObjectId+"\""+"\n");
        digraph.append(this.allSubgraphs);
        digraph.append("}");
        System.out.println(digraph);
    }

    public static void main(String[] args) throws IOException {
        // Example usage:
        Set<Map.Entry<String, String>> entries = new HashSet<>(Arrays.asList(
                mapsto("A", "B"),
                mapsto("B", "C"),
                mapsto("C", "D")
        ));

        DotDiagramLogger logger = new DotDiagramLogger();
        String rootObjectId = "Root";
        StringBuilder digraph = new StringBuilder();
        digraph.append("digraph "+rootObjectId+"{"+"\n");
        digraph.append("label=\""+rootObjectId+"\""+"\n");
        logger.startSubgraph("A");
        logger.addMethodTransitions("test",entries);
        logger.endSubgraph();
        digraph.append(logger.allSubgraphs);
        digraph.append("}");
        System.out.println(digraph);
    }
}
