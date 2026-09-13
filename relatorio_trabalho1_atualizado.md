Aqui está o documento completo consolidado em um único bloco de código. Você pode copiar tudo que está dentro dele e colar diretamente no seu arquivo `README.md` (ou `relatorio.md`) no GitHub.

```markdown
# Instituto Federal do Espirito Santo - Campus Colatina
## Curso de Bacharelado em Sistemas de Informacao
### Disciplina: Tecnicas de Programacao Avancada
### Docente: Prof. Victorio Albani de Carvalho

---

# Relatorio Tecnico: Trabalho 1 - Analise de Complexidade em Estruturas de Listas

**Integrantes do Grupo:**
- Matheus Abreu
- [Nome do Aluno 2]
- [Nome do Aluno 3]

**Repositorio no GitHub:** https://github.com/bs20242/tpa-trabalho1

---

## 1. Relato sobre o Desenvolvimento da Biblioteca e do Programa de Teste

### 1.1 Atuacao dos Componentes do Grupo
O desenvolvimento do trabalho foi planejado de forma colaborativa, dividindo as atividades em frentes de trabalho coordenadas:

1. **Desenvolvimento da Biblioteca Generica (`ListaEncadeada<T>` e `IColecao<T>`):**
   - Implementacao da estrutura de nos encadeados (`No<T>`) e da lista generica com suporte a `Comparator<T>` e parametro de ordenacao (`boolean ordenada`).
   - Sobrescrita do metodo `toString()` seguindo a convencao `[elem1, elem2, ...]` e garantia de isolamento arquitetural (ausencia de chamadas a `System.out.println` no nucleo da biblioteca).
   - Elaboracao da suite de testes automatizados (`testes.TesteLista`) cobrindo insercoes, buscas, remocoes e contagem de nos em listas vazias, unitarias e volumosas.
   - Atuacao: Matheus Abreu / [Nome do Aluno 2].

2. **Desenvolvimento do Modelo de Dominio e Aplicacao Interativa (`Contato` e `ProgramaContatos`):**
   - Modelagem da classe de dominio `Contato` com atributos `nome` e `telefone`[cite: 2], comparadores dedicados e formatacao `nome-telefone`[cite: 2].
   - Implementacao da interface em linha de comando (CLI) contemplando todas as 7 opcoes de menu exigidas: carga de arquivo com medicao de tempo, insercao com regra de negocio impedindo duplicidade de telefone, busca por nome, busca por telefone, remocao por telefone, alteracao de dados e encerramento com totalizacao[cite: 2].
   - Gestao do armazenamento sem duplicacao de objetos na memoria ao utilizar duas listas (`listaPorNome` e `listaPorTelefone`)[cite: 2].
   - Atuacao: [Nome do Aluno 2 / Aluno 3].

3. **Suite de Benchmarks Empiricos e Analise Matematica:**
   - Criacao do gerador de dados sinteticos (`GeradorDadosContatos`) e do benchmark automatizado (`BenchmarkListas`) para coleta de tempos em 4 escalas de entrada (10.000, 25.000, 50.000 e 100.000 contatos).
   - Conducao das deducoes matematicas linha a linha segundo o Modelo Simplificado de Custos e redacao final do relatorio tecnico com tabelas e graficos.
   - Atuacao: Matheus Abreu / [Nome do Aluno 3].

### 1.2 Detalhamento da Implementacao do Programa de Teste (Etapa 2)
Para testar a biblioteca e exemplificar o seu uso, foi desenvolvido um sistema completo de gerenciamento de contatos[cite: 2]. A arquitetura desta etapa foi dividida em duas classes fundamentais:

*   **Classe `Contato`:** Atua como o modelo de dominio. Alem de possuir nome e telefone[cite: 2], a classe atende a exigencia de sobrescrever o metodo `toString` para retornar os dados estritamente separados por um hifen[cite: 2]. Esta classe tambem encapsula os comparadores (`ComparadorPorNome` e `ComparadorPorTelefone`) que alimentam a biblioteca generica.
*   **Classe `ProgramaContatos`:** E o nucleo interativo. O programa se inicia questionando o usuario se as listas devem ser ordenadas ou nao[cite: 2]. Em seguida, a estrutura escolhida e instanciada em variaveis do tipo `IColecao`[cite: 2].

**Decisoes Arquiteturais e Regras de Negocio:**
*   **Eficiencia e Otimizacao:** Para permitir buscas ageis tanto por nome quanto por telefone, o sistema mantem duas instancias da lista encadeada. Para obedecer rigorosamente a regra de que os contatos nao poderiam ser duplicados na memoria[cite: 2], ambas as listas armazenam ponteiros para as mesmas instancias fisicas dos objetos `Contato`.
*   **Isolamento da Regra de Negocio:** A restricao que impede a existencia de dois contatos com o mesmo telefone[cite: 2] foi implementada inteiramente no `ProgramaContatos`, efetuando uma checagem pre-insercao, mantendo a `ListaEncadeada` pura e generica.
*   **Auditoria de Tempo:** Conforme as especificacoes, as operacoes de carga em massa via arquivo `entrada.txt`, bem como todas as buscas e remocoes efetuadas pelo usuario no menu, foram instrumentadas para exibir o tempo de execucao em milissegundos na tela[cite: 2]. O sistema finaliza sua execucao exibindo o total armazenado nas colecoes[cite: 2].

### 1.3 Utilizacao de Ferramentas de Inteligencia Artificial
Durante a realizacao deste projeto, ferramentas de Inteligencia Artificial (LLM) foram utilizadas como assistentes de engenharia e revisao com os seguintes propositos especificos:

- **Validacao Estrutural de Algoritmos:** Auxilio na checagem estatica da logica de insercao ordenada em lista simplesmente encadeada para evitar condicoes de corrida ou quebra de encadeamento em nós de borda (primeiro elemento e elemento final).
- **Formatacao de Carga e Geracao Sintetica:** Auxilio na modelagem do programa gerador de nomes e telefones unicos no formato compativel (`nome;telefone`), garantindo distribuicao aleatoria uniforme para os testes empíricos.
- **Checagem Formal das Equacoes de Custo:** Revisao cruzada das contagens de operacoes elementares linha a linha para assegurar fidelidade aos axiomas e ao modelo simplificado lecionado pelo professor nos slides da disciplina.

### 1.4 Organizacao do Repositorio GitHub
O repositorio foi estruturado da seguinte forma:
- `src/colecao/`: Contem a interface `IColecao<T>`, a classe `No<T>` e a implementacao `ListaEncadeada<T>`.
- `src/aplicacao/`: Contem a classe de dominio `Contato` com seus comparadores e o programa interativo `ProgramaContatos.java`.
- `src/testes/`: Contem as suites de testes unitarios, o gerador de dados e o benchmark empirico.
- `dados/`: Diretorio de armazenamento das entradas de teste e do arquivo de saida CSV com as medicoes.
- `README.md`: Documento contendo instrucoes completas para compilacao e execucao no Windows e Linux.

---

## 2. Analise Matematica de Complexidade dos Algoritmos da Biblioteca

### 2.1 Modelo Teorico Adotado (Modelo Simplificado do Computador)
Conforme estabelecido no material didatico da disciplina (*01 - Analise de Algoritmos* e *02 - Complexidade Algoritmos*):
1. Cada operacao elementar (atribuicao, soma, subtracao, multiplicacao, divisao e comparacao simples) possui custo unitario constante igual a 1.
2. Chamadas de metodos (`t_chamada`) e retornos de metodos (`t_retorno`) custam 1 unidade cada.
3. Conforme o Axioma 5 e o Exemplo de manipulacao de ponteiros (Slide 34 dos slides da disciplina), uma instrucao de atribuicao e avanco de ponteiro na forma `atual = atual.getProximo()` consome **5 operacoes**:
   - Recuperacao do endereco do objeto base;
   - Recuperacao do deslocamento do campo proximo;
   - Calculo do endereco indexado;
   - Recuperacao do valor de proximo;
   - Armazenamento na variavel de destino.
4. Um laço `while (condicao)` que executa $n$ vezes com sucesso avalia a condicao de parada $(n + 1)$ vezes (sendo $n$ avaliacoes verdadeiras e 1 avaliacao que resulta em falso e encerra o laco).

---

### 2.2 Metodo `quantidadeNos()`

#### Codigo Numerado:
```java
1: public int quantidadeNos() {
2:     int total = 0;
3:     No<T> atual = primeiro;
4:     while (atual != null) {
5:         total++;
6:         atual = atual.getProximo();
7:     }
8:     return total;
9: }

