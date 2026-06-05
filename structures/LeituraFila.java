package structures;

import java.io.FileWriter;
import java.util.Scanner;
import models.Livro;
import models.NoLista;

public class LeituraFila {
    private NoLista inicio;
    private NoLista fim;

    public LeituraFila() {
        this.inicio = null;
        this.fim = null;
    }

    public boolean estaVazia() {
        return this.inicio == null;
    }

    public void enfileirar(Livro livro) {
        enfileirarSemMensagem(livro);
        System.out.println("\"" + livro.getTitulo() + "\" foi marcado para ler depois.");
    }

    private void enfileirarSemMensagem(Livro livro) {
        NoLista novoNo = new NoLista(livro);

        if (estaVazia()) {
            this.inicio = novoNo;
            this.fim = novoNo;
        } else {
            this.fim.setProximo(novoNo);
            this.fim = novoNo;
        }
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

    public void salvarNoArquivo(FileWriter writer) {
        NoLista atual = this.inicio;

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
            System.out.println("Erro ao salvar a fila de leitura no arquivo.");
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
                    enfileirarSemMensagem(livroRecuperado);
                } catch (Exception ignored) {
                }
            }
        }
    }
}