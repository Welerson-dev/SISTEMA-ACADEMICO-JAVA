import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela da aba Turmas no JTabbedPane (desafio opcional, quarta aba).
 * Contém JTable + campos de texto + botões Adicionar, Alterar e Excluir.
 * Cuida de todo o CRUD da entidade Turma, usando TurmaArquivo para persistência.
 *
 * O campo vagas é numérico (int). Se o usuário digitar algo que não é
 * um número inteiro, trata NumberFormatException com JOptionPane, sem gravar.
 */
public class TelaTurma extends JPanel {

    private JTextField campoCodigo;
    private JTextField campoCurso;
    private JTextField campoVagas;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ArrayList<Turma> turmas;
    private TurmaArquivo turmaArquivo;

    public TelaTurma() {
        turmaArquivo = new TurmaArquivo();
        turmas = turmaArquivo.listarTodos();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel de campos ---
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        painelCampos.setBorder(BorderFactory.createTitledBorder("Dados da Turma"));

        painelCampos.add(new JLabel("Código:"));
        campoCodigo = new JTextField();
        painelCampos.add(campoCodigo);

        painelCampos.add(new JLabel("Curso:"));
        campoCurso = new JTextField();
        painelCampos.add(campoCurso);

        painelCampos.add(new JLabel("Vagas:"));
        campoVagas = new JTextField();
        painelCampos.add(campoVagas);

        add(painelCampos, BorderLayout.NORTH);

        // --- Tabela ---
        modeloTabela = new DefaultTableModel(new String[]{"Código", "Curso", "Vagas"}, 0) {
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
                    Turma turma = turmas.get(linhaSelecionada);
                    campoCodigo.setText(turma.getCodigo());
                    campoCurso.setText(turma.getCurso());
                    campoVagas.setText(String.valueOf(turma.getVagas()));
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
     * Adiciona uma nova turma com os dados dos campos.
     * Nenhum campo pode ficar vazio. As vagas devem ser um número inteiro válido.
     */
    private void adicionar() {
        String codigo = campoCodigo.getText().trim();
        String curso = campoCurso.getText().trim();
        String vagasTexto = campoVagas.getText().trim();

        if (codigo.isEmpty() || curso.isEmpty() || vagasTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int vagas;
        try {
            vagas = Integer.parseInt(vagasTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "As vagas devem ser um número inteiro válido!\nExemplo: 45",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Turma novaTurma = new Turma(codigo, curso, vagas);
        turmas.add(novaTurma);
        turmaArquivo.salvarTodos(turmas);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Altera o registro da linha selecionada na tabela pelos dados dos campos.
     * Verifica se há linha selecionada e se as vagas são válidas.
     */
    private void alterar() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma linha na tabela para alterar!",
                    "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = campoCodigo.getText().trim();
        String curso = campoCurso.getText().trim();
        String vagasTexto = campoVagas.getText().trim();

        if (codigo.isEmpty() || curso.isEmpty() || vagasTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int vagas;
        try {
            vagas = Integer.parseInt(vagasTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "As vagas devem ser um número inteiro válido!\nExemplo: 45",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Turma turma = turmas.get(linhaSelecionada);
        turma.setCodigo(codigo);
        turma.setCurso(curso);
        turma.setVagas(vagas);

        turmaArquivo.salvarTodos(turmas);
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

        turmas.remove(linhaSelecionada);
        turmaArquivo.salvarTodos(turmas);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Atualiza a JTable com os dados atuais da lista em memória.
     */
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Turma turma : turmas) {
            modeloTabela.addRow(new Object[]{
                    turma.getCodigo(),
                    turma.getCurso(),
                    turma.getVagas()
            });
        }
    }

    /**
     * Limpa todos os campos de texto após uma operação.
     */
    private void limparCampos() {
        campoCodigo.setText("");
        campoCurso.setText("");
        campoVagas.setText("");
    }
}
