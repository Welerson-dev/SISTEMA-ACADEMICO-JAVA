import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe responsável pela persistência de Disciplinas em arquivo texto.
 * É a única classe que abre o arquivo disciplinas.txt.
 * Operações: listarTodos (lê do arquivo) e salvarTodos (reescreve o arquivo inteiro).
 */
public class DisciplinaArquivo {

    private static final String ARQUIVO = "disciplinas.txt";

    /**
     * Lê todas as linhas do arquivo disciplinas.txt e retorna uma lista de objetos Disciplina.
     * Se o arquivo não existir ou estiver vazio, retorna uma lista vazia.
     */
    public ArrayList<Disciplina> listarTodos() {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    disciplinas.add(Disciplina.fromLinha(linha));
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe — retorna lista vazia
        }

        return disciplinas;
    }

    /**
     * Reescreve o arquivo disciplinas.txt inteiro com a lista atual de disciplinas.
     * Usa FileWriter(arquivo, false) para sobrescrever.
     */
    public void salvarTodos(ArrayList<Disciplina> disciplinas) {
        try (FileWriter escritor = new FileWriter(ARQUIVO, false)) {
            for (Disciplina disciplina : disciplinas) {
                escritor.write(disciplina.toLinha() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo de disciplinas: " + e.getMessage());
        }
    }
}
