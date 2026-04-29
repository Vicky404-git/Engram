package com.engine.memory.graph;

import com.engine.memory.store.ContextNode;
import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {

    public List<GraphEdge> build(List<ContextNode> nodes) {
        List<GraphEdge> edges = new ArrayList<>();

        for (ContextNode node : nodes) {
            if (node.parentId != null) {
                edges.add(new GraphEdge(
                        node.parentId,
                        node.id,
                        node.relation != null ? node.relation : "related"
                ));
            }
        }

        return edges;
    }
}
