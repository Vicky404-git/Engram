package com.engine.memory.markdown;

import com.engine.memory.store.ContextNode;
import com.engine.memory.store.ContextRepository;
import com.engine.core.compression.*;
import com.engine.memory.graph.*;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StateCompiler {

    private final ContextRepository repository;
    private final ContextSummarizer summarizer;

    private final ContextDeduplicator deduplicator = new ContextDeduplicator();
    private final ContextFilter filter = new ContextFilter();
    private final ContextScorer scorer = new ContextScorer(); // 🔥 NEW
    private final GraphBuilder graphBuilder = new GraphBuilder();

    private final Path contextFilePath = Paths.get("master-context.md");

    public StateCompiler(ContextRepository repository, ContextSummarizer summarizer) {
        this.repository = repository;
        this.summarizer = summarizer;
    }

    public void compileState() {

        // 🔥 SINGLE PIPELINE (clean)
        List<ContextNode> nodes = repository.findAll();

        nodes = deduplicator.deduplicate(nodes);
        nodes = filter.filterImportant(nodes);
        nodes = scorer.score(nodes);
        nodes = nodes.stream().limit(25).toList();

        String summary = summarizer.summarize(nodes);

        List<GraphEdge> edges = graphBuilder.build(nodes);

        StringBuilder md = new StringBuilder();

        // YAML
        md.append("---\n");
        md.append("project: Context-Aware AI Memory Engine\n");
        md.append("last_updated: ").append(LocalDateTime.now()).append("\n");
        md.append("total_nodes: ").append(nodes.size()).append("\n");
        md.append("---\n\n");

        // 🔥 COMPRESSED STATE FIRST (important for LLM)
        md.append("# ⚡ Compressed State\n");
        md.append(summary).append("\n\n");

        md.append("# 🧠 Neural State Log\n\n");

        // Graph
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

        // Memory
        md.append("## Memory Blocks\n");

        for (ContextNode node : nodes) {
            md.append("### [").append(node.type).append("] Node ID: ").append(node.id).append("\n");
            md.append("> **Tags:** `").append(node.tags).append("`\n>\n");

            String cleanContent = node.content.replace("\n", "\n> ");
            md.append("> ").append(cleanContent).append("\n\n");
            md.append("---\n");
        }

        try {
            Files.writeString(contextFilePath, md.toString());
            System.out.println("[StateCompiler] Neural state updated.");
        } catch (IOException e) {
            System.err.println("Failed to write context file: " + e.getMessage());
        }
    }
}
