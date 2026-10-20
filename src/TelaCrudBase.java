import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe base abstrata para as telas de cadastro (CRUD) do Sistema Acadêmico.
 *
 * Centraliza e padroniza todo o comportamento comum das abas:
 * - JTable com isCellEditable falso (edição restrita aos campos + botão Alterar)
 * - Carregamento inicial via listarTodos()
 * - Listener de seleção na tabela para preenchimento automático dos campos
 * - Botões padronizados: Adicionar, Alterar e Excluir
 * - Validação de preenchimento obrigatório com JOptionPane
 * - Guarda contra operações sem linha selecionada (getSelectedRow() < 0)
 * - Persistência automática completa (salvarTodos) após cada alteração
 * - Tratamento gracioso de NumberFormatException para campos numéricos
 *
 * @param <T> Tipo da entidade que estende Persistivel
 */
public abstract class TelaCrudBase<T extends Persistivel> extends JPanel {

    protected final JTable tabela;
    protected final DefaultTableModel modeloTabela;
    protected final ArrayList<T> itens;
    protected final ArquivoBase<T> arquivo;

    public TelaCrudBase(ArquivoBase<T> arquivo, String tituloCampos) {
        this.arquivo = arquivo;
        this.itens = arquivo.listarTodos();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Painel superior com campos específicos da entidade
        JPanel painelCampos = criarPainelCampos();
        painelCampos.setBorder(BorderFactory.createTitledBorder(tituloCampos));
        add(painelCampos, BorderLayout.NORTH);

        // 2. Tabela central com células não editáveis
        modeloTabela = new DefaultTableModel(getColunasTabela(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Edição é feita pelos campos + botão Alterar
            }
        };
        tabela = new JTable(modeloTabela);
        atualizarTabela();

        // Listener de seleção: clicar na linha joga os dados nos campos
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0 && linha < itens.size()) {
                    preencherCampos(itens.get(linha));
                }
            }
        });

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // 3. Painel inferior com os botões de ação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        btnAdicionar.addActionListener(e -> acaoAdicionar());
        btnAlterar.addActionListener(e -> acaoAlterar());
        btnExcluir.addActionListener(e -> acaoExcluir());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    /**
     * Auxiliar para checar se campos de texto foram preenchidos.
     */
    protected boolean validarCamposPreenchidos(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fluxo de Adicionar: valida campos vazios, converte entidade, adiciona à lista,
     * salva no arquivo, atualiza a tabela e limpa os campos.
     */
    private void acaoAdicionar() {
        if (!validarCampos()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            T novo = criarEntidadeDosCampos();
            if (novo != null) {
                itens.add(novo);
                arquivo.salvarTodos(itens);
                atualizarTabela();
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            tratarErroNumero(ex);
        }
    }

    /**
     * Fluxo de Alterar: guarda contra nenhuma linha selecionada, valida campos,
     * atualiza o registro, salva no arquivo, atualiza a tabela e limpa os campos.
     */
    private void acaoAlterar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma linha na tabela para alterar!",
                    "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validarCampos()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            T itemExistente = itens.get(linha);
            atualizarEntidade(itemExistente);
            arquivo.salvarTodos(itens);
            atualizarTabela();
            limparCampos();
        } catch (NumberFormatException ex) {
            tratarErroNumero(ex);
        }
    }

    /**
     * Fluxo de Excluir: guarda contra nenhuma linha selecionada, remove o item,
     * reescreve o arquivo com salvarTodos, atualiza a tabela e limpa os campos.
     */
    private void acaoExcluir() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma linha na tabela para excluir!",
                    "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        itens.remove(linha);
        arquivo.salvarTodos(itens);
        atualizarTabela();
        limparCampos();
    }

    /**
     * Atualiza todas as linhas da JTable a partir da lista em memória.
     */
    protected void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (T item : itens) {
            modeloTabela.addRow(converterParaLinhaTabela(item));
        }
    }

    // --- Métodos de customização para as subclasses ---

    protected abstract JPanel criarPainelCampos();

    protected abstract String[] getColunasTabela();

    protected abstract Object[] converterParaLinhaTabela(T item);

    protected abstract boolean validarCampos();

    protected abstract T criarEntidadeDosCampos() throws NumberFormatException;

    protected abstract void atualizarEntidade(T item) throws NumberFormatException;

    protected abstract void preencherCampos(T item);

    protected abstract void limparCampos();

    /**
     * Tratamento personalizável para erros de formatação de número.
     */
    protected void tratarErroNumero(NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "Valor numérico inválido!",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
    }
}
