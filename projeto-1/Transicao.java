public class Transicao {
    public Estado estadoOrigem;
    public Estado estadoDestino;
    public Palavra palavra;
    public Transicao(Estado estadoOrigem, Estado estadoDestino, Palavra palavra) {
        this.estadoOrigem = estadoOrigem;
        this.estadoDestino = estadoDestino;
        this.palavra = palavra;
    }

    @Override
    public String toString() {
        return "Transição{" +
                "estadoOrigem=" + estadoOrigem +
                ", estadoDestino=" + estadoDestino +
                ", palavra=" + palavra +
                '}';
    }

    public Estado getEstadoDestino() {
        return estadoDestino;
    }

    public Palavra getSimbolo() {
        return this.palavra;
    }

    public String getEstadoOrigem() {
        return this.estadoOrigem.estado;
    }
}
