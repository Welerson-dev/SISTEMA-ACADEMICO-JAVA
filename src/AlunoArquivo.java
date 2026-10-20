/**
 * Classe responsável pela persistência de Alunos em arquivo texto (alunos.txt).
 * Herda as operações de ArquivoBase, eliminando duplicação de I/O (DRY).
 */
public class AlunoArquivo extends ArquivoBase<Aluno> {

    public AlunoArquivo() {
        super("alunos.txt", Aluno::fromLinha);
    }
}
