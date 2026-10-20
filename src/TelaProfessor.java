import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaProfessor extends TelaCrudBase<Professor> {

    private JTextField campoNome;
    private JTextField campoTitulacao;
    private JTextField campoSalario;

    public TelaProfessor() {
        super(new ProfessorArquivo(), "Dados do Professor");
    }

    @Override
    protected JPanel criarPainelCampos() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 5, 5));

        painel.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painel.add(campoNome);

        painel.add(new JLabel("Titulação:"));
        campoTitulacao = new JTextField();
        painel.add(campoTitulacao);

        painel.add(new JLabel("Salário:"));
        campoSalario = new JTextField();
        painel.add(campoSalario);

        return painel;
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Nome", "Titulação", "Salário"};
    }

    @Override
    protected Object[] converterParaLinhaTabela(Professor professor) {
        return new Object[]{
                professor.getNome(),
                professor.getTitulacao(),
                String.format("%.2f", professor.getSalario())
        };
    }

    @Override
    protected boolean validarCampos() {
        return validarCamposPreenchidos(campoNome, campoTitulacao, campoSalario);
    }

    private double parseSalario() throws NumberFormatException {
        return Double.parseDouble(campoSalario.getText().trim().replace(",", "."));
    }

    @Override
    protected Professor criarEntidadeDosCampos() throws NumberFormatException {
        return new Professor(
                campoNome.getText().trim(),
                campoTitulacao.getText().trim(),
                parseSalario()
        );
    }

    @Override
    protected void atualizarEntidade(Professor professor) throws NumberFormatException {
        professor.setNome(campoNome.getText().trim());
        professor.setTitulacao(campoTitulacao.getText().trim());
        professor.setSalario(parseSalario());
    }

    @Override
    protected void preencherCampos(Professor professor) {
        campoNome.setText(professor.getNome());
        campoTitulacao.setText(professor.getTitulacao());
        campoSalario.setText(String.format("%.2f", professor.getSalario()));
    }

    @Override
    protected void limparCampos() {
        campoNome.setText("");
        campoTitulacao.setText("");
        campoSalario.setText("");
    }

    @Override
    protected void tratarErroNumero(NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "O salário deve ser um valor numérico válido!\nExemplo: 6200,50",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
    }
}
