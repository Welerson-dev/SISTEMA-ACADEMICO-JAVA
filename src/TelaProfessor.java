import javax.swing.*;
import java.awt.*;

/**
 * Tela da aba Professores no JTabbedPane.
 * Herda a estrutura de TelaCrudBase e implementa a conversão e tratamento
 * de exceção para o campo numérico de salário (double).
 */
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
        // Exibe o salário formatado com duas casas decimais e vírgula
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

    /**
     * Converte o texto digitado em double, substituindo vírgula por ponto.
     * @throws NumberFormatException se o valor não for um número válido.
     */
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
        // Apresenta com vírgula para manter a experiência brasileira de digitação
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
