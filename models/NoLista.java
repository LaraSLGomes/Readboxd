package models;

public class NoLista {
    public Livro livro;      
    private NoLista proximo;  

    public NoLista(Livro libro) {
        this.livro = libro;
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