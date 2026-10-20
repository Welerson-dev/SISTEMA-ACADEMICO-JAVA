/**
 * Classe responsável pela persistência de Professores em arquivo texto (professores.txt).
 * Herda as operações de ArquivoBase, eliminando duplicação de I/O (DRY).
 */
public class ProfessorArquivo extends ArquivoBase<Professor> {

    public ProfessorArquivo() {
        super("professores.txt", Professor::fromLinha);
    }
}
