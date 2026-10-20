import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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

        JPanel painelCampos = criarPainelCampos();
        painelCampos.setBorder(BorderFactory.createTitledBorder(tituloCampos));
        add(painelCampos, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(getColunasTabela(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        atualizarTabela();

        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int linha = tabela.getSelectedRow();
                    if (linha >= 0 && linha < itens.size()) {
                        preencherCampos(itens.get(linha));
                    }
                }
            }
        });

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoAdicionar();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoAlterar();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acaoExcluir();
            }
        });

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    protected boolean validarCamposPreenchidos(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

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

    protected void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (T item : itens) {
            modeloTabela.addRow(converterParaLinhaTabela(item));
        }
    }

    protected abstract JPanel criarPainelCampos();

    protected abstract String[] getColunasTabela();

    protected abstract Object[] converterParaLinhaTabela(T item);

    protected abstract boolean validarCampos();

    protected abstract T criarEntidadeDosCampos() throws NumberFormatException;

    protected abstract void atualizarEntidade(T item) throws NumberFormatException;

    protected abstract void preencherCampos(T item);

    protected abstract void limparCampos();

    protected void tratarErroNumero(NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "Valor numérico inválido!",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
    }
}
