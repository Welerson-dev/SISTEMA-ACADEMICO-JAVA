import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AlunoArquivo {

    private String arquivo = "alunos.txt";

    public ArrayList<Aluno> listarTodos() {
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    alunos.add(Aluno.fromLinha(linha));
                }
            }
        } catch (IOException e) {
        }
        return alunos;
    }

    public void salvarTodos(ArrayList<Aluno> alunos) {
        try (FileWriter escritor = new FileWriter(arquivo, false)) {
            for (Aluno aluno : alunos) {
                escritor.write(aluno.toLinha() + "\n");
            }
        } catch (IOException e) {
        }
    }
}
