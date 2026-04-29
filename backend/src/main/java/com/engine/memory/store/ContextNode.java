package com.engine.memory.store;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "context_nodes")
public class ContextNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String type;
    public String content;
    public String tags;

    public Long parentId;
    public String relation;

    public LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
