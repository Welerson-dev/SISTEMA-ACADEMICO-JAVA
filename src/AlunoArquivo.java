public class AlunoArquivo extends ArquivoBase<Aluno> {

    public AlunoArquivo() {
        super("alunos.txt");
    }

    @Override
    protected Aluno fromLinha(String linha) {
        return Aluno.fromLinha(linha);
    }
}
