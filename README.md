# ReadBoxd

**ReadBoxd** é uma plataforma de gerenciamento de leitura inspirada no funcionamento do Letterboxd. Este projeto foi desenvolvido como Projeto Final (AV3) da disciplina de **Estrutura de Dados** do curso de Ciência da Computação da Universidade de Fortaleza (UNIFOR).

O objetivo central do sistema é aplicar, de forma prática e integrada, diferentes estruturas de dados dinâmicas para gerenciar o catálogo de livros, a biblioteca pessoal do usuário, sua fila de leituras futuras e o histórico de navegação.

---

## Estruturas de Dados Utilizadas

Para garantir a eficiência e demonstrar a aplicação dos conceitos estudados, o sistema utiliza 4 estruturas de dados principais, cada uma isolada em sua própria classe e responsável por uma regra de negócio específica:

1. **Árvore Binária de Busca (BST) - `CatalogoBST`**
   - **Uso:** Organiza o catálogo geral de livros disponíveis na plataforma.
   - **Vantagem:** Permite buscas eficientes por título e a exibição do catálogo em ordem alfabética utilizando o percurso *In-Order*.

2. **Lista Dinâmica Encadeada - `BibliotecaLista`**
   - **Uso:** Gerencia a biblioteca pessoal do usuário.
   - **Vantagem:** Oferece flexibilidade total para adicionar, remover e listar livros lidos, além de permitir a atribuição de notas (ratings de 1.0 a 5.0), sem desperdício de memória.

3. **Fila Dinâmica (FIFO) - `LeituraFila`**
   - **Uso:** Controla a funcionalidade "Ler Depois" (Read Later).
   - **Vantagem:** Respeita a ordem cronológica de interesse. O primeiro livro adicionado à fila será o primeiro a ser lido e removido.

4. **Pilha Dinâmica (LIFO) - `PilhaHistorico`**
   - **Uso:** Registra o histórico de navegação do usuário.
   - **Vantagem:** Simula o comportamento do botão "voltar" de um navegador, permitindo desempilhar a última tela acessada para retornar à anterior.

---

## Funcionalidades e Requisitos Atendidos

- [x] Implementação de pelo menos 3 estruturas de dados dinâmicas.
- [x] Integração total entre as estruturas (Ex: buscar na árvore para inserir na lista).
- [x] Operações de Inserção, Remoção, Busca e Impressão em todas as estruturas.
- [x] **Requisito Bônus:** Persistência de dados. O sistema salva e carrega o catálogo, a biblioteca pessoal e a fila de leitura em arquivos CSV para não perder o progresso entre as sessões.

---

## Arquitetura do Projeto

O código-fonte está estruturado no padrão de pacotes (packages) do Java para manter a separação de responsabilidades (MVC / Camadas):

    src/
    ├── app/                  # Lógica de controle, menus e inicialização
    │   ├── Main.java
    │   └── ReadBoxdApp.java
    ├── models/               # Entidades de domínio e Nós
    │   ├── Livro.java
    │   ├── NoArvore.java
    │   ├── NoLista.java
    │   └── NoString.java
    └── structures/           # Implementação pura das estruturas de dados
        ├── BibliotecaLista.java
        ├── CatalogoBST.java
        ├── LeituraFila.java
        └── PilhaHistorico.java

---

## Como Executar

1. Certifique-se de ter o **Java JDK** instalado na sua máquina.
2. Clone o repositório:
    git clone https://github.com/LaraSLGomes/Readboxd.git
3. Navegue até a pasta raiz do projeto.
4. Compile as classes:
    javac app/Main.java models/Livro.java models/NoArvore.java models/NoLista.java models/NoString.java structures/CatalogoBST.java structures/BibliotecaLista.java structures/LeituraFila.java structures/PilhaHistorico.java
5. Execute o sistema:
    java app.Main
