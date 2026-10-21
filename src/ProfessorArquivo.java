import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProfessorArquivo {

    private String arquivo = "professores.txt";

    public ArrayList<Professor> listarTodos() {
        ArrayList<Professor> professores = new ArrayList<Professor>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    professores.add(Professor.fromLinha(linha));
                }
            }
        } catch (IOException e) {
        }
        return professores;
    }

    public void salvarTodos(ArrayList<Professor> professores) {
        try (FileWriter escritor = new FileWriter(arquivo, false)) {
            for (Professor professor : professores) {
                escritor.write(professor.toLinha() + "\n");
            }
        } catch (IOException e) {
        }
    }
}
