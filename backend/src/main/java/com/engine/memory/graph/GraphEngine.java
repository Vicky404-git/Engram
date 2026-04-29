package com.engine.memory.graph;

public class GraphEdge {
    public Long from;
    public Long to;
    public String relation;

    public GraphEdge(Long from, Long to, String relation) {
        this.from = from;
        this.to = to;
        this.relation = relation;
    }
}
