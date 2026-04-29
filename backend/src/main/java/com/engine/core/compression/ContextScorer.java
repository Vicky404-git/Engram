package com.engine.core.compression;

import com.engine.memory.store.ContextNode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ContextScorer {

    public List<ContextNode> score(List<ContextNode> nodes) {
        for (ContextNode n : nodes) {

            int score = 0;

            // Type importance
            if ("RESULT".equals(n.type)) score += 5;
            if ("CODE_CHANGE".equals(n.type)) score += 4;
            if ("IDEA".equals(n.type)) score += 3;

            // Recency boost
            long minutes = Duration.between(n.timestamp, LocalDateTime.now()).toMinutes();
            score += Math.max(0, 10 - (int)(minutes / 10));

            n.weight = score;
        }

        return nodes.stream()
                .sorted((a, b) -> b.weight - a.weight)
                .toList();
    }
}
