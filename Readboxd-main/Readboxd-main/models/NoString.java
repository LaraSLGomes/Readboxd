package models;

public class NoString {
    public String pagina;       
    private NoString proximo;   

    public NoString(String pagina) {
        this.pagina = pagina;
        this.proximo = null;
    }

    public NoString getProximo() {
        return this.proximo;
    }

    public void setProximo(NoString proximo) {
        this.proximo = proximo;
    }

    public boolean temProximo() {
        return this.proximo != null;
    }
}