# Trabalho 1: Análise de Complexidade em Estruturas de Listas

**Disciplina:** Técnicas de Programação Avançada (TPA)  
**Instituição:** Instituto Federal do Espírito Santo (IFES) - Campus Colatina  
**Curso:** Bacharelado em Sistemas de Informação  
**Docente:** Prof. Victorio Albani de Carvalho  

**Integrantes:**
- Bernardo Simão Rosa
- Levi Monteiro
- Matheus Abreu

---

## 📂 Organização do Projeto

```text
├── .gitignore
├── entrada.txt                     # Arquivo de contatos de exemplo para carga inicial
├── RELATORIO.md                    # Relatório técnico completo (Seções 1, 2 e 3)
├── dados/
│   ├── resultados.csv              # Tempos reais coletados nos benchmarks
│   └── gerar_graficos.py           # Script para plotagem dos gráficos de desempenho
├── relatorio/                      # Imagens dos gráficos gerados
│   ├── grafico1_montagem.png
│   ├── grafico2_busca.png
│   └── grafico3_remocao.png
└── src/
    ├── colecao/                    # Biblioteca genérica de lista encadeada
    │   ├── IColecao.java           # Interface obrigatória da disciplina
    │   ├── No.java                 # Estrutura de nós genéricos (T)
    │   └── ListaEncadeada.java     # Implementação com suporte a ordenação e Comparator<T>
    ├── dominio/                    # Modelo de domínio
    │   └── Contato.java            # Entidade Contato e seus comparadores
    ├── app/                        # Interface com o usuário
    │   └── ProgramaContatos.java   # Programa CLI interativo (Menu de 1 a 7)
    └── testes/                     # Testes e benchmarks
        ├── TesteLista.java         # Testes unitários da biblioteca
        ├── GeradorDadosContatos.java # Gerador de arquivos de teste
        └── BenchmarkListas.java    # Bateria de testes empíricos de desempenho
```

---

## 🚀 Como Compilar e Executar

### 1. Compilar todo o projeto:
```bash
javac -d bin src/colecao/*.java src/dominio/*.java src/app/*.java src/testes/*.java
```

### 2. Executar os Testes Unitários:
```bash
java -cp bin testes.TesteLista
```

### 3. Executar o Programa Interativo de Contatos:
```bash
java -cp bin app.ProgramaContatos
```

### 4. Executar os Benchmarks de Desempenho:
```bash
# Gerar base sintética (ex: 10.000 contatos):
java -cp bin testes.GeradorDadosContatos 10000 dados/contatos_10000.txt

# Executar benchmark (ex: modo ordenado = true):
java -cp bin testes.BenchmarkListas dados/contatos_10000.txt true
```

---

## 📄 Relatório Técnico
O relatório completo com as análises matemáticas linha a linha e os resultados empíricos encontra-se em [`RELATORIO.md`](./RELATORIO.md).
