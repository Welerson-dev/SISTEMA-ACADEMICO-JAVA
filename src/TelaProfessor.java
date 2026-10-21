import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

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

        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Titulação", "Salário"}, 0) {
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
                    if (linha >= 0 && linha < professores.size()) {
                        Professor professor = professores.get(linha);
                        campoNome.setText(professor.getNome());
                        campoTitulacao.setText(professor.getTitulacao());
                        campoSalario.setText(String.format("%.2f", professor.getSalario()));
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
            salario = Double.parseDouble(salarioTexto.replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "O salário deve ser um valor numérico válido!\nExemplo: 6200,50",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Professor novo = new Professor(nome, titulacao, salario);
        professores.add(novo);
        professorArquivo.salvarTodos(professores);
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
            salario = Double.parseDouble(salarioTexto.replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "O salário deve ser um valor numérico válido!\nExemplo: 6200,50",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Professor professor = professores.get(linha);
        professor.setNome(nome);
        professor.setTitulacao(titulacao);
        professor.setSalario(salario);

        professorArquivo.salvarTodos(professores);
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

        professores.remove(linha);
        professorArquivo.salvarTodos(professores);
        atualizarTabela();
        limparCampos();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Professor professor : professores) {
            modeloTabela.addRow(new Object[]{
                    professor.getNome(),
                    professor.getTitulacao(),
                    String.format("%.2f", professor.getSalario())
            });
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoTitulacao.setText("");
        campoSalario.setText("");
    }
}
