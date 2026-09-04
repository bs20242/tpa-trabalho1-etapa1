package colecao;

public interface IColecao<T> {
    public boolean adicionar(T novoValor);
    public T pesquisar(T valor);
    public boolean remover(T valor);
    public int quantidadeNos();
}
