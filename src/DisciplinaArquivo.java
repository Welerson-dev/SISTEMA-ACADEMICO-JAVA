/**
 * Classe responsável pela persistência de Disciplinas em arquivo texto (disciplinas.txt).
 * Herda as operações de ArquivoBase, eliminando duplicação de I/O (DRY).
 */
public class DisciplinaArquivo extends ArquivoBase<Disciplina> {

    public DisciplinaArquivo() {
        super("disciplinas.txt", Disciplina::fromLinha);
    }
}
