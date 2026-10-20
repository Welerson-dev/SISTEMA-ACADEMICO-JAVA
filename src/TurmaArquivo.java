import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe responsável pela persistência de Turmas em arquivo texto (desafio opcional).
 * É a única classe que abre o arquivo turmas.txt.
 * Operações: listarTodos (lê do arquivo) e salvarTodos (reescreve o arquivo inteiro).
 */
public class TurmaArquivo {

    private static final String ARQUIVO = "turmas.txt";

    /**
     * Lê todas as linhas do arquivo turmas.txt e retorna uma lista de objetos Turma.
     * Se o arquivo não existir ou estiver vazio, retorna uma lista vazia.
     */
    public ArrayList<Turma> listarTodos() {
        ArrayList<Turma> turmas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    turmas.add(Turma.fromLinha(linha));
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe — retorna lista vazia
        }

        return turmas;
    }

    /**
     * Reescreve o arquivo turmas.txt inteiro com a lista atual de turmas.
     * Usa FileWriter(arquivo, false) para sobrescrever.
     */
    public void salvarTodos(ArrayList<Turma> turmas) {
        try (FileWriter escritor = new FileWriter(ARQUIVO, false)) {
            for (Turma turma : turmas) {
                escritor.write(turma.toLinha() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo de turmas: " + e.getMessage());
        }
    }
}
