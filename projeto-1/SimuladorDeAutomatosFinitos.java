import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

        var response = automato.toString();
        System.out.println(response);
    }
}