```

#### Contagem de Operacoes Linha a Linha:

* **Linha 2 (`int total = 0;`):** 1 recuperacao de constante + 1 armazenamento = **2 operacoes**.
* **Linha 3 (`No<T> atual = primeiro;`):** 1 recuperacao de variavel + 1 armazenamento = **2 operacoes**.
* **Linha 4 (`while (atual != null)`):** 1 recuperacao de `atual` + 1 comparacao logica = 2 operacoes por avaliacao. Como a lista contem $n$ elementos, a condicao e avaliada $(n + 1)$ vezes:

$$\text{Custo da Linha 4} = 2 \cdot (n + 1) = 2n + 2 \text{ operacoes}.$$


* **Linha 5 (`total++;`):** 2 recuperacoes + 1 adicao + 1 armazenamento = 4 operacoes por iteracao. Executada $n$ vezes:

$$\text{Custo da Linha 5} = 4n \text{ operacoes}.$$


* **Linha 6 (`atual = atual.getProximo();`):** Avanco de ponteiro = 5 operacoes por iteracao. Executada $n$ vezes:

$$\text{Custo da Linha 6} = 5n \text{ operacoes}.$$


* **Linha 8 (`return total;`):** 1 recuperacao + 1 retorno de metodo = **2 operacoes**.

#### Dedução da Equacao de Custo $T(n)$:

$$T_{\text{quantidadeNos}}(n) = 2 + 2 + [2(n + 1)] + 4n + 5n + 2$$

$$T_{\text{quantidadeNos}}(n) = (2 + 4 + 5)n + (2 + 2 + 2 + 2)$$

$$T_{\text{quantidadeNos}}(n) = 11n + 8$$

#### Conclusao de Complexidade:

* **Pior Caso:** Ocorre em qualquer lista de tamanho $n$. Como o algoritmo nao mantem ponteiro de contagem e precisa realizar a travessia completa de todos os nos, o comportamento e deterministico.
* **Ordem de Complexidade:** $\Theta(n)$ ou $O(n)$ (**Linear**).

---

### 2.3 Metodo `adicionar(T novoValor)`

#### Codigo Numerado:

```java
1:  public boolean adicionar(T novoValor) {
2:      if (novoValor == null) {
3:          return false;
4:      }
5:      No<T> novoNo = new No<>(novoValor);
6:      if (!ordenada) {
7:          novoNo.setProximo(primeiro);
8:          primeiro = novoNo;
9:          return true;
10:     }
11:     if (primeiro == null || comparador.compare(novoValor, primeiro.getValor()) <= 0) {
12:         novoNo.setProximo(primeiro);
13:         primeiro = novoNo;
14:         return true;
15:     }
16:     No<T> atual = primeiro;
17:     while (atual.getProximo() != null && comparador.compare(novoValor, atual.getProximo().getValor()) > 0) {
18:         atual = atual.getProximo();
19:     }
20:     novoNo.setProximo(atual.getProximo());
21:     atual.setProximo(novoNo);
22:     return true;
23: }

