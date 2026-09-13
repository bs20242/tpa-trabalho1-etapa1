package app;

import colecao.IColecao;
import colecao.ListaEncadeada;
import dominio.Contato;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ProgramaContatos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistema de Gerenciamento de Contatos ===");
        // Pergunta inicial se as listas devem ser ordenadas ou nao
        System.out.print("Deseja que as listas sejam ordenadas? (1 - Sim / 2 - Nao): ");
        boolean isOrdenada = scanner.nextLine().trim().equals("1");

        // Instancia as duas listas como IColecao para nome e telefone
        // Mesma referencia compartilhada entre as listas (sem duplicar objetos em memoria)
        IColecao<Contato> listaPorNome = new ListaEncadeada<>(new Contato.ComparadorPorNome(), isOrdenada);
        IColecao<Contato> listaPorTelefone = new ListaEncadeada<>(new Contato.ComparadorPorTelefone(), isOrdenada);

        int opcao = 0;

        while (opcao != 7) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Carregar dados de arquivo");
            System.out.println("2 - Adicionar contato");
            System.out.println("3 - Pesquisar contato por nome");
            System.out.println("4 - Pesquisar contato por telefone");
            System.out.println("5 - Remover contato por telefone");
            System.out.println("6 - Alterar dados de contato");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = 0;
            }

            long inicioTempo, fimTempo;
            Contato contatoBusca, contatoEncontrado;
            String nome, telefone;

            switch (opcao) {
                case 1:
                    // 1. Carga de arquivo com medicao de tempo total
                    System.out.print("Digite o caminho do arquivo (Enter para 'entrada.txt'): ");
                    String caminhoArquivo = scanner.nextLine().trim();
                    if (caminhoArquivo.isEmpty()) {
                        caminhoArquivo = "entrada.txt";
                    }

                    inicioTempo = System.nanoTime();
                    int contatosCarregados = 0;

                    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                        String linha;
                        while ((linha = br.readLine()) != null) {
                            String[] partes = linha.split(";");
                            if (partes.length >= 2) {
                                Contato novo = new Contato(partes[0].trim(), partes[1].trim());
                                // Regra de negocio: nao permite contatos com mesmo telefone
                                if (listaPorTelefone.pesquisar(novo) == null) {
                                    listaPorNome.adicionar(novo);
                                    listaPorTelefone.adicionar(novo);
                                    contatosCarregados++;
                                }
                            }
                        }
                        fimTempo = System.nanoTime();
                        System.out.println(contatosCarregados + " contatos carregados com sucesso!");
                        System.out.printf("Tempo total de leitura e montagem das listas: %.4f ms\n", (fimTempo - inicioTempo) / 1_000_000.0);
                    } catch (IOException e) {
                        System.out.println("Erro ao ler o arquivo: " + e.getMessage());
                    }
                    break;

                case 2:
                    // 2. Adicionar contato solicitando nome e telefone
                    System.out.print("Nome do contato: ");
                    nome = scanner.nextLine().trim();
                    System.out.print("Telefone do contato: ");
                    telefone = scanner.nextLine().trim();

                    if (nome.isEmpty() || telefone.isEmpty()) {
                        System.out.println("Erro: Nome e telefone nao podem ser vazios.");
                        break;
                    }

                    Contato novoContato = new Contato(nome, telefone);

                    // Validacao de telefone unico
                    if (listaPorTelefone.pesquisar(novoContato) != null) {
                        System.out.println("Erro: Ja existe um contato cadastrado com esse telefone!");
                    } else {
                        listaPorNome.adicionar(novoContato);
                        listaPorTelefone.adicionar(novoContato);
                        System.out.println("Contato adicionado com sucesso!");
                    }
                    break;

                case 3:
                    // 3. Pesquisar contato por nome e exibir tempo gasto
                    System.out.print("Digite o nome a pesquisar: ");
                    nome = scanner.nextLine().trim();
                    contatoBusca = new Contato(nome, "");

                    inicioTempo = System.nanoTime();
                    contatoEncontrado = listaPorNome.pesquisar(contatoBusca);
                    fimTempo = System.nanoTime();

                    if (contatoEncontrado != null) {
                        System.out.println("Contato encontrado: " + contatoEncontrado);
                        System.out.println("Telefone: " + contatoEncontrado.getTelefone());
                    } else {
                        System.out.println("Contato nao existe.");
                    }
                    System.out.printf("Tempo gasto na busca: %.4f ms\n", (fimTempo - inicioTempo) / 1_000_000.0);
                    break;

                case 4:
                    // 4. Pesquisar contato por telefone e exibir tempo gasto
                    System.out.print("Digite o telefone a pesquisar: ");
                    telefone = scanner.nextLine().trim();
                    contatoBusca = new Contato("", telefone);

                    inicioTempo = System.nanoTime();
                    contatoEncontrado = listaPorTelefone.pesquisar(contatoBusca);
                    fimTempo = System.nanoTime();

                    if (contatoEncontrado != null) {
                        System.out.println("Contato encontrado: " + contatoEncontrado);
                        System.out.println("Nome: " + contatoEncontrado.getNome());
                    } else {
                        System.out.println("Contato nao existe.");
                    }
                    System.out.printf("Tempo gasto na busca: %.4f ms\n", (fimTempo - inicioTempo) / 1_000_000.0);
                    break;

                case 5:
                    // 5. Remover por telefone e exibir tempo gasto
                    System.out.print("Digite o telefone do contato a remover: ");
                    telefone = scanner.nextLine().trim();
                    contatoBusca = new Contato("", telefone);

                    inicioTempo = System.nanoTime();
                    contatoEncontrado = listaPorTelefone.pesquisar(contatoBusca);
                    if (contatoEncontrado != null) {
                        listaPorNome.remover(contatoEncontrado);
                        listaPorTelefone.remover(contatoEncontrado);
                        fimTempo = System.nanoTime();
                        System.out.println("Contato (" + contatoEncontrado + ") excluido com sucesso.");
                    } else {
                        fimTempo = System.nanoTime();
                        System.out.println("Contato nao existia.");
                    }
                    System.out.printf("Tempo gasto na remocao: %.4f ms\n", (fimTempo - inicioTempo) / 1_000_000.0);
                    break;

                case 6:
                    // 6. Alterar dados de contato
                    System.out.print("Digite o nome do contato a alterar: ");
                    nome = scanner.nextLine().trim();
                    contatoBusca = new Contato(nome, "");

                    contatoEncontrado = listaPorNome.pesquisar(contatoBusca);
                    if (contatoEncontrado != null) {
                        System.out.println("Contato atual encontrado: " + contatoEncontrado);

                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine().trim();
                        System.out.print("Novo telefone: ");
                        String novoTelefone = scanner.nextLine().trim();

                        if (novoNome.isEmpty() || novoTelefone.isEmpty()) {
                            System.out.println("Erro: Nome e telefone nao podem ser vazios.");
                            break;
                        }

                        Contato contatoAlterado = new Contato(novoNome, novoTelefone);

                        // Verifica se o novo telefone ja pertence a outro contato cadastrado
                        Contato verificaTelefone = listaPorTelefone.pesquisar(contatoAlterado);
                        if (verificaTelefone != null && !verificaTelefone.equals(contatoEncontrado)) {
                            System.out.println("Erro: O novo telefone ja pertence a outro contato!");
                        } else {
                            // Para manter a integridade da ordem nas listas encadeadas, removemos o antigo e adicionamos o novo
                            listaPorNome.remover(contatoEncontrado);
                            listaPorTelefone.remover(contatoEncontrado);

                            listaPorNome.adicionar(contatoAlterado);
                            listaPorTelefone.adicionar(contatoAlterado);

                            System.out.println("Dados alterados com sucesso para: " + contatoAlterado);
                        }
                    } else {
                        System.out.println("Contato nao existe.");
                    }
                    break;

                case 7:
                    // 7. Sair exibindo o total de contatos cadastrados
                    System.out.println("Encerrando o programa...");
                    System.out.println("Quantidade total atual de contatos: " + listaPorNome.quantidadeNos());
                    break;

                default:
                    System.out.println("Opcao invalida. Digite um numero de 1 a 7.");
            }
        }
        scanner.close();
    }
}
