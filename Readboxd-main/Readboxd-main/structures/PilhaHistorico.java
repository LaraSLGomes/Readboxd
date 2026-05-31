package structures;

import models.NoString;

public class PilhaHistorico {
    private NoString topo;

    public PilhaHistorico() {
        this.topo = null;
    }

    public boolean estaVazia() {
        return this.topo == null;
    }

    public void empilhar(String pagina) {
        NoString novoNo = new NoString(pagina);
        novoNo.setProximo(this.topo);
        this.topo = novoNo;

        System.out.println("Página adicionada ao histórico: " + pagina);
    }

    public String desempilhar() {
        if (estaVazia()) {
            System.out.println("Histórico vazio. Não há página para voltar.");
            return null;
        }

        String paginaRemovida = this.topo.pagina;
        this.topo = this.topo.getProximo();

        return paginaRemovida;
    }

    public String consultarTopo() {
        if (estaVazia()) {
            System.out.println("Histórico vazio.");
            return null;
        }

        return this.topo.pagina;
    }

    public void voltarPagina() {
        if (estaVazia()) {
            System.out.println("Histórico vazio. Não é possível voltar.");
            return;
        }

        String paginaAtual = desempilhar();

        System.out.println("Voltando da página: " + paginaAtual);

        if (!estaVazia()) {
            System.out.println("Página anterior: " + consultarTopo());
        } else {
            System.out.println("Não há página anterior no histórico.");
        }
    }

    public void exibirHistorico() {
        if (estaVazia()) {
            System.out.println("Histórico de navegação vazio.");
            return;
        }

        System.out.println("\n--- HISTÓRICO DE NAVEGAÇÃO ---");

        NoString atual = this.topo;

        while (atual != null) {
            System.out.println(atual.pagina);
            atual = atual.getProximo();
        }

        System.out.println("------------------------------");
    }
}