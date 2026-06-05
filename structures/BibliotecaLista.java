package structures;

import java.io.FileWriter;
import java.util.Scanner;
import models.Livro;
import models.NoLista;

public class BibliotecaLista {
    private NoLista cabeca;

    public BibliotecaLista() {
        this.cabeca = null;
    }

    public void adicionar(Livro livro) {
        adicionarSemMensagem(livro);
        System.out.println("\"" + livro.getTitulo() + "\" adicionado à sua biblioteca pessoal!");
    }

    private void adicionarSemMensagem(Livro livro) {
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
    }

    public void salvarNoArquivo(FileWriter writer) {
        NoLista atual = cabeca;
        try {
            while (atual != null) {
                Livro livro = atual.livro;
                writer.write(livro.getTitulo() + ";" +
                             livro.getAutor() + ";" +
                             livro.getAno() + ";" +
                             livro.getGenero() + ";" +
                             livro.getRating() + "\n");
                atual = atual.getProximo();
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar a biblioteca no arquivo.");
        }
    }

    public void carregarDoArquivo(Scanner leitor) {
        while (leitor.hasNextLine()) {
            String linha = leitor.nextLine();
            String[] dados = linha.split(";");
            if (dados.length >= 4) {
                try {
                    String titulo = dados[0];
                    String autor = dados[1];
                    int ano = Integer.parseInt(dados[2]);
                    String genero = dados[3];
                    Livro livroRecuperado = new Livro(titulo, autor, ano, genero);
                    if (dados.length >= 5) {
                        try {
                            livroRecuperado.setRating(Double.parseDouble(dados[4]));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    adicionarSemMensagem(livroRecuperado);
                } catch (Exception ignored) {
                }
            }
        }
    }
}