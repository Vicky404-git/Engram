package com.engine.core.compression;

import com.engine.memory.store.ContextNode;
import java.util.*;

public class ContextDeduplicator {

    public List<ContextNode> deduplicate(List<ContextNode> nodes) {
        Set<String> seen = new HashSet<>();
        List<ContextNode> result = new ArrayList<>();

        for (ContextNode node : nodes) {
            if (seen.add(node.content)) {
                result.add(node);
            }
        }

        return result;
    }
}
