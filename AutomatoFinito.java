import java.util.ArrayList;
import java.util.List;

public class AutomatoFinito {
    public int numEstados;
    public List<Estado> estados;
    public int numTransicoes;
    public List<Transicao> transicoes;
    public int numSimbolosAlfabeto;
    public List<Simbolo> alfabeto;
    public Estado estadoInicial;
    public int numEstadosFinais;
    public List<Estado> estadoFinais;
    public int numCadeias;
    public List<Cadeia> cadeias;

    public AutomatoFinito() {
        this.estados = new ArrayList<>();
        this.transicoes = new ArrayList<>();
        this.alfabeto = new ArrayList<>();
        this.estadoInicial = new Estado();
        this.estadoFinais = new ArrayList<>();
        this.cadeias = new ArrayList<>();
    }

    public void setNumSimbolosAlfabeto(int numSimbolosAlfabeto) {
        this.numSimbolosAlfabeto = numSimbolosAlfabeto;
    }

    public int getNumSimbolosAlfabeto() {
        return numSimbolosAlfabeto;
    }

    public int getNumEstados() {
        return this.numEstados;
    }

    public void setNumEstados(int numEstados) {
        this.numEstados = numEstados;
    }

    public void addEstado(Estado estado) {
        this.estados.add(estado);
    }

    public void addTransicao(Transicao transicao) {
        this.transicoes.add(transicao);
    }

    public void addSimboloAlfabeto(String simbolo) {
        Simbolo a = new Simbolo();
        a.simbolo = simbolo;
        this.alfabeto.add(a);
    }

    public void setEstadoInicial(Estado estado) {
        this.estadoInicial = estado;
    }

    public void addEstadoFinal(Estado estado) {
        this.estadoFinais.add(estado);
    }

    public void addCadeia(String cadeia) {
        this.cadeias.add(new Cadeia(cadeia));
    }

    public List<Cadeia> getCadeias() {
        return this.cadeias;
    }

    public void setNumEstadosFinais(int i) {
        this.numEstadosFinais = i;
    }

    public int getNumEstadosFinais() {
        return this.numEstadosFinais;
    }

    public void setNumCadeias(int i) {
        this.numCadeias = i;
    }

    public int getNumCadeias() {
        return this.numCadeias;
    }

    public void setNumTransicoes(int i) {
        this.numTransicoes = i;
    }

    public int getNumTransicoes() {
        return this.numTransicoes;
    }

    public void addEstadoIfNotExists(int i) {
        Estado e = new Estado();
        e.estado = String.valueOf(i);
        if (!this.estados.contains(e)) {
            this.estados.add(e);
        }
    }

    public void addEstadoFinalIfNotExists(String estado) {
        Estado e = new Estado();
        e.estado = estado;
        if (!this.estadoFinais.contains(e)) {
            this.estadoFinais.add(e);
        }
    }

    public void addTransicaoIfNotExists(String s, String s1, String s2) {
        Estado e1 = new Estado();
        e1.estado = s;
        Estado e2 = new Estado();
        e2.estado = s1;
        Palavra p = new Palavra();
        p.palavra = s2;
        Transicao t = new Transicao(e1, e2, p);
        if (!this.transicoes.contains(t)) {
            this.transicoes.add(t);
        }
    }

    public String toString() {
        return "Automato Finito: {" +
                "\nnumEstados=" + numEstados +
                ", estados=" + estados +
                "\n\n" +
                "numTransições=" + numTransicoes +
                ", transições=" + transicoes.toString() +
                "\n\n" +
                "numSimbolosAlfabeto=" + numSimbolosAlfabeto +
                ", alfabeto=" + alfabeto.toString() +
                "\n\n" +
                "estadoInicial=" + estadoInicial.toString() +
                "numEstadosFinais=" + numEstadosFinais +
                "estadoFinais=" + estadoFinais.toString() +
                "\n\n" +
                "numCadeias=" + numCadeias +
                ", cadeias=" + cadeias.toString() +
                '}';
    }
}
