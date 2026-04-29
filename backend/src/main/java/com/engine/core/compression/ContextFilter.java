package com.engine.core.compression;

import com.engine.memory.store.ContextNode;
import java.util.List;
import java.util.stream.Collectors;

public class ContextFilter {

    public List<ContextNode> filterImportant(List<ContextNode> nodes) {
        return nodes.stream()
                .filter(n -> !n.type.equals("CHAT"))
                .limit(40)
                .collect(Collectors.toList());
    }
}
