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
                String[] transicao = myReader.nextLine().split("");
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

            for (Map.Entry<String, Boolean> entry : respostas.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
    }

    public static Map<String, Boolean> simular(AutomatoFinito automato) {
        Map<String, Boolean> respostas = new HashMap<>();
        for (Cadeia cadeia : automato.getCadeias()) {
            System.out.println("Cadeia sendo testada: " + cadeia.cadeia);
            Estado estadoAtual = automato.getEstadoInicial();
            var index = 0;
            boolean answer = false;
            if (cadeia.cadeia.isEmpty()) {
                if (automato.getEstadoFinais().contains(estadoAtual)) {
                    System.out.println("Cadeia vazia");
                    respostas.put(cadeia.cadeia, true);
                    continue;
                }
                respostas.put(cadeia.cadeia, false);
                continue;
            }
            String simbolo = String.valueOf(cadeia.cadeia.charAt(index));
            List<Transicao> transicoes = automato.getTransicao(estadoAtual, simbolo);
            if (transicoes == null) {
                System.out.println("Não há transições possíveis");
                respostas.put(cadeia.cadeia, false);
                continue;
            }
            System.out.println("Transições possíveis: " + transicoes.size());
            for (Transicao transicao : transicoes) {
                System.out.println("Transição: " + transicao.getEstadoOrigem() + " " + transicao.getSimbolo() + " " + transicao.getEstadoDestino().estado);
                estadoAtual = transicao.getEstadoDestino();
                answer = simularInner(automato, estadoAtual, index+1, cadeia);
                System.out.println("Transição válida: " + answer);
                if (answer) {
                    break;
                }
            }
            System.out.println("Resposta para cadeia " + cadeia.cadeia + ": " + answer);
            respostas.put(cadeia.cadeia, answer);
        }
        return respostas;
    }

    public static boolean verificacaoFinal(Estado estadoAtual, AutomatoFinito automato) {
        System.out.println("Estado atual: " + estadoAtual.estado);
        System.out.println("Estados finais: " + automato.getEstadoFinais());
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
                System.out.println("Último index e estado final");
                return true;
            }
            return false;
        }
        var simboloAtual = String.valueOf(cadeia.cadeia.charAt(indexCadeia));
        List<Transicao> transicoesAtual = automato.getTransicao(estadoAtual, simboloAtual);
        if (transicoesAtual == null) {
            System.out.println("Lista de transições vazia");
            return false;
        }
        System.out.println("Transições possíveis: " + transicoesAtual.size());
        for (Transicao transicao : transicoesAtual) {
            System.out.println("Transição: " + transicao.getEstadoOrigem() + " " + transicao.getSimbolo() + " " + transicao.getEstadoDestino().estado);
            boolean result = simularInner(automato, transicao.getEstadoDestino(), indexCadeia + 1, cadeia);
            System.out.println("Transição válida: " + result);
            if (result) {
                return true;
            }
        }
        System.out.println("Nenhuma transição válida encontrada");
        return false;
    }
}