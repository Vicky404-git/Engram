package com.engine.memory.store;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContextRepository extends JpaRepository<ContextNode, Long> {
    // We will use this to grab the latest context to build our Markdown file
    List<ContextNode> findTop50ByOrderByTimestampDesc();
}
