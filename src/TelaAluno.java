import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaAluno extends TelaCrudBase<Aluno> {

    private JTextField campoNome;
    private JTextField campoMatricula;
    private JTextField campoCidade;

    public TelaAluno() {
        super(new AlunoArquivo(), "Dados do Aluno");
    }

    @Override
    protected JPanel criarPainelCampos() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 5, 5));

        painel.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painel.add(campoNome);

        painel.add(new JLabel("Matrícula:"));
        campoMatricula = new JTextField();
        painel.add(campoMatricula);

        painel.add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        painel.add(campoCidade);

        return painel;
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Nome", "Matrícula", "Cidade"};
    }

    @Override
    protected Object[] converterParaLinhaTabela(Aluno aluno) {
        return new Object[]{aluno.getNome(), aluno.getMatricula(), aluno.getCidade()};
    }

    @Override
    protected boolean validarCampos() {
        return validarCamposPreenchidos(campoNome, campoMatricula, campoCidade);
    }

    @Override
    protected Aluno criarEntidadeDosCampos() {
        return new Aluno(
                campoNome.getText().trim(),
                campoMatricula.getText().trim(),
                campoCidade.getText().trim()
        );
    }

    @Override
    protected void atualizarEntidade(Aluno aluno) {
        aluno.setNome(campoNome.getText().trim());
        aluno.setMatricula(campoMatricula.getText().trim());
        aluno.setCidade(campoCidade.getText().trim());
    }

    @Override
    protected void preencherCampos(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoMatricula.setText(aluno.getMatricula());
        campoCidade.setText(aluno.getCidade());
    }

    @Override
    protected void limparCampos() {
        campoNome.setText("");
        campoMatricula.setText("");
        campoCidade.setText("");
    }
}
