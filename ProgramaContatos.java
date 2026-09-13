package colecao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ProgramaContatos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bem-vindo ao Sistema de Contatos!");
        // Pergunta inicial obrigatória[cite: 2]
        System.out.print("Deseja que as listas sejam ordenadas? (1 - Sim / 2 - Nao): ");
        boolean isOrdenada = scanner.nextLine().trim().equals("1");

        // Armazenando em variáveis do tipo IColecao[cite: 2]
        // Usamos duas listas para busca rápida, mas armazenando a mesma referência do contato[cite: 2]
        IColecao<Contato> listaPorNome = new ListaEncadeada<>(new Contato.ComparadorPorNome(), isOrdenada);
        IColecao<Contato> listaPorTelefone = new ListaEncadeada<>(new Contato.ComparadorPorTelefone(), isOrdenada);

        int opcao = 0;
        
        while (opcao != 7) {
            System.out.println("\n--- MENU ---");
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
                    // Opção 1: Ler "entrada.txt" e exibir tempo total gasto[cite: 2]
                    System.out.print("Digite o caminho do arquivo (ex: entrada.txt): ");
                    String caminhoArquivo = scanner.nextLine().trim();
                    
                    inicioTempo = System.nanoTime();
                    int contatosCarregados = 0;
                    
                    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                        String linha;
                        while ((linha = br.readLine()) != null) {
                            String[] partes = linha.split(";"); // Assumindo formato Nome;Telefone
                            if (partes.length == 2) {
                                Contato novo = new Contato(partes[0].trim(), partes[1].trim());
                                // Regra de negócio: não permite dois contatos com mesmo telefone[cite: 2]
                                if (listaPorTelefone.pesquisar(novo) == null) {
                                    listaPorNome.adicionar(novo);
                                    listaPorTelefone.adicionar(novo);
                                    contatosCarregados++;
                                }
                            }
                        }
                        fimTempo = System.nanoTime();
                        System.out.println(contatosCarregados + " contatos carregados com sucesso!");
                        System.out.println("Tempo total de montagem das listas: " + (fimTempo - inicioTempo) / 1_000_000.0 + " ms");
                    } catch (IOException e) {
                        System.out.println("Erro ao ler o arquivo: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Opção 2: Adicionar contato solicitando nome e telefone[cite: 2]
                    System.out.print("Nome do contato: ");
                    nome = scanner.nextLine().trim();
                    System.out.print("Telefone do contato: ");
                    telefone = scanner.nextLine().trim();
                    
                    Contato novoContato = new Contato(nome, telefone);
                    
                    // Validação de telefone único[cite: 2]
                    if (listaPorTelefone.pesquisar(novoContato) != null) {
                        System.out.println("Erro: Ja existe um contato com esse telefone!");
                    } else {
                        listaPorNome.adicionar(novoContato);
                        listaPorTelefone.adicionar(novoContato);
                        System.out.println("Contato adicionado com sucesso!");
                    }
                    break;

                case 3:
                    // Opção 3: Pesquisar por nome, exibir telefone e tempo gasto[cite: 2]
                    System.out.print("Digite o nome a pesquisar: ");
                    nome = scanner.nextLine().trim();
                    contatoBusca = new Contato(nome, ""); // Telefone vazio para busca
                    
                    inicioTempo = System.nanoTime();
                    contatoEncontrado = listaPorNome.pesquisar(contatoBusca);
                    fimTempo = System.nanoTime();
                    
                    if (contatoEncontrado != null) {
                        System.out.println("Telefone: " + contatoEncontrado.getTelefone());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo gasto na busca: " + (fimTempo - inicioTempo) / 1_000_000.0 + " ms");
                    break;

                case 4:
                    // Opção 4: Pesquisar por telefone, exibir nome e tempo gasto[cite: 2]
                    System.out.print("Digite o telefone a pesquisar: ");
                    telefone = scanner.nextLine().trim();
                    contatoBusca = new Contato("", telefone); // Nome vazio para busca
                    
                    inicioTempo = System.nanoTime();
                    contatoEncontrado = listaPorTelefone.pesquisar(contatoBusca);
                    fimTempo = System.nanoTime();
                    
                    if (contatoEncontrado != null) {
                        System.out.println("Nome: " + contatoEncontrado.getNome());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo gasto na busca: " + (fimTempo - inicioTempo) / 1_000_000.0 + " ms");
                    break;

                case 5:
                    // Opção 5: Remover por telefone e exibir tempo gasto[cite: 2]
                    System.out.print("Digite o telefone do contato a remover: ");
                    telefone = scanner.nextLine().trim();
                    contatoBusca = new Contato("", telefone);
                    
                    inicioTempo = System.nanoTime();
                    contatoEncontrado = listaPorTelefone.pesquisar(contatoBusca);
                    if (contatoEncontrado != null) {
                        listaPorNome.remover(contatoEncontrado);
                        listaPorTelefone.remover(contatoEncontrado);
                        fimTempo = System.nanoTime();
                        System.out.println("Contato excluído com sucesso.");
                    } else {
                        fimTempo = System.nanoTime();
                        System.out.println("Contato não existia.");
                    }
                    System.out.println("Tempo gasto na remoção: " + (fimTempo - inicioTempo) / 1_000_000.0 + " ms");
                    break;

                case 6:
                    // Opção 6: Alterar dados (busca por nome, exibe telefone, pede novos dados)[cite: 2]
                    System.out.print("Digite o nome do contato a alterar: ");
                    nome = scanner.nextLine().trim();
                    contatoBusca = new Contato(nome, "");
                    
                    contatoEncontrado = listaPorNome.pesquisar(contatoBusca);
                    if (contatoEncontrado != null) {
                        System.out.println("Telefone atual: " + contatoEncontrado.getTelefone());
                        
                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine().trim();
                        System.out.print("Novo telefone: ");
                        String novoTelefone = scanner.nextLine().trim();
                        
                        Contato contatoAlterado = new Contato(novoNome, novoTelefone);
                        
                        // Verifica se o novo telefone não pertence a outro contato[cite: 2]
                        Contato verificaTelefone = listaPorTelefone.pesquisar(contatoAlterado);
                        if (verificaTelefone != null && verificaTelefone != contatoEncontrado) {
                            System.out.println("Erro: O novo telefone já pertence a outro contato!");
                        } else {
                            // Para manter a ordenação interna da lista encadeada, removemos o antigo e adicionamos o novo
                            listaPorNome.remover(contatoEncontrado);
                            listaPorTelefone.remover(contatoEncontrado);
                            
                            listaPorNome.adicionar(contatoAlterado);
                            listaPorTelefone.adicionar(contatoAlterado);
                            
                            System.out.println("Dados alterados com sucesso!");
                        }
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    break;

                case 7:
                    // Opção 7: Exibe a quantidade total de contatos ao encerrar[cite: 2]
                    System.out.println("Encerrando o programa...");
                    System.out.println("Quantidade total atual de contatos: " + listaPorNome.quantidadeNos());
                    break;

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }
        scanner.close();
    }
}