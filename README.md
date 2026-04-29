# 🧠 Context-Aware Memory Engine

A system that converts messy project state into a **single, structured, token-efficient context file** for AI models.

Instead of forcing LLMs to scan entire codebases, this engine compiles your project’s **true state** into one continuously updated source of truth.

---

## 🚀 The Core Idea

Modern AI tools waste tokens by scanning:

* entire repositories
* unrelated files
* redundant history

This project replaces that with:

> **One file. One state. Full context.**

---

## ⚡ What It Does

### 1. 🧩 Structured Memory (Not Raw Files)

Every meaningful action is stored as a node:

* `CODE_CHANGE` → what changed
* `IDEA` → why it exists
* `RESULT` → what happened
* `CHAT` → conversation history

This ensures the AI understands **intent, not just code**.

---

### 2. 📄 Single Source of Truth (SSoT)

All memory is compiled into:

```id="c9l2wp"
master-context.md
```

This file contains:

* project metadata
* compressed memory
* relationships (via graph)
* latest state of the system

The LLM reads only this file.

---

### 3. 🧠 Context Compilation Engine

The system continuously:

* pulls latest memory nodes
* organizes them
* builds a structured Markdown file
* injects a visual graph (Mermaid)

This acts as a **token-optimized brain for AI**.

---

### 4. 🌐 API-Driven Memory

Push new context via API:

```id="r7rjcv"
POST /api/context/push
```

Chat with full context:

```id="s9p2dc"
POST /api/chat
```

The engine:

1. Loads compiled context
2. Injects it into the LLM prompt
3. Generates response
4. Saves interaction back into memory

---

### 5. 🔄 Self-Updating Intelligence

Every interaction:

* updates memory
* recompiles context
* evolves system knowledge

---

## 🏗 Architecture

```id="c8a7z3"
User / Code / Ideas
        ↓
   Context API
        ↓
   ContextNode (DB)
        ↓
   StateCompiler
        ↓
 master-context.md
        ↓
     LLM (Groq)
        ↓
     Response
        ↓
   Saved as Memory
```

---

## 🧬 Data Model

Each memory unit:

```java id="8bq5u4"
type: "CODE_CHANGE" | "IDEA" | "RESULT" | "CHAT"
content: String
tags: String
timestamp: LocalDateTime
```

---

## 🗺 Conceptual Graph

The system generates a live mind map:

* nodes = memory units
* edges = chronological + conceptual links
* rendered using Mermaid

---

## 🎯 Why This Exists

LLMs are powerful, but:

* ❌ They don’t remember state
* ❌ They re-scan everything
* ❌ They lose intent over time

This engine fixes that by:

✅ preserving reasoning
✅ compressing context
✅ enabling consistent intelligence

---

## 🔥 Use Cases

* AI-assisted development (no repo scanning)
* long-running projects with evolving context
* replacing “chat history” with structured memory
* building persistent AI agents

---

## ⚙️ Tech Stack

* Java (Spring Boot)
* PostgreSQL / JPA
* LangChain4j
* Groq (LLaMA 3.1)
* Markdown + Mermaid

---

## 🧪 Example Flow

```id="j7qvzm"
POST /api/context/push
{
  "type": "IDEA",
  "content": "Use markdown as compressed AI memory",
  "tags": "architecture,ai"
}
```

→ gets stored
→ compiled into context
→ used in next AI response

---

## ⚠️ What This Is NOT

* ❌ Not a note-taking app
* ❌ Not a task manager
* ❌ Not a background daemon
* ❌ Not a full code indexer

---

## 🧭 Roadmap

* [ ] automatic watcher (real-time updates)
* [ ] relationship-aware graph (true linking)
* [ ] context compression (dedup + summarization)
* [ ] Web UI for graph visualization
* [ ] VS Code extension

---

## 👤 Author

Vicky404

---

## 🧠 Final Thought

> Stop giving AI your entire project.
> Give it your **understanding of the project**.