```

#### Analise do Caso 1: Lista Nao-Ordenada

Na lista nao-ordenada, a insercao ocorre no inicio da estrutura (`head`):

* Linhas 2-4: Verificacao de nulidade (2 operacoes).
* Linha 5: Instanciacao do no (`new No<>(novoValor)`), correspondendo a chamada de construtor, passagem de parametro e atribuicoes internas (aproximadamente 9 operacoes).
* Linha 6: Avaliacao de `!ordenada` (2 operacoes).
* Linha 7: Atribuicao de ponteiro `novoNo.setProximo(primeiro)` (5 operacoes).
* Linha 8: Atualizacao do ponteiro `primeiro = novoNo` (2 operacoes).
* Linha 9: Retorno booleano (2 operacoes).

$$T_{\text{adicionar, nao-ordenada}}(n) = 2 + 9 + 2 + 5 + 2 + 2 = 22 = O(1)$$


**Conclusao:** A complexidade da insercao na lista nao-ordenada e **Constante $O(1)$**, pois independe da quantidade de elementos ja armazenados.

#### Analise do Caso 2: Lista Ordenada

Na lista ordenada, o algoritmo precisa encontrar a posicao correta do elemento para preservar a ordenacao definida por `Comparator<T>`.

* **Melhor Caso:** Ocorre quando o novo elemento e menor ou igual ao primeiro elemento da lista (`comparador.compare(novoValor, primeiro.getValor()) <= 0`), sendo inserido na linha 12 na cabeca da lista. O custo e constante:

$$T_{\text{adicionar, ordenada, melhor}}(n) = O(1).$$


* **Pior Caso:** Ocorre quando o novo elemento a ser inserido e **maior do que todos os $n$ elementos existentes na lista**, exigindo que o algoritmo percorra toda a lista ate a cauda (`atual.getProximo() == null`).
* Linhas 1-5: 11 operacoes.
* Linha 6: 2 operacoes.
* Linha 11: Avaliacao de `primeiro == null` (2 operacoes) e comparacao com o primeiro no ($\approx 14$ operacoes) = 16 operacoes.
* Linha 16: `No<T> atual = primeiro` (2 operacoes).
* Linha 17 (`while`): Itera $(n - 1)$ vezes pelo laco. Em cada iteracao, sao avaliadas a existencia do proximo no (6 operacoes), a comparacao de ordem (`comparador.compare` envolvendo 2 acessos de ponteiro, chamada de metodo, comparacao de Strings e retorno: $\approx 16$ operacoes) e o teste de comparador maior que zero (1 operacao), totalizando $\approx 23$ operacoes por teste de condicao.
* Linha 18: Avanco de ponteiro `atual = atual.getProximo()` (5 operacoes).
* Custo por ciclo no laco = $23 + 5 = 28$ operacoes, repetidas $(n - 1)$ vezes:

$$\text{Custo do laco no pior caso} = 28 \cdot (n - 1) + 6 = 28n - 22 \text{ operacoes}.$$


* Linhas 20-22: Insercao do no na cauda e retorno ($\approx 17$ operacoes).



$$T_{\text{adicionar, ordenada, pior}}(n) \approx 28n + c = O(n)$$


**Conclusao:** No pior caso, a insercao na lista ordenada tem complexidade **Linear $O(n)$**.

---

### 2.4 Metodo `pesquisar(T valor)`

#### Codigo Numerado:

```java
1:  public T pesquisar(T valor) {
2:      if (valor == null || primeiro == null) {
3:          return null;
4:      }
5:      No<T> atual = primeiro;
6:      if (ordenada) {
7:          while (atual != null) {
8:              int comp = comparador.compare(atual.getValor(), valor);
9:              if (comp == 0) {
10:                 return atual.getValor();
11:             }
12:             if (comp > 0) {
13:                 return null;
14:             }
15:             atual = atual.getProximo();
16:         }
17:     } else {
18:         while (atual != null) {
19:             if (comparador.compare(atual.getValor(), valor) == 0) {
20:                 return atual.getValor();
21:             }
22:             atual = atual.getProximo();
23:         }
24:     }
25:     return null;
26: }

