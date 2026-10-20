import javax.swing.*;
import java.awt.*;

/**
 * Tela da aba Disciplinas no JTabbedPane.
 * Herda a estrutura de TelaCrudBase e implementa a conversão e tratamento
 * de exceção para o campo numérico de carga horária (int).
 */
public class TelaDisciplina extends TelaCrudBase<Disciplina> {

    private JTextField campoNome;
    private JTextField campoCargaHoraria;
    private JTextField campoPeriodo;

    public TelaDisciplina() {
        super(new DisciplinaArquivo(), "Dados da Disciplina");
    }

    @Override
    protected JPanel criarPainelCampos() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 5, 5));

        painel.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painel.add(campoNome);

        painel.add(new JLabel("Carga Horária:"));
        campoCargaHoraria = new JTextField();
        painel.add(campoCargaHoraria);

        painel.add(new JLabel("Período:"));
        campoPeriodo = new JTextField();
        painel.add(campoPeriodo);

        return painel;
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Nome", "Carga Horária", "Período"};
    }

    @Override
    protected Object[] converterParaLinhaTabela(Disciplina disciplina) {
        return new Object[]{
                disciplina.getNome(),
                disciplina.getCargaHoraria(),
                disciplina.getPeriodo()
        };
    }

    @Override
    protected boolean validarCampos() {
        return validarCamposPreenchidos(campoNome, campoCargaHoraria, campoPeriodo);
    }

    private int parseCargaHoraria() throws NumberFormatException {
        return Integer.parseInt(campoCargaHoraria.getText().trim());
    }

    @Override
    protected Disciplina criarEntidadeDosCampos() throws NumberFormatException {
        return new Disciplina(
                campoNome.getText().trim(),
                parseCargaHoraria(),
                campoPeriodo.getText().trim()
        );
    }

    @Override
    protected void atualizarEntidade(Disciplina disciplina) throws NumberFormatException {
        disciplina.setNome(campoNome.getText().trim());
        disciplina.setCargaHoraria(parseCargaHoraria());
        disciplina.setPeriodo(campoPeriodo.getText().trim());
    }

    @Override
    protected void preencherCampos(Disciplina disciplina) {
        campoNome.setText(disciplina.getNome());
        campoCargaHoraria.setText(String.valueOf(disciplina.getCargaHoraria()));
        campoPeriodo.setText(disciplina.getPeriodo());
    }

    @Override
    protected void limparCampos() {
        campoNome.setText("");
        campoCargaHoraria.setText("");
        campoPeriodo.setText("");
    }

    @Override
    protected void tratarErroNumero(NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "A carga horária deve ser um número inteiro válido!\nExemplo: 60",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
    }
}
