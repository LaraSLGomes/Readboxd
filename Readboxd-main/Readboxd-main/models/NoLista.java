package models;

public class NoLista {
    public Livro livro;      
    private NoLista proximo;  

    public NoLista(Livro livro) {
        this.livro = livro;
        this.proximo = null;
    }

    public NoLista getProximo() {
        return this.proximo;
    }

    public void setProximo(NoLista proximo) {
        this.proximo = proximo;
    }

    public boolean temProximo() {
        return this.proximo != null;
    }
}