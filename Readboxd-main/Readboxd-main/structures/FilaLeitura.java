package structures;

import models.Livro;
import models.NoLista;

public class FilaLeitura {
    private NoLista inicio;
    private NoLista fim;

    public FilaLeitura() {
        this.inicio = null;
        this.fim = null;
    }

    public boolean estaVazia() {
        return this.inicio == null;
    }

    public void enfileirar(Livro livro) {
        NoLista novoNo = new NoLista(livro);

        if (estaVazia()) {
            this.inicio = novoNo;
            this.fim = novoNo;
        } else {
            this.fim.setProximo(novoNo);
            this.fim = novoNo;
        }

        System.out.println("\"" + livro.getTitulo() + "\" foi marcado para ler depois.");
    }

    public Livro desenfileirar() {
        if (estaVazia()) {
            System.out.println("A fila de leitura está vazia.");
            return null;
        }

        Livro livroRemovido = this.inicio.livro;
        this.inicio = this.inicio.getProximo();

        if (this.inicio == null) {
            this.fim = null;
        }

        System.out.println("\"" + livroRemovido.getTitulo() + "\" removido da fila de leitura.");
        return livroRemovido;
    }

    public Livro consultarProximo() {
        if (estaVazia()) {
            System.out.println("A fila de leitura está vazia.");
            return null;
        }

        return this.inicio.livro;
    }

    public void listar() {
        if (estaVazia()) {
            System.out.println("A fila de leitura futura está vazia.");
            return;
        }

        System.out.println("\n--- FILA DE LEITURA FUTURA ---");

        NoLista atual = this.inicio;

        while (atual != null) {
            System.out.println(atual.livro.toString());
            atual = atual.getProximo();
        }

        System.out.println("--------------------------------");
    }

    public boolean buscar(String titulo) {
        NoLista atual = this.inicio;

        while (atual != null) {
            if (atual.livro.getTitulo().equalsIgnoreCase(titulo)) {
                return true;
            }

            atual = atual.getProximo();
        }

        return false;
    }
}