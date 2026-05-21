package models;

public class NoArvore {
    public Livro livro;         
    public NoArvore esquerda;
    public NoArvore direita;   

  
    public NoArvore(Livro livro) {
        this.livro = livro;
        this.esquerda = null;
        this.direita = null;
    }
}