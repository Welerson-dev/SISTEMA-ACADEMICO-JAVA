public class DisciplinaArquivo extends ArquivoBase<Disciplina> {

    public DisciplinaArquivo() {
        super("disciplinas.txt");
    }

    @Override
    protected Disciplina fromLinha(String linha) {
        return Disciplina.fromLinha(linha);
    }
}
