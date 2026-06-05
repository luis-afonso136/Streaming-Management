# 🎬 Streaming Content Management Platform

> Practical assignment for **Algorithms and Programming** — Bachelor's in Computer Graphics and Multimedia Engineering · 2025/2026

---

## 📋 Table of Contents

- [💡 The Idea](#-the-idea)
- [📌 Description](#-description)
- [🗂️ Project Structure](#️-project-structure)
- [🧱 Domain Model](#-domain-model)
- [🌳 Binary Search Tree (BST)](#-binary-search-tree-bst)
- [📚 Sorting Algorithms](#-sorting-algorithms)
- [🔍 Searching Algorithms](#-searching-algorithms)
- [▶️ How to Compile and Run](#️-how-to-compile-and-run)
- [🎭 DemoApp — Demo Cases](#-demoapp--demo-cases)
- [💾 Persistence](#-persistence)
- [📤 Export](#-export)
- [👥 Group](#-group)

---

## 💡 The Idea

Imagine you're running a streaming service like Netflix. Every day, hundreds of new movies, series and documentaries need to be added, searched, filtered and organised. Doing this manually would be slow and error-prone.

**StreamFlow** is a backend content management platform built exactly for this purpose. It gives streaming operators a powerful system to:

- 📥 **Add** new audiovisual content with full data validation
- 🔍 **Search** by ID, title, year, age restriction, or production status
- 📊 **Sort and filter** content using efficient custom algorithms
- 🏆 **Track** ratings, subtitles and awards per content type
- 💾 **Export** catalogues to `.json` or `.txt` for external use
- 🔄 **Persist** data across sessions so nothing is lost on shutdown

The system is designed to be fast and reliable — using a **Binary Search Tree** for movie retrieval by year, a **custom LIFO Stack** to track the most recently added series, and a **TreeSet** to keep documentaries always sorted and duplicate-free.

---

## 📌 Description

An audiovisual content management platform inspired by services such as Netflix, Prime Video and HBO Max. It allows managing **Movies**, **Series** and **Documentaries**, applying the core principles of Object-Oriented Programming, data structures, and sorting and searching algorithms.

---

## 🗂️ Project Structure

```
StreamingPlatform/
├── src/
│   ├── interfaces/
│   │   ├── Classifiable.java       # Ratings/classifications
│   │   ├── Captionable.java        # Subtitles/captions
│   │   └── Awardable.java          # Awards
│   ├── utilities/
│   │   ├── StackNode.java          # Generic Stack node
│   │   └── Stack.java              # Generic LIFO Stack with linked nodes
│   ├── tree/
│   │   ├── Node.java               # Generic BST node
│   │   └── BinarySearchTree.java   # BST for Movies (year + title)
│   ├── model/
│   │   ├── Movie.java
│   │   ├── Series.java
│   │   └── Documentary.java
│   ├── services/
│   │   ├── MovieService.java
│   │   ├── SeriesService.java
│   │   └── DocumentaryService.java
│   └── app/
│       ├── App.java                # Interactive menu
│       └── DemoApp.java            # Automated demo (presentation)
├── .gitignore
└── README.md
```

---

## 🧱 Domain Model

### Interfaces per Entity

| Interface | Movie | Series | Documentary |
|---|:---:|:---:|:---:|
| `Classifiable` (ratings) | ✅ | ✅ | ❌ |
| `Captionable` (subtitles) | ✅ | ✅ | ✅ |
| `Awardable` (awards) | ❌ | ❌ | ✅ |

### Data Structures per Service

| Service | Main Collection | Secondary Structure |
|---|---|---|
| `MovieService` | `HashMap<String, Movie>` | Binary Search Tree (year + title) |
| `SeriesService` | `HashMap<String, Series>` | Custom LIFO Stack |
| `DocumentaryService` | `TreeSet<Documentary>` | — |

---

## 🌳 Binary Search Tree (BST)

The BST stores `Movie` objects ordered **primarily by `yearRelease`** and **secondarily by `title`** to avoid conflicts between movies released in the same year.

Implemented operations:

| Operation | Description |
|---|---|
| `insert` | Inserts a movie in the correct position |
| `inOrder` | Traversal from oldest to newest |
| `preOrder` | Pre-order traversal (root → left → right) |
| `postOrder` | Post-order traversal (left → right → root) |
| `search` | Search by year and title |
| `remove` | Removes a movie while maintaining the structure |
| `minimum` | Oldest movie |
| `maximum` | Newest movie |
| `height` | Tree height |
| `size` | Number of nodes |

---

## 📚 Sorting Algorithms

> Custom implementations — no use of `Collections.sort`.

| Algorithm | Usage |
|---|---|
| **Quick Sort** | Movies sorted by duration |
| **Optimized Bubble Sort** | Movies sorted by title |
| **Insertion Sort** | Series sorted by title |
| **Selection Sort** | Documentaries sorted by year |

---

## 🔍 Searching Algorithms

| Algorithm | Usage |
|---|---|
| **Sequential Search** | Search by title in `HashMap` |
| **BST Search** | Search by year/title in Movies |

---

## ▶️ How to Compile and Run

### Linux / macOS

```bash
# Compile
find src -name "*.java" > sources.txt
javac -d out -sourcepath src @sources.txt

# Interactive app (full menu)
java -cp out app.App

# Automated demo (no manual input required)
java -cp out app.DemoApp
```

### Windows

```bat
mkdir out
dir /s /b src\*.java > sources.txt
javac -d out -sourcepath src @sources.txt
java -cp out app.App
```

---

## 🎭 DemoApp — Demo Cases

`DemoApp` runs automatically without any manual input and covers:

**Simple Cases**
- Create a valid Movie, Series and Documentary
- Reject duplicates (same ID or title)
- Search by ID and by title

**Medium Cases**
- List movies ordered by duration
- List series currently in production
- List documentaries filtered by age restriction
- Retrieve the last 3 series created (via Stack)

**Complex Cases**
- In-order, pre-order and post-order BST traversals
- Remove a movie from the BST and show the resulting structure
- Export movies to `.json` and documentaries to `.txt`
- List all content sorted by title
- Filter movies by year interval

---

## 💾 Persistence

All entities implement `Serializable`. Each service provides `saveToFile()` and `loadFromFile()` methods to persist and restore data between executions using Java binary serialization.

---

## 📤 Export

| Entity | Format |
|---|---|
| Movies | `.json` and `.txt` |
| Documentaries | `.json` and `.txt` |

---

## 👥 Group

Luís Afonso, 29731

Pedro Ferreira, 29739

Bachelor's in Computer Graphics and Multimedia Engineering  
Algorithms and Programming · 2025/2026
