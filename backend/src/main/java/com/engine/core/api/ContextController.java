package com.engine.core.api;

import com.engine.memory.store.ContextNode;
import com.engine.memory.store.ContextRepository;
import com.engine.memory.markdown.StateCompiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import com.engine.core.watcher.ContextEvent;

import java.util.List;
@RestController
@RequestMapping("/api/context")
public class ContextController {

    private final ContextRepository repo;
    private final StateCompiler compiler;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public ContextController(ContextRepository repo, StateCompiler compiler) {
        this.repo = repo;
        this.compiler = compiler;
    }

    @PostMapping("/push")
    public ResponseEntity<String> pushContext(@RequestBody ContextNode node) {
      if ("null".equals(node.parentId + "")) node.parentId = null;
      if ("null".equals(node.relation)) node.relation = null;

      repo.save(node);
      eventPublisher.publishEvent(new ContextEvent(node.id));

      return ResponseEntity.ok("Node saved.");
}
=======
      if ("null".equals(node.parentId + "")) node.parentId = null;
      if ("null".equals(node.relation)) node.relation = null;

      repo.save(node);
      eventPublisher.publishEvent(new ContextEvent(node.id));
      return ResponseEntity.ok("Node saved.");
}
>>>>>>> test-branch
}
