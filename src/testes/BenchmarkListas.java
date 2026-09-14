package testes;

import colecao.ListaEncadeada;
import dominio.Contato;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Roda uma carga de arquivo + busca (telefone e nome) + remocao do ultimo elemento
// da lista de telefones, medindo os tempos em milissegundos, e imprime uma linha CSV.
public class BenchmarkListas {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uso: java testes.BenchmarkListas <arquivoEntrada> <ordenada:true|false>");
            return;
        }
        String arquivo = args[0];
        boolean ordenada = Boolean.parseBoolean(args[1]);

        ListaEncadeada<Contato> listaPorNome = new ListaEncadeada<>(new Contato.ComparadorPorNome(), ordenada);
        ListaEncadeada<Contato> listaPorTelefone = new ListaEncadeada<>(new Contato.ComparadorPorTelefone(), ordenada);

        int n = 0;
        long inicio = System.nanoTime();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 2) {
                    Contato c = new Contato(partes[0].trim(), partes[1].trim());
                    listaPorNome.adicionar(c);
                    listaPorTelefone.adicionar(c);
                    n++;
                }
            }
        }
        long fim = System.nanoTime();
        double tempoMontagemMs = (fim - inicio) / 1_000_000.0;

        // Pior caso de busca: ultimo no percorrido na travessia da lista de telefones.
        Contato alvo = listaPorTelefone.obterUltimo();

        long i1 = System.nanoTime();
        listaPorTelefone.pesquisar(new Contato("", alvo.getTelefone()));
        long f1 = System.nanoTime();
        double tempoBuscaTelefoneMs = (f1 - i1) / 1_000_000.0;

        long i2 = System.nanoTime();
        listaPorNome.pesquisar(new Contato(alvo.getNome(), ""));
        long f2 = System.nanoTime();
        double tempoBuscaNomeMs = (f2 - i2) / 1_000_000.0;

        long i3 = System.nanoTime();
        listaPorTelefone.remover(new Contato("", alvo.getTelefone()));
        long f3 = System.nanoTime();
        double tempoRemocaoMs = (f3 - i3) / 1_000_000.0;

        System.out.println(String.join(",",
            ordenada ? "ordenada" : "nao-ordenada",
            String.valueOf(n),
            String.valueOf(tempoMontagemMs),
            String.valueOf(tempoBuscaTelefoneMs),
            String.valueOf(tempoBuscaNomeMs),
            String.valueOf(tempoRemocaoMs)
        ));
    }
}
