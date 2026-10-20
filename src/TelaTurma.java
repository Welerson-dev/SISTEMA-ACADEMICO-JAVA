import javax.swing.*;
import java.awt.*;

/**
 * Tela da aba Turmas no JTabbedPane (desafio opcional).
 * Herda a estrutura de TelaCrudBase e implementa a conversão e tratamento
 * de exceção para o campo numérico de vagas (int).
 */
public class TelaTurma extends TelaCrudBase<Turma> {

    private JTextField campoCodigo;
    private JTextField campoCurso;
    private JTextField campoVagas;

    public TelaTurma() {
        super(new TurmaArquivo(), "Dados da Turma");
    }

    @Override
    protected JPanel criarPainelCampos() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 5, 5));

        painel.add(new JLabel("Código:"));
        campoCodigo = new JTextField();
        painel.add(campoCodigo);

        painel.add(new JLabel("Curso:"));
        campoCurso = new JTextField();
        painel.add(campoCurso);

        painel.add(new JLabel("Vagas:"));
        campoVagas = new JTextField();
        painel.add(campoVagas);

        return painel;
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Código", "Curso", "Vagas"};
    }

    @Override
    protected Object[] converterParaLinhaTabela(Turma turma) {
        return new Object[]{
                turma.getCodigo(),
                turma.getCurso(),
                turma.getVagas()
        };
    }

    @Override
    protected boolean validarCampos() {
        return validarCamposPreenchidos(campoCodigo, campoCurso, campoVagas);
    }

    private int parseVagas() throws NumberFormatException {
        return Integer.parseInt(campoVagas.getText().trim());
    }

    @Override
    protected Turma criarEntidadeDosCampos() throws NumberFormatException {
        return new Turma(
                campoCodigo.getText().trim(),
                campoCurso.getText().trim(),
                parseVagas()
        );
    }

    @Override
    protected void atualizarEntidade(Turma turma) throws NumberFormatException {
        turma.setCodigo(campoCodigo.getText().trim());
        turma.setCurso(campoCurso.getText().trim());
        turma.setVagas(parseVagas());
    }

    @Override
    protected void preencherCampos(Turma turma) {
        campoCodigo.setText(turma.getCodigo());
        campoCurso.setText(turma.getCurso());
        campoVagas.setText(String.valueOf(turma.getVagas()));
    }

    @Override
    protected void limparCampos() {
        campoCodigo.setText("");
        campoCurso.setText("");
        campoVagas.setText("");
    }

    @Override
    protected void tratarErroNumero(NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "As vagas devem ser um número inteiro válido!\nExemplo: 45",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
    }
}
