import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe responsável pela persistência de Professores em arquivo texto.
 * É a única classe que abre o arquivo professores.txt.
 * Operações: listarTodos (lê do arquivo) e salvarTodos (reescreve o arquivo inteiro).
 */
public class ProfessorArquivo {

    private static final String ARQUIVO = "professores.txt";

    /**
     * Lê todas as linhas do arquivo professores.txt e retorna uma lista de objetos Professor.
     * Se o arquivo não existir ou estiver vazio, retorna uma lista vazia.
     */
    public ArrayList<Professor> listarTodos() {
        ArrayList<Professor> professores = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    professores.add(Professor.fromLinha(linha));
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe — retorna lista vazia
        }

        return professores;
    }

    /**
     * Reescreve o arquivo professores.txt inteiro com a lista atual de professores.
     * Usa FileWriter(arquivo, false) para sobrescrever.
     */
    public void salvarTodos(ArrayList<Professor> professores) {
        try (FileWriter escritor = new FileWriter(ARQUIVO, false)) {
            for (Professor professor : professores) {
                escritor.write(professor.toLinha() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo de professores: " + e.getMessage());
        }
    }
}
