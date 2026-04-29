package com.engine.core.llm;

import com.engine.memory.store.ContextNode;
import com.engine.memory.store.ContextRepository;
import com.engine.memory.markdown.StateCompiler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/chat")
public class ChatEngine {

    private final ContextRepository repo;
    private final StateCompiler compiler;
    private final ChatLanguageModel model;

    public ChatEngine(ContextRepository repo, 
                      StateCompiler compiler, 
                      @Value("${GROQ_API_KEY}") String groqApiKey) {
        this.repo = repo;
        this.compiler = compiler;
        
        // LangChain4j's OpenAI adapter perfectly wraps Groq's API
        this.model = OpenAiChatModel.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .apiKey(groqApiKey)
                .modelName("llama-3.1-8b-instant")
                .temperature(0.3)
                .maxTokens(1024)
                .build();
    }

    @PostMapping
    public String chat(@RequestBody String prompt) throws Exception {
        // 1. Read only the highly compressed state file
        String context = Files.readString(Path.of("master-context.md"));
       // 2. The Context-Aware Intelligence Persona
        String fullPrompt = """
            SYSTEM: You are the core intelligence of a Context-Aware Memory Engine.
            Read the Project State below. The 'Memory Blocks' are listed in REVERSE chronological order (newest at the top).
            
            CRITICAL RULES:
            1. Treat [CODE_CHANGE] and [IDEA] nodes as absolute ground-truth facts.
            2. Treat [CHAT] nodes ONLY as past conversation history. Do NOT use [CHAT] nodes to determine the current state of the project.
            3. Answer the user's query directly and analytically. Use bullet points or code blocks.
            
            """ + "PROJECT STATE:\n" + context + "\n\nUSER QUERY: " + prompt;        // 3. Fire to Groq
        String response = model.generate(fullPrompt);

        // 4. Save the interaction back to the mind map
        ContextNode chatNode = new ContextNode();
        chatNode.type = "CHAT";
        chatNode.tags = "ai-reasoning,chat";
        chatNode.content = "User: " + prompt + "\n\nAI: " + response;
        repo.save(chatNode);
        
        // 5. Recompile the visual .md file
        compiler.compileState();

        return response;
    }
}
