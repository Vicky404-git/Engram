package com.engine.core.watcher;

import com.engine.memory.markdown.StateCompiler;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ContextWatcher {

    private final StateCompiler compiler;

    public ContextWatcher(StateCompiler compiler) {
        this.compiler = compiler;
    }

    @Async
    @EventListener
    public void onContextChange(ContextEvent event) {
        try {
            Thread.sleep(300); // debounce
            compiler.compileState();
            System.out.println("[Watcher] Context updated.");
        } catch (Exception e) {
            System.err.println("[Watcher] Error: " + e.getMessage());
        }
    }
}
