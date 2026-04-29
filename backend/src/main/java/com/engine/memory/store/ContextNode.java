package com.engine.memory.store;

import jakarta.persistence.*;
import java.time.LocalDateTime;
public Long parentId;
public String relation;
@Entity
@Table(name = "context_nodes")
public class ContextNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String type; // e.g., "CODE_CHANGE", "CHAT", "IDEA"

    @Column(columnDefinition = "TEXT", nullable = false)
    public String content; // The actual code or chat summary

    public String tags; // Comma-separated for the mind map (e.g., "auth,bugfix")

    public LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
