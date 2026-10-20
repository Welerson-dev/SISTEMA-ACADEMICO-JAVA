import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela da aba Professores no JTabbedPane.
 * Contém JTable + campos de texto + botões Adicionar, Alterar e Excluir.
 * Cuida de todo o CRUD da entidade Professor, usando ProfessorArquivo para persistência.
 *
 * Diferença em relação à TelaAluno: o campo salário é numérico (double).
 * O usuário digita com vírgula (padrão brasileiro), e a vírgula é trocada
 * por ponto antes de converter com Double.parseDouble.
 * Se o texto não for um número válido, trata NumberFormatException com JOptionPane.
 */
public class TelaProfessor extends JPanel {

    private JTextField campoNome;
    private JTextField campoTitulacao;
    private JTextField campoSalario;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ArrayList<Professor> professores;
    private ProfessorArquivo professorArquivo;

    public TelaProfessor() {
        professorArquivo = new ProfessorArquivo();
        professores = professorArquivo.listarTodos();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel de campos ---
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        painelCampos.setBorder(BorderFactory.createTitledBorder("Dados do Professor"));

        painelCampos.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelCampos.add(campoNome);

        painelCampos.add(new JLabel("Titulação:"));
        campoTitulacao = new JTextField();
        painelCampos.add(campoTitulacao);

        painelCampos.add(new JLabel("Salário:"));
        campoSalario = new JTextField();
        painelCampos.add(campoSalario);

        add(painelCampos, BorderLayout.NORTH);

        // --- Tabela ---
        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Titulação", "Salário"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // A edição é feita pelos campos + botão Alterar
            }
        };
        tabela = new JTable(modeloTabela);
        atualizarTabela();

        // Listener de seleção: ao clicar numa linha, preenche os campos
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    Professor professor = professores.get(linhaSelecionada);
                    campoNome.setText(professor.getNome());
                    campoTitulacao.setText(professor.getTitulacao());
                    // Exibe o salário com vírgula para o usuário brasileiro
                    campoSalario.setText(String.valueOf(professor.getSalario()).replace(".", ","));
                }
            }
        });

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // --- Painel de botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        btnAdicionar.addActionListener(e -> adicionar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    /**
     * Converte o texto do campo salário para double.
     * Troca vírgula por ponto antes de converter, pois o usuário brasileiro
     * digita com vírgula, mas Double.parseDouble exige ponto.
     *
     * @throws NumberFormatException se o texto não for um número válido
     */
    private double converterSalario(String texto) throws NumberFormatException {
        return Double.parseDouble(texto.replace(",", "."));
    }

    /**
     * Adiciona um novo professor com os dados dos campos.
     * Nenhum campo pode ficar vazio. O salário deve ser um número válido.
     */
    private void adicionar() {
        String nome = campoNome.getText().trim();
        String titulacao = campoTitulacao.getText().trim();
        String salarioTexto = campoSalario.getText().trim();

        if (nome.isEmpty() || titulacao.isEmpty() || salarioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double salario;
        try {
            salario = converterSalario(salarioTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "O salário deve ser um valor numérico válido!\nExemplo: 6200,50",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Professor novoProfessor = new Professor(nome, titulacao, salario);
        professores.add(novoProfessor);
        professorArquivo.salvarTodos(professores);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Altera o registro da linha selecionada na tabela pelos dados dos campos.
     * Verifica se há linha selecionada e se o salário é válido.
     */
    private void alterar() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma linha na tabela para alterar!",
                    "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nome = campoNome.getText().trim();
        String titulacao = campoTitulacao.getText().trim();
        String salarioTexto = campoSalario.getText().trim();

        if (nome.isEmpty() || titulacao.isEmpty() || salarioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double salario;
        try {
            salario = converterSalario(salarioTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "O salário deve ser um valor numérico válido!\nExemplo: 6200,50",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Professor professor = professores.get(linhaSelecionada);
        professor.setNome(nome);
        professor.setTitulacao(titulacao);
        professor.setSalario(salario);

        professorArquivo.salvarTodos(professores);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Exclui o registro da linha selecionada.
     * Verifica se há linha selecionada antes de prosseguir.
     */
    private void excluir() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma linha na tabela para excluir!",
                    "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        professores.remove(linhaSelecionada);
        professorArquivo.salvarTodos(professores);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Atualiza a JTable com os dados atuais da lista em memória.
     */
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Professor professor : professores) {
            modeloTabela.addRow(new Object[]{
                    professor.getNome(),
                    professor.getTitulacao(),
                    professor.getSalario()
            });
        }
    }

    /**
     * Limpa todos os campos de texto após uma operação.
     */
    private void limparCampos() {
        campoNome.setText("");
        campoTitulacao.setText("");
        campoSalario.setText("");
    }
}
