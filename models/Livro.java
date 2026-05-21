package models;

public class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private String genero;
    private double rating; 

    public Livro(String titulo, String autor, int ano, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.genero = genero;
        this.rating = 0.0; 
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double nota) {
        if (nota >= 0.0 && nota <= 5.0) {
            this.rating = nota;
        } else {
            System.out.println("A nota deve ser entre 0.0 e 5.0!");
        }
    }

    @Override
    public String toString() {
        String notaFormatada = (this.rating == 0.0) ? "Nenhuma" : String.valueOf(this.rating);
        return "Título: \"" + this.titulo + "\" | Autor: " + this.autor + 
               " | Ano: " + this.ano + " | Gênero: " + this.genero + 
               " | Avaliação: " + notaFormatada;
    }
}