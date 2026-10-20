import javax.swing.*;

/**
 * Classe principal do Sistema Acadêmico.
 * Monta a janela com JTabbedPane contendo as abas de cada entidade.
 *
 * A janela principal só monta o JTabbedPane e põe cada aba dentro dele —
 * ela não conhece CRUD nem arquivo. Quem cuida de cada entidade é a sua
 * própria tela (TelaAluno, TelaProfessor, TelaDisciplina, TelaTurma).
 */
public class SistemaAcademico {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame janela = new JFrame("Sistema Acadêmico");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(750, 520);
            janela.setLocationRelativeTo(null); // Centraliza na tela

            // JTabbedPane — cada aba é a tela de cadastro de uma entidade
            JTabbedPane abas = new JTabbedPane();

            // Três abas obrigatórias
            abas.addTab("Alunos", new TelaAluno());
            abas.addTab("Professores", new TelaProfessor());
            abas.addTab("Disciplinas", new TelaDisciplina());

            // Quarta aba — desafio opcional
            abas.addTab("Turmas", new TelaTurma());

            janela.add(abas);
            janela.setVisible(true);
        });
    }
}
