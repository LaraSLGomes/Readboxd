package structures;

import models.Livro;
import models.NoLista;

public class BibliotecaLista {
    private NoLista cabeca;

    public BibliotecaLista() {
        this.cabeca = null;
    }

    public void adicionar(Livro livro) {
        NoLista novoNo = new NoLista(livro);
        if (cabeca == null) {
            cabeca = novoNo;
        } else {
            NoLista atual = cabeca;
            while (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
            atual.setProximo(novoNo);
        }
        System.out.println("\"" + livro.getTitulo() + "\" adicionado à sua biblioteca pessoal!");
    }

    public void remover(String titulo) {
        if (cabeca == null) {
            System.out.println("Sua biblioteca está vazia.");
            return;
        }

        if (cabeca.livro.getTitulo().equalsIgnoreCase(titulo)) {
            cabeca = cabeca.getProximo();
            System.out.println("\"" + titulo + "\" removido da sua biblioteca.");
            return;
        }

        NoLista atual = cabeca;
        while (atual.getProximo() != null && !atual.getProximo().livro.getTitulo().equalsIgnoreCase(titulo)) {
            atual = atual.getProximo();
        }

        if (atual.getProximo() != null) {
            atual.setProximo(atual.getProximo().getProximo());
            System.out.println("\"" + titulo + "\" removido da sua biblioteca.");
        } else {
            System.out.println("Livro \"" + titulo + "\" não encontrado na biblioteca.");
        }
    }

    public void avaliar(String titulo, double nota) {
        NoLista atual = cabeca;
        while (atual != null) {
            if (atual.livro.getTitulo().equalsIgnoreCase(titulo)) {
                atual.livro.setRating(nota);
                System.out.println("Nota " + nota + " atribuída a \"" + titulo + "\" com sucesso!");
                return;
            }
            atual = atual.getProximo();
        }
        System.out.println("Livro \"" + titulo + "\" não encontrado na sua biblioteca para avaliar.");
    }

    public void listar() {
        if (cabeca == null) {
            System.out.println("Sua biblioteca pessoal está vazia.");
            return;
        }
        System.out.println("\n--- SUA BIBLIOTECA PESSOAL ---");
        NoLista atual = cabeca;
        while (atual != null) {
            System.out.println(atual.livro.toString());
            atual = atual.getProximo();
        }
        System.out.println("------------------------------");
    }
}