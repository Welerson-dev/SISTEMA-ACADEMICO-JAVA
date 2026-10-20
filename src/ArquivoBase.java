import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * Classe base genérica para operações de persistência em arquivos de texto.
 * Elimina a duplicação de código de I/O em conformidade com o princípio DRY (Don't Repeat Yourself).
 *
 * @param <T> Tipo da entidade que implementa Persistivel
 */
public abstract class ArquivoBase<T extends Persistivel> {

    private final String nomeArquivo;
    private final Function<String, T> parserLinha;

    /**
     * Construtor da classe base de persistência.
     *
     * @param nomeArquivo Nome do arquivo texto (ex: "alunos.txt")
     * @param parserLinha Função que converte uma linha do arquivo em um objeto T (ex: Aluno::fromLinha)
     */
    public ArquivoBase(String nomeArquivo, Function<String, T> parserLinha) {
        this.nomeArquivo = nomeArquivo;
        this.parserLinha = parserLinha;
    }

    /**
     * Lê todas as linhas do arquivo e retorna a lista de entidades.
     * Se o arquivo não existir, retorna uma lista vazia sem quebrar a aplicação.
     *
     * @return ArrayList com os objetos carregados do arquivo
     */
    public ArrayList<T> listarTodos() {
        ArrayList<T> lista = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(parserLinha.apply(linha));
                }
            }
        } catch (IOException e) {
            // Arquivo ainda não existe na primeira execução — retorna lista vazia
        }

        return lista;
    }

    /**
     * Reescreve o arquivo inteiro com o estado atual da lista.
     * Utiliza FileWriter(nomeArquivo, false) para garantir sobrescrita completa.
     *
     * @param itens Lista de entidades a serem persistidas
     */
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
