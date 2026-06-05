package structures;

import models.Livro;
import models.NoArvore;
import java.io.FileWriter;

public class CatalogoBST {
    private NoArvore raiz;

    public CatalogoBST() {
        this.raiz = null;
    }

    public void inserir(Livro livro) {
        this.raiz = inserirRecursivo(this.raiz, livro);
    }

    private NoArvore inserirRecursivo(NoArvore atual, Livro livro) {
        if (atual == null) {
            return new NoArvore(livro);
        }

        int valor = compararTextos(livro.getTitulo(), atual.livro.getTitulo());

        if (valor < 0) {
            atual.esquerda = inserirRecursivo(atual.esquerda, livro);
        } else if (valor > 0) {
            atual.direita = inserirRecursivo(atual.direita, livro);
        }
        
        return atual;
    }

    public Livro buscar(String titulo) {
        return buscarRecursivo(this.raiz, titulo);
    }

    private Livro buscarRecursivo(NoArvore atual, String titulo) {
        if (atual == null) {
            return null; 
        }

        int valor = compararTextos(titulo, atual.livro.getTitulo());

        if (valor == 0) {
            return atual.livro;
        }
        
        if (valor < 0) {
            return buscarRecursivo(atual.esquerda, titulo);
        } else {
            return buscarRecursivo(atual.direita, titulo);
        }
    }

    public void remover(String titulo) {
        this.raiz = removerRecursivo(this.raiz, titulo);
    }

    private NoArvore removerRecursivo(NoArvore atual, String titulo) {
        if (atual == null) {
            System.out.println("Livro não encontrado no catálogo.");
            return null;
        }

        int valor = compararTextos(titulo, atual.livro.getTitulo());

        if (valor < 0) {
            atual.esquerda = removerRecursivo(atual.esquerda, titulo);
        } else if (valor > 0) {
            atual.direita = removerRecursivo(atual.direita, titulo);
        } else {
            System.out.println("\"" + titulo + "\" removido do catálogo geral.");

            if (atual.esquerda == null) {
                return atual.direita;
            }

            if (atual.direita == null) {
                return atual.esquerda;
            }

            NoArvore menorDireita = encontrarMenor(atual.direita);
            atual.livro = menorDireita.livro;
            atual.direita = removerRecursivo(atual.direita, menorDireita.livro.getTitulo());
        }
        return atual;
    }

    private NoArvore encontrarMenor(NoArvore node) {
        return (node.esquerda == null) ? node : encontrarMenor(node.esquerda);
    }

    public void listarEmOrdem() {
        if (raiz == null) {
            System.out.println("O catálogo geral está vazio.");
            return;
        }
        System.out.println("\n--- CATÁLOGO GERAL DE LIVROS (Ordem Alfabética) ---");
        percorrerEImprimir(this.raiz);
        System.out.println("---------------------------------------------------");
    }

    private void percorrerEImprimir(NoArvore atual) {
        if (atual != null) {
            percorrerEImprimir(atual.esquerda);
            System.out.println(atual.livro.toString());
            percorrerEImprimir(atual.direita);
        }
    }

    public void salvarNoArquivo(FileWriter writer) {
        salvarRecursivo(this.raiz, writer);
    }

    private void salvarRecursivo(NoArvore atual, FileWriter writer) {
        if (atual != null) {
            try {
                salvarRecursivo(atual.esquerda, writer);
                writer.write(atual.livro.getTitulo() + ";" +
                             atual.livro.getAutor() + ";" +
                             atual.livro.getAno() + ";" +
                             atual.livro.getGenero() + ";" +
                             atual.livro.getRating() + "\n");
                salvarRecursivo(atual.direita, writer);
            } catch (Exception e) {
                System.out.println("Erro ao gravar nó no arquivo.");
            }
        }
    }

    private int compararTextos(String texto1, String texto2) {
        String t1 = texto1.toLowerCase();
        String t2 = texto2.toLowerCase();
        
        int menorTamanho = Math.min(t1.length(), t2.length());
        
        for (int i = 0; i < menorTamanho; i++) {
            char letra1 = t1.charAt(i);
            char letra2 = t2.charAt(i);
            
            if (letra1 < letra2) {
                return -1;
            } else if (letra1 > letra2) {
                return 1;
            }
        }
        
        if (t1.length() < t2.length()) return -1;
        if (t1.length() > t2.length()) return 1;
        
        return 0;
    }
}