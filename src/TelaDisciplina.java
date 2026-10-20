import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela da aba Disciplinas no JTabbedPane.
 * Contém JTable + campos de texto + botões Adicionar, Alterar e Excluir.
 * Cuida de todo o CRUD da entidade Disciplina, usando DisciplinaArquivo para persistência.
 *
 * O campo carga horária é numérico (int). Se o usuário digitar algo que não é
 * um número inteiro, trata NumberFormatException com JOptionPane, sem gravar.
 */
public class TelaDisciplina extends JPanel {

    private JTextField campoNome;
    private JTextField campoCargaHoraria;
    private JTextField campoPeriodo;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ArrayList<Disciplina> disciplinas;
    private DisciplinaArquivo disciplinaArquivo;

    public TelaDisciplina() {
        disciplinaArquivo = new DisciplinaArquivo();
        disciplinas = disciplinaArquivo.listarTodos();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel de campos ---
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        painelCampos.setBorder(BorderFactory.createTitledBorder("Dados da Disciplina"));

        painelCampos.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelCampos.add(campoNome);

        painelCampos.add(new JLabel("Carga Horária:"));
        campoCargaHoraria = new JTextField();
        painelCampos.add(campoCargaHoraria);

        painelCampos.add(new JLabel("Período:"));
        campoPeriodo = new JTextField();
        painelCampos.add(campoPeriodo);

        add(painelCampos, BorderLayout.NORTH);

        // --- Tabela ---
        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Carga Horária", "Período"}, 0) {
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
                    Disciplina disciplina = disciplinas.get(linhaSelecionada);
                    campoNome.setText(disciplina.getNome());
                    campoCargaHoraria.setText(String.valueOf(disciplina.getCargaHoraria()));
                    campoPeriodo.setText(disciplina.getPeriodo());
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
     * Adiciona uma nova disciplina com os dados dos campos.
     * Nenhum campo pode ficar vazio. A carga horária deve ser um número inteiro válido.
     */
    private void adicionar() {
        String nome = campoNome.getText().trim();
        String cargaTexto = campoCargaHoraria.getText().trim();
        String periodo = campoPeriodo.getText().trim();

        if (nome.isEmpty() || cargaTexto.isEmpty() || periodo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(cargaTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "A carga horária deve ser um número inteiro válido!\nExemplo: 60",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Disciplina novaDisciplina = new Disciplina(nome, cargaHoraria, periodo);
        disciplinas.add(novaDisciplina);
        disciplinaArquivo.salvarTodos(disciplinas);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Altera o registro da linha selecionada na tabela pelos dados dos campos.
     * Verifica se há linha selecionada e se a carga horária é válida.
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
        String cargaTexto = campoCargaHoraria.getText().trim();
        String periodo = campoPeriodo.getText().trim();

        if (nome.isEmpty() || cargaTexto.isEmpty() || periodo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(cargaTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "A carga horária deve ser um número inteiro válido!\nExemplo: 60",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Disciplina disciplina = disciplinas.get(linhaSelecionada);
        disciplina.setNome(nome);
        disciplina.setCargaHoraria(cargaHoraria);
        disciplina.setPeriodo(periodo);

        disciplinaArquivo.salvarTodos(disciplinas);
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

        disciplinas.remove(linhaSelecionada);
        disciplinaArquivo.salvarTodos(disciplinas);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Atualiza a JTable com os dados atuais da lista em memória.
     */
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Disciplina disciplina : disciplinas) {
            modeloTabela.addRow(new Object[]{
                    disciplina.getNome(),
                    disciplina.getCargaHoraria(),
                    disciplina.getPeriodo()
            });
        }
    }

    /**
     * Limpa todos os campos de texto após uma operação.
     */
    private void limparCampos() {
        campoNome.setText("");
        campoCargaHoraria.setText("");
        campoPeriodo.setText("");
    }
}
