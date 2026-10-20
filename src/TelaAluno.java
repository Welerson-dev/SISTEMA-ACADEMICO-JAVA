import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela da aba Alunos no JTabbedPane.
 * Contém JTable + campos de texto + botões Adicionar, Alterar e Excluir.
 * Cuida de todo o CRUD da entidade Aluno, usando AlunoArquivo para persistência.
 */
public class TelaAluno extends JPanel {

    private JTextField campoNome;
    private JTextField campoMatricula;
    private JTextField campoCidade;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ArrayList<Aluno> alunos;
    private AlunoArquivo alunoArquivo;

    public TelaAluno() {
        alunoArquivo = new AlunoArquivo();
        alunos = alunoArquivo.listarTodos();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel de campos ---
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        painelCampos.setBorder(BorderFactory.createTitledBorder("Dados do Aluno"));

        painelCampos.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelCampos.add(campoNome);

        painelCampos.add(new JLabel("Matrícula:"));
        campoMatricula = new JTextField();
        painelCampos.add(campoMatricula);

        painelCampos.add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        painelCampos.add(campoCidade);

        add(painelCampos, BorderLayout.NORTH);

        // --- Tabela ---
        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Matrícula", "Cidade"}, 0) {
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
                    Aluno aluno = alunos.get(linhaSelecionada);
                    campoNome.setText(aluno.getNome());
                    campoMatricula.setText(aluno.getMatricula());
                    campoCidade.setText(aluno.getCidade());
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
     * Adiciona um novo aluno com os dados dos campos.
     * Nenhum campo pode ficar vazio.
     */
    private void adicionar() {
        String nome = campoNome.getText().trim();
        String matricula = campoMatricula.getText().trim();
        String cidade = campoCidade.getText().trim();

        if (nome.isEmpty() || matricula.isEmpty() || cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aluno novoAluno = new Aluno(nome, matricula, cidade);
        alunos.add(novoAluno);
        alunoArquivo.salvarTodos(alunos);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Altera o registro da linha selecionada na tabela pelos dados dos campos.
     * Verifica se há linha selecionada antes de prosseguir.
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
        String matricula = campoMatricula.getText().trim();
        String cidade = campoCidade.getText().trim();

        if (nome.isEmpty() || matricula.isEmpty() || cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aluno aluno = alunos.get(linhaSelecionada);
        aluno.setNome(nome);
        aluno.setMatricula(matricula);
        aluno.setCidade(cidade);

        alunoArquivo.salvarTodos(alunos);
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

        alunos.remove(linhaSelecionada);
        alunoArquivo.salvarTodos(alunos);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Atualiza a JTable com os dados atuais da lista em memória.
     */
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Aluno aluno : alunos) {
            modeloTabela.addRow(new Object[]{
                    aluno.getNome(),
                    aluno.getMatricula(),
                    aluno.getCidade()
            });
        }
    }

    /**
     * Limpa todos os campos de texto após uma operação.
     */
    private void limparCampos() {
        campoNome.setText("");
        campoMatricula.setText("");
        campoCidade.setText("");
    }
}
