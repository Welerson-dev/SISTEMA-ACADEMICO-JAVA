import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe responsável pela persistência de Alunos em arquivo texto.
 * É a única classe que abre o arquivo alunos.txt.
 * Operações: listarTodos (lê do arquivo) e salvarTodos (reescreve o arquivo inteiro).
 */
public class AlunoArquivo {

    private static final String ARQUIVO = "alunos.txt";

    /**
     * Lê todas as linhas do arquivo alunos.txt e retorna uma lista de objetos Aluno.
     * Se o arquivo não existir ou estiver vazio, retorna uma lista vazia.
     */
    public ArrayList<Aluno> listarTodos() {
        ArrayList<Aluno> alunos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    alunos.add(Aluno.fromLinha(linha));
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe — retorna lista vazia
        }

        return alunos;
    }

    /**
     * Reescreve o arquivo alunos.txt inteiro com a lista atual de alunos.
     * Usa FileWriter(arquivo, false) para sobrescrever — um append não serve
     * para alterar nem excluir registros.
     */
    public void salvarTodos(ArrayList<Aluno> alunos) {
        try (FileWriter escritor = new FileWriter(ARQUIVO, false)) {
            for (Aluno aluno : alunos) {
                escritor.write(aluno.toLinha() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo de alunos: " + e.getMessage());
        }
    }
}