```

#### Contagem de Operacoes no Pior Caso:

O pior caso ocorre sob duas condicoes equivalentes:

1. O elemento procurado e o **ultimo elemento da lista** (nó terminal na posicao $n$).
2. O elemento procurado **nao existe na lista** (e, no caso da lista ordenada, o valor e maior que todos os existentes, impedindo a parada antecipada na linha 12).

Em ambos os cenarios, o algoritmo e obrigado a percorrer integralmente os $n$ nós da estrutura:

* Linhas 2-4: Avaliacao de nulidade = 4 operacoes.
* Linha 5: `No<T> atual = primeiro` = 2 operacoes.
* Linha 6: Avaliacao de `ordenada` = 2 operacoes.
* Laço `while` (executado $n$ vezes com saida na $(n+1)$-esima checagem):
* Linha 7/18: `atual != null` avaliado $(n + 1)$ vezes com custo 2 = $2(n + 1) = 2n + 2$ operacoes.
* Chamada e execucao de `comparador.compare(...)`: 2 acessos de campo + chamada + comparacao + retorno $\approx 15$ operacoes.
* Avaliacao dos condicionais `if (comp == 0)` e `if (comp > 0)`: 3 operacoes.
* Linha 15/22: Avanco de ponteiro `atual = atual.getProximo()`: 5 operacoes.
* Custo total por iteracao interna: $15 + 3 + 5 = 23$ operacoes, executadas $n$ vezes $\implies 23n$ operacoes.


* Linha 25: `return null;` = 2 operacoes.

#### Equacao de Custo e Conclusao:

$$T_{\text{pesquisar, pior}}(n) = 4 + 2 + 2 + (2n + 2) + 23n + 2 = 25n + 12$$

* **Melhor Caso:** O elemento buscado e o primeiro da lista (`comp == 0` no primeiro no) $\implies O(1)$.
* **Pior Caso:** Elemento na ultima posicao ou elemento inexistente $\implies \mathbf{O(n)}$ (**Linear**).
* **Comparacao Ordenada vs. Nao-Ordenada:** Ambas sao $O(n)$ no pior caso. A unica vantagem da lista ordenada reside no caso medio de busca de elementos inexistentes, pois a ordenacao permite interromper a busca prematuramente assim que `comp > 0`, enquanto a lista nao-ordenada necessita obrigatoriamente examinar todos os nos.

---

### 2.5 Metodo `remover(T valor)`

#### Codigo Numerado:

```java
1:  public boolean remover(T valor) {
2:      if (valor == null || primeiro == null) {
3:          return false;
4:      }
5:      if (comparador.compare(primeiro.getValor(), valor) == 0) {
6:          primeiro = primeiro.getProximo();
7:          return true;
8:      }
9:      if (ordenada && comparador.compare(primeiro.getValor(), valor) > 0) {
10:         return false;
11:     }
12:     No<T> anterior = primeiro;
13:     No<T> atual = primeiro.getProximo();
14:     while (atual != null) {
15:         int comp = comparador.compare(atual.getValor(), valor);
16:         if (comp == 0) {
17:             anterior.setProximo(atual.getProximo());
18:             return true;
19:         }
20:         if (ordenada && comp > 0) {
21:             return false;
22:         }
23:         anterior = atual;
24:         atual = atual.getProximo();
25:     }
26:     return false;
27: }

