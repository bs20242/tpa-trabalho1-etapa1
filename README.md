# Trabalho 1: Analise de Complexidade em Estruturas de Listas
## Etapa 1: Implementacao da Biblioteca de Listas Encadeadas Genericas

Disciplina: Tecnicas de Programacao Avancada (TPA)    
Docente: Prof. Victorio Albani de Carvalho  

---

## 1. Escopo desta Etapa

Este repositorio contem a entrega da **Etapa 1** do Trabalho 1 da disciplina, correspondente ao desenvolvimento da biblioteca de estrutura de dados generica e sua respectiva suite de validacao funcional:

- Interface generica `IColecao<T>` disponibilizada para a disciplina;
- Estrutura de no generico `No<T>`;
- Implementacao completa da classe `ListaEncadeada<T>` com suporte a ordenacao parametrizada;
- Suite de testes unitarios automatizados (`testes.TesteLista`) cobrindo casos de borda e operacoes principais.

As etapas subsequentes do projeto (Etapa 2: Aplicacao de Contatos e Regras de Negocio; Etapa 3: Bateria Empirica e Relatorio Tecnico) integrarao esta biblioteca como dependencia base.

---

## 2. Estrutura do Repositorio

```text
tpa-trabalho1-etapa1/
├── src/
│   ├── colecao/
│   │   ├── IColecao.java          # Interface obrigatoria da biblioteca
│   │   ├── No.java                # Estrutura do no generico
│   │   └── ListaEncadeada.java    # Implementacao da lista encadeada
│   └── testes/
│       └── TesteLista.java        # Testes unitarios automatizados
├── .gitignore                     # Arquivo de exclusao de binarios
└── README.md                      # Documentacao e instrucoes desta etapa
```

---

## 3. Especificacao Tecnica da Biblioteca

### 3.1 Interface `IColecao<T>`
A classe `ListaEncadeada<T>` implementa os quatro metodos da interface:
- `boolean adicionar(T novoValor)`: Adiciona o elemento a estrutura. Na lista nao-ordenada, insere na cabeca em tempo constante O(1). Na lista ordenada, insere mantendo a ordem crescente via comparador em tempo O(n).
- `T pesquisar(T valor)`: Realiza a busca pelo elemento utilizando o comparador e retorna a referencia armazenada (ou null caso ausente).
- `boolean remover(T valor)`: Localiza e desconecta o elemento da lista encadeada, retornando true em caso de sucesso ou false se inexistente.
- `int quantidadeNos()`: Contabiliza e retorna o numero total de nos da lista percorrendo a cadeia linearmente.

### 3.2 Construtor e Parametrizacao
A classe `ListaEncadeada<T>` disponibiliza o construtor:
```java
public ListaEncadeada(Comparator<T> comparador, boolean ordenada)
```
- `comparador`: Instancia de `Comparator<T>` utilizada nas comparacoes de igualdade e ordem;
- `ordenada`: Booleano que define se a lista devera manter os elementos ordenados (`true`) ou se permitira insercao nao-ordenada na cabeca (`false`).

### 3.3 Formatacao `toString()` e Isolamento
- O metodo `toString()` foi sobrescrito para devolver uma String iniciada por `[` e finalizada por `]`, com os elementos separados por virgula e espaco (`[elem1, elem2, ...]`).
- Em estrita conformidade com os requisitos da disciplina, nenhum metodo da biblioteca executa instrucoes de entrada/saida em console (`System.out.println` ou leitura via `Scanner`).

---

## 4. Compilacao e Execucao dos Testes

### Requisitos
- JDK 17 ou superior (testado com OpenJDK 23).

### Compilar o Codigo-Fonte
No Windows (PowerShell):
```powershell
javac -d bin (Get-ChildItem -Recurse src/*.java)
```

No Linux / macOS (Bash):
```bash
javac -d bin $(find src -name "*.java")
```

### Executar os Testes Unitarios
Execute a suite de testes ativando as assercoes da JVM (`-ea`):
```powershell
java -ea -cp bin testes.TesteLista
```

Saida esperada:
```text
TODOS_OS_TESTES_PASSARAM
```
