import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ArquivoBase<T extends Persistivel> {

    private final String nomeArquivo;

    public ArquivoBase(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    protected abstract T fromLinha(String linha);

    public ArrayList<T> listarTodos() {
        ArrayList<T> lista = new ArrayList<T>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(fromLinha(linha));
                }
            }
        } catch (IOException e) {
        }

        return lista;
    }

    public void salvarTodos(ArrayList<T> itens) {
        try (FileWriter escritor = new FileWriter(nomeArquivo, false)) {
            for (T item : itens) {
                escritor.write(item.toLinha() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
