public class ProfessorArquivo extends ArquivoBase<Professor> {

    public ProfessorArquivo() {
        super("professores.txt");
    }

    @Override
    protected Professor fromLinha(String linha) {
        return Professor.fromLinha(linha);
    }
}
