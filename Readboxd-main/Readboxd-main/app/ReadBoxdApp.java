package app;

import models.Livro;
import structures.BibliotecaLista;
import structures.CatalogoBST;
import structures.FilaLeitura;
import structures.PilhaHistorico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReadBoxdApp {
    private CatalogoBST catalogo;
    private BibliotecaLista biblioteca;
    private FilaLeitura filaLeitura;
    private PilhaHistorico historico;
    private Scanner scanner;

    public ReadBoxdApp() {
        this.catalogo = new CatalogoBST();
        this.biblioteca = new BibliotecaLista();
        this.filaLeitura = new FilaLeitura();
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
                    historico.desempilhar();
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
                        }
                    } else {
                        System.out.println("Livro não encontrado no catálogo.");
                    }
                    historico.desempilhar();
                    break;
                
                case "3":
                    historico.empilhar("Minha Biblioteca");
                    biblioteca.listar();
                    historico.desempilhar();
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
                    historico.desempilhar();
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
                    historico.desempilhar();
                    break;
                
                case "6":
                    historico.empilhar("Histórico de Navegação");
                    historico.exibirHistorico();
                    historico.desempilhar();
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
                    historico.desempilhar();
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
        try (FileWriter writer = new FileWriter("banco_de_dados.csv")) {
            catalogo.salvarNoArquivo(writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo.");
        }
    }

    private void carregarDados() {
        File arquivo = new File("banco_de_dados.csv");
        if (arquivo.exists()) {
            try (Scanner leitorArquivo = new Scanner(arquivo)) {
                while (leitorArquivo.hasNextLine()) {
                    String linha = leitorArquivo.nextLine();
                    String[] dados = linha.split(";");
                    if (dados.length == 4) {
                        Livro livroRecuperado = new Livro(dados[0], dados[1], Integer.parseInt(dados[2]), dados[3]);
                        catalogo.inserir(livroRecuperado);
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao ler o arquivo de dados.");
            }
        } else {

            catalogo.inserir(new Livro("Poemas Selecionados", "Emily Dickinson", 1890, "Poesia"));
            catalogo.inserir(new Livro("Uma Vida Pequena", "Hanya Yanagihara", 2015, "Drama"));
            catalogo.inserir(new Livro("A Hora da Estrela", "Clarice Lispector", 1977, "Romance"));
            catalogo.inserir(new Livro("Capitães da Areia", "Jorge Amado", 1937, "Romance"));
        }
    }
}