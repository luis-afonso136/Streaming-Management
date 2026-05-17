# 🎬 Streaming Content Management Platform

> Projeto prático de **Algoritmia e Programação** — Licenciatura em Engenharia de Computação Gráfica e Multimédia · 2025/2026

---

## 📌 Descrição

Plataforma de gestão de conteúdos audiovisuais inspirada em serviços como Netflix, Prime Video e HBO Max. Permite gerir **Filmes**, **Séries** e **Documentários**, aplicando os principais conceitos de Programação Orientada a Objetos, estruturas de dados e algoritmos de ordenação e pesquisa.

---

## 🗂️ Estrutura do Projeto

```
StreamingPlatform/
├── src/
│   ├── interfaces/
│   │   ├── Classifiable.java       # Ratings/classificações
│   │   ├── Captionable.java        # Legendas/captions
│   │   └── Awardable.java          # Prémios/awards
│   ├── utilities/
│   │   ├── StackNode.java          # Nó genérico da Stack
│   │   └── Stack.java              # Stack LIFO genérica com linked nodes
│   ├── tree/
│   │   ├── Node.java               # Nó genérico da BST
│   │   └── BinarySearchTree.java   # BST de filmes (ano + título)
│   ├── model/
│   │   ├── Movie.java
│   │   ├── Series.java
│   │   └── Documentary.java
│   ├── services/
│   │   ├── MovieService.java
│   │   ├── SeriesService.java
│   │   └── DocumentaryService.java
│   └── app/
│       ├── App.java                # Menu interativo
│       └── DemoApp.java            # Demo automático (apresentação)
├── .gitignore
└── README.md
```

---

## 🧱 Modelo de Domínio

### Interfaces por Entidade

| Interface | Movie | Series | Documentary |
|---|:---:|:---:|:---:|
| `Classifiable` (ratings) | ✅ | ✅ | ❌ |
| `Captionable` (legendas) | ✅ | ✅ | ✅ |
| `Awardable` (prémios) | ❌ | ❌ | ✅ |

### Estruturas de Dados por Serviço

| Serviço | Coleção Principal | Estrutura Secundária |
|---|---|---|
| `MovieService` | `HashMap<String, Movie>` | Binary Search Tree (ano + título) |
| `SeriesService` | `HashMap<String, Series>` | Stack LIFO personalizada |
| `DocumentaryService` | `TreeSet<Documentary>` | — |

---

## 🌳 Binary Search Tree (BST)

A BST armazena `Movie` ordenados **primariamente por `yearRelease`** e **secundariamente por `title`** para evitar conflitos entre filmes do mesmo ano.

Operações implementadas:

| Operação | Descrição |
|---|---|
| `insert` | Insere um filme na posição correta |
| `inOrder` | Percurso do mais antigo para o mais recente |
| `preOrder` | Percurso pré-ordem (raiz → esquerda → direita) |
| `postOrder` | Percurso pós-ordem (esquerda → direita → raiz) |
| `search` | Pesquisa por ano e título |
| `remove` | Remove um filme mantendo a estrutura |
| `minimum` | Filme mais antigo |
| `maximum` | Filme mais recente |
| `height` | Altura da árvore |
| `size` | Número de nós |

---

## 📚 Algoritmos de Ordenação

> Implementações próprias — sem uso de `Collections.sort`.

| Algoritmo | Utilização |
|---|---|
| **Quick Sort** | Filmes ordenados por duração |
| **Bubble Sort Otimizado** | Filmes ordenados por título |
| **Insertion Sort** | Séries ordenadas por título |
| **Selection Sort** | Documentários ordenados por ano |

---

## 🔍 Algoritmos de Pesquisa

| Algoritmo | Utilização |
|---|---|
| **Pesquisa Sequencial** | Pesquisa por título em `HashMap` |
| **Pesquisa via BST** | Pesquisa por ano/título em filmes |

---

## ▶️ Como Compilar e Executar

### Linux / macOS

```bash
# Compilar
find src -name "*.java" > sources.txt
javac -d out -sourcepath src @sources.txt

# App interativa (menu completo)
java -cp out app.App

# Demo automático (para apresentação, sem input manual)
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

## 🎭 DemoApp — Casos de Demonstração

O `DemoApp` corre automaticamente sem necessidade de input e cobre:

**Casos Simples**
- Criar Movie, Series e Documentary válidos
- Rejeitar duplicados (mesmo ID ou título)
- Pesquisar por ID e por título

**Casos Médios**
- Listar filmes ordenados por duração
- Listar séries em produção
- Listar documentários por restrição de idade
- Obter as últimas 3 séries criadas (via Stack)

**Casos Complexos**
- Percurso in-order, pre-order e post-order da BST
- Remover filme da BST e mostrar estrutura resultante
- Exportar filmes para `.json` e documentários para `.txt`
- Listar todo o conteúdo ordenado por título
- Filtrar filmes por intervalo de anos

---

## 💾 Persistência

Todas as entidades implementam `Serializable`. Os serviços disponibilizam métodos `saveToFile()` e `loadFromFile()` para persistir e restaurar dados entre execuções usando serialização binária Java.

---

## 📤 Exportação

| Entidade | Formato |
|---|---|
| Movies | `.json` e `.txt` |
| Documentaries | `.json` e `.txt` |

---

## 👥 Grupo

Luís Afonso, 29731
Pedro Ferreira, 29739

Licenciatura em Engenharia de Computação Gráfica e Multimédia  
UC Algoritmia e Programação · 2025/2026
