package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import models.Livro;
import structures.BibliotecaLista;
import structures.CatalogoBST;
import structures.LeituraFila;
import structures.PilhaHistorico;

public class ReadBoxdApp {
    private static final String ARQUIVO_CATALOGO = "catalogo.csv";
    private static final String ARQUIVO_BIBLIOTECA = "biblioteca.csv";
    private static final String ARQUIVO_FILA = "fila_de_leitura.csv";

    private CatalogoBST catalogo;
    private BibliotecaLista biblioteca;
    private LeituraFila filaLeitura;
    private PilhaHistorico historico;
    private Scanner scanner;

    public ReadBoxdApp() {
        this.catalogo = new CatalogoBST();
        this.biblioteca = new BibliotecaLista();
        this.filaLeitura = new LeituraFila();
        this.historico = new PilhaHistorico();
        this.scanner = new Scanner(System.in);
        carregarDados();
    }

    public void iniciarLoopDeComandos() {
        historico.empilhar("Menu Principal");
        boolean rodando = true;

        while (rodando) {
            System.out.println("             READBOXD              ");
            System.out.println("1. Ver Catálogo Geral (Ordem Alfabética)");
            System.out.println("2. Buscar Livro no Catálogo / Adicionar");
            System.out.println("3. Ver Minha Biblioteca Pessoal");
            System.out.println("4. Avaliar Livro da Biblioteca");
            System.out.println("5. Ver Fila 'Ler Depois'");
            System.out.println("6. Ver Histórico de Navegação");
            System.out.println("7. Cadastrar Novo Livro no Catálogo");
            System.out.println("0. Salvar e Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    historico.empilhar("Catálogo Geral");
                    catalogo.listarEmOrdem();
                    break;
                
                case "2":
                    historico.empilhar("Buscar no Catálogo");
                    System.out.print("Digite o título do livro: ");
                    String tituloBusca = scanner.nextLine();
                    Livro achado = catalogo.buscar(tituloBusca);
                    
                    if (achado != null) {
                        System.out.println("\nLivro Encontrado: " + achado.toString());
                        System.out.println("Deseja: [1] Adicionar à Biblioteca | [2] Ler Depois | [3] Voltar");
                        System.out.print("Escolha: ");
                        String acao = scanner.nextLine();
                        if (acao.equals("1")) {
                            biblioteca.adicionar(achado);
                        } else if (acao.equals("2")) {
                            filaLeitura.enfileirar(achado);
                        } else if (acao.equals("3")) {
                            System.out.println("Voltando ao menu principal...");
                        } else {
                            System.out.println("Ação inválida! Voltando ao menu principal...");
                        }
                    } else {
                        System.out.println("Livro não encontrado no catálogo.");
                    }
                    break;
                
                case "3":
                    historico.empilhar("Minha Biblioteca");
                    biblioteca.listar();
                    break;
                
                case "4":

                    historico.empilhar("Avaliar Livro");
                    System.out.print("Qual livro da sua biblioteca deseja avaliar? ");
                    String tituloAvaliar = scanner.nextLine();
                    System.out.print("Digite a nota (de 1.0 a 5.0): ");
                    try {
                        double nota = Double.parseDouble(scanner.nextLine());
                        biblioteca.avaliar(tituloAvaliar, nota);
                    } catch (Exception e) {
                        System.out.println("Erro: Digite um valor decimal válido usando ponto (ex: 4.5).");
                    }
                    break;
                
                case "5":

                    historico.empilhar("Fila de Leitura");
                    filaLeitura.listar();
                    System.out.print("\nDeseja iniciar a leitura do próximo livro da fila? (s/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        Livro removido = filaLeitura.desenfileirar();
                        if (removido != null) {
                            System.out.println("Você começou a ler: \"" + removido.getTitulo() + "\". Removido da fila.");
                        }
                    }
                    break;
                
                case "6":
                    historico.empilhar("Histórico de Navegação");
                    historico.exibirHistorico();
                    break;
                
                case "7":

                    historico.empilhar("Cadastrar Livro");
                    System.out.println("\n--- CADASTRAR NOVO LIVRO ---");
                    System.out.print("Título: "); String t = scanner.nextLine();
                    System.out.print("Autor: "); String a = scanner.nextLine();
                    System.out.print("Ano de Publicação: "); 
                    int ano = 0;
                    try { ano = Integer.parseInt(scanner.nextLine()); } catch(Exception e) { ano = 0; }
                    System.out.print("Gênero: "); String g = scanner.nextLine();
                    
                    catalogo.inserir(new Livro(t, a, ano, g));
                    System.out.println("Livro \"" + t + "\" cadastrado com sucesso!");
                    break;

                case "0":

                    System.out.println("Salvando dados no banco de arquivos (banco_de_dados.csv)...");
                    salvarDados(); 
                    System.out.println("Encerrando o ReadBoxd... Até logo!");
                    rodando = false;
                    break;
                
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO_CATALOGO)) {
            catalogo.salvarNoArquivo(writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o catálogo no arquivo.");
        }

        try (FileWriter writer = new FileWriter("banco_de_dados.csv")) {
            catalogo.salvarNoArquivo(writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o catálogo no arquivo antigo.");
        }

        try (FileWriter writer = new FileWriter(ARQUIVO_BIBLIOTECA)) {
            biblioteca.salvarNoArquivo(writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a biblioteca no arquivo.");
        }

        try (FileWriter writer = new FileWriter(ARQUIVO_FILA)) {
            filaLeitura.salvarNoArquivo(writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a fila de leitura no arquivo.");
        }
    }

    private void carregarDados() {
        File arquivoCatalogo = new File(ARQUIVO_CATALOGO);
        File arquivoCatalogoAntigo = new File("banco_de_dados.csv");

        if (arquivoCatalogo.exists()) {
            carregarCatalogo(arquivoCatalogo);
        } else if (arquivoCatalogoAntigo.exists()) {
            carregarCatalogo(arquivoCatalogoAntigo);
        } else {
            carregarCatalogoPadrao();
        }

        File arquivoBiblioteca = new File(ARQUIVO_BIBLIOTECA);
        if (arquivoBiblioteca.exists()) {
            carregarBiblioteca(arquivoBiblioteca);
        }

        File arquivoFila = new File(ARQUIVO_FILA);
        if (arquivoFila.exists()) {
            carregarFila(arquivoFila);
        }
    }

    private void carregarCatalogo(File arquivo) {
        try (Scanner leitorArquivo = new Scanner(arquivo)) {
            while (leitorArquivo.hasNextLine()) {
                String linha = leitorArquivo.nextLine();
                Livro livroRecuperado = criarLivroAPartirDeDados(linha.split(";"));
                if (livroRecuperado != null) {
                    catalogo.inserir(livroRecuperado);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo do catálogo.");
        }
    }

    private void carregarBiblioteca(File arquivo) {
        try (Scanner leitorArquivo = new Scanner(arquivo)) {
            biblioteca.carregarDoArquivo(leitorArquivo);
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo da biblioteca.");
        }
    }

    private void carregarFila(File arquivo) {
        try (Scanner leitorArquivo = new Scanner(arquivo)) {
            filaLeitura.carregarDoArquivo(leitorArquivo);
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo da fila de leitura.");
        }
    }

    private void carregarCatalogoPadrao() {
        catalogo.inserir(new Livro("Poemas Selecionados", "Emily Dickinson", 1890, "Poesia"));
        catalogo.inserir(new Livro("Uma Vida Pequena", "Hanya Yanagihara", 2015, "Drama"));
        catalogo.inserir(new Livro("A Hora da Estrela", "Clarice Lispector", 1977, "Romance"));
        catalogo.inserir(new Livro("Capitães da Areia", "Jorge Amado", 1937, "Romance"));
    }

    private Livro criarLivroAPartirDeDados(String[] dados) {
        if (dados.length < 4) {
            return null;
        }

        try {
            String titulo = dados[0];
            String autor = dados[1];
            int ano = Integer.parseInt(dados[2]);
            String genero = dados[3];
            Livro livro = new Livro(titulo, autor, ano, genero);

            if (dados.length >= 5) {
                try {
                    double nota = Double.parseDouble(dados[4]);
                    livro.setRating(nota);
                } catch (NumberFormatException ignored) {
                }
            }

            return livro;
        } catch (Exception e) {
            return null;
        }
    }
}