import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

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

        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Carga Horária", "Período"}, 0) {
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
                    if (linha >= 0 && linha < disciplinas.size()) {
                        Disciplina disciplina = disciplinas.get(linha);
                        campoNome.setText(disciplina.getNome());
                        campoCargaHoraria.setText(String.valueOf(disciplina.getCargaHoraria()));
                        campoPeriodo.setText(disciplina.getPeriodo());
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
                adicionar();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterar();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);

        add(painelBotoes, BorderLayout.SOUTH);
    }

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

        Disciplina nova = new Disciplina(nome, cargaHoraria, periodo);
        disciplinas.add(nova);
        disciplinaArquivo.salvarTodos(disciplinas);
        atualizarTabela();
        limparCampos();
    }

    private void alterar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
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

        Disciplina disciplina = disciplinas.get(linha);
        disciplina.setNome(nome);
        disciplina.setCargaHoraria(cargaHoraria);
        disciplina.setPeriodo(periodo);

        disciplinaArquivo.salvarTodos(disciplinas);
        atualizarTabela();
        limparCampos();
    }

    private void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma linha na tabela para excluir!",
                    "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        disciplinas.remove(linha);
        disciplinaArquivo.salvarTodos(disciplinas);
        atualizarTabela();
        limparCampos();
    }

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

    private void limparCampos() {
        campoNome.setText("");
        campoCargaHoraria.setText("");
        campoPeriodo.setText("");
    }
}
