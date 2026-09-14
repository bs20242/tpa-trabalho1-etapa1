# tpa-trabalho1-etapa1

Sistema de gerenciamento de contatos com listas encadeadas genéricas (ordenadas e não-ordenadas), desenvolvido para a disciplina de Técnicas de Programação Avançada (IFES - Campus Colatina).

> O relatório técnico completo (relato de desenvolvimento, análise matemática e análise empírica de complexidade) está em [`RELATORIO.md`](RELATORIO.md).

## Organização do Código

```
tpa-trabalho1-etapa1/
├── src/
│   ├── colecao/       # IColecao<T>, No<T>, ListaEncadeada<T>
│   ├── dominio/       # Contato (com ComparadorPorNome e ComparadorPorTelefone)
│   ├── app/           # ProgramaContatos (aplicação interativa de linha de comando)
│   └── testes/        # TesteLista (testes unitários), GeradorDadosContatos
│                       # e BenchmarkListas (geração de massa de dados e benchmark)
├── dados/             # Arquivos de entrada gerados (não versionados), CSV de
│                       # resultados e script gerar_graficos.py
├── relatorio/         # Gráficos gerados a partir dos benchmarks
├── entrada.txt        # Arquivo de exemplo pequeno para uso manual do programa
├── RELATORIO.md        # Relatório técnico completo
└── README.md          # Este arquivo
```

* **`src/colecao/`** — biblioteca genérica de lista encadeada (`ListaEncadeada<T>`), independente de domínio, implementando a interface `IColecao<T>` e recebendo um `Comparator<T>` e um sinalizador `ordenada` no construtor.
* **`src/dominio/`** — classe `Contato` (nome, telefone) com os comparadores usados para indexar por nome ou por telefone.
* **`src/app/`** — `ProgramaContatos`, o menu interativo que usa a biblioteca para carregar, buscar, adicionar, alterar e remover contatos.
* **`src/testes/`** — `TesteLista` (testes unitários com `assert`), `GeradorDadosContatos` (gera arquivos de contatos sintéticos) e `BenchmarkListas` (mede tempos de montagem, busca e remoção).

## Como Compilar e Rodar

Requer JDK 11+.

```bash
# Compilar
javac -d bin -encoding UTF-8 $(find src -name "*.java")

# Rodar o programa interativo
java -cp bin app.ProgramaContatos

# Rodar a suíte de testes unitários (assertions habilitadas)
java -ea -cp bin testes.TesteLista

# Gerar um arquivo de dados sintéticos (quantidade, arquivo de saída, seed opcional)
java -cp bin testes.GeradorDadosContatos 10000 dados/contatos_10000.txt 42

# Rodar o benchmark para um arquivo (true = lista ordenada, false = não-ordenada)
java -cp bin testes.BenchmarkListas dados/contatos_10000.txt false

# Gerar os gráficos a partir de dados/resultados.csv (requer Python 3 + matplotlib)
python3 dados/gerar_graficos.py
```

O programa `app.ProgramaContatos`, ao iniciar, pergunta se as listas devem ser ordenadas ou não e em seguida oferece um menu para carregar dados de arquivo (`entrada.txt` por padrão), adicionar, pesquisar, remover e alterar contatos.
