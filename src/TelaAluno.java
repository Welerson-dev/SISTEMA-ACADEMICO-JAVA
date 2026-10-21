import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

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

        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Matrícula", "Cidade"}, 0) {
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
                    if (linha >= 0 && linha < alunos.size()) {
                        Aluno aluno = alunos.get(linha);
                        campoNome.setText(aluno.getNome());
                        campoMatricula.setText(aluno.getMatricula());
                        campoCidade.setText(aluno.getCidade());
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
        String matricula = campoMatricula.getText().trim();
        String cidade = campoCidade.getText().trim();

        if (nome.isEmpty() || matricula.isEmpty() || cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aluno novo = new Aluno(nome, matricula, cidade);
        alunos.add(novo);
        alunoArquivo.salvarTodos(alunos);
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
        String matricula = campoMatricula.getText().trim();
        String cidade = campoCidade.getText().trim();

        if (nome.isEmpty() || matricula.isEmpty() || cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos!",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aluno aluno = alunos.get(linha);
        aluno.setNome(nome);
        aluno.setMatricula(matricula);
        aluno.setCidade(cidade);

        alunoArquivo.salvarTodos(alunos);
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

        alunos.remove(linha);
        alunoArquivo.salvarTodos(alunos);
        atualizarTabela();
        limparCampos();
    }

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

    private void limparCampos() {
        campoNome.setText("");
        campoMatricula.setText("");
        campoCidade.setText("");
    }
}
