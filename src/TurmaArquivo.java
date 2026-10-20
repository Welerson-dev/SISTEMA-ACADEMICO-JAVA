/**
 * Classe responsável pela persistência de Turmas em arquivo texto (turmas.txt).
 * Herda as operações de ArquivoBase, eliminando duplicação de I/O (DRY).
 */
public class TurmaArquivo extends ArquivoBase<Turma> {

    public TurmaArquivo() {
        super("turmas.txt", Turma::fromLinha);
    }
}
