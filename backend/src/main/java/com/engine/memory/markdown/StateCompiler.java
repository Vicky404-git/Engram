package com.engine.memory.markdown;

import com.engine.memory.store.ContextNode;
import com.engine.memory.store.ContextRepository;
import org.springframework.stereotype.Service;
import com.engine.core.compression.*;
import com.engine.memory.graph.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class StateCompiler {

    private final ContextRepository repository;
    private final ContextSummarizer summarizer;
    private final ContextDeduplicator deduplicator = new ContextDeduplicator();
    private final ContextFilter filter = new ContextFilter();
    private final GraphBuilder graphBuilder = new GraphBuilder();
    private final Path contextFilePath = Paths.get("master-context.md");

    public StateCompiler(ContextRepository repository, ContextSummarizer summarizer) {
    this.repository = repository;
    this.summarizer = summarizer;
    }

    /**
     * Pulls the latest nodes and compiles them into a single, token-optimized Markdown file.
     * This file acts as the Single Source of Truth (SSoT) for the LLM.
     */
   public void compileState() {
       List<ContextNode> nodes = repository.findTop50ByOrderByTimestampDesc();

        StringBuilder md = new StringBuilder();

        
// Compression pipeline
nodes = deduplicator.deduplicate(nodes);
nodes = filter.filterImportant(nodes);

// Summary
String summary = summarizer.summarize(nodes);
md.append("## ⚡ Compressed State\n");
md.append(summary).append("\n\n");
// Graph
List<GraphEdge> edges = graphBuilder.build(nodes);

        // 1. YAML Frontmatter
        md.append("---\n");
        md.append("project: Context-Aware AI Memory Engine\n");
        md.append("last_updated: ").append(java.time.LocalDateTime.now()).append("\n");
        md.append("total_nodes: ").append(nodes.size()).append("\n");
        md.append("---\n\n");

        md.append("# 🧠 Neural State Log\n\n");

        // 2. AUTO-GENERATED MERMAID MIND MAP
        md.append("## Conceptual Topology\n");
        md.append("```mermaid\n");
        md.append("graph TD;\n");
        for (GraphEdge edge : edges) {
          md.append(String.format(
                "N%d -->|%s| N%d;\n",
                edge.from,
                edge.relation,
                edge.to
                ));
        }

        md.append("```\n\n");

        // 3. The Raw Context Payload
        md.append("## Memory Blocks\n");
        for (ContextNode node : nodes) {
            md.append("### [").append(node.type).append("] Node ID: ").append(node.id).append("\n");
            md.append("> **Tags:** `").append(node.tags).append("`\n>\n");
            
            // Format content nicely
            String cleanContent = node.content.replace("\n", "\n> ");
            md.append("> ").append(cleanContent).append("\n\n");
            md.append("---\n");
        }

        try {
            Files.writeString(contextFilePath, md.toString());
            System.out.println("[StateCompiler] Neural state updated. Mermaid graph injected.");
        } catch (IOException e) {
            System.err.println("Failed to write context file: " + e.getMessage());
        }
    }
}