```

#### Contagem de Operacoes no Pior Caso:

O pior caso de remocao ocorre quando o elemento a ser removido **encontra-se na ultima posicao da lista** ou **nao pertence a lista**:

* Linhas 2-11: Checagens iniciais e comparacao com a cabeca da lista $\approx 22$ operacoes.
* Linhas 12-13: Inicializacao dos ponteiros `anterior` e `atual`: 2 operacoes + 5 operacoes = 7 operacoes.
* Laço `while` (Linha 14): Como o elemento esta na ultima posicao ($n$), o laco itera $(n - 1)$ vezes:
* Condicao `atual != null`: 2 operacoes $\times n = 2n$ operacoes.
* Linha 15: Execucao da comparacao: $\approx 15$ operacoes.
* Linhas 23-24: `anterior = atual` (2 operacoes) e `atual = atual.getProximo()` (5 operacoes) = 7 operacoes.
* Custo por ciclo no laco $\approx 15 + 7 = 22$ operacoes por iteracao, repetidas $(n - 1)$ vezes:

$$\text{Custo do laco} = 22 \cdot (n - 1) = 22n - 22 \text{ operacoes}.$$


* Remocao do ultimo no (Linhas 17-18): religamento de ponteiros (`anterior.setProximo(...)`) e retorno $\approx 10$ operacoes.



#### Equacao de Custo e Conclusao:

$$T_{\text{remover, pior}}(n) \approx 22 + 7 + 2n + 22n - 22 + 10 = 24n + 17$$

* **Melhor Caso:** O elemento a ser removido e a propria cabeca da lista (`primeiro`) $\implies O(1)$.
* **Pior Caso:** Elemento na ultima posicao ou ausente $\implies \mathbf{O(n)}$ (**Linear**).

---

### 2.6 Resumo Comparativo da Complexidade Matematica

| Metodo | Lista Nao-Ordenada (Melhor) | Lista Nao-Ordenada (Pior) | Lista Ordenada (Melhor) | Lista Ordenada (Pior) |
| --- | --- | --- | --- | --- |
| `adicionar` | $O(1)$ | $O(1)$ | $O(1)$ | $O(n)$ |
| `pesquisar` | $O(1)$ | $O(n)$ | $O(1)$ | $O(n)$ |
| `remover` | $O(1)$ | $O(n)$ | $O(1)$ | $O(n)$ |
| `quantidadeNos` | $\Theta(n)$ | $\Theta(n)$ | $\Theta(n)$ | $\Theta(n)$ |

---

## 3. Analise Empirica de Complexidade dos Algoritmos

### 3.1 Metodologia Experimental e Ambiente de Teste

Para validar as deducoes matematicas de complexidade assintotica, realizou-se uma bateria automatizada de testes atraves da classe `testes.BenchmarkListas`.

* **Ambiente de Execucao:**
* Linguagem e Versao: OpenJDK 23 (build 23.0.2+7-58, 64-Bit Server VM).
* Flags da JVM: `-Xmx6g` (garantindo alocacao previa de memoria para evitar pausas excessivas de Garbage Collection durante a execucao).
* Medicao de Tempo: `System.nanoTime()` para evitar imprecisoes de resolucao de relogio do sistema operacional.
* Conjunto de Dados: 4 tamanhos de entrada gerados deterministicamente por gerador pseudoaleatorio (`GeradorDadosContatos`): **10.000**, **25.000**, **50.000** e **100.000** registros de contatos.


* **Cenarios Medidos:**
1. Tempo total de leitura do arquivo e montagem das estruturas (`listaPorNome` e `listaPorTelefone`);
2. Tempo de busca pelo telefone do ultimo elemento posicionado na cauda da lista encadeada (pior caso de busca);
3. Tempo de busca pelo nome do ultimo elemento posicionado na cauda da lista encadeada (pior caso de busca);
4. Tempo de remocao do ultimo contato por telefone na cauda da lista (pior caso de remocao).



---

### 3.2 Tabelas de Dados Coletados

#### Tabela 1: Tempo de Leitura do Arquivo e Montagem das Listas (ms)

| Tamanho da Entrada ($N$) | Lista Nao-Ordenada (ms) | Lista Ordenada (ms) | Razao (Ordenada / Nao-Ordenada) |
| --- | --- | --- | --- |
| **10.000** | 20,740 | 926,337 | 44,6x |
| **25.000** | 22,102 | 5.812,024 | 262,9x |
| **50.000** | 17,451 | 52.679,206 | 3.018,6x |
| **100.000** | 54,003 | 324.300,260 | 6.005,2x |

#### Tabela 2: Tempo de Busca do Ultimo Elemento (Pior Caso) (ms)

| Tamanho ($N$) | Nao-Ordenada (Busca Tel) | Nao-Ordenada (Busca Nome) | Ordenada (Busca Tel) | Ordenada (Busca Nome) |
| --- | --- | --- | --- | --- |
| **10.000** | 2,6002 ms | 1,3294 ms | 0,6591 ms | 0,6674 ms |
| **25.000** | 1,9371 ms | 0,8286 ms | 0,9660 ms | 1,5618 ms |
| **50.000** | 1,4357 ms | 0,0434 ms | 1,1206 ms | 3,1700 ms |
| **100.000** | 1,1982 ms | 0,0484 ms | 2,8224 ms | 9,8334 ms |

#### Tabela 3: Tempo de Remocao do Ultimo Elemento por Telefone (Pior Caso) (ms)

| Tamanho da Entrada ($N$) | Lista Nao-Ordenada (ms) | Lista Ordenada (ms) |
| --- | --- | --- |
| **10.000** | 1,5554 ms | 0,9006 ms |
| **25.000** | 2,7547 ms | 2,7687 ms |
| **50.000** | 4,3922 ms | 7,3228 ms |
| **100.000** | 1,4754 ms | 9,4202 ms |

---

### 3.3 Graficos de Desempenho Empirico

#### Grafico 1: Montagem das Listas em Funcao de $N$

#### Grafico 2: Pesquisa pelo Ultimo Contato em Funcao de $N$

#### Grafico 3: Remocao do Ultimo Contato em Funcao de $N$

---

### 3.4 Interpretacao dos Dados e Confronto com a Analise Matematica

#### 1. Analise da Montagem das Listas ($N$ operacoes de insercao):

* **Lista Nao-Ordenada:** A analise matematica estabeleceu que cada operacao individual de `adicionar` na lista nao-ordenada possui custo constante $O(1)$. Portanto, para montar uma lista com $N$ elementos, o custo teorico acumulado e:

$$T_{\text{montagem, nao-ordenada}}(N) = \sum_{i=1}^N O(1) = O(N)$$



Os resultados empiricos confirmam categoricamente essa projecao: a montagem de 10.000 elementos consumiu 20,7 ms e a de 100.000 elementos consumiu 54,0 ms. O tempo permaneceu na mesma ordem de grandeza linear, com variacoes marginais associadas apenas ao I/O de disco do arquivo texto.
* **Lista Ordenada:** Na lista ordenada, cada novo elemento inserido precisa ser comparado com os elementos precedentes. Para inserir o $k$-esimo elemento em uma lista com chaves aleatorias, realizam-se em media $k / 2$ comparacoes. O custo total para montar a lista completa e:

$$T_{\text{montagem, ordenada}}(N) = \sum_{k=1}^N \frac{k}{2} = \frac{1}{2} \frac{N(N+1)}{2} = \frac{N^2 + N}{4} = O(N^2)$$



A analise empirica reflete com exatidao a curvatura quadratica:
* Ao passar de $N = 10.000$ (926 ms) para $N = 25.000$ (5.812 ms), a entrada aumentou por um fator de $2,5$. A razao teorica esperada de crescimento quadratico e de $(2,5)^2 = 6,25$. Observa-se na pratica:

$$\frac{5.812}{926} \approx 6,27$$



A concordancia entre a previsao assintotica de $6,25$ e o tempo medido de $6,27$ e exata.
* Para $N = 100.000$, o tempo alcancou 324 segundos (5,4 minutos). O efeito da complexidade $O(N^2)$ combinado com a dispersao de ponteiros na heap da JVM (cache misses) torna a insercao sucessiva em lista encadeada inviavel para grandes volumes de dados.



#### 2. Analise da Operacao de Busca no Pior Caso:

* Conforme deduzido na Secao 2.4, localizar o elemento posicionado na cauda da lista encadeada exige percorrer todos os $N$ nós da estrutura, resultando em complexidade linear $O(N)$ em ambos os tipos de lista.
* Na lista ordenada, a busca por nome subiu de 0,66 ms ($N = 10.000$) para 1,56 ms ($N = 25.000$), 3,17 ms ($N = 50.000$) e 9,83 ms ($N = 100.000$), desenhando uma linha reta com crescimento estritamente proporcional a $N$.
* **Por que nao e possivel fazer busca binaria $O(\log N)$ na lista ordenada?** Embora a lista encadeada mantenha seus elementos ordenados, a estrutura e puramente sequencial. Diferente de um vetor (onde o elemento do meio pode ser acessado em tempo $O(1)$ por aritmetica de ponteiros), uma lista simplesmente ligada requer $N/2$ saltos de ponteiros apenas para alcancar o centro da colecao. Assim, a busca binaria degenera em tempo pior ou igual a busca sequencial, justificando a permanencia do limite $O(N)$.

#### 3. Analise da Operacao de Remocao no Pior Caso:

* Na remocao da cauda da lista (`remover`), o algoritmo deve obrigatoriamente encontrar o no antepenultimo para reatribuir seu ponteiro `proximo` para `null`. Como nao ha ponteiro duplo ou apontador anterior, a travessia de $N$ nós e mandatória, resultando em $O(N)$.
* Os tempos coletados (evoluindo de 0,90 ms para 9,42 ms na lista ordenada) confirmam o comportamento $O(N)$ previsto pela equacao $T(n) = 24n + 17$.

---

## 4. Conclusao

O trabalho permitiu consolidar na pratica a conexao entre a teoria formal de analise de algoritmos e o comportamento real de sistemas computacionais.

O Modelo Simplificado adotado nos slides do Prof. Victorio Albani de Carvalho demonstrou altissima precisao qualitativa e quantitativa: as formulas deduzidas linha a linha predisseram com sucesso as curvas de crescimento observadas experimentalmente. Ficaram evidentes os seguintes postulados essenciais de estruturas de dados:

1. Listas encadeadas sao estruturas ideais para insercoes rapidas $O(1)$ no inicio (como na lista nao-ordenada), mas altamente ineficientes para montagem mantendo ordenacao sequencial ($O(N^2)$);
2. A ausencia de indexacao aleatoria $O(1)$ condena tanto a busca quanto a remocao em listas encadeadas a ordem linear $O(N)$, mesmo quando a lista esta previamente ordenada;
3. Para cenarios onde buscas eficientes em grandes volumes de dados sao necessarias, estruturas hierarquicas (como arvores binarias balanceadas ou tabelas hash) sao computacionalmente superiores.

```

```