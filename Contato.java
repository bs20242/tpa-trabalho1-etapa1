package colecao;

import java.util.Comparator;

public class Contato {
    private String nome;
    private String telefone;

    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Sobrescrita obrigatória: devolve nome e telefone separados por hífen
    @Override
    public String toString() {
        return nome + "-" + telefone;
    }

    // Comparador para ordenar/buscar pela lista de Nomes
    public static class ComparadorPorNome implements Comparator<Contato> {
        @Override
        public int compare(Contato c1, Contato c2) {
            return c1.getNome().compareToIgnoreCase(c2.getNome());
        }
    }

    // Comparador para ordenar/buscar pela lista de Telefones
    public static class ComparadorPorTelefone implements Comparator<Contato> {
        @Override
        public int compare(Contato c1, Contato c2) {
            return c1.getTelefone().compareTo(c2.getTelefone());
        }
    }
}