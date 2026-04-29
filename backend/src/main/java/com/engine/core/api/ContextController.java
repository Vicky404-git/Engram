package com.engine.core.api;

import com.engine.memory.store.ContextNode;
import com.engine.memory.store.ContextRepository;
import com.engine.memory.markdown.StateCompiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/context")
public class ContextController {

    private final ContextRepository repo;
    private final StateCompiler compiler;

    public ContextController(ContextRepository repo, StateCompiler compiler) {
        this.repo = repo;
        this.compiler = compiler;
    }

    // Hit this endpoint to push a new Idea, Code Change, or Result
    @PostMapping("/push")
    public ResponseEntity<String> pushContext(@RequestBody ContextNode node) {
        repo.save(node);
        compiler.compileState(); // Instantly rebuilds the single .md file
        return ResponseEntity.ok("Node saved. Token-optimized Markdown updated.");
    }

    // The React/D3.js frontend hits this to draw the visual mind map
    @GetMapping("/graph")
    public ResponseEntity<List<ContextNode>> getGraphData() {
        return ResponseEntity.ok(repo.findAll());
    }
}
