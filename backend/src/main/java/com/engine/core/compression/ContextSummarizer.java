package com.engine.core.compression;

import com.engine.memory.store.ContextNode;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Configuration
public class AIConfig {

    @Bean
    public ChatLanguageModel chatModel(@Value("${GROQ_API_KEY}") String key) {
        return OpenAiChatModel.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .apiKey(key)
                .modelName("llama-3.1-8b-instant")
                .build();
    }
}

@Service
public class ContextSummarizer {

    private final ChatLanguageModel model;

    public ContextSummarizer(ChatLanguageModel model) {
        this.model = model;
    }

    public String summarize(List<ContextNode> nodes) {
        StringBuilder input = new StringBuilder();

        for (ContextNode n : nodes) {
            input.append(n.type).append(": ").append(n.content).append("\n");
        }

        return model.generate("""
            Summarize the project state into concise bullet points.
            Focus on:
            - architecture
            - key decisions
            - recent changes
            - important results
            
            DATA:
            """ + input);
    }
}
