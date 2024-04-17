import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SimuladorDeAutomatosFinitos {
    public static void main(String[] args) {
        AutomatoFinito automato = new AutomatoFinito();

        try (Scanner myReader = new Scanner(new File("automato.txt"))) {
            automato.setNumEstados(Integer.parseInt(myReader.nextLine()));
            for (int i = 0; i < automato.getNumEstados(); i++) {
                automato.addEstadoIfNotExists(i);
            }

            automato.setEstadoInicial(automato.estados.get(0));

            String[] numSimbolosParts = myReader.nextLine().split(" ");
            automato.setNumSimbolosAlfabeto(Integer.parseInt(numSimbolosParts[0]));
            for (int i = 1; i < numSimbolosParts.length; i++) {
                automato.addSimboloAlfabeto(numSimbolosParts[i]);
            }

            String[] numEstadosFinaisParts = myReader.nextLine().split(" ");
            automato.setNumEstadosFinais(Integer.parseInt(numEstadosFinaisParts[0]));
            for (int i = 1; i < numEstadosFinaisParts.length; i++) {
                automato.addEstadoFinalIfNotExists(numEstadosFinaisParts[i]);
            }

            automato.setNumTransicoes(Integer.parseInt(myReader.nextLine()));
            for (int i = 0; i < automato.getNumTransicoes(); i++) {
                String[] transicao = myReader.nextLine().split(" ");
                automato.addTransicaoIfNotExists(transicao[0], transicao[2], transicao[1]);
            }

            automato.setNumCadeias(Integer.parseInt(myReader.nextLine()));
            for (int i = 0; i < automato.getNumCadeias(); i++) {
                if (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    if (line.equals("-")) {
                        automato.addCadeia("");
                    } else {
                        automato.addCadeia(line);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        Map<String, Boolean> respostas = simular(automato);

        for (Cadeia cadeia : automato.getCadeias()) {
            System.out.println(cadeia.cadeia + " " + (respostas.get(cadeia.cadeia) ? "aceita" : "rejeita"));
        }
    }

    public static Map<String, Boolean> simular(AutomatoFinito automato) {
        Map<String, Boolean> respostas = new HashMap<>();
        for (Cadeia cadeia : automato.getCadeias()) {
            Estado estadoAtual = automato.getEstadoInicial();
            var index = 0;
            boolean answer = false;
            if (cadeia.cadeia.isEmpty()) {
                if (verificacaoFinal(estadoAtual, automato)) {
                    respostas.put(cadeia.cadeia, true);
                    continue;
                }
                respostas.put(cadeia.cadeia, false);
                continue;
            }
            String simbolo = String.valueOf(cadeia.cadeia.charAt(index));
            List<Transicao> transicoes = automato.getTransicao(estadoAtual, simbolo);
            if (transicoes == null) {
                respostas.put(cadeia.cadeia, false);
                continue;
            }
            for (Transicao transicao : transicoes) {
                estadoAtual = transicao.getEstadoDestino();
                answer = simularInner(automato, estadoAtual, index+1, cadeia);
                if (answer) {
                    break;
                }
            }
            respostas.put(cadeia.cadeia, answer);
        }
        return respostas;
    }

    public static boolean verificacaoFinal(Estado estadoAtual, AutomatoFinito automato) {
        for (Estado estadoFinal : automato.getEstadoFinais()) {
            if (estadoAtual.estado.equals(estadoFinal.estado)) {
                return true;
            }
        }
        return false;
    }

    public static boolean simularInner(AutomatoFinito automato, Estado estadoAtual, int indexCadeia, Cadeia cadeia){
        if (indexCadeia == cadeia.cadeia.length()) {
            if (verificacaoFinal(estadoAtual, automato)) {
                return true;
            }
            return false;
        }
        var simboloAtual = String.valueOf(cadeia.cadeia.charAt(indexCadeia));
        List<Transicao> transicoesAtual = automato.getTransicao(estadoAtual, simboloAtual);
        if (transicoesAtual == null) {
            return false;
        }
        for (Transicao transicao : transicoesAtual) {
            boolean result = simularInner(automato, transicao.getEstadoDestino(), indexCadeia + 1, cadeia);
            if (result) {
                return true;
            }
        }
        return false;
    }
}