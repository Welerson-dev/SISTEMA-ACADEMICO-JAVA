public class TurmaArquivo extends ArquivoBase<Turma> {

    public TurmaArquivo() {
        super("turmas.txt");
    }

    @Override
    protected Turma fromLinha(String linha) {
        return Turma.fromLinha(linha);
    }
}
