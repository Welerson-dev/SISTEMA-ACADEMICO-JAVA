import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DisciplinaArquivo {

    private String arquivo = "disciplinas.txt";

    public ArrayList<Disciplina> listarTodos() {
        ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    disciplinas.add(Disciplina.fromLinha(linha));
                }
            }
        } catch (IOException e) {
        }
        return disciplinas;
    }

    public void salvarTodos(ArrayList<Disciplina> disciplinas) {
        try (FileWriter escritor = new FileWriter(arquivo, false)) {
            for (Disciplina disciplina : disciplinas) {
                escritor.write(disciplina.toLinha() + "\n");
            }
        } catch (IOException e) {
        }
    }
}
