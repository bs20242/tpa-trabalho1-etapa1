# Trabalho 1 - Listas e análise de complexidade

IFES - Campus Serra | Técnicas de Programação Avançada
Professor: Victorio Albani de Carvalho
Integrantes: Bernardo Simão Rosa, Levi Monteiro e Matheus Abreu.

## Arquivos

- `src/colecao`: interface, nó e lista encadeada genérica.
- `src/dominio`: contato e comparadores por nome e telefone.
- `src/app`: menu e regras de cadastro compartilhadas com o benchmark.
- `src/testes`: testes da lista, testes do cadastro, gerador e benchmark.
- `entrada.txt`: exemplo pequeno, no formato `nome;telefone`, em UTF-8.
- `dados/resultados_brutos.csv`: cada medição realizada.
- `dados/resultados.csv`: mediana de três medições para cada tamanho e modo.
- `dados/ambiente.json`: Java, sistema, seed e hashes dos arquivos usados.
- `dados/*.py`: scripts para testar o menu, medir tempos e gerar relatório e gráficos.
- [RELATORIO.md](RELATORIO.md): explicações, análise por linha e resultados.
- [Relatório em PDF](relatorio/Relatorio_Trabalho1_TPA.pdf): versão para entrega.

## Compilar e executar

Requer JDK 11 ou superior. `java` e `javac` devem estar no PATH e usar o mesmo JDK.
Execute os comandos na raiz do repositório. A compilação abaixo funciona no PowerShell e no Bash.

```sh
javac -encoding UTF-8 -d bin src/colecao/*.java src/dominio/*.java src/app/*.java src/testes/*.java
java -cp bin app.ProgramaContatos
```

Na opção 1, pressione Enter para carregar `entrada.txt`, ou informe outro caminho.
A aplicação aceita nomes repetidos e rejeita telefones já cadastrados. O conjunto de telefones usado para essa validação fica na camada do cadastro, fora da biblioteca.

## Testes

```sh
java -ea -cp bin testes.TesteLista
java -cp bin testes.TesteCadastro
python dados/testar_programa.py
```

O `-ea` é obrigatório no primeiro comando, pois ativa os `assert`.
Os testes do cadastro e do menu cobrem remoção e alteração com homônimos,
telefone repetido e consistência das duas listas. O teste Java também verifica carga
repetida, entradas inválidas e o percurso completo até uma chave na cauda.
O script Python usa apenas a biblioteca padrão.

## Repetir os experimentos

```sh
python dados/executar_benchmarks.py
```

Esse comando gera quatro entradas (10.000, 25.000, 50.000 e 100.000 contatos),
com seed 42, e executa três processos Java para cada modo e tamanho.
Ele substitui os CSVs e o registro de ambiente. A execução pode demorar:
a carga usa um conjunto de telefones para evitar duplicatas. No modo não ordenado, o cadastro completo é linear; no modo ordenado, a inserção pode ser quadrática.
Não rode duas coletas ao mesmo tempo. Se uma coleta falhar, repita-a antes de atualizar o relatório.

Para uma execução isolada:

```sh
java -cp bin testes.GeradorDadosContatos 10000 dados/contatos_10000.txt 42
java -cp bin testes.BenchmarkListas dados/contatos_10000.txt false
java -cp bin testes.BenchmarkListas dados/contatos_10000.txt true
```

O gerador reserva um contato artificial único, maior nas duas chaves e primeiro no
arquivo. Assim, ele termina na cauda das duas listas nos dois modos. O benchmark
valida essa condição e recusa arquivos que não a atendam.

A medição da carga usa a mesma rotina do menu. As buscas medem `pesquisar`.
A remoção mede somente `remover` na lista por telefone; atualizar a lista por nome
ocorre fora do tempo. As chaves e a seleção do alvo também ficam fora do cronômetro.

## Atualizar gráficos, texto e PDF

Requer Python 3.10 ou superior. Instale as dependências e execute, após uma coleta completa:

```sh
python -m pip install -r dados/requirements.txt
python dados/gerar_graficos.py
python dados/atualizar_relatorio.py
python dados/gerar_pdf.py
```

`atualizar_relatorio.py` monta o texto técnico, extrai o código numerado e inclui as
medianas coletadas. Ele preserva o conteúdo existente da seção 1.3 sobre IA.
`gerar_pdf.py` lê `RELATORIO.md`, para que o PDF e o texto tenham o mesmo conteúdo.
Se mudar explicações técnicas, ajuste também o script que gera o relatório.

Os arquivos grandes podem ser recriados e são ignorados pelo Git. Os CSVs, scripts,
gráficos, relatório e PDF ficam versionados.

Para usar um Java fora do PATH nos scripts Python, defina a variável `JAVA` com o
caminho completo do executável. Isso não muda o comando de compilação.